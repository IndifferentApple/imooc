package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/11 20:17
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        System.out.println("xixi");
        simpleAccountRealm.addAccount("Mark","123","admin","user");
    }

    @Test
    public  void testAuthentication(){

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123");
        subject.login(token);

        //账号密码验证
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        //登出验证
//        subject.logout();
//        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //验证是否具备单个角色
        subject.checkRole("admin");
        //验证是否具备多个角色
        subject.checkRoles("admin","user");

    }
    @Test
    public void hehe(){
        System.out.println("hehe");
    }
}
