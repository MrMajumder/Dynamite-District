
package example;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;


public class Example extends Application {
    
    static int PlayerCount = 1;
    static Profile thisProfile = new Profile();
    static Sound sound=new Sound();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root=FXMLLoader.load(getClass().getResource("FXML.fxml"));
        
        Scene scene = new Scene(root,GameInfo.ScreenWidth.getValue(),GameInfo.ScreenHeight.getValue());
        
        primaryStage.setTitle("DYNAMITE DISTRICT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
   
    public static void main(String[] args) {
        launch(args);
    }
    
}
