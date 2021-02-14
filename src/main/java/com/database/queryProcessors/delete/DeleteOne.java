package com.database.queryProcessors.delete;

import com.database.queryProcessors.Processor;
import com.database.utils.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

public class DeleteOne implements Processor {

    private final Jdbi jdbi;

    public DeleteOne(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public String getName() {
        return "deleteOne";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String tableName = Utils.getTableName(clazz);
        String query = String.format("DELETE FROM %s WHERE id=?", tableName);
        return jdbi.withHandle(h -> h.execute(query, args[0]));
    }
}
