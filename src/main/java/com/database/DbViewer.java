package com.database;

import java.lang.reflect.Proxy;

public class DbViewer {

    private QueryRunner queryRunner;

    public DbViewer(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public <T> T create(Class<T> clazz) {

        Handler handler = new Handler(queryRunner, clazz);


        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                handler);
    }
}
