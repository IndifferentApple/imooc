package com.itmuch.usercenter;

import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/9 2:24
 */
@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/test")
    public User testInsert(){
        User user = new User();
        user.setAvatarUrl("xxx");
        user.setBonus(100);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
//        insert() 为所有字段插入 insertSelective() 只为有值的字段插入
        this.userMapper.insertSelective(user);
        return user;
    }
}
