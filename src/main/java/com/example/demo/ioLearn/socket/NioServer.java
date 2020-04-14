package com.example.demo.ioLearn.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author haitao.chen
 * @date 2020/4/11
 */
public class NioServer {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建一个selector对象

        Selector selector = Selector.open();

        //绑定一个端口在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //将serverSocketChannel注册到selector中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("没有连接发生");
                continue;
            }

            //有连接事件发生，获取相应事件的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                //获取key
                SelectionKey selectionKey = iterator.next();
                //看看key对应的通道发生的事件
                if (selectionKey.isAcceptable()) {
                    //如果是OP_ACCEPT事件，生成一个SocketChannel
                    SocketChannel acceptChannel = serverSocketChannel.accept();
                    acceptChannel.configureBlocking(false);
                    //将该socketChannel注册到selector
                    //并且给该channel绑定一个buffer
                    acceptChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (selectionKey.isReadable()) {
                    //如果是OP_READ，则是读事件
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer attachment = (ByteBuffer) selectionKey.attachment();

                    socketChannel.read(attachment);
                    System.out.println("客户端发送：" + new String(attachment.array()));


                }
                //手动把当前这个selectionKey从当前集合中移除

                iterator.remove();

            }

        }


    }
}








