
package example;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Music {
    private double i = 0.1;
    MediaPlayer media;
    Media musicfile = new Media(new File(System.getProperty("user.dir") + "\\src\\music\\gunshot.wav").toURI().toString());

    private int autotune = 1;
    
    Music()
    {
        media = new MediaPlayer(musicfile);
        media.setVolume(i);
        //media.setAutoPlay(true);
    }
    
    public void SoundOn()
    {
        media.play();
    }
   
    public void SoundOff()
    {
        media.stop();
    }
    public void SoundP()
    {
        i=i+0.1;
        if(i>1)
            i=1;
        media.setVolume(i);  
    }
    public void SoundM()
    {
        i=i-0.1;
        if(i<0)
            i=0;
        media.setVolume(i);
    }
    
    
}
