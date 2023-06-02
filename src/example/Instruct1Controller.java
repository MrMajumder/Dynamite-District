
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

public class Instruct1Controller implements Initializable {

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
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
