package com.database;

import com.database.queryProcessors.Processor;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;
import java.util.Map;

public class JdbiQueryRunner extends QueryRunner {

    private final Jdbi jdbi;

    public JdbiQueryRunner(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    protected Processor getProcessor(Method method) {
        String name = method.getName();
        Map<String, Processor> processors = getProcessors();
        for (String s : processors.keySet()) {
            if (name.startsWith(s)) {
                Processor processor = processors.get(s);
                if (processor != null) {
                    return processor;
                }
            }
        }
        throw new RuntimeException("No such processor for " + name);
    }
}
