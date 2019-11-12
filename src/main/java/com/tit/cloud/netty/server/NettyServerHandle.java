package com.tit.cloud.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * netty服务端事务处理
 *
 * @author : zzy
 * @date : 2019-11-12 10:25
 **/

@ChannelHandler.Sharable
public class NettyServerHandle extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Netty", CharsetUtil.UTF_8));
    }

    /*** 服务端读取到网络数据后的处理*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server Accept:" + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
        ByteBufAllocator allocator = ctx.alloc();
        allocator.buffer();
        ctx.channel().alloc();
        ByteBuf buffer = Unpooled.buffer();
        Unpooled.directBuffer();
    }


    /*** 服务端读取完成网络数据后的处理*/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)/*flush掉所有的数据*/
                .addListener(ChannelFutureListener.CLOSE);/*当flush完成后，关闭连接*/
    }


    /**
     * 捕获异常后处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭上下文
        cause.printStackTrace();
        ctx.close();
    }
}
