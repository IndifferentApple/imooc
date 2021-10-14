package com.imooc.test;

import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/10/12 14:59
 */
public class CustomRealmTest {
    @Test
    public  void testAuthentication(){

        CustomRealm customRealm = new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 2.声明CustomRealm使用了Md5加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        // 设置加密次数
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //3.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);

        //账号密码验证
        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        //验证是否具备单个角色
        subject.checkRoles("admin");
        //验证是否具备用户删除权限
        subject.checkPermissions("user:add","user:delete");
    }
}
