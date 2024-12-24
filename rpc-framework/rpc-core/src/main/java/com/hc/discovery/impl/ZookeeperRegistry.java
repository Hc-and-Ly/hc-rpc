package com.hc.discovery.impl;

import com.hc.Constant;
import com.hc.ServiceConfig;
import com.hc.discovery.AbstractRegistry;
import com.hc.utils.NetUtils;
import com.hc.utils.zookeeper.ZookeeperNode;
import com.hc.utils.zookeeper.ZookeeperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author 小盒
 * @verson 1.0
 */
@Slf4j
public class ZookeeperRegistry extends AbstractRegistry {

    //维护一个zookeeper实例
    private ZooKeeper zooKeeper ;

    public ZookeeperRegistry() {
        this.zooKeeper = ZookeeperUtils.createZookeeper();
    }
    public ZookeeperRegistry(String connectString,int timeout) {
        this.zooKeeper = ZookeeperUtils.createZookeeper(connectString,timeout);
    }

    @Override
    public void register(ServiceConfig<?> service) {
        //服务名称节点
        String parentNode = Constant.BASE_PROVIDERS_PATH+"/"+service.getInterface().getName();

        //这个是一个持久节点
        if(!ZookeeperUtils.exists(zooKeeper,parentNode,null)) {
            ZookeeperNode zookeeperNode = new ZookeeperNode(parentNode,null);
            ZookeeperUtils.createNode(zooKeeper, zookeeperNode, null, CreateMode.PERSISTENT);
        }
        //创建本机临时节点 ip:port
        //服务提供方的端口一般自己设定，我们还需要一个获取ip的方法
        //ip我们通常是需要一个局域网ip，不是127.0.0.1 也不是ipv6
        //192.168.106.1
        //todo: 端口port后续处理
        String node = parentNode+"/"+ NetUtils.getIp()+":"+8080;
        if(!ZookeeperUtils.exists(zooKeeper,node,null)) {
            ZookeeperNode zookeeperNode = new ZookeeperNode(node,null);
            ZookeeperUtils.createNode(zooKeeper, zookeeperNode, null, CreateMode.EPHEMERAL);
        }


        if(log.isDebugEnabled()) {
            log.debug("服务{}，已经被注册：",service);
        }


    }
}
