package serverPackage;

public class MessageControl {
	
	String[] parts;
	String str1,str2;
	
	
	public void StringSplit(String text)
	{
		parts=text.split("-");
		str1 = parts[0];
		str2 = parts[1];
		Control();
	}
	
	
	private void Control()
	{
		if(str1.equals("xun"))
		{
			ServerMain.clientList.add(str2);
		}
		else if(str1.equals("msg"))
		{
			String sendTo = parts[2];
			int position = ServerMain.clientList.indexOf(sendTo);
			System.out.println("kime : "+position);
			String fromWho  = parts[1];
			String messageContent = parts[3];
			
			
			
			ServerConnection sc = ServerMain.connections.get(position);
			sc.sendToClient("msg-" + fromWho + "-" + messageContent);
			
		}
	}
	
	public void printScreen()
	{
		for (String str : ServerMain.clientList) {
			System.out.println(str);
		}
	}
	
}
