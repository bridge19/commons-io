package io.bridge.common.redis;

import com.timevale.framework.tedis.Bootstrap;
import com.timevale.framework.tedis.config.DefaultTedisConfig;
import com.timevale.framework.tedis.core.Tedis;
import com.timevale.framework.tedis.util.TedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "tedis.enabled", havingValue = "true")
public class RedisConfig {

    @Value("${tedis-redis-type:stand-alone}")
    String redisMode;
    @Value("${tedis-host:localhost}")
    String host;
    @Value("${tedis-port:6379}")
    String port;
    @Value("${tedis-password:empty}")
    String pwd;
    @Value("${tedis-sentinel-master-name:MASTER}")
    String masterName;
    @Value("${tedis-sentinel-address:localhost:6379}")
    String sentinelAddress;
    @Value("${tedis-cluster-node-address:localhost:6379}")
    String clusterAddress;
    @Value("${tedis-value-threshold:1024}")
    String tedisValueThreshold;


    @Bean
    public Tedis getTedisUtil(){
        DefaultTedisConfig config = new DefaultTedisConfig();
        if("stand-alone".equals(redisMode)) {
            config.setHost(host);
            config.setPort(port);
            if(!"empty".equals(pwd)){
                config.setPassword(pwd);
            }
        }else if("sentinel".equals(redisMode)){
            config.setRedisSentinelAddress(sentinelAddress);
            config.setRedisSentinelMasterName(masterName);
            config.setRedisSentinelAddress(sentinelAddress);
        }else if("cluster".equals(redisMode)){
            config.setRedisClusterAddress(clusterAddress);
        }
        config.setRedisType(redisMode);
        config.setValueThresholdValue(tedisValueThreshold);
        Tedis tedis = Bootstrap.getInstance().start(config).getTedisProxy();
        TedisUtil.setTedis(tedis);
        return tedis;
    }
}
