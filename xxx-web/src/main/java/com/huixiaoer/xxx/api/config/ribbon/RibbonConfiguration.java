package com.huixiaoer.xxx.api.config.ribbon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;

/**
 * 
 * Here, we override the IPing and IRule used by the default load balancer. The
 * default IPing is a NoOpPing (which doesn’t actually ping server instances,
 * instead always reporting that they’re stable), and the default IRule is a
 * ZoneAvoidanceRule (which avoids the Amazon EC2 zone that has the most
 * malfunctioning servers, and might thus be a bit difficult to try out in our
 * local environment).
 * 
 */
public class RibbonConfiguration {
    @Autowired  
    private IClientConfig ribbonClientConfig;  
  
      
    @Bean  
    public IRule ribbonRule(IClientConfig config) {            
        return new MyRule();  
    }  

}
