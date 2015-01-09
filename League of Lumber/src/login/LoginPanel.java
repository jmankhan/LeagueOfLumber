package login;

import graphics.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.Client;
import packets.LoginPacket;
import packets.Packet;

public class LoginPanel extends JPanel {
	public static String SERVER_IP = "localhost"; //70.15.134.76 - my ip address

	private final String SPLASH_PATH = "/pause_small.jpg";
	private final Color backColor = new Color(.0705f, .0902f, .1647f, .7f);
	static final int WIDTH = 1200, HEIGHT = 800;

	private BufferedImage splash;
	private JTextField user, pass;
	private JButton login;
	private JCheckBox saveCredentialsBox;

	private boolean saveCredentials;
	private boolean correctCredentials;
	
	private Font font;

	private Client client;
	private LoginPacket loginPacket;
	public LoginPanel() {
		init();
		setupGUI();
	}

	public void init() {
		ImageLoader loader = new ImageLoader();
		splash = loader.load(SPLASH_PATH);
		font = new Font("Constantia", Font.PLAIN, 16);
		user = new JTextField(20);
		pass = new JTextField(20);

		login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginPacket = new LoginPacket();
				loginPacket.setUsername(user.getText().trim());
				loginPacket.setPassword(pass.getText().trim());
				
				byte[] content = new byte[1024];
				content[0] = Packet.CONNECT;
				System.arraycopy(loginPacket.getUsername(), 0, content, 1, loginPacket.getUsernameLength());
				content[loginPacket.getUsernameLength()+1] = ' ';
				System.arraycopy(loginPacket.getPassword(), 0, content, loginPacket.getUsernameLength()+2, loginPacket.getPasswordLength());
				
				client.sendData(content);
			}
		});
		saveCredentialsBox = new JCheckBox();
		saveCredentialsBox.setOpaque(false);
		
		saveCredentials = false;
		correctCredentials = false;
		
		try {
			client = new Client(null, SERVER_IP);
		} catch (SocketException | UnknownHostException e) {e.printStackTrace();}
	}

	public void setupGUI() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();

		GridBagLayout credentialsLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 1.0;
		gc.insets = new Insets(0, WIDTH/25, HEIGHT/5, 0);
		
		JPanel credentialsPanel = new JPanel();
		credentialsPanel.setLayout(credentialsLayout);
		credentialsPanel.setBackground(backColor);

		JLabel header = new JLabel("Account Login"); 
		JLabel userLabel = new JLabel("Username: "); 
		JLabel passLabel = new JLabel("Password: "); 
		JLabel saveCredentialsLabel = new JLabel("        Save login?");
		
		JLabel[] labels = {header, userLabel, passLabel, saveCredentialsLabel};
		
		for(JLabel l: labels) {
			l.setForeground(Color.white);
			l.setFont(font);
		}
		saveCredentialsLabel.setFont(font.deriveFont(12f));
		
		int margin = WIDTH/25;
		int gridy = 0;
		
		gbc.gridx = 0;
		gbc.gridy = gridy++;
		gbc.insets = new Insets(margin, margin, 0, margin);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		credentialsPanel.add(header, gbc);
		
		gbc.gridy = gridy++;
		gbc.insets = new Insets(25, margin, 0, margin);
		credentialsPanel.add(userLabel, gbc);
		
		gbc.gridy = gridy++;
		gbc.insets = new Insets(5, margin, 0, margin);
		credentialsPanel.add(user, gbc);
		
		gbc.gridy = gridy++;
		credentialsPanel.add(passLabel, gbc);
		
		gbc.gridy = gridy++;
		credentialsPanel.add(pass, gbc);
		
		gbc.gridy = gridy++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_START;
		credentialsPanel.add(saveCredentialsBox, gbc);
		credentialsPanel.add(saveCredentialsLabel, gbc);
		
		gbc.gridy = gridy++;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(5, margin, margin, margin);
		credentialsPanel.add(login, gbc);

		add(credentialsPanel, gc);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(splash, 0, 0, getParent().getWidth(), getParent().getHeight(), null);
		g.drawLine(user.getX(), user.getY(), user.getX()+user.getWidth(), user.getY());
	}

	public static void main(String args[]) {
		JFrame f = new JFrame("League of Lumber");
		
		f.add(new LoginPanel());
		f.setVisible(true);
		f.setSize(WIDTH, HEIGHT);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		
	}
}
