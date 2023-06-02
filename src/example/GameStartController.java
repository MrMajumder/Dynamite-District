
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameStartController implements Initializable {

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void host(ActionEvent event) throws IOException
    {
        Multiplayer.isServer = true;
        Parent op=FXMLLoader.load(getClass().getResource("Lobby.fxml"));
            Scene opscene=new Scene(op);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(opscene);
            window.show();
            new Multiplayer().serverLikeEntity();

    }
    public void join(ActionEvent event) throws IOException
    {
            Parent op=FXMLLoader.load(getClass().getResource("JoinGame.fxml"));
            Scene opscene=new Scene(op);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(opscene);
            window.show();
    }
    public void back(ActionEvent event) throws IOException
    {
            Parent op=FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene opscene=new Scene(op);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(opscene);
            window.show();
    }
    
    
}
