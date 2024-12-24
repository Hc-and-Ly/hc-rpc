package com.hc;

import com.hc.utils.zookeeper.ZookeeperNode;
import com.hc.utils.zookeeper.ZookeeperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.List;


/**
 * @author 小盒
 * @verson 1.0
 */
@Slf4j
public class Application {
    public static void main(String[] args)  {
        // 帮我们创建基础目录
        // yrpc-metadata   (持久节点)
        //  └─ providers （持久节点）
        //  		└─ service1  （持久节点，接口的全限定名）
        //  		    ├─ node1 [data]     /ip:port
        //  		    ├─ node2 [data]
        //            └─ node3 [data]
        //  └─ consumers
        //        └─ service1
        //             ├─ node1 [data]
        //             ├─ node2 [data]
        //             └─ node3 [data]
        //  └─ config


                //创建一个zookeeper实例
                ZooKeeper zooKeeper = ZookeeperUtils.createZookeeper();
                //定义节点和数据
                String basePath = "/rpc-metadata";
                String providesPath = basePath+"/providers";
                String consumersPath = basePath+"/consumers";

                ZookeeperNode baseNode = new ZookeeperNode(basePath,null);
                ZookeeperNode providesNode = new ZookeeperNode(providesPath,null);
                ZookeeperNode consumersNode = new ZookeeperNode(consumersPath,null);

                List.of(baseNode,providesNode,consumersNode).forEach(node -> {

                        ZookeeperUtils.createNode(zooKeeper,node,null,CreateMode.PERSISTENT);
                });

        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
