package com.YYchainsAw.mapper;

import com.YYchainsAw.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;


public interface UserMapper {

    @Select("select * from tb_user where account = #{account} and password = #{password}")
    @ResultMap("UserResultMapper")
    User login(@Param("account") String account, @Param("password") String password);

    void register(User user);
}
