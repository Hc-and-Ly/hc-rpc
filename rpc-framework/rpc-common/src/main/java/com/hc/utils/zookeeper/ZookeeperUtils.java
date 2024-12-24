package com.hc.utils.zookeeper;

import com.hc.Constant;
import com.hc.exceptions.ZookeeperException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author 小盒
 * @verson 1.0
 */
@Slf4j
public class ZookeeperUtils {
    /**
     * 创建zookeeper
     *
     * @return
     */
    public static ZooKeeper createZookeeper() {
        // 定义连接参数
        String connectString = Constant.DEFAULT_ZK_CONNECT;
        // 定义超时时间
        int timeout = Constant.TIME_OUT;
        return createZookeeper(connectString, timeout);
    }

    public static ZooKeeper createZookeeper(String connectString, int timeout) {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        try {
            // new MyWatcher() 默认的watcher
            final ZooKeeper zooKeeper = new ZooKeeper(connectString, timeout, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    System.out.println("客户端已经连接成功");
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            return zooKeeper;
        } catch (IOException | InterruptedException e) {
            log.error("创建zookeeper时候产生异常{}", e.getMessage());
            throw new ZookeeperException();
        }
    }


    /**
     * 创建一个节点的工具方法
     *
     * @param zooKeeper  实例
     * @param node       节点
     * @param watcher    watcher机制
     * @param createMode 节点类型
     * @return
     */
    public static Boolean createNode(ZooKeeper zooKeeper, ZookeeperNode node, Watcher watcher, CreateMode createMode) {

        try {
            if (zooKeeper.exists(node.getNodePath(), null) == null) {
                String result = zooKeeper.create(node.getNodePath(), node.getData(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//权限和节点类型
                log.info("持久{}根节点创建完毕", result);
                return true;
            } else {
                if (log.isDebugEnabled()) {
                    log.info("节点{}已经存在", node.getNodePath());
                }
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("创建基础目录产生异常{}", e);
            throw new ZookeeperException();
        }
        return false;
    }

    /**
     * 判断节点是否存在
     * @param zk zookeeper实例
     * @param node 节点
     * @param watcher
     * @return
     */
    public static Boolean exists(ZooKeeper zk,String node, Watcher watcher) {
        try {
            return zk.exists(node,watcher) !=null;
        } catch (KeeperException | InterruptedException e) {
            log.error("判断节点[{}]是否存在发生异常{}", node,e);
            throw new ZookeeperException(e);
        }
    }
    /**
     * 关闭zookeeper的方法
     * @param zooKeeper zooKeeper实例
     */
    public static void close(ZooKeeper zooKeeper){
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            log.error("关闭zookeeper时发生问题：",e);
            throw new ZookeeperException();
        }
    }


}
