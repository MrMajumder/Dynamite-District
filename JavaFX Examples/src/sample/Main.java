package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    
    //creating a stage object as a separate method for referrence purposes
    public Stage theStage;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        DoubleValue degree = new DoubleValue();
        theStage = primaryStage;
        theStage.setTitle( "Timeline Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        Canvas canvas = new Canvas( GameInfo.ScreenWidth.getValue(), GameInfo.ScreenHeight.getValue() );
        root.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();

        theScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                degree.setValue(Math.toDegrees(Math.atan2( event.getY() - canvas.getHeight()/2, event.getX() - canvas.getWidth()/2)));
                degree.setValue(degree.getValue()%360);
            }
        });
        theScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                degree.setValue(Math.toDegrees(Math.atan2( event.getY() - canvas.getHeight()/2, event.getX() - canvas.getWidth()/2)));
                degree.setValue(degree.getValue()%360);
            }
        });

        ArrayList<String> keyInput = new ArrayList<>();
        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();

                // only add once... prevent duplicates
                if ( !keyInput.contains(code) )
                    keyInput.add( code );
            }
        });
        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                keyInput.remove( code );
            }
        });

        Sprite characterSprite = new Sprite("images/sprite.png", canvas.getWidth()/2, canvas.getHeight()/2, 5, 50, 50);

        Map1 mainMap = new Map1(characterSprite.getMapPosX(), characterSprite.getMapPosY(), 0);

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gc.clearRect(0, 0, 1000, 1000);
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                // background image clears canvas
                gc.setFill( new Color(1, 1, 1, 1.0) );
                gc.fillRect(0, 0, 1000, 1000);



                characterSprite.setDegree(degree.getValue());
                mainMap.setDegree(degree.getValue());

                if (keyInput.contains("W")) {
                    //characterSprite.goFront(degree.getValue());
                    mainMap.mapGoFront(degree.getValue());
                }
                if (keyInput.contains("A")) {
                    //characterSprite.goLeft(degree.getValue());
                    mainMap.mapGoLeft(degree.getValue());
                }
                if (keyInput.contains("S")) {
                    //characterSprite.goBack(degree.getValue());
                    mainMap.mapGoBack(degree.getValue());
                }
                if (keyInput.contains("D")) {
                    //characterSprite.goRight(degree.getValue());
                    mainMap.mapGoRight(degree.getValue());
                }

                mainMap.render(gc);
                characterSprite.render(gc);

            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
