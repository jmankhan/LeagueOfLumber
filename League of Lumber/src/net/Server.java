package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server extends Thread {
	private final int PORT = 1331;
	private DatagramSocket socket;
	
	public Server () throws SocketException, UnknownHostException {
		socket = new DatagramSocket(PORT);
		
	}

	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {e.printStackTrace();}
			String message = new String(packet.getData());
			if(message.trim().equals("ping")) {
				System.out.println("<SERVER> "+ message.trim());
				try {
					sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
				} catch (IOException e) {e.printStackTrace();}
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		socket.send(packet);
	}
}
