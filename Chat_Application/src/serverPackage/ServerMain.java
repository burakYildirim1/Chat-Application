package serverPackage;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ServerMain {

	private JFrame frame;

	
	static ServerSocket ss;
	static DataInputStream dataIn;
	static DataOutputStream dataOut;
	static Socket socket;
	static boolean shouldRun = true;
	
	static JTextArea txtLog;
	
	static List<ServerConnection> connections = new ArrayList<ServerConnection>();
	
	static public ArrayList<String> clientList;
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerMain window = new ServerMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		clientList = new ArrayList();
		
		try
		{
			ss = new ServerSocket(6068);
			System.out.println("server started");
			while(shouldRun)
			{
				socket = ss.accept();
				txtLog.append("accept" + "\n");
				
				ServerConnection sc = new ServerConnection(socket);
				sc.start();
				connections.add(sc);
				
			}
			
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}


	public ServerMain() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtLog = new JTextArea();
		txtLog.setBounds(431, 0, -423, 212);
		frame.getContentPane().add(txtLog);
	}
}
