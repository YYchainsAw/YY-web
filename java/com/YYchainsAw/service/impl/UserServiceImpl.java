package com.YYchainsAw.service.impl;


import com.YYchainsAw.mapper.UserMapper;
import com.YYchainsAw.pojo.User;
import com.YYchainsAw.service.UserService;
import com.YYchainsAw.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public class UserServiceImpl implements UserService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public User login(String account, String password) {
        SqlSession sqlSession = factory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.login(account, password);

        sqlSession.close();

        return user;
    }

    @Override
    public void register(User user) {
        SqlSession sqlSession = factory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        mapper.register(user);

        sqlSession.commit();

        sqlSession.close();
    }
}
