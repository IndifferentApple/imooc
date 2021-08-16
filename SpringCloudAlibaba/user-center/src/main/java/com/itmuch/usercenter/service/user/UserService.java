package com.itmuch.usercenter.service.user;

import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/10 19:15
 */
@Service
/**
 * @RequiredArgsConstructor(onConstructor = @_(@Autowired))
 * 1. 含义
 * @NoArgsConstructor后会 生成无参的构造方法，
 * @RequiredArgsConstructor会将类的每一个final字段或者non-null字段生成一个构造方法 ，
 * @AllArgsConstructor 生成一个包含过所有字段的构造方法。

 * 2. 用法
 * @RequiredArgsConstructor(onConstructor =@_(@Autowired)) 写在类上可以代替@AutoWired注解，
 */
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserService {
    private final UserMapper userMapper;

    public User findById(Integer id){
        //select * from user where id = #{id}
        return this.userMapper.selectByPrimaryKey(id);
    }
}
