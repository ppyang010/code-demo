package com.code;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * @author ccy
 * @description
 * @time 2019-10-31 11:54
 */
public class ZookeeperDemo {

    public static void main(String[] args) throws Exception {
//        String adr = "192.168.190.103:2181";
        String adr = "127.0.0.1:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(adr, new RetryNTimes(10, 5000));
        client.start();// 连接
        // 获取子节点，顺便监控子节点
        List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println("监控： " + event);
            }
        }).forPath("/");
        System.out.println(children);
        // 创建节点
        String path = "/test_ccy123123";
        String result = client
                //创建节点或设置值
                .create().orSetData()
                .withProtection()
                //节点类型 永久
                .withMode(CreateMode.PERSISTENT)
                //权限,全部
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)

                .forPath(path, "Data123".getBytes());
        System.out.println(result);
        // 设置节点数据
        client.setData().forPath(path, "111".getBytes());
        client.setData().forPath(path, "222".getBytes());
        byte[] pathData = client.getData().forPath(path);
        System.out.println(new String(pathData, StandardCharsets.UTF_8));

        // 删除节点
//        System.out.println(client.checkExists().forPath("/test"));
//        client.delete().withVersion(-1).forPath("/test");
//        System.out.println(client.checkExists().forPath("/test"));
//        client.close();
//        System.out.println("OK！");

        Scanner s = new Scanner(System.in);

        while(true){
            String line = s.nextLine();
            System.out.println(line);
        }
    }

    public static  void demo() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("", 3000, null);
        zooKeeper.create("1",null,null,null);
    }
}
