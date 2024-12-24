package com.hc.discovery;

import com.hc.Constant;
import com.hc.discovery.impl.NacosRegistry;
import com.hc.discovery.impl.ZookeeperRegistry;
import com.hc.exceptions.DiscoveryException;

/**
 * @author 小盒
 * @verson 1.0
 */
public class RegistryConfig {

//    定义连接的url zookeeper://127.0.0.1:2181 redis://192.168.106.1:3306
    private final String connectString;
    public RegistryConfig(String connectString) {

        this.connectString = connectString;
    }

    /**
     * 使用简单工厂
     * @return
     */
    public Registry getRegistry() {
        //1.获取注册中心的类型
        String registryType = getRegistryType(connectString,true).toLowerCase().trim();
        if(registryType.equals("zookeeper")) {
            String host = getRegistryType(connectString,false);
            return new ZookeeperRegistry(host, Constant.TIME_OUT);
        } else if(registryType.equals("nacos")) {
            String host = getRegistryType(connectString,false);
            return new NacosRegistry(host, Constant.TIME_OUT);
        }
        //2.
        throw new DiscoveryException("未发现合适的注册中心 " + registryType);
    }
    private String getRegistryType(String connectString,boolean ifType) {
        String[] typeAndHost = connectString.split("://");
        if(typeAndHost.length != 2) {
            throw new RuntimeException("给定的注册中心连接url不合法.");
        }
        if(ifType) {
            return typeAndHost[0];//拿到 名称（redis or zookeeper）
        } else {
            return typeAndHost[1];
        }
    }


}
