
package example;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class MainController implements Initializable {
    
    
   @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    public void start(ActionEvent event) throws IOException
    {
            Parent op=FXMLLoader.load(getClass().getResource("GameStart.fxml"));
            Scene opscene=new Scene(op);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(opscene);
            window.show();
    }
    
    
    public void lead(ActionEvent event) throws IOException
    {
       Parent op=FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
       Scene opscene=new Scene(op);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(opscene);
       window.show();
    }
    public void options(ActionEvent event) throws IOException
    {
       Parent op=FXMLLoader.load(getClass().getResource("Options1.fxml"));
       Scene opscene=new Scene(op);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(opscene);
       window.show();
    }
    public void credits(ActionEvent event) throws IOException
    {
       Parent op=FXMLLoader.load(getClass().getResource("Credits.fxml"));
       Scene opscene=new Scene(op);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(opscene);
       window.show();
    }
    public void profile(ActionEvent event) throws IOException
    {
       Parent op=FXMLLoader.load(getClass().getResource("profile.fxml"));
       Scene opscene=new Scene(op);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(opscene);
       window.show();
    }
    public void instruct(ActionEvent event) throws IOException
    {
       Parent op=FXMLLoader.load(getClass().getResource("instruct1.fxml"));
       Scene opscene=new Scene(op);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(opscene);
       window.show();
    }
    
    public void exit(ActionEvent event) throws IOException
    {
       System.exit(0);
    }
    

}
