package com.database.queryProcessors.update;

import com.database.annotations.Bind;
import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class UpdateOneProcessor implements Processor {
    private final Jdbi jdbi;

    public UpdateOneProcessor(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String tableName = Utils.getTableName(clazz);

        String editableFieldNameTemp = method.getName().replace("edit", "");
        String editableFieldName = editableFieldNameTemp.substring(0, editableFieldNameTemp.lastIndexOf("By")).toLowerCase();
        Object parameter_valueToSet = args[0];
        if (parameter_valueToSet instanceof String) {
            parameter_valueToSet = String.format("'%s'", parameter_valueToSet);
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType() == Bind.class) {
                    Bind bind = (Bind) annotation;
                    editableFieldName = bind.value();
                }
            }
        }

        String conditionalFiledName = method.getName().substring(method.getName().indexOf("By") + 2).toLowerCase();

        Object conditionalParam=args[1];

        if (conditionalParam   instanceof String)
            conditionalParam = String.format("'%s'", conditionalParam);
        Annotation[][] conditionalParameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : conditionalParameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation.annotationType() == Bind.class) {
                    Bind bind = (Bind) annotation;
                    conditionalFiledName = bind.value();
                }
            }
        }

        String query = String.format("UPDATE %s SET %s=%s WHERE %s=%s", tableName, editableFieldName, parameter_valueToSet, conditionalFiledName, conditionalParam);
        System.out.println("QUERY: " + query);
        return jdbi.withHandle(h -> h.execute(query, args));
    }
}
