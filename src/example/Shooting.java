
package example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.w3c.dom.css.Rect;

import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Shooting {
   
    private Map1 mp1;
    private MyImage image;
    private MyImage image2;
    private MyImage blood;
    private double absolutePosX;
    private double absolutePosY;
    private double returnX;
    private double returnY;
    public boolean check;
    private double speed = 30;
    static  int objNum = 0;
    protected double degree1;
    private double height;
    private double width;
    private boolean hit = false;
    public int bulletNumber = 100;
    private boolean check2;
    private double AX;
    private double AY;
    private Random rand = new Random();

    public boolean isCheck2() {
        return check2;
    }

    public void setCheck2(boolean check2) {
        this.check2 = check2;
    }
    
    public Shooting(String url,double x,double y, Map1 mp, boolean check)
    {
        image = new MyImage();
        image2 = new MyImage();
        blood = new MyImage();
        height = 40;
        width = 30;
        this.image.makeMyImage(url, height, width);
        this.image2.makeMyImage("image/gunshot.png", 100, 100);
        this.blood.makeMyImage("image/bloodSplash.png", 100, 100);
        mp1 = mp;
        absolutePosX = x;
        absolutePosY = y;
        this.check = false;

    }

    public void updateAbsolutePosX() {
         this.absolutePosX=this.absolutePosX + speed * Math.cos(Math.toRadians(this.degree1));
         //AX = absolutePosX;
    }

    public void updateAbsolutePosY() {
        this.absolutePosY = this.absolutePosY + speed * Math.sin(Math.toRadians(this.degree1));
        //AY = absolutePosY;
    }

    public void setabsolutePosX(double absolutePosX) {
        
        this.absolutePosX = absolutePosX + (75 * Math.cos(Math.toRadians(degree1))) + (35 * Math.cos(Math.toRadians(degree1 + 90)));//(20 * Math.cos(Math.toRadians(this.degree1)));
        AX = absolutePosX;
    }

    public void setabsolutePosY(double absolutePosY) {
         this.absolutePosY = absolutePosY + (75 * Math.sin(Math.toRadians(degree1))) + (35 * Math.cos(Math.toRadians(degree1)));// + (20 * Math.sin(Math.toRadians(this.degree1)));//+ speed * Math.sin(Math.toRadians(this.degree1));
         AY = absolutePosY;
    }
    public double getReturnX() {
        returnX = absolutePosX;
        return returnX;
    }

    public double getReturnY() {
        returnY = absolutePosY;
        return returnY;
    }
    public boolean Hcheck(GraphicsContext gc, int playerID, double midPosX, double midPosY)
    {
        updateAbsolutePosX();
        updateAbsolutePosY();
        if(check){
            double x = absolutePosX - (midPosX - (GameInfo.ScreenWidth.getValue()/2)) - width/2;
            double y = absolutePosY - (midPosY - (GameInfo.ScreenHeight.getValue()/2)) - height/2;

            for (int i = 0; i < LobbyController.activePlayer.size(); i++) {
                if (i == playerID || !LobbyController.activePlayer.elementAt(i).getIsAlive() || !LobbyController.activePlayer.elementAt(i).getIsActive()) {
                    continue;
                }
                double playerWidth = LobbyController.activePlayer.elementAt(i).getPlayerWidth();
                double playerHeight = LobbyController.activePlayer.elementAt(i).getPlayerHeight();
                //System.out.println(playerWidth + " " + playerHeight);
                double playerX = LobbyController.activePlayer.elementAt(i).getAbsolutePosX() - (midPosX - (GameInfo.ScreenWidth.getValue()/2))- (playerWidth/2);
                double playerY = LobbyController.activePlayer.elementAt(i).getAbsolutePosY() - (midPosY - (GameInfo.ScreenHeight.getValue()/2))- (playerHeight/2);

                javafx.geometry.Rectangle2D playerBound = new javafx.geometry.Rectangle2D(playerX, playerY, playerWidth, playerHeight);

                if (playerBound.contains(x, y, width, height)) {
                    if (i == LobbyController.thisPlayerID) {
                        LobbyController.activePlayer.elementAt(i).decreaseLife(20);

                        if (LobbyController.activePlayer.elementAt(i).getLife() <= 0) {
                            if (LobbyController.activePlayer.elementAt(i).isIsAlive()) {
                                LobbyController.activePlayer.elementAt(i).setIsAlive(false);
                                LobbyController.activePlayer.elementAt(i).death++;
                                LobbyController.activePlayer.elementAt(playerID).kill++;
                                Multiplayer.activeHandlers.elementAt(0).broadcastKill();
                                Multiplayer.activeHandlers.elementAt(0).broadcastKill(playerID);
                                System.out.println("life broadcasted for " + i);
                                respawnUtil(i);
                            }
                        }
                    }
                    Multiplayer.activeHandlers.elementAt(0).broadcastLife();
                    gc.drawImage( blood.setRotate(this.degree1), playerX, playerY);
                    return true;
                }
            }
            if(x >= 0 && x <= GameInfo.ScreenWidth.getValue() && y >= 0 && y <= GameInfo.ScreenHeight.getValue()) {
                hit = mp1.hitcheck(this.degree1, absolutePosX, absolutePosY);
                return hit;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }
    public void updateAbsolute(double X, double Y)
    {
        absolutePosX = X;
        absolutePosY = Y;
    }

    public void render(int playerID, GraphicsContext gc, double midPosX, double midPosY)
    {


        if(!(Hcheck(gc, playerID, midPosX, midPosY))) {
            gc.drawImage( image.setRotate(this.degree1), (absolutePosX - (midPosX - GameInfo.ScreenWidth.getValue()/2)) - image.getWidth()/2, (absolutePosY - (midPosY - GameInfo.ScreenHeight.getValue()/2)) - image.getHeight()/2);

            // image.setRotate(this.degree1), (absolutePosX - (midPosX - GameInfo.ScreenWidth.getValue()/2)) - image.getWidth()/2, (absolutePosY - (midPosY - GameInfo.ScreenHeight.getValue()/2)) - image.getHeight()/2);
        }
        else {
            check = false;
        }

    }
    public void render2(int playerID, GraphicsContext gc, double midPosX, double midPosY)
    {

        gc.drawImage(image2.setRotate(this.degree1), (AX - (midPosX - GameInfo.ScreenWidth.getValue()/2)) - image2.getWidth()/2 + (75 * Math.cos(Math.toRadians(degree1)))+ (35 * Math.cos(Math.toRadians(degree1 + 90))), (AY - (midPosY - GameInfo.ScreenHeight.getValue()/2)) - image2.getHeight()/2 + (75 * Math.sin(Math.toRadians(degree1))) + (35 * Math.cos(Math.toRadians(degree1))));
    }

    public void respawnUtil (int index) {

        Random rand = new Random();
        Timer respawn = new Timer();
        respawn.schedule(new TimerTask(){
            public void run(){
                LobbyController.activePlayer.elementAt(index).setIsAlive(true);
                LobbyController.activePlayer.elementAt(index).setLife(100);
                LobbyController.activePlayer.elementAt(index).setNewlyAlive(true);
                LobbyController.activePlayer.elementAt(index).updateAbsolute((((rand.nextInt(8) * ( GameInfo.WorldWidth.getValue() - GameInfo.ScreenWidth.getValue() - (30 * GameInfo.TileWidth.getValue()))) / 10) + (GameInfo.ScreenWidth.getValue() / 2) + (12 * GameInfo.TileWidth.getValue())), (((rand.nextInt(8) * ( GameInfo.WorldHeight.getValue() - GameInfo.ScreenHeight.getValue() - (30 * GameInfo.TileHeight.getValue()))) / 10) + (GameInfo.ScreenHeight.getValue() / 2) + (12 * GameInfo.TileHeight.getValue())));
                Multiplayer.activeHandlers.elementAt(0).broadcastLife();
                Multiplayer.activeHandlers.elementAt(0).broadcastMovement();
                System.out.println("Respawn completed");
                respawn.cancel();
            }
        }, 5000);

    }
}
