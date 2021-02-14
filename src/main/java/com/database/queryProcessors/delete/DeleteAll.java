package com.database.queryProcessors.delete;

import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

public class DeleteAll implements Processor {

    private final Jdbi jdbi;

    public DeleteAll(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public String getName() {
        return "deleteAll";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String tableName = Utils.getTableName(clazz);
        String query = String.format("DELETE FROM %s", tableName);
        return jdbi.withHandle(h -> h.execute(query));
    }
}
