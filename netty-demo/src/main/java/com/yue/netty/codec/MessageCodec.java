package com.yue.netty.codec;

import com.yue.netty.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author yueyue
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        //1 4个字节数
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        //2 1个字节版本
        byteBuf.writeByte(1);
        //3 1个字节的序列化方式
        byteBuf.writeByte(1);
        //4 1个字节的指令类型
        byteBuf.writeByte(message.getMessageType());
        //5 4个字节的请求序号
        byteBuf.writeInt(message.getSequenceId());

        byteBuf.writeByte(0xff);
        //6 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();
        //7 长度
        byteBuf.writeInt(bytes.length);
        //8 写入内容
        byteBuf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 取数 魔数
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializerType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byte no = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
        Message message = (Message) ois.readObject();
        log.info("{} {} {} {} {} {} {}", magicNum, version, serializerType, messageType, sequenceId, no, length);

        log.info("{}", message);

        list.add(message);
    }
}
