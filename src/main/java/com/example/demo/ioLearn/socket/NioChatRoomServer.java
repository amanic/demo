package com.example.demo.ioLearn.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author haitao.chen
 * @date 2020/4/12
 */
public class NioChatRoomServer {

    public static void main(String[] args) throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int select = selector.select(2000);

            if (select != 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    if (selectionKey.isAcceptable()) {
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);

                        accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println(accept.getRemoteAddress() + "---我上线了");

                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

                        channel.read(byteBuffer);
                        String msg = new String(byteBuffer.array());
                        System.out.println(channel.getRemoteAddress() + "说：" + msg);

                        for (SelectionKey key : selector.keys()) {
                            if (key.equals(selectionKey)) {

                            } else {
                                SocketChannel socketChannel = (SocketChannel) key.channel();

                                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
                            }
                        }

                    }
                    iterator.remove();
                }
            } else {
                System.out.println("");
            }
        }


    }
}
