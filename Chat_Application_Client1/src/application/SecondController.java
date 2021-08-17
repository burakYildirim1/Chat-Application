package application;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
//import serverPackage.ServerMain;

public class SecondController implements Initializable{

	@FXML
    Label lblUsername;
	
	

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtMessage;

    @FXML
    private Button btnSend;
    
    @FXML
    private ListView<Label> listview;
	
	
	Socket socket;
	DataInputStream dataIn;
	DataOutputStream dataOut;
	private static Integer usersNumber;
	private static String[] parts;
	
	private static String selectedText;

	private static FileOutputStream fos;
	private static FileInputStream fis;
	
	public void getUsername(String username)
	{
		lblUsername.setText(username);
	}
	
	
	
	private void stringSplit(String text)
	{
		parts = text.split("-");
		if(parts[0].equals("xk"))
		{
			usersNumber = Integer.valueOf(parts[1].trim());
		}
		
		control();
	}
	
	
	private void control()
	{
		if(parts[0].equals("xk"))
		{
			listview.getItems().clear();
			int clientsNumber = Integer.valueOf(parts[1]); 
			for (int i = 2; i <= clientsNumber+1 ; i++) {
				System.out.println(parts[i]);
				
				Image image = new Image(getClass().getResourceAsStream("profil.png"));
				Label label = new Label(parts[i]);
				label.setGraphic(new ImageView(image));
				
				listview.getItems().add(label);
				
				
			}
		}
		else if(parts[0].equals("msg"))
		{
			txtHistory.appendText(parts[1] + " : " + parts[2] + "\n");
		}
	}
	
	
	private void connect()
	{
		SampleController sampc = new SampleController();
	
		try {
			socket = new Socket("localhost",6068);
			System.out.println("client basariyla baglandi");
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			dataOut.writeUTF("xun-"+sampc.username);
			dataOut.flush();
			
			String messageIn = "";
			while(!messageIn.equals("exit"))
			{
				messageIn = dataIn.readUTF();
				System.out.println("client ::: "+messageIn);
				stringSplit(messageIn);
			}
			
		}catch(Exception e) {
			
		}
		
	}
	
	
	public SecondController()
	{
		
	}
	
	
	
	public void saveMessage()
	{
		try {
			fos = new FileOutputStream(lblUsername.getText()+"-"+getSelectedItemText()+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		try {
			osw.write(txtHistory.getText());
			osw.flush();
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSelectedItemText()
	{
		listview.setOnMouseClicked(new EventHandler() {

			@Override
			public void handle(Event event) {
				
				int selectedIndex = listview.getSelectionModel().getSelectedIndex();
				selectedText = listview.getItems().get(selectedIndex).getText();
				System.out.println(selectedText);
				
				try {
					fis = new FileInputStream(getFileName());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				txtHistory.setText("");
				BufferedReader br = new BufferedReader(new InputStreamReader(fis,StandardCharsets.UTF_8));
				String line;
				try {
					while((line = br.readLine()) != null)
					{
						txtHistory.appendText(line);
						txtHistory.appendText("\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		return selectedText;
	}
	
	
	
	private String getFileName()
	{
		String user1 = lblUsername.getText();
		String fileName = user1+"-"+getSelectedItemText()+".txt";
		
		return fileName;
		
	}
	
	
	
	
	public void sendMessage(String message) {

		try {
			dataOut.writeUTF("msg-" + lblUsername.getText() + "-" + getSelectedItemText() + "-" + message);
			dataOut.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
		
		txtHistory.appendText(message+"\n");
		
		saveMessage();

	}
	
	
	@FXML
	void btnSend_click(ActionEvent event) {
		
			sendMessage(txtMessage.getText());
		
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				connect();
			}
			
		});
		
		getSelectedItemText();
		
	}

	
	
	
	
}
