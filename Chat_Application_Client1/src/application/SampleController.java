package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SampleController {

	SecondController sc;
	
	
    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    public static String username,password;
    private static boolean login = false;
    
    
    public void connectDB()
    {
    	username = txtUser.getText();
    	password = txtPassword.getText();
    	
    	try {
			Class.forName("org.postgresql.Driver");
			
			String url = "jdbc:postgresql://localhost:5432/dbchat?user=postgres&password=12345&ssl=false";
			
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Connected");
			
			PreparedStatement ps = conn.prepareStatement("select * from table_user where username='"+username+"' and password ='"+password+"'");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				System.out.println("SUCCESS!");
				login = true;
				
			}
			
			if(login)
			{
				FXMLLoader fxloader = new FXMLLoader(getClass().getResource("Second.FXML"));
				Parent parent2 = (Parent)fxloader.load();
				Stage stage = new Stage();
				stage.setTitle("ChatApp");
				stage.setScene(new Scene(parent2));
				stage.show();
				
				
				stage.setHeight(600);
				stage.setWidth(800);
//				stage.setMaxHeight(600);
//				stage.setMaxWidth(800);
//				stage.setMinHeight(600);
//				stage.setMinWidth(800);
				
				sc = fxloader.getController();
				sc.getUsername(username);
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void btnLogin_click(ActionEvent event) {

    	connectDB();
    	
    	
    	
    }

}
