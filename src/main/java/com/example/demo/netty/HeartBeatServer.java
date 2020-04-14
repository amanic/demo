package com.example.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author haitao.chen
 * @date 2020/4/14
 */
public class HeartBeatServer {

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
                            //处理空闲状态的处理器，查看在线的channel出否处于空闲状态，
                            //当idle事件触发后，就会传递给管道的下一个handler，通过调用触发下一个handler的userEventTriggered
                            ChannelPipeline pipeline = ch.pipeline().addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    if (evt instanceof IdleStateEvent) {
                                        IdleStateEvent stateEvent = (IdleStateEvent) evt;
                                        String sout = "";
                                        switch (stateEvent.state()) {
                                            case ALL_IDLE:
                                                sout = "所有超时";
                                                ctx.channel().close();
                                                break;
                                            case READER_IDLE:
                                                sout = "读超时";
                                                break;
                                            case WRITER_IDLE:
                                                sout = "写超时";
                                                break;
                                            default:
                                                break;
                                        }

                                        System.out.println(sout);
                                    }
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
