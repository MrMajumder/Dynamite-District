
package example;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ProfileController  {
    
    Profile pff = Example.thisProfile;
    @FXML
    private TextField name;
    @FXML
    private TextField level;
 
    @FXML
    private Button Save;
    @FXML
    private Button Back;
    @FXML
    private Button forward;
    @FXML
    private Button backward;
    MyImage img = new MyImage();
  
    @FXML
    Image i ;
    
    @FXML
    Pane paneview = new Pane();
   
    public void Changes(ActionEvent e) throws IOException{
        pff.setName(name.getText());
        System.out.println(pff.getName());
        
        
    }
    public void Forward(ActionEvent e) throws IOException{
        pff.valueplus();
        i= new Image(pff.getImage());
        paneview.getChildren().clear();
        javafx.scene.image.ImageView imv=new  javafx.scene.image.ImageView(i);
        paneview.getChildren().add(imv);
        
        
    }
    public void Backward(ActionEvent e) throws IOException{
        pff.valueminus();
        i= new Image(pff.getImage());
        paneview.getChildren().clear();
        javafx.scene.image.ImageView imv=new  javafx.scene.image.ImageView(i);
        paneview.getChildren().add(imv);
        
        
    }
    @FXML
     public void back(ActionEvent event) throws IOException
    {
       Parent op=FXMLLoader.load(getClass().getResource("FXML.fxml"));
       Scene opscene=new Scene(op);
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(opscene);
       window.show();
    }
   
}