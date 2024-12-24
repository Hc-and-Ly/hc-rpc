package com.hc;

import com.hc.discovery.RegistryConfig;

/**
 * @author 小盒
 * @verson 1.0
 */
public class Application {
    public static void main(String[] args) {
       //配置项都给reference
        ReferenceConfig<HelloRpc> reference = new ReferenceConfig<>();
        reference.setInterface(HelloRpc.class);


        RpcBootstrap.getInstance()
                    .application("first-rpc-consumer")
                    .registry(new RegistryConfig("zookeeper://192.168.10"))
                    .reference(reference);


        //代理做了些什么 1.连接注册中心 2.拉去服务列表 3.选择一个服务并建立连接 4.发送请求，携带信息（接口名，参数列表，方法的名字）
        //获取代理对象
        HelloRpc helloRpc =  reference.get();
        helloRpc.sayHi("你好");

    }
}
