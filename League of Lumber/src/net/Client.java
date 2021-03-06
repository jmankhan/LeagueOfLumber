package net;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import main.Game;
import packets.Packet;

public class Client extends Thread {
	public final static String NEWLINE = " !@#$%^&*() "; //code for new line, hopefully no one actually types this as a message
	private final int PORT = 4444;
	private InetAddress ipAddress;
	private DatagramSocket socket;

	private Packet recievedPacket;
	private String message;
	private StringBuilder recievedMessages;
	private int numMessages = 0;

	private boolean connected;

	public Client(Game game, String _ipAddress) throws SocketException, UnknownHostException {
		socket = new DatagramSocket();
		ipAddress = InetAddress.getByName(_ipAddress);

		recievedMessages = new StringBuilder();
		recievedPacket = new Packet();

		connected = false;
		message = "";
	}

	public void run() {
		while(true) {
			//receive data
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			//figure out what to do with data
			try {
				
				socket.receive(packet);
				parsePacket(packet);
				
			} catch (IOException e) {e.printStackTrace();}

			//reset packet content
			message = new String(packet.getData()).trim();
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, PORT);
		try {
			socket.send(packet);
			//log activity to text file if necessary
			//			log("sent packet to "+packet.getAddress()+":"+packet.getPort() + "     message: " + new String(packet.getData()).trim());
		} catch (IOException e) {e.printStackTrace();}
	}

	private void parsePacket(DatagramPacket packet) {
		byte[] data = packet.getData();
		recievedPacket.setID(data[0]);

		if(recievedPacket.getID() == Packet.CONNECT) {
			if(data[1] == Packet.CONNECT) {
				connected=true;
			}
		}

		else if(recievedPacket.getID() == Packet.MESSAGE) {
			recievedMessages.append(new String(data).trim() + NEWLINE);
			numMessages++;
		}

	}

	public String getMessage() {
		return message;
	}

	public int getNumMessages() {
		return numMessages;
	}

	public String getReceivedMessages() {
		return recievedMessages.toString();
	}

	public boolean isConnected() {
		return connected;
	}

	private void log(String text) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LumberLog.txt", true))); 
			out.append(text);
			out.println();
			out.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}

	}

}