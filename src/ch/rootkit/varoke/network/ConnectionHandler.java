package ch.rootkit.varoke.network;

import ch.rootkit.varoke.utils.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;


public class ConnectionHandler extends ChannelInboundHandlerAdapter{
	
	private Channel channel;
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Varoke.getSessionManager().addConnection(ctx.channel());
		channel = ctx.channel();
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {	
		Varoke.getSessionManager().removeSession(ctx.channel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		e.getCause().printStackTrace();
	}
	
	public Session getConnection(){
		return Varoke.getSessionManager().getSessionByChannel(channel);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
		ByteBuf buffer = (ByteBuf)obj;
			while( buffer.readableBytes() != 0){
				int length = buffer.readInt();
				readMessage(ctx, buffer.readBytes(length));
			}
	}
	
	public void readMessage(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		short Header = buffer.readShort();
		if(Varoke.getGame().getPacketManager().hasEvent(Header)){
			Logger.printIncomingLine(Header, true, getCharFilter(buffer), Varoke.getGame().getPacketManager().get(Header).getClass().getSimpleName());
			if(!Varoke.getGame().getPacketManager().isHandshake(Header) && !Varoke.getSessionManager().getSessionByChannel(ctx.channel()).passedHandshake()){
				Logger.printWarningLine("User sent Non-Handshake Event without even passing the Handshake!");
				return;
			}
			Varoke.getGame().getPacketManager().get(Header).handle(getConnection(), new ClientMessage(Header, buffer));
		}else{
			Logger.printIncomingLine(Header, false, getCharFilter(buffer), "NULL");
		}
	}
	
	public String getCharFilter(ByteBuf buffer) {
		String data = new String(buffer.array());
		String output = "";
		for (char o : data.toCharArray())
		{
			int C = o;
			if (C < 14)
				output += "[" + C + "]";
			else
				output += o;
		}
		return output;
	}
}
