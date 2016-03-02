package ch.rootkit.varoke.communication.messages;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class ClientMessage {
	public short Id;
	private ByteBuf buffer;
	
	
	public ClientMessage(short Header, ByteBuf message) {
		Id = Header;
		buffer = message;
	}
	
	/**
	 * reads a string from the buffer.
	 * @return string from buffer (UTF-8 Encoding)
	 */
	public String readString(){
		byte[] tmp = new byte[buffer.readShort()];
		buffer.readBytes(tmp);
		String out = "";
		try {
			out = new String(tmp, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			/* Why the fuck shouldn't you support UTF-8 ? XD */
		}
		tmp = null;
		return out;
	}
	
	/**
	 * reads an integer
	 * @return integer from buffer
	 */
	public Integer readInt(){
		return buffer.readInt();
	}
	
	/**
	 * reads a byte and returns if the byte is 1
	 * @return byte == 1
	 */
	public Boolean readBoolean(){
		return buffer.readByte() == 1;
	}
	
	/**
	 * has the same function like readString except it returns an Byte-Array
	 * @param length the size of the array
	 * @return array with them bytes
	 */
	public byte[] readBytes(int length){
		byte[] tmp = new byte[length];
		buffer.readBytes(tmp);
		return tmp;
	}
}
