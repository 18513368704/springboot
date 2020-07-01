package com.yy;

import io.netty.buffer.ByteBuf;

import io.netty.channel.*;

import io.netty.util.CharsetUtil;


@ChannelHandler.Sharable
public  class HttpHandler extends SimpleChannelInboundHandler<ByteBuf>  {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("服务器接收内容:"+msg.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(msg);

    }
}
