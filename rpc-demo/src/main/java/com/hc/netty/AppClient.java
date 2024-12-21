package com.hc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author 小盒
 * @verson 1.0
 */
public class AppClient {

    public void run() {
        //定义线程池 EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        //启动一个客户端需要辅助类bootstrp
        Bootstrap bootstrap = new Bootstrap();
       bootstrap = bootstrap.group(group)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
                .channel(NioSocketChannel.class)//初始化channel
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MyChannerHandLer2());
                    }
                });
        //尝试连接服务器 同步等待
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect().sync();
            //获取channel并且写出数据
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("hello netty".getBytes(Charset.forName("UTF-8"))));
            //阻塞程序，等待接收消息
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {
        new AppClient().run();
    }

}
