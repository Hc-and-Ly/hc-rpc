package com.hc;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author 小盒
 * @verson 1.0
 */
public class ZookeeperTest {
    ZooKeeper zooKeeper;

    @Before
    public void createZk(){

        // 定义连接参数
        String connectString = "192.168.106.128:2181";
        // 定义超时时间
        int timeout = 10000;
        try {
            // new MyWatcher() 默认的watcher
            zooKeeper = new ZooKeeper(connectString,timeout,new MyWatcher());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testCreatePNode(){
        try {
            String result = zooKeeper.create("/hc", "hello".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//权限和节点类型
            System.out.println("result = " + result);
        } catch (KeeperException  | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(zooKeeper != null){
                    zooKeeper.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    @Test
    public void testDeletePNode(){
        try {
            // version: cas  mysql  乐观锁，  也可以无视版本号  -1
            zooKeeper.delete("/hc",-1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if(zooKeeper != null){
                    zooKeeper.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testExistsPNode(){
        try {
            // version: cas  mysql  乐观锁，  也可以无视版本号  -1
            Stat stat = zooKeeper.exists("/hc", null);

            zooKeeper.setData("/hc","hi".getBytes(),-1);

            // 当前节点的数据版本
            int version = stat.getVersion();
            System.out.println("version = " + version);
            // 当前节点的acl数据版本
            int aversion = stat.getAversion();
            System.out.println("aversion = " + aversion);
            // 当前子节点数据的版本
            int cversion = stat.getCversion();
            System.out.println("cversion = " + cversion);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if(zooKeeper != null){
                    zooKeeper.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testWatcher(){
        try {
            // 以下三个方法可以注册watcher，可以直接new一个新的watcher，
            // 也可以使用true来选定默认的watcher
            zooKeeper.exists("/hc", true);
            //            zooKeeper.getChildren();
            //            zooKeeper.getData();

            while(true){
                Thread.sleep(1000);
            }

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if(zooKeeper != null){
                    zooKeeper.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





}
