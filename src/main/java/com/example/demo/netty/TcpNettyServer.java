package com.example.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author haitao.chen
 * @date 2020/4/13
 */
public class TcpNettyServer {


    public static void main(String[] args) {

        try {

            //bossGroup和WorkerGroup含有的子线程（NioEventLoop）的个数，默认是cpu的核数*2
            NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

            NioEventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器端的通道
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    ctx.executor().execute();
//                                    ctx.channel().eventLoop().execute();
//                                    ctx.channel().eventLoop().schedule()
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    System.out.println("----->" + byteBuf.toString(CharsetUtil.UTF_8));
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println("服务器发送消息...");
                                    String msg = "收到了";
                                    ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
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
