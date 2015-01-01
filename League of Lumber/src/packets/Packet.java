package packets;

public class Packet {
	public static final byte INVALID = -1, CONNECT = 00, MESSAGE = 10;
	
	protected byte packetID;
	protected byte[] content;
	
	public Packet() {
		content = new byte[1024];
	}
	
	public void setID(byte id) {
		packetID = id;
	}
	
	public byte getID() {
		return packetID;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] _content) {
		content = _content;
	}
}