import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import packets.*;

public class Server extends Thread {
	private final int PORT = 4444;
	private DatagramSocket socket;
	private String message;
	
	private Packet recievedPacket;
	private ArrayList<PlayerConnection> connections;

	public Server () throws SocketException, UnknownHostException {
		socket = new DatagramSocket(PORT);
		connections = new ArrayList<PlayerConnection>();
		recievedPacket = new Packet();
	}

	public void run() {
		while(true) {
			
			//recieve data from client
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			//figure out what to do with it, return a packet if necessary
			try {
			
				socket.receive(packet);
				parsePacket(packet);

			} catch (IOException e) {e.printStackTrace();}
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		socket.send(packet);
	}

	public void sendDataToAll(byte[] data) {
		for(PlayerConnection p: connections) {
			DatagramPacket packet = new DatagramPacket(data, data.length, p.getIpAddress(), p.getPort());
			try {
				socket.send(packet);
			} catch(IOException e) {e.printStackTrace();}
					
		}
	}

	//figure out what to do with a packet
	private void parsePacket(DatagramPacket packet) {
		
		//recieve packet
		byte[] data = packet.getData();
		recievedPacket.setID(data[0]);
		
		//add to connections list if new connection is being established
		if(recievedPacket.getID() == Packet.CONNECT) {
			connections.add(new PlayerConnection(packet.getAddress(), packet.getPort()));
		}
		
		//record message if chat is being sent
		//send message to all clients as chat
		else if(recievedPacket.getID() == Packet.MESSAGE) {
			message = new String(packet.getData()).trim();
			sendDataToAll(data);
		}
		
	}
	
	public static void main(String args[]) {
		try {
			new Server().start(); } 
		catch (SocketException e) {e.printStackTrace();}
		catch (UnknownHostException e) {e.printStackTrace();}
	}
}