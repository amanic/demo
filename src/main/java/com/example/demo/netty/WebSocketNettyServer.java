package com.example.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Date;

/**
 *
 * 基于netty的一个webSocket长链接
 * @author haitao.chen
 * @date 2020/4/14
 */
public class WebSocketNettyServer {

    public static void main(String[] args) {

        try {

            //bossGroup和WorkerGroup含有的子线程（NioEventLoop）的个数，默认是cpu的核数*2
            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

            NioEventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器端的通道
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //因为是基于http实现的，所以需要加入http的编解码器
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            //是以块的方式读写，
                            pipeline.addLast(new ChunkedWriteHandler());
                            //http是分段提交的，用来将多段聚合，拆包粘包？
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //对于websocket，数据是以帧的形式传递的,并且将http协议升级为ws协议
                            //浏览器请求：ws://localhost:8080/xxx
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            pipeline.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                                @Override
                                //textWebSocketFrame表是一个文本帧
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
                                    System.out.println("--->" + textWebSocketFrame.text());
                                    channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("服务器时间：" + new Date() + "--" + textWebSocketFrame.text()));
                                }

                                @Override
                                public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                    ctx.channel().writeAndFlush("你上线了");
                                }


                                @Override
                                public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("掉线：" + ctx.channel().id().asLongText());
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                }
                            });
                        }
                    });

            ChannelFuture sync = bootstrap.bind(8080).sync();

            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("成功");
                    } else {
                        System.out.println("失败");
                    }
                }
            });
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
