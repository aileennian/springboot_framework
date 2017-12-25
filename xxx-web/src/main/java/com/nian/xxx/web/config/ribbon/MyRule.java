package com.nian.xxx.web.config.ribbon;

import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;  
  
import com.netflix.client.config.IClientConfig;  
import com.netflix.loadbalancer.AbstractLoadBalancerRule;  
import com.netflix.loadbalancer.ILoadBalancer;  
import com.netflix.loadbalancer.Server;  

/** 
 * 自定义rule插件 
 *  
 * @author zhu yang 
 * 
 */  
public class MyRule extends AbstractLoadBalancerRule {  
    /** 
     * 选择能被2整除的server 
     *  
     * @param lb 
     * @param key 
     * @return 
     */  
	public Server choose(ILoadBalancer lb, Object key) {  
        if (lb == null) {  
            return null;  
        }  
        Server server = null;  
  
        while (server == null) {  
            if (Thread.interrupted()) {  
                return null;  
            }  
            List<Server> upList = lb.getReachableServers();  
            System.out.println("upList=" + upList);  
            List<Server> allList = lb.getAllServers();  
            System.out.println("allList=" + allList);  
            int serverCount = allList.size();  
            if (serverCount == 0) {  
                /* 
                 * No servers. End regardless of pass, because subsequent passes 
                 * only get more restrictive. 
                 */  
                return null;  
            }  
            if (serverCount < 2) {  
                server = upList.get(0);// if only 1 server. return  
                return server;  
            }  
            List<Server> newList = new ArrayList<Server>();  
            for(int i=0;i<upList.size();i++){//create a new list to store the server index %2==0  
                if(i%2==0){  
                    newList.add(upList.get(i));  
                }  
            }  
            Random rand=new Random();  
            int newListCount=newList.size();  
            int index = rand.nextInt(newListCount);  
            server = newList.get(index);  
  
            if (server == null) {  
                /* 
                 * The only time this should happen is if the server list were 
                 * somehow trimmed. This is a transient condition. Retry after 
                 * yielding. 
                 */  
                Thread.yield();  
                continue;  
            }  
  
            if (server.isAlive()) {  
                return (server);  
            }  
  
            // Shouldn't actually happen.. but must be transient or a bug.  
            server = null;  
            Thread.yield();  
        }  
  
        return server;  
  
    }  
  
    @Override  
    public Server choose(Object key) {  
        // TODO Auto-generated method stub  
        return choose(getLoadBalancer(), key);  
    }  
  
    @Override  
    public void initWithNiwsConfig(IClientConfig clientConfig) {  
        // TODO Auto-generated method stub  
  
    }  
  
}  
