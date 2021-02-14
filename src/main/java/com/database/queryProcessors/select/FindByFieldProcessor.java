package com.database.queryProcessors.select;

import com.database.annotations.Bind;
import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class FindByFieldProcessor implements Processor {

    private final Jdbi jdbi;

    public FindByFieldProcessor(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public String getName() {
        return "findBy";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String tableName = Utils.getTableName(clazz);
        String fieldName=method.getName().replace("findBy", "").toLowerCase();

        Object parameter = args[0];
        if(parameter instanceof String){
            parameter=String.format("'%s'", parameter);
        }

        Annotation[][] parameterAnnotations=method.getParameterAnnotations();
        for(Annotation[] parameterAnnotation : parameterAnnotations){
            for(Annotation annotation : parameterAnnotation){
                if(annotation.annotationType()== Bind.class){
                    Bind bind=(Bind) annotation;
                    fieldName=bind.value();
                }
            }
        }

        String query = String.format("SELECT * FROM %s WHERE %s = %s", tableName, fieldName, parameter);
        return jdbi.withHandle(h -> h.select(query).mapToBean(clazz).list());
    }
}
