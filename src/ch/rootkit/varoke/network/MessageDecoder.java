package ch.rootkit.varoke.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;

import ch.rootkit.varoke.Varoke;
public class MessageDecoder
extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
    	in = Varoke.getSessionManager().getSessionByChannel(ctx.channel()).getRC4() == null ? in : Varoke.getSessionManager().getSessionByChannel(ctx.channel()).getRC4().decipher(in);
    	in.markReaderIndex();
        if (in.readableBytes() < 6) {
            return;
        }
        int length = in.readInt();
        if (length > 5120 && length >> 24 != 60) {
            ctx.close();
        }
        if (in.capacity() < length + 2) {
            if (length == 1014001516) {
                in.resetReaderIndex();
                in.readBytes(in.readableBytes());
                ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer("<?xml version=\"1.0\"?>\n  <!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\n  <cross-domain-policy>\n  <allow-access-from domain=\"*\" to-ports=\"1-31111\" />\n  </cross-domain-policy>\u0000", CharsetUtil.UTF_8));
                f.channel().close();
                return;
            }
            in.resetReaderIndex();
            return;
        }
        in.resetReaderIndex();
        ByteBuf read = in.readBytes(length + 4);
        out.add(read);
    }
}

