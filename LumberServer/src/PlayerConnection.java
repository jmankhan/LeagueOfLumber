import java.net.InetAddress;


public class PlayerConnection {
	private int port;
	private InetAddress ipAddress;
	
	public PlayerConnection(InetAddress ip, int p) {
		port = p;
		ipAddress = ip;
		System.out.println("new connection at: "+ipAddress.getCanonicalHostName() + ":" + port);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
}
