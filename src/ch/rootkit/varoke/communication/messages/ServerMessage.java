package ch.rootkit.varoke.communication.messages;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class ServerMessage {
	ByteBuf buffer;
	ByteBufOutputStream send;
	private int Id;
	
	public ServerMessage(int Header) throws IOException {
		Id = Header;
		buffer = Unpooled.buffer();
		send = new ByteBufOutputStream(buffer);
		send.writeInt(0);
		send.writeShort(Id);
	}
	
	/**
	 * writes a short to the buffer
	 * @param i the short
	 * @throws IOException if something goes wrong!
	 */
	public void writeShort(short i) throws IOException{
		send.writeShort(i);
	}
	
	/**
	 * writes an integer to the buffer
	 * @param i the integer
	 * @throws IOException if something goes wrong!
	 */
	public void writeInt(int i) throws IOException{
		send.writeInt(i);
	}
	
	/**
	 * if the boolean is true it writes an 1 to the buffer (if not it's a 0)
	 * @param b the boolean
	 * @throws IOException if something goes wrong!
	 */
	public void writeInt(boolean b) throws IOException{
		send.writeInt(b ? 1 : 0);
	}
	
	/**
	 * writes a very long number to the buffer
	 * @param i the long number
	 * @throws IOException if something goes wrong!
	 */
	public void writeLong(long i) throws IOException{
		send.writeLong(i);
	}
	
	/**
	 * writes a UTF-String to the buffer
	 * @param i the string
	 * @throws IOException if something goes wrong!
	 */
	public void writeString(String i) throws IOException{
		send.writeUTF(i);
	}
	
	/**
	 * writes a boolean to the buffer (NOT AN INTEGER)
	 * @param i the boolean
	 * @throws IOException if something goes wrong!
	 */
	public void writeBoolean(boolean i) throws IOException{
		send.writeBoolean(i);
	}
	
	/**
	 * writes a byte to the buffer
	 * @param b the byte
	 * @throws IOException if something goes wrong!
	 */
	public void writeByte(int b) throws IOException{
		send.writeByte(b);
	}
	
	/**
	 * sends the message buffer to the socket
	 * @param socket the socket
	 * @throws IOException if something goes wrong!
	 */
	public void send(Channel socket) throws IOException{
			buffer.setInt(0, buffer.writerIndex() - 4);
			socket.write(buffer.copy(), socket.voidPromise());
			socket.flush();
	}
}
