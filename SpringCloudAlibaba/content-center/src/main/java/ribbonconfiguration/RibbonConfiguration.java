package ribbonconfiguration;

import com.itmuch.contentcenter.configuration.NacosSameClusterWeightedRule;
import com.itmuch.contentcenter.configuration.NacosSameVersionRule;
import com.itmuch.contentcenter.configuration.NacosWeightedRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/12 22:05
 */
@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule(){
//        return new RandomRule();
//        return new NacosWeightedRule();
//        return  new NacosSameClusterWeightedRule();
        return new NacosSameVersionRule();
    }

//    @Bean
//    public IPing ping(){
//        return new PingUrl();
//    }

}
