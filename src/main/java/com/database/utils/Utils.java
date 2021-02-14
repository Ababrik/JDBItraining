package com.database.utils;

import com.database.annotations.FieldName;
import com.database.annotations.NotQueryParam;
import com.database.annotations.TableName;


import java.lang.reflect.Field;

import java.util.LinkedList;
import java.util.List;

public class Utils {

    public static String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        TableName annotation = clazz.getDeclaredAnnotation(TableName.class);
        if (annotation != null) {
            tableName = annotation.value();
        }
        return tableName;
    }


    public static List<String> getFieldNames(Class<?> clazz) {
        List<String> fieldNames = new LinkedList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
                FieldName annot = f.getDeclaredAnnotation(FieldName.class);
                NotQueryParam a=f.getDeclaredAnnotation(NotQueryParam.class);
            if (( a== null) && (annot==null)) {
                fieldNames.add(f.getName());
            }else if(annot != null) {
                    fieldNames.add(annot.value());
                }
            }
        return fieldNames;
    }

   }