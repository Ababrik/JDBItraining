package com.database.repos;


import com.database.annotations.Bind;
import com.database.model.User;

import java.util.List;

public interface UserRepository extends Repository<Integer, User> {

    List<User> findByName(@Bind("first_name") String name);

    int insertUser(@Bind("first_name") String name);


}

