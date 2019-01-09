
package example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MainController implements Initializable {
    
    @FXML
    private AnchorPane rootpane;
    
   @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    public void start(MouseEvent e) throws IOException
    {
       AnchorPane pane=FXMLLoader.load(getClass().getResource("Start.fxml"));
       rootpane.getChildren().setAll(pane);
    }
    @FXML
    public void lead(MouseEvent e) throws IOException
    {
       AnchorPane pane=FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
       rootpane.getChildren().setAll(pane);
    }
    @FXML
    public void options(MouseEvent e) throws IOException
    {
       AnchorPane pane=FXMLLoader.load(getClass().getResource("Options.fxml"));
       rootpane.getChildren().setAll(pane);
    }
    @FXML
    public void credits(MouseEvent e) throws IOException
    {
       AnchorPane pane=FXMLLoader.load(getClass().getResource("Credits.fxml"));
       rootpane.getChildren().setAll(pane);
    }
    
    public void exit(MouseEvent e) throws IOException
    {
       
    }
    

    
    
}
