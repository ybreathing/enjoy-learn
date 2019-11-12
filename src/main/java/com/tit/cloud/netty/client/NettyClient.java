package com.tit.cloud.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Desc：
 * @Author: zzy
 * @Date 2019/11/12
 */
public class NettyClient {

    private static int port;
    private static String host;


    public static void start(String host, int port) throws InterruptedException {
        //线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端必须启动的
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))/*配置远程服务器的地址*/
                    .handler(new NettyClientHandle());
            ChannelFuture future = bootstrap.connect().sync();/*连接到远程节点，阻塞等待直到连接完成*/
            future.channel().closeFuture().sync(); /*阻塞，直到channel关闭*/
        } finally {
            group.shutdownGracefully().sync();

        }

    }

    public static void main(String[] args) throws InterruptedException {
        start("127.0.0.1", 9090);
    }

}
