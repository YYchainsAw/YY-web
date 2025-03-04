package com.YYchainsAw.service;

import com.YYchainsAw.pojo.User;

public interface UserService {

    User login(String account, String password);

    void register(User user);
}
