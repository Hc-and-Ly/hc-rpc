package com.hc;

import com.hc.netty.AppClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author 小盒
 * @verson 1.0
 */
public class NettyTest {

    @Test
    public void testByteBuf() {
        ByteBuf byteBuf = null;

    }
    @Test
    public void testMessage() throws IOException {
        //封装报文
        ByteBuf message = Unpooled.buffer();
        message.writeBytes("hello world".getBytes(StandardCharsets.UTF_8));
        message.writeByte(1);
        message.writeShort(125);
        message.writeInt(256);
        message.writeByte(1);
        message.writeByte(0);
        message.writeByte(2);
        message.writeLong(251455L);

        //用对象流转化为字节数据 需要序列化
        AppClient appClient = new AppClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(appClient);
        byte[] bytes = outputStream.toByteArray();
        message.writeBytes(bytes);

    }

    @Test
    public void testCompress() throws IOException {
        byte[] buf = new byte[]{12,12,12,12,12,25,34,23,25,14,12,12,12,12,12,25,34,23,25,14,12,12,12,12,12,25,34,23,25,14,12,12,12,12,12,25,34,23,25,14};
        //本质就是 buf作为输入，将结果输出到另一个字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos);
        gzipOutputStream.write(buf);
        gzipOutputStream.finish();
        byte[] bytes = baos.toByteArray();
        System.out.println(buf.length);

    }

    @Test
    public void testDeCompress() throws IOException {
        byte[] buf = new byte[]{12,12,12,12,12,25,34,23,25,14,12,12,12,12,12,25,34,23,25,14,12,12,12,12,12,25,34,23,25,14,12,12,12,12,12,25,34,23,25,14};


        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        GZIPInputStream gzipInputStream = new GZIPInputStream(bais);

        byte[] bytes = gzipInputStream.readAllBytes();
        System.out.println(buf.length + "-->" + bytes.length);
        System.out.println(Arrays.toString(bytes));



    }




}
