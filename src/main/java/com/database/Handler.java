package com.database;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Handler implements InvocationHandler {
    private final QueryRunner queryRunner;
    private final Class<?> clazz;

    public Handler(QueryRunner queryRunner, Class<?> clazz) {
        this.queryRunner = queryRunner;
        this.clazz = clazz;
    }


    @Override
    public Object invoke(Object o, Method method, Object[] arg) throws Throwable {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (genericInterfaces[0] instanceof ParameterizedType) {
            Type[] genericTypes = ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments();
            String className = genericTypes[1].toString().split(" ")[1];
            Class<?> clazz = Class.forName(className);
            return queryRunner.process(method, clazz, arg);
        }
        return null;
    }
}