package com.imooc.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/12 14:02
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public  void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //设置权限开关，默认为false，true才会查询权限数据
        jdbcRealm.setPermissionsLookupEnabled(true);

        //自定义查询语句查询用户
        String sql = "select password from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);
        //自定义查询语句查询用户角色
        String rolesql = "select role_name from test_user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(rolesql);


        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming","654321");
        subject.login(token);

        //账号密码验证
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //验证是否具备单个角色
//        subject.checkRoles("admin");
//        subject.checkRoles("admin","user");
//        //验证是否具备用户删除权限
//        subject.checkPermission("user:delete");

        //验证是否具备单个角色
        subject.checkRole("user");
    }
}
