package com.database.model;

import com.database.annotations.FieldName;
import com.database.annotations.NotQueryParam;
import com.database.annotations.TableName;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@TableName("public.\"deals\"")
public class Deal {

    @NotQueryParam
    private int id;
    private String source;
    private String currency;
    private String amount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getSource() {
        return source;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "source='" + source + '\'' +
                ", currency='" + currency + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
