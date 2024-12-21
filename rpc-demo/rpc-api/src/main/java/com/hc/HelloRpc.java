package com.hc;

/**
 * @author 小盒
 * @verson 1.0
 */
public interface HelloRpc {
    /**
     * 通用接口 service和client 都需要依赖
     * @param msg 发送的具体消息
     * @return 返回结果
     */
    String sayHi(String msg);
}
