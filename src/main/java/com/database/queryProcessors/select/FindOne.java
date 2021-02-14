package com.database.queryProcessors.select;

import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

public class FindOne implements Processor {

    private final Jdbi jdbi;

    public FindOne(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public String getName() {
        return "findOne";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {

                String tableName = Utils.getTableName(clazz);
                String query = String.format("SELECT * FROM %s WHERE id = ?", tableName);
        System.out.println("QUERY: "+query);
                return jdbi.withHandle(h -> h.select(query, args[0]).mapToBean(clazz).findOne().orElse(null));

    }
}
