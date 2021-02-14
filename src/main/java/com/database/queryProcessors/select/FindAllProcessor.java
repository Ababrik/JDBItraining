package com.database.queryProcessors.select;

import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

public class FindAllProcessor implements Processor {

    private final Jdbi jdbi;

    @Override
    public String getName() {
        return "findAll";
    }

    public FindAllProcessor(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String tableName = Utils.getTableName(clazz);
        String query = String.format("SELECT * FROM %s", tableName);
        return jdbi.withHandle(h -> h.select(query).mapToBean(clazz).list());
    }
}
