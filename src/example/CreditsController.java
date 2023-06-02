/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.io.IOException;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class CreditsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Button IBACK;

    public void back(ActionEvent event) throws IOException
    {
        Parent op= FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Scene opscene=new Scene(op);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(opscene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
