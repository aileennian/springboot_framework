package com.huixiaoer.xxx.api.config.ribbon;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.servo.util.Strings;

@RibbonClient(name = "xxx",configuration = UserRibbonConfig.class)
public class UserRibbonConfig {

    String listOfServers = "http://192.168.99.100:8080,http://192.168.99.101:8080";
    @Bean
    public ServerList<Server> ribbonServerList() {
        List<Server> list = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(listOfServers)) {
            for (String s: listOfServers.split(",")) {
                list.add(new Server(s.trim()));
            }
        }
        return null;
    }
}
