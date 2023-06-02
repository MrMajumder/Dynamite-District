/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Sprite{
    

    private PlayerNetworkEntity shadow;
    private boolean isShooting;
    private boolean isAlive;
    private boolean isActive;
    private boolean isReady;
    private boolean newlyAlive;
    private int playerID;
    Profile profile;
    private double absolutePosX;
    private double absolutePosY;
    private int life;
    int kill;
    int death;
    public Player(int ID,Profile profile, double absolutePosX, double absolutePosY, double velocity, boolean isAlive) {
        super(profile.getImage(), absolutePosX, absolutePosY, velocity, GameInfo.PlayerWidth.getValue(), GameInfo.PlayerHeight.getValue());
        this.playerID = ID;
        this.isAlive = isAlive;
        this.isActive = true;
        this.isReady = false;
        this.profile = profile;
        this.life = 100;
        newlyAlive = true;
        kill = 0;
        death = 0;
    }

    public PlayerNetworkEntity getShadow() {
        return shadow;
    }

    public void setShadow(PlayerNetworkEntity shadow) {
        this.shadow = shadow;
    }

    public double getPlayerX() {
        return super.getPositionX();
    }

    public double getPlayerY() {
        return super.getPositionY();
    }

    public double getPlayerWidth () {
        return super.getWidth();
    }

    public double getPlayerHeight () {
        return super.getHeight();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public boolean isIsReady() {
        return isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady =
                isReady;
    }

    public boolean isNewlyAlive() {
        return newlyAlive;
    }

    public void setNewlyAlive(boolean newlyAlive) {
        this.newlyAlive = newlyAlive;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void decreaseLife(int life) {
        this.life -= life;
    }

    public void setXY (double x, double y) {
        super.setPositionX(x);
        super.setPositionY(y);
    }



    public void setIsShooting(boolean isShooting) {
        this.isShooting = isShooting;
    }

    public boolean getIsShooting() {
        return isShooting;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive( boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public double getDegree() {
        return super.getDegree();
    }

    public void draw (GraphicsContext gc) {

        double boxWidth = (3 * GameInfo.ScreenWidth.getValue()) / 15;
        double lifeWidth = (3 * life * GameInfo.ScreenWidth.getValue()) / (15 * 100);
        double boxHeight = (0.9 * GameInfo.ScreenHeight.getValue()) / 30;
        double x = GameInfo.ScreenWidth.getValue() - boxWidth - 10;
        double y = (4 * GameInfo.ScreenHeight.getValue()) / 30;

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x, y, boxWidth, boxHeight);
        gc.fillRect(x, y, lifeWidth, boxHeight);
    }

}
