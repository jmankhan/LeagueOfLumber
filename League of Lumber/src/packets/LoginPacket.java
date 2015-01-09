package packets;

public class LoginPacket extends Packet {
	
	private byte[] user, pass;
	
	public LoginPacket() {
		super();
		packetID = CONNECT;
	}
	
	public void setUsername(String _user) {
		user = _user.getBytes();
	}
	
	public void setPassword(String _pass) {
		pass = _pass.getBytes();
	}
	
	public int getUsernameLength() {
		return user.length;
	}
	
	public int getPasswordLength() {
		return pass.length;
	}
	
	public byte[] getUsername() {
		return user;
	}
	
	public byte[] getPassword() {
		return pass;
	}
}
