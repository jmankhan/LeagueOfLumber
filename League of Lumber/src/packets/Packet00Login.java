package packets;

import net.Client;
import net.Packet;
import net.Server;

public class Packet00Login extends Packet {

	private String username;
	
	public Packet00Login(byte[] data) {
		super(00);
		username = readData(data);
	}

	public Packet00Login(String _username) {
		super(00);
		username = _username;
	}
	
	@Override
	public void writeData(Client client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(Server server) {
	}

	@Override
	public byte[] getData() {
		return ("00"+username).getBytes();
	}

}
