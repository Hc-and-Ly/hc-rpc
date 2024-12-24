package com.hc.discovery;

import com.hc.ServiceConfig;

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

}
