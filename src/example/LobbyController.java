package example;

import static example.Shooting.objNum;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class LobbyController implements Initializable {
    public static Vector<Player> activePlayer = new Vector<>();
    public static Vector<Spawn> spawnItems = new Vector<>();
    public static int thisPlayerID = 0;
   // public Profile thisProfile ;
    
    public static Player player;
    private Canvas canvas;
    private GraphicsContext gc;
    public int bulletNumber = 100;
    private Random rand = new Random();
    private Spawn health = new Spawn("image/health.png", (((rand.nextInt(8) * ( GameInfo.WorldWidth.getValue() - GameInfo.ScreenWidth.getValue() - (30 * GameInfo.TileWidth.getValue()))) / 10) + (GameInfo.ScreenWidth.getValue() / 2) + (15 * GameInfo.TileWidth.getValue())), (((rand.nextInt(8) * ( GameInfo.WorldHeight.getValue() - GameInfo.ScreenHeight.getValue() - (30 * GameInfo.TileHeight.getValue()))) / 10) + (GameInfo.ScreenHeight.getValue() / 2) + (15 * GameInfo.TileHeight.getValue())), 0, 50, 50);

    @FXML
    Button start ;
    @FXML
    Button SetTimeButton;
    static int totaltime = 0;
    public int timemin;
    static int endTime;
    public int timecount = 0;
    public int timesec = 0;
    String Finish = null; 
    String killcount = null;
    public String time;
    String killshow = "KILL : " ;
    @FXML
    private TextField TimeInput;
    Text timeText = new Text();
    Text level = new Text();

    public void TimeSet(ActionEvent event){
        totaltime = Integer.parseInt(TimeInput.getText())*60*1000;
        endTime = totaltime;

        if (!Multiplayer.activeShadow.isEmpty()) {
            for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
                Multiplayer.activeShadow.elementAt(i).userOutput.println(0 + "#" + "TIME" + "#" + totaltime);
                System.out.println("Sending to shadow " + i);
            }
        }
    }

    Timer mytimer = new Timer();
    TimerTask task = new TimerTask(){
        public void run(){
            timemin = totaltime/(60*1000);
            timesec = (totaltime%(60*1000))/1000;
            time = timemin +":" +timesec;
            totaltime -= 1000;
            
            if(timesec<10){
                time = timemin +":"+0+timesec;
            }
            else{
                time = timemin +":" +timesec;
            }
        }
    };
    public void TimeStart(){
        mytimer.scheduleAtFixedRate(task, 0, 1000);
        mytimer.scheduleAtFixedRate(spawn, 0, 10000);
        mytimer.scheduleAtFixedRate(Finishing, endTime, endTime);
                
    }
    ActionEvent event1 = new ActionEvent();
    Timer GStart;
    public void Gstart(){
        //GStart.scheduleAtFixedRate(GameStart, 0, 5000);
        GStart = new Timer();
        GStart.schedule(GameStart, 2000);

    }
    TimerTask GameStart = new TimerTask(){
        @Override
        public void run(){
            Platform.runLater(() -> {
                try{
                    System.out.println("inside gamestart task");
                    play(event1);
                    GStart.cancel();
                }catch(IOException e){}
            });
        }
    };
    TimerTask Finishing = new TimerTask(){
        @Override
        public void run(){

            int ID = 0;
            int maxKill = activePlayer.elementAt(ID).kill;
            for(int i=1;i<activePlayer.size();i++){
                if (activePlayer.elementAt(i).kill > maxKill) {
                    ID = i;
                    maxKill = activePlayer.elementAt(i).kill;
                    
                }
            }
            if(ID == thisPlayerID){
                Finish = "HURRAH! YOU WIN!!!!";
            }
            else{
                Finish = "BAD LUCK! YOU LOSE!";
            }
            killcount = String.valueOf(activePlayer.elementAt(thisPlayerID).kill);
            task.cancel();
            mytimer.cancel();
        }
    };
    
    TimerTask spawn = new TimerTask() {
        @Override
        public void run() {
            if (spawnItems.isEmpty()) {
                spawnItems.add(health);
            }
            if (thisPlayerID == 0) {
                spawnItems.elementAt(0).setPicked(false);
                spawnItems.elementAt(0).updateAbsolute((((rand.nextInt(8) * ( GameInfo.WorldWidth.getValue() - GameInfo.ScreenWidth.getValue() - (30 * GameInfo.TileWidth.getValue()))) / 10) + (GameInfo.ScreenWidth.getValue() / 2) + (15 * GameInfo.TileWidth.getValue())), (((rand.nextInt(8) * ( GameInfo.WorldHeight.getValue() - GameInfo.ScreenHeight.getValue() - (30 * GameInfo.TileHeight.getValue()))) / 10) + (GameInfo.ScreenHeight.getValue() / 2) + (15 * GameInfo.TileHeight.getValue())));
                if (Multiplayer.playerID != 1) {
                    Multiplayer.activeHandlers.elementAt(0).broadcastSpawn(thisPlayerID + "#"+ "SPAWN" + "#" + spawnItems.elementAt(0).isPicked() + "#" + spawnItems.elementAt(0).getAbsolutePosX() + "#" + spawnItems.elementAt(0).getAbsolutePosY());
                }
            }
        }
    };

   @Override
    public void initialize(URL location, ResourceBundle resources) {
       canvas = new Canvas( GameInfo.ScreenWidth.getValue(), GameInfo.ScreenHeight.getValue() );
       gc = canvas.getGraphicsContext2D();
       if (!Multiplayer.isServer) {
           start.setText("Ready");
           SetTimeButton.setDisable(true);
       }
    }

    public void start(ActionEvent event){
        try{
            event1 = event;
            if (Multiplayer.isServer) {
                if (Multiplayer.playerID != 1) {
                    Multiplayer.ss.close();
                }
                Multiplayer.inLobby = false;
                for (int i = 0 ; i < Multiplayer.activeShadow.size(); i++) {
                    Multiplayer.activeShadow.elementAt(i).userOutput.println("0#START");
                }
                Gstart();
                for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
                    Multiplayer.activeShadow.elementAt(i).userOutput.println("0#EXIT");
                }
            }
            else {
                activePlayer.elementAt(thisPlayerID).setIsReady(true);
                Multiplayer.activeShadow.elementAt(0).userOutput.println(thisPlayerID + "#" + "READY" + true);
                start.setDisable(true);
            }

        }catch(IOException e){}
    }
   
    public void play(ActionEvent event) throws IOException
    {
        TimeStart();
        Multiplayer.inLobby = false;
        if (thisPlayerID == 0) {
            Multiplayer.ss.close();
            for (int i = 0 ; i < Multiplayer.activeShadow.size(); i++) {
                Multiplayer.activeShadow.elementAt(i).userOutput.println("0#START");
            }
        }
        System.out.println(Multiplayer.activeHandlers.size());
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println("0#EXIT");
            
        }

        spawnItems.add(health);
        if (Multiplayer.playerID != 1) {
            System.out.println("Calling handler: ");
            Multiplayer.activeHandlers.elementAt(0).handle();
        }

        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            if (Multiplayer.playerID != 1 && thisPlayerID == 0) {
                Multiplayer.activeHandlers.elementAt(0).broadcastSpawn(thisPlayerID + "#"+ "SPAWN" + "#"+ health.isPicked()+ "#" + health.getAbsolutePosX() + "#" + health.getAbsolutePosY());
            }
        }


        Stage theStage=new Stage();
        DoubleValue degree = new DoubleValue();
        Rectangle rec=new Rectangle(850,20,120,30);
        ArrayList<Node> bullets = new  ArrayList<Node>();
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        rec.setFill(Color.WHITE);
        Rectangle rec1=new Rectangle(850,75,120,30);
        rec1.setFill(Color.WHITE);
        Text name = new Text();
        
        name.setText(player.profile.getName());
        name.setX(853);
        name.setY(45);
        name.setFont(Font.font("Sans serif", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 24));
        name.setFill(Color.BLUE);
        name.setStrokeWidth(2); 
        name.setStroke(Color.PURPLE);
        
        Image back = new Image("image/back_1.png");
        Button button = new Button();
        ImageView imageView = new ImageView(back);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        button.setGraphic(imageView);
        //Button button = new Button("BACK");
        button.setLayoutX(930);
        button.setLayoutY(930);



        button.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               try{
                Parent op=FXMLLoader.load(getClass().getResource("FXML.fxml"));
                Scene opscene=new Scene(op);
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(opscene);
                window.show();
               }catch(Exception e){}
            }
                    
        });
        root.getChildren().addAll( canvas, rec, name , rec1, button);


        Map1 mainMap = new Map1(activePlayer.elementAt(thisPlayerID).getAbsolutePosX(), activePlayer.elementAt(thisPlayerID).getAbsolutePosY(), 0);
        MiniMap miniMap = new MiniMap();

        theScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                degree.setValue(Math.toDegrees(Math.atan2( event.getY() - canvas.getHeight()/2, event.getX() - canvas.getWidth()/2)));
                degree.setValue(degree.getValue());
            }
        });
        theScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                degree.setValue(Math.toDegrees(Math.atan2( event.getY() - canvas.getHeight()/2, event.getX() - canvas.getWidth()/2)));
                degree.setValue(degree.getValue());
            }
        });

        ArrayList<String> keyInput = new ArrayList<>();
        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();

                // only add once... prevent duplicates
                if ( !keyInput.contains(code) )
                    keyInput.add( code );
            }
        });
        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e)
            {
                String code = e.getCode().toString();
                keyInput.remove( code );
            }
        });
       
        Shooting[][] Shoot = new Shooting[activePlayer.size()][bulletNumber];
        Music[] gunshot = new Music[activePlayer.size()];
        for(int i =0;i<activePlayer.size();i++)
        {
            gunshot[i] = new Music();
        }
        for(int j = 0;j<activePlayer.size();j++) {
            for (int i = 0; i < bulletNumber; i++) {
                Shoot[j][i] = new Shooting("image/g.png", activePlayer.elementAt(j).getAbsolutePosX(), activePlayer.elementAt(j).getAbsolutePosY(), mainMap, false);
            }
        }
        final long startNanoTime = System.nanoTime();
        
        AnimationTimer programButtonAnimation;
        programButtonAnimation = new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                gc.clearRect(0, 0, GameInfo.ScreenWidth.getValue(), GameInfo.ScreenHeight.getValue());
                double t = (currentNanoTime + startNanoTime) / 1000000000.0;
                //double t = 1000;
                // background image clears canvas
                gc.setFill( new Color(0.133, 0.545, 0.133, 0.8) );
                gc.fillRect(0, 0, 980, 980);

                mainMap.setDegree(degree.getValue());
                if (Multiplayer.playerID != 1) {
                    Multiplayer.activeHandlers.elementAt(0).broadcastDegree();
                }

                if (!(keyInput.isEmpty())) {
                    if((keyInput.get(0) == "W") || (keyInput.get(0) == "A")|| (keyInput.get(0) == "S") || (keyInput.get(0) == "D")){


                        mainMap.mapTraverse(degree.getValue(), keyInput.get(0));
                        //player[0].spriteTraverse(degree.getValue(), keyInput.get(0));
                        if (Multiplayer.playerID != 1) {
                            Multiplayer.activeHandlers.elementAt(0).broadcastMovement();
                        }

                    }
                }
                activePlayer.elementAt(thisPlayerID).setDegree(degree.getValue());
                mainMap.render(gc);
                for(int i = 0; i < activePlayer.size(); i++) {
                    if(activePlayer.elementAt(i).isIsAlive()) {
                        if(i == thisPlayerID){
                            if (activePlayer.elementAt(i).isNewlyAlive()) {
                                mainMap.setAbsolutePosX(activePlayer.elementAt(thisPlayerID).getAbsolutePosX());
                                mainMap.setAbsolutePosY(activePlayer.elementAt(thisPlayerID).getAbsolutePosY());
                                activePlayer.elementAt(i).setNewlyAlive(false);
                            }
                            activePlayer.elementAt(i).updateAbsolute(mainMap.getAbsolutePosX(), mainMap.getAbsolutePosY());
                        }
                        activePlayer.elementAt(i).render(gc, mainMap.getAbsolutePosX(), mainMap.getAbsolutePosY());

                    }
                }
                activePlayer.elementAt(thisPlayerID).draw(gc);

                for (int i = 0; i < spawnItems.size(); i++) {
                    if (!spawnItems.elementAt(i).isPicked())
                    spawnItems.elementAt(i).render(gc, mainMap.getAbsolutePosX(), mainMap.getAbsolutePosY());
                }

                
                theScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        if (activePlayer.elementAt(thisPlayerID).getIsAlive()) {
                            activePlayer.elementAt(thisPlayerID).setIsShooting(true);

                            gunshot[thisPlayerID].SoundOff();
                            gunshot[thisPlayerID].SoundOn();
                            if (Multiplayer.playerID != 1) {
                                Multiplayer.activeHandlers.elementAt(0).broadcastShoot(thisPlayerID + "#" + "SHOOT" + "#" + true);
                            }
                            Shoot[thisPlayerID][objNum].check = true;
                            Shoot[thisPlayerID][objNum].setCheck2(true);
                        }
                    }
                });

                for(int i = 0;i<activePlayer.size();i++) {
                    if (activePlayer.elementAt(i).getIsShooting()) {
                        System.out.println("Entering for element " + i);
                        Shoot[i][objNum].degree1 = activePlayer.elementAt(i).getDegree();
                        Shoot[i][objNum].setabsolutePosX(activePlayer.elementAt(i).getAbsolutePosX());
                        Shoot[i][objNum].setabsolutePosY(activePlayer.elementAt(i).getAbsolutePosY());
                        Shoot[i][objNum].check = true;
                        //Shoot[i][objNum].setCheck2(true);
                        activePlayer.elementAt(i).setIsShooting(false);
                        if(objNum >= (bulletNumber - 1)){
                            objNum = -1;
                        }
                        objNum++;
                    }
                }
                
                for (int j = 0; j < activePlayer.size(); j++) {
                    for (int i = 0; i < bulletNumber; i++) {
                        if (Shoot[j][i].check == true) {
                            Shoot[j][i].render(j, gc,  mainMap.getAbsolutePosX(), mainMap.getAbsolutePosY());
//                            Shoot[j][i].render2(j, gc, mainMap.getAbsolutePosX(), mainMap.getAbsolutePosY());
//                            Shoot[j][i].setCheck2(false);
                           
                        }
                        if(Shoot[j][i].isCheck2()) {
                            Shoot[j][i].render2(j, gc, mainMap.getAbsolutePosX(), mainMap.getAbsolutePosY());
                            Shoot[j][i].setCheck2(false);
                        }
                    }
                }
                if (Finish != null) {
                    gc.setFill(new Color(0, 0, 0, 0.7) );
                    gc.fillRect(100, 100, GameInfo.ScreenWidth.getValue() - 200, GameInfo.ScreenHeight.getValue() - 200);
                    gc.setFont(Font.font("Sans serif", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
                    gc.setFill(Color.YELLOW);
                    gc.setStroke(Color.BLACK);
                    gc.fillText(Finish,
                            490 - 180,
                            490);
                    gc.strokeText(Finish,
                            490 - 180,
                            490);
                }
                
                killshow = "KILL : " + activePlayer.elementAt(thisPlayerID).kill;
                level.setText(killshow);
                timeText.setText(time);
                miniMap.render(gc);
            }
        };

        timeText.setFont(Font.font("Sans serif", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 24));
        timeText.setFill(Color.YELLOW);
        timeText.setStroke(Color.BLACK);
        timeText.setLayoutX(460);
        timeText.setLayoutY(40);
        level.setX(853);
        level.setY(100);
        level.setFont(Font.font("Sans serif", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 24));
        level.setFill(Color.BLUE);
        level.setStrokeWidth(2); 
        level.setStroke(Color.PURPLE);
        root.getChildren().addAll(timeText, level);
        programButtonAnimation.start();
        theStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        theStage.setScene(theScene);
        theStage.show();
    }

    public void back(ActionEvent event) throws IOException
    {
            Parent op = FXMLLoader.load(getClass().getResource("GameStart.fxml"));
            Scene opscene=new Scene(op);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(opscene);
            window.show();
    }
    
}
