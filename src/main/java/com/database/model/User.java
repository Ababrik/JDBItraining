package com.database.model;

import com.database.annotations.FieldName;
import com.database.annotations.NotQueryParam;
import com.database.annotations.TableName;

@TableName("public.\"users\"")
public class User {
    @NotQueryParam
    private long id;

    @FieldName("first_name")
    private String firstName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
