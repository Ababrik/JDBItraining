package com.database;

import com.database.queryProcessors.Processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class QueryRunner {


    private final Map<String, Processor> processors = new HashMap<>();

    public Map<String, Processor> getProcessors() {
        return processors;
    }

    protected final void addProcessor(Processor processor) {
        processors.put(processor.getName(), processor);
    }

    protected abstract Processor getProcessor(Method method);


    protected Object process(Method method, Class<?> clazz, Object[] args) {
        Processor processor = getProcessor(method);
        return processor.process(method, clazz, args);
    }


}
