package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import main.Game;

public class Client extends Thread {
	private final int PORT = 4444;
	private InetAddress ipAddress;
	private DatagramSocket socket;
	
	private String message;
	private StringBuilder recievedMessages;
	private int numMessages = 0;
	
	public Client(Game game, String _ipAddress) throws SocketException, UnknownHostException {
		socket = new DatagramSocket();
		ipAddress = InetAddress.getByName(_ipAddress);
		
		recievedMessages = new StringBuilder();
	}

	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
				recievedMessages.append(new String(packet.getData()).trim() + "\n");
				numMessages++;
			} catch (IOException e) {e.printStackTrace();}
			message = new String(packet.getData()).trim();
		}
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getNumMessages() {
		return numMessages;
	}
	
	public String getRecievedMessages() {
		return recievedMessages.toString();
	}
}