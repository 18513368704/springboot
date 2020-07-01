package com.yy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;


public class HttpServer {
    private final  int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public static  void main(String[] args)  {
        try {
            int port = Integer.parseInt("9090");
            new HttpServer(port).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(group)                                //4
                    .channel(NioServerSocketChannel.class)        //5
                    .localAddress(new InetSocketAddress(port))    //6
                    .childHandler(new ChannelInitializer<SocketChannel>() { //7
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast( "stringDecoder", new StringDecoder(CharsetUtil.UTF_8))
                                    .addLast( "stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                                    .addLast(new HttpHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)// determining the number of connections queued
                    .childOption(ChannelOption.SO_TIMEOUT, 3000);//maxwaiting one thread  millisecond  times


            ChannelFuture future = b.bind(port).sync();

            System.out.println(HttpServer.class.getName() + " started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            group.shutdownGracefully().sync();
        }
    }
}
