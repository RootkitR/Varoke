package ch.rootkit.varoke.communication.messages;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class ServerMessage {
	ByteBuf buffer;
	ByteBufOutputStream send;
	private int Id;
	public ServerMessage(int Header) throws Exception {
		Id = Header;
		buffer = Unpooled.buffer();
		send = new ByteBufOutputStream(buffer);
		send.writeInt(0);
		send.writeShort(Id);
	}
	public void writeShort(short i) throws Exception {
		send.writeShort(i);
	}
	public void writeInt(int i) throws Exception{
		send.writeInt(i);
	}
	public void writeInt(boolean b) throws Exception{
		send.writeInt(b ? 1 : 0);
	}
	public void writeLong(long i) throws Exception{
		send.writeLong(i);
	}
	public void writeString(String i) throws Exception{
		send.writeUTF(i);
	}
	public void writeBoolean(boolean i) throws Exception{
		send.writeBoolean(i);
	}
	public void writeByte(int b) throws Exception{
		send.writeByte(b);
	}
	public void send(Channel socket) throws Exception{
			buffer.setInt(0, buffer.writerIndex() - 4);
			socket.write(buffer.copy(), socket.voidPromise());
			socket.flush();
	}
}
