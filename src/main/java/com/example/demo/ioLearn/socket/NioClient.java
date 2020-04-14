package com.example.demo.ioLearn.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author haitao.chen
 * @date 2020/4/11
 */
public class NioClient {

    public static void main(String[] args) throws Exception{
        //得到一个网络通道

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);



        InetSocketAddress inetSocketAddress = new InetSocketAddress("", 6666);

        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端是不会阻塞的，还可以做其他工作...");
            }
        }

        String str = "生活的理想是为了理想的生活！";

        ByteBuffer wrappedBuffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(wrappedBuffer);

    }
}
