package com.hc;

import com.hc.discovery.RegistryConfig;
import com.hc.impl.HelloRpcImpl;

/**
 * @author 小盒
 * @verson 1.0
 */
public class Application {
    public static void main(String[] args) {

        ServiceConfig<HelloRpc> service = new ServiceConfig<>();
        service.setInterface(HelloRpc.class);
        service.setRef(new HelloRpcImpl());

        //服务提供方，需要注册服务，启动服务
        //1、封装要发布的服务 定义注册中心

        //2、通过启动引导程序，启动服务提供方
        //（1）配置--应用名称--注册中心--序列化协议--压缩方式
        //（2）发布服务
        RpcBootstrap.getInstance()
                .application("first-rpc-provider")
                //配置注册中心
                .registry(new RegistryConfig("zookeeper://192.168.106.128:2181"))
                //序列化协议
                .protocol(new ProtocolConfig("s"))
                // 发布服务
                .publish(service)
                //启动服务
                .start();

        //1、封装要发布的服务
        //1、封装要发布的服务



    }
}
