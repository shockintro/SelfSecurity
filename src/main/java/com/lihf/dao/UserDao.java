package com.lihf.dao;

import com.lihf.entity.User;

public interface UserDao {

    public User findByUserName(String username);
}
