
package example;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Sprite {
    private MyImage image;
    private double positionX;
    private double positionY;
    private double tempPosX;
    private double tempPosY;
    private double velocity;
    private double width;
    private double height;
    private double degree;
    private double absolutePosX;
    private double absolutePosY;
   

    /*Need to add some methods like _ collision detection (creating an invisible rectangle obj of width and height around the sprite), shoot method (this can also be added in a separate class)*/

    public Sprite(){}

    //constructor - Shaf
    public Sprite(String imageURL, double absolutePosX, double absolutePosY, double velocity, double width, double height) {
        this.image = new MyImage();
        this.image.makeMyImage(imageURL, width, height);
        this.positionX = GameInfo.ScreenWidth.getValue();
        this.positionY = GameInfo.ScreenHeight.getValue();
        this.absolutePosX = absolutePosX;
        this.absolutePosY = absolutePosY;
        this.tempPosX = positionX;
        this.tempPosY = positionY;
        this.velocity = velocity;
        
        this.width = 150;
        this.height = 150;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
    //returns the width of the sprite - Shaf
    public double getWidth() {
        return width;
    }

    //returns the height of the sprite - Shaf
    public double getHeight() {
        return height;
    }

    public double getAbsolutePosX() {
        return absolutePosX;
    }

    public void setAbsolutePosX(double absolutePosX) {
        this.absolutePosX = absolutePosX;
    }

    public double getAbsolutePosY() {
        return absolutePosY;
    }

    public void setAbsolutePosY(double absolutePosY) {
        this.absolutePosY = absolutePosY;
    }

    //Sets the degree orientation of the Sprite from x - axis - Shaf
    public void setDegree(double degree) {
        this.degree = degree;
    }

    public double getDegree() {
        return degree;
    }

    //Makes the sprite go forward - Shaf
    public void spriteTraverse(double degree, String keyPressed)
    {
        this.degree = degree;
        double tempDegree = degree;
        switch (keyPressed){
            case "A": tempDegree -= 90;
                break;
            case "S": tempDegree += 180;
                break;
            case "D": tempDegree += 90;
                break;
        }

        tempPosX = positionX;
        positionX += velocity * Math.cos(Math.toRadians(tempDegree));
        //(GameInfo.ScreenHeight.getValue() / 2 )

        if(!((positionX >= (980 - (GameInfo.ScreenHeight.getValue() / 2)) && positionX <= (980)) || (positionX >= (0) && positionX <= (GameInfo.ScreenHeight.getValue() / 2 )))){
            positionX = tempPosX;
        }

        tempPosY = positionY;
        positionY += velocity * Math.sin(Math.toRadians(tempDegree));
        if(!((positionY >= (7000 - (GameInfo.ScreenWidth.getValue() / 2)) && positionY <= (7000)) || (positionY >= (0) && positionY <= (GameInfo.ScreenWidth.getValue() / 2 )))){
            positionY = tempPosY;
        }
    }

    public Rectangle2D getBounds( double midPosX, double midPosY){
        return new Rectangle2D(((absolutePosX - (midPosX - GameInfo.ScreenWidth.getValue()/2)) - image.getWidth()/2), ((absolutePosY - (midPosY - GameInfo.ScreenHeight.getValue()/2) - image.getHeight()/2)), width, height);
    }

    //Technically draws the sprite in the canvas - Shaf
    public void render(GraphicsContext gc, double midPosX, double midPosY)
    {
        if(((midPosX - GameInfo.ScreenWidth.getValue()/2) <= absolutePosX) && ((midPosX + GameInfo.ScreenWidth.getValue()/2)>= absolutePosX) && ((midPosY - GameInfo.ScreenHeight.getValue()/2) <= absolutePosY) && ((midPosY + GameInfo.ScreenHeight.getValue()/2)>= absolutePosY)) {
            gc.drawImage( image.setRotate(this.degree),  (absolutePosX - (midPosX - GameInfo.ScreenWidth.getValue()/2)) - image.getWidth()/2, (absolutePosY - (midPosY - GameInfo.ScreenHeight.getValue()/2)) - image.getHeight()/2);
            //((midPosX - GameInfo.ScreenWidth.getValue()/2) <= absolutePosX) && ((midPosX + GameInfo.ScreenWidth.getValue()/2)>= absolutePosX) && ((midPosY - GameInfo.ScreenHeight.getValue()/2) <= absolutePosY) && ((midPosY + GameInfo.ScreenHeight.getValue()/2)>= absolutePosY)) {
            //            gc.drawImage( image.setRotate(this.degree),  (absolutePosX - (midPosX - GameInfo.ScreenWidth.getValue()/2)) - image.getWidth()/2, (absolutePosY - (midPosY - GameInfo.ScreenHeight.getValue()/2)) - image.getHeight()/2
        }
    }
    public void updateAbsolute (double midPosX, double midPosY) {
        this.absolutePosX = midPosX;
        this.absolutePosY = midPosY;
    }
    
}
