package com.itmuch.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/12 21:58
 */
//java代码方式配置细粒度负载均衡规则
//@Configuration
//@RibbonClient(name = "user-center",configuration = RibbonConfiguration.class)
//全局配置负载均衡规则
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {
}
