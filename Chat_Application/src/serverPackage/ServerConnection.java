package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread {

	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	boolean serverStatus=true;
	
	public ServerConnection(Socket socket)
	{
		this.socket=socket;
	}
	
	public void sendToClient(String message)
	{
		try {
			dataOut.writeUTF(message);
			dataOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void sendToAllClient(String message)
	{
		for (int i = 0; i < ServerMain.connections.size(); i++) {
			ServerConnection sc = ServerMain.connections.get(i);
			sc.sendToClient(message);
		}
	}
	
	
	
	
	public void run()
	{
		try
		{
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			while(serverStatus) 
			{
				while(dataIn.available()==0)
				{
					try
					{
						Thread.sleep(1);
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					
				}
				
				String text = dataIn.readUTF();
				System.out.println("Message ->  "+text);
				
				MessageControl messageControl = new MessageControl();
				messageControl.StringSplit(text);
				messageControl.printScreen();
				String list = "xk-" + String.valueOf(ServerMain.clientList.size()) + "-";
				for (String str : ServerMain.clientList) {
					list = list + str +"-";
				}
				sendToAllClient(list);
				
			}
			
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}
