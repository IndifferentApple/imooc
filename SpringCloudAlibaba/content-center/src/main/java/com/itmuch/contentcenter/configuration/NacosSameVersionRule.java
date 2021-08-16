package com.itmuch.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/13 21:30
 */
@Slf4j
public class NacosSameVersionRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        // 负载均衡规则：优先选择同集群下，符合metadata的实例
        // 如果没有，就选择所有集群下，符合metadata的实例

        // 1. 查询所有实例 A
        // 2. 筛选元数据匹配的实例 B
        // 3. 筛选出同cluster下元数据匹配的实例 C
        // 4. 如果C为空，就用B
        // 5. 随机选择实例
        try{
            //拿到配置文件中metadata的k值
            String versionName = nacosDiscoveryProperties.getMetadata().get("k");

            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //想要请求的微服务的名称
            String name = loadBalancer.getName();
            //拿到服务发现的相关API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            //1.找到指定服务的所有实例 A
            List<Instance> instances = namingService.selectInstances(name, true);

            //2.过滤出相同集群下的所有实例 B
            List<Instance> sameVersionInstances = instances.stream()
                    .filter(instance -> Objects.equals(instance.getMetadata().get("k"),versionName))
                    .collect(Collectors.toList());
//            Map<String, String> metadata = instances.get(0).getMetadata();

            //3.如果B是空，就用A
            List<Instance> instancesToBeChosen = new ArrayList<>();
            if(CollectionUtils.isEmpty(sameVersionInstances)){
//                instancesToBeChosen = instances;
                log.warn("相同版本user-center不存在");
                return null;
            }
            else {
                instancesToBeChosen = sameVersionInstances;
            }
            //4.基于权重的负载均衡算法，返回1个实例
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToBeChosen);
            log.info("选择的实例是port = {},instance = {}",instance.getPort(),instance);

            return new NacosServer(instance);
        }
        catch(NacosException e) {
            return null;
        }
    }
}
