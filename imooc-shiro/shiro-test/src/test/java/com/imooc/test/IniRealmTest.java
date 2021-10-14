package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/12 8:49
 */
public class IniRealmTest {


    @Test
    public  void testAuthentication(){

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123");
        subject.login(token);

        //账号密码验证
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //验证是否具备单个角色
        subject.checkRoles("admin");
        //验证是否具备用户删除权限
        subject.checkPermission("user:update");
    }
}
