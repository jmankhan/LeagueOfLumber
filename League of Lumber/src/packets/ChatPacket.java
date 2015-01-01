package packets;

public class ChatPacket extends Packet {

	public ChatPacket() {
		super();
		
		packetID = MESSAGE;
	}
	
	public String getContentAsString() {
		return new String(content);
	}
}