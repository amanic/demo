package com.example.demo.ioLearn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;

/**
 *
 * @author haitao.chen
 * @date 2020/4/10
 */
public class BasicBuffer {

    public static void main(String[] args) throws Exception {
        writeToFile();
    }

    /**
     * 最简单的关于buffer 的使用
     */
    public static void test() {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < 5; i++) {
            intBuffer.put(i * 2);
        }
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }


    /**
     * 本地文件写数据，通过channel和buffer
     */
    public static void writeToFile() throws Exception {
        String str = "gv8sdrbpadsrgur";
        //创建一个输出流

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/martea/Desktop/a.txt");
        FileChannel channel = fileOutputStream.getChannel();


        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        //为了将buffer里面的数据写入到通道
        buffer.flip();

        channel.write(buffer);
        fileOutputStream.close();


    }

    /**
     * 读取文件
     * @throws Exception
     */
    public static void readFromFile() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("");

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer);

        System.out.println(new String(buffer.array()));

    }

    /**
     * 仅使用一个Buffer，进行文件的读与写，即拷贝
     */
    public static void readAndWrite() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("");
        FileOutputStream fileOutputStream = new FileOutputStream("");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = inputChannel.read(byteBuffer);
        while (read != -1) {

            byteBuffer.flip();
            outputChannel.write(byteBuffer);

            //这里的clear很重要，不然read就会一直为0而进入死循环
            byteBuffer.clear();
            read = inputChannel.read(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }


    /**
     * 使用channel的transTO和transFrom，是不是涉及到了零拷贝？
     */
    public static void transFile() throws IOException {

        FileInputStream fileInputStream = new FileInputStream("");
        FileOutputStream fileOutputStream = new FileOutputStream("");


        FileChannel inputChannel = fileInputStream.getChannel();

        FileChannel outputChannel = fileOutputStream.getChannel();


        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        fileInputStream.close();

        fileOutputStream.close();

    }


    /**
     * 可以让文件直接在内存（堆外内存）中修改，无需拷贝
     */
    public static void testMappedBuffer() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("", "rw");
        FileChannel rwChannel = rw.getChannel();

        /**
         * 操作文件的模式
         * 可直接修改的起始位置
         * 映射到内存的大小，即将文件的多少内容映射到内存
         */
        MappedByteBuffer mapByteBuffer = rwChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mapByteBuffer.put(0, "123".getBytes()[0]);
        mapByteBuffer.put(0, (byte) 'H');
    }


    /**
     * 测试Buffer的分散以及聚合
     */
    public static void testScatAndGather()  throws Exception{
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        socketChannel.socket().bind(inetSocketAddress);

        socketChannel.accept();
    }

}
