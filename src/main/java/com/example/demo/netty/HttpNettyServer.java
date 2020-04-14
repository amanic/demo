package com.example.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author haitao.chen
 * @date 2020/4/13
 */
public class HttpNettyServer {

    public static void main(String[] args) throws Exception {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("codec", new HttpServerCodec())//netty提供的一个http的编解码处理器
                                .addLast("myHandler", new SimpleChannelInboundHandler<HttpObject>() {

                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                        cause.printStackTrace();
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
                                        if (msg instanceof HttpRequest) {
                                            HttpRequest httpRequest = (HttpRequest) msg;
                                            if ("/favicon.ico".equals(new URI(httpRequest.uri()).getPath())) {
                                                return;
                                            }
                                            System.out.println("客户端地址：" + ctx.channel().remoteAddress());
                                            System.out.println("ctx = " + System.identityHashCode(ctx));
                                            System.out.println("ctx.channel() = " + System.identityHashCode(ctx.channel()));
                                            System.out.println("this = "+System.identityHashCode(this));
                                            ByteBuf content = Unpooled.copiedBuffer("我是一个netty的http服务器", CharsetUtil.UTF_8);
                                            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                                            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                                            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                                            response.headers().set(HttpHeaderNames.ACCEPT_CHARSET, CharsetUtil.UTF_8);
                                            ctx.writeAndFlush(response);
                                        }
                                    }
                                });
                    }
                });

        ChannelFuture sync = serverBootstrap.bind(8080).sync();
        sync.channel().closeFuture().sync();
    }


}
