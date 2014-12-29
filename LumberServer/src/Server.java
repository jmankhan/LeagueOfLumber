import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server extends Thread {
	private final int PORT = 4444;
	private DatagramSocket socket;

	private ArrayList<PlayerConnection> connections;
	String message="";
	
	public Server () throws SocketException, UnknownHostException {
		socket = new DatagramSocket(PORT);
		connections = new ArrayList<PlayerConnection>();
	}

	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
				connections.add(new PlayerConnection(packet.getPort(), packet.getAddress()));
			} catch (IOException e) {e.printStackTrace();}
			message = new String(packet.getData());
			try {
				sendDataToAll(message.getBytes());
			} catch (IOException e) {e.printStackTrace();}
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		socket.send(packet);
	}

	public void sendDataToAll(byte[] data) throws IOException {
		for(PlayerConnection p: connections) {
			DatagramPacket packet = new DatagramPacket(data, data.length, p.getIpAddress(), p.getPort());
			socket.send(packet);
		}
	}
	
	public static void main(String args[]) {
		try {
			new Server().start(); } 
		catch (SocketException e) {e.printStackTrace();}
		catch (UnknownHostException e) {e.printStackTrace();}
	}
}