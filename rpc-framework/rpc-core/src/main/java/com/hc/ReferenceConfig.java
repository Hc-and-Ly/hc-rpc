package com.hc;

import com.hc.discovery.Registry;
import com.hc.discovery.RegistryConfig;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.PSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author 小盒
 * @verson 1.0
 */
@Slf4j
public class ReferenceConfig<T> {
    private Class<T> interfaceRef;
    private Registry registry;

    public void setInterfaceRef(Class<T> interfaceRef) {
        this.interfaceRef = interfaceRef;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Class<T> getInterfaceRef() {
        return interfaceRef;
    }




    public void setInterface(Class<T> interfaceRef) {
        this.interfaceRef = interfaceRef;
    }

    /**
     * 代理设计模式，生成一个api接口的代理对象
     * @return
     */
    public T get() {
        //动态代理
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class[] classes = new Class[]{interfaceRef};

        //动态代理，生成代理对象
        Object helloProxy = Proxy.newProxyInstance(classLoader, classes, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //我们调用sayhi方法，事实上会走进这个代码段，
                //我们知道method，args
                System.out.println("hello proxy");
                //1.发现服务，从注册中心寻找一个可用服务
                //传入服务的名字 返回ip+port
                //todo 我们每次调用相关方法的时候都需要去注册中心拉取服务列表吗？本地缓存+watcher
                //     我们如何合理的选择一个可用的服务，而不是只获取第一个
               InetSocketAddress address =  registry.lookup(interfaceRef.getName());
               if(log.isDebugEnabled()) {
                   log.debug("服务调用方，发现了服务【{}】的可用主机【{}】", interfaceRef.getName(), address);
               }

                //2.使用netty连接服务器，发送调用的服务的名字+方法的名字+参数列表 得到结果，


                return method.invoke(interfaceRef, args);
            }
        });

        return (T)helloProxy;
    }
}
