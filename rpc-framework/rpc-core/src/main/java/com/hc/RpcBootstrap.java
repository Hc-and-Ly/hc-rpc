package com.hc;

import com.hc.discovery.Registry;
import com.hc.discovery.RegistryConfig;
import com.hc.discovery.impl.ZookeeperRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 小盒
 * @verson 1.0
 */
@Slf4j
public class RpcBootstrap {
    /**
     * -----------------服务提供方的相关api
     */

    //RpcBootstrap是个单例  每个应用程序只有一个实例
    private static RpcBootstrap rpcBootstrap = new RpcBootstrap();

    //定义相关的一些基础配置
    private String applicationName = "default";
    private RegistryConfig registryConfig;
    private ProtocolConfig protocolConfig;
    private int port = 8080;


    //注册中心
    private Registry registry;

    //维护已经发布且暴露的服务列表 key->interface的全限定名 value->ServiceConfig
    private static final Map<String,ServiceConfig<?> > SERVERS_LIST = new ConcurrentHashMap<>(16);


//    private ZooKeeper zooKeeper ;



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
        this.applicationName = applicationName;
        return this;
    }

    /**
     *
     * @param registryConfig
     * @return
     */
    public RpcBootstrap registry(RegistryConfig registryConfig) {
        //维护一个zookeeper实例，但是这样写会将zookeeper和当前工程耦合
        //希望以后可以扩展更多不同的实现
        //尝试使用registryConfig
        this.registry= registryConfig.getRegistry();
        return this;
    }

    /**
     * 序列化协议 协议封装
     * @param protocolConfig
     * @return
     */
    public RpcBootstrap protocol(ProtocolConfig protocolConfig) {
        this.protocolConfig = protocolConfig;
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
        //抽象注册中心的概念，使用注册中心的一个实现完成注册
        //
        registry.register(service);

        //1.当服务调用方，通过接口，方法名，具体的方法参数列表发起调用，提供怎么知道使用哪个
        //（1）new 一个 （2） spring beanFactory.getBean(class) （3） 自己维护映射关系
        SERVERS_LIST.put(service.getInterface().getName(),service);
        return  this;
    }

    /**
     * 批量发布
     * @param services 服务集合
     * @return
     */
    public RpcBootstrap publish(List<ServiceConfig> services) {
        for(ServiceConfig<?> service : services) {
            this.publish(service);
        }


        return  this;
    }

    /**
     * 启动netty服务
     */
    public void start() {
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * -----------------服务调用方放相关api
     */
    public RpcBootstrap reference(ReferenceConfig<?> reference) {
        //这方法里是否可以拿到相关的配置项-注册中心
        //配置reference 调用get（）方便生成代理对象
        //1、reference需要一个注册中心
        reference.setRegistry(registry);

        return this;
    }

}
