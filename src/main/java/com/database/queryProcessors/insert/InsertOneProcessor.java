package com.database.queryProcessors.insert;

import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class InsertOneProcessor implements Processor {

    private final Jdbi jdbi;

    public InsertOneProcessor(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String tableName = Utils.getTableName(clazz);
        List<String> fieldsNames = Utils.getFieldNames(clazz);

        String fieldsNamesTemp = "";
        for (String s : fieldsNames) {
            fieldsNamesTemp = fieldsNamesTemp + s + ", ";
        }
        String fields = fieldsNamesTemp.substring(0, fieldsNamesTemp.length() - 2);

        List<Object> arguments = new LinkedList<>();
        for (Object o : args) {
            if (o instanceof String) {
                arguments.add(String.format("'%s'", o));
            } else {
                arguments.add(o);
            }
        }

        String fieldsValuesTemp = "";
        for (Object o : arguments) {
            fieldsValuesTemp = fieldsValuesTemp + o + ", ";
        }
        String fieldsValues = fieldsValuesTemp.substring(0, fieldsValuesTemp.length() - 2);

        String query = String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, fields, fieldsValues);
        return jdbi.withHandle(h -> h.execute(query, args));
    }
}
