package com.hc;






import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 小盒
 * @verson 1.0
 */
@Slf4j
public class RpcBootstrap {
    /**
     * -----------------服务提供方的相关api
     */

    private static RpcBootstrap rpcBootstrap = new RpcBootstrap();
    private RpcBootstrap(){

    }
    //单例模式 饿汉式
    public static RpcBootstrap getInstance() {
        return rpcBootstrap;
    }

    /**
     * 定义当前应用的名字
     * @param applicationName 名字
     * @return
     */
    public RpcBootstrap application(String applicationName) {
        return this;
    }

    /**
     *
     * @param registryConfig
     * @return
     */
    public RpcBootstrap registry(RegistryConfig registryConfig) {
        return this;
    }

    /**
     * 序列化协议 协议封装
     * @param protocolConfig
     * @return
     */
    public RpcBootstrap protocol(ProtocolConfig protocolConfig) {
        if(log.isDebugEnabled()) {
            log.debug("当前工程使用了："+protocolConfig.toString()+"进行序列化");
        }
        return this;
    }

    /**
     * 发布服务 将接口实现 注册到服务中心
     * @param service 封装的需要发布的服务
     * @return
     */
    public RpcBootstrap publish(ServiceConfig<?> service) {
        if(log.isDebugEnabled()) {
            log.debug("服务{}，已经被注册：",service);
        }
        return  this;
    }

    /**
     * 批量发布
     * @param services 服务集合
     * @return
     */
    public RpcBootstrap publish(List<?> services) {
        return  this;
    }

    /**
     * 启动netty服务
     */
    public void start() {
    }
    /**
     * -----------------服务提调用放相关api
     */
    public RpcBootstrap reference(ReferenceConfig<?> reference) {
        //这方法里是否可以拿到相关的配置项-注册中心
        //配置reference 调用get（）方便生成代理对象
        return this;
    }

}
