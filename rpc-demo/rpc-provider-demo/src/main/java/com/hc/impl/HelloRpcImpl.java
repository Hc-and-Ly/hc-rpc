package com.hc.impl;

import com.hc.HelloRpc;

/**
 * @author 小盒
 * @verson 1.0
 */
public class HelloRpcImpl implements HelloRpc {
    @Override
    public String sayHi(String msg) {
        return "hi consumer:" + msg;
    }
}
