package com.database.repos;

import java.util.List;

public interface Repository<S, T> {

    T findOne(S id);

    List<T> findAll();

    int deleteOne(S id);

    int deleteAll();



}
