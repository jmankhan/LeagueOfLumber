import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server extends Thread {
	private final int PORT = 4444, MAX_PLAYERS = 2;
	private DatagramSocket socket;
	private SocketAddress[] connectedPlayerPorts;
	
	String message="";
	public Server () throws SocketException, UnknownHostException {
		socket = new DatagramSocket(PORT);
		connectedPlayerPorts = new SocketAddress[MAX_PLAYERS];
	}

	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
				for(int i=0;i<MAX_PLAYERS;i++) {
					if(connectedPlayerPorts[i] == null) {
						connectedPlayerPorts[i] = packet.getSocketAddress();
						break;
					}
					if(packet.getSocketAddress().equals(connectedPlayerPorts[i]))
						break;
				}
			} catch (IOException e) {e.printStackTrace();}
			message = new String(packet.getData());
//			try {
//				sendData(message.getBytes(), packet.getAddress(), packet.getPort());
			if(connectedPlayerPorts[MAX_PLAYERS-1]!=null)
				sendDataToAll(message.getBytes(), packet.getPort());
//			} catch (IOException e) {e.printStackTrace();}
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		socket.send(packet);
	}

	public void sendDataToAll(byte[] data, int port) {
		for(SocketAddress s:connectedPlayerPorts) {
			DatagramPacket packet = new DatagramPacket(data, data.length, ((InetSocketAddress)s).getAddress(), port);
			try {
				socket.send(packet);
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public static void main(String args[]) {
		try {
			new Server().start(); } 
		catch (SocketException e) {e.printStackTrace();}
		catch (UnknownHostException e) {e.printStackTrace();}
	}
}