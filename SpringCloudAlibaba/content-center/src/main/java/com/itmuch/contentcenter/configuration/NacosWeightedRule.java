package com.itmuch.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/13 15:31
 */
@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        //读取配置文件，并初始化NacosWeightedRule
    }

    @Override
    public Server choose(Object o) {
//        ILoadBalancer iLoadBalancer = this.getLoadBalancer();
//        log.info("lb = {}",iLoadBalancer);

        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();

            //想要请求的微服务的名称
            String name = loadBalancer.getName();

            //拿到服务发现的相关API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            //nacos client 自动通过基于权重的负载均衡算法，给我们一个实例
            Instance instance = namingService.selectOneHealthyInstance(name);

            log.info("选择的实例是:port = {}, instance = {}",instance.getPort(),instance);

            return new NacosServer(instance);
        } catch (NacosException e) {
            return null;
        }
    }
}
