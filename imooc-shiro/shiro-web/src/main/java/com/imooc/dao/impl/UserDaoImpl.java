package com.imooc.dao.impl;

import com.imooc.dao.UserDao;
import com.imooc.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/12 19:44
 */
@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUserName(String userName) {
        String sql = "select username,password from users where username=?";
        List<User> list= jdbcTemplate.query(sql,new String[]{userName},new RowMapper<User>(){

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        if((CollectionUtils.isEmpty(list))){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> getRolesByUserName(String userName) {
        String sql = "select role_name from user_roles where username = ?";
        return jdbcTemplate.query(sql,new String[]{userName},new RowMapper<String>(){

            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");

            }
        });
    }

    @Override
    public List<String> getPermissionsByUserName(String userName) {
        String sql = "select permission from roles_permissions LEFT JOIN  user_roles on user_roles.role_name=roles_permissions.role_name where user_roles.username = ?";
        return jdbcTemplate.query(sql,new String[]{userName},new RowMapper<String>(){

            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");

            }
        });
    }
}
