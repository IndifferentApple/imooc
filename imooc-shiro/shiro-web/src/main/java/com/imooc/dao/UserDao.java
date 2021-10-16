package com.imooc.dao;

import com.imooc.vo.User;

import java.util.List;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/12 19:43
 */
public interface UserDao {
    User getUserByUserName(String userName);

    List<String> getRolesByUserName(String userName);

    List<String> getPermissionsByUserName(String userName);
}
