package com.hc.discovery;

import com.hc.ServiceConfig;

import java.net.InetSocketAddress;

/**
 * 思考注册中心应该具有什么能力
 * @author 小盒
 * @verson 1.0
 */
public interface Registry {

    /**
     *注册服务
     * @param serviceConfig 服务的配置内容
     */
    void register(ServiceConfig<?> serviceConfig);

    /**
     * 从注册中心拉取一个可用的服务
     * @param serviceName 服务的名称
     * @return 服务的ip+端口
     */
    InetSocketAddress lookup(String serviceName);
}
