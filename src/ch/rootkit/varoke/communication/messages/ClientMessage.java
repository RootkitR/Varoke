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
	public Integer readInt(){
		return buffer.readInt();
	}
	public Boolean readBoolean(){
		return buffer.readByte() == 1;
	}
	public byte[] readBytes(int length){
		byte[] tmp = new byte[length];
		buffer.readBytes(tmp);
		return tmp;
	}
}
