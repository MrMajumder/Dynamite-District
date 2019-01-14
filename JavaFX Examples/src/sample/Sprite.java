package sample;

import javafx.scene.canvas.GraphicsContext;

public class Sprite {
    private MyImage image;
    private double positionX;
    private double positionY;
    private double velocity;
    private double width;
    private double height;
    private double degree;
    private int mapPosX;
    private int mapPosY;

    /*Need to add some methods like _ collision detection (creating an invisible rectangle obj of width and height around the sprite), shoot method (this can also be added in a separate class)*/


    //constructor - Shaf
    public Sprite(String imageURL, double positionX, double positionY, double velocity, int mapX, int mapY) {
        this.image = new MyImage();
        this.image.makeMyImage(imageURL, 150, 150);
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocity = velocity;
        this.width = 150;
        this.height = 150;
        this.mapPosX = mapX;
        this.mapPosY = mapY;
    }

    //returns the width of the sprite - Shaf
    public double getWidth() {
        return width;
    }

    //returns the height of the sprite - Shaf
    public double getHeight() {
        return height;
    }

    public int getMapPosX() {
        return mapPosX;
    }

    public void setMapPosX(int mapPosX) {
        this.mapPosX = mapPosX;
    }

    public int getMapPosY() {
        return mapPosY;
    }

    public void setMapPosY(int mapPosY) {
        this.mapPosY = mapPosY;
    }

    //Sets the degree orientation of the Sprite from x - axis - Shaf
    public void setDegree(double degree) {
        this.degree = degree;
    }

    //Makes the sprite go forward - Shaf
    public void goFront(double degree)
    {
        this.degree = degree;
        positionX += velocity * Math.cos(Math.toRadians(this.degree));
        positionY += velocity * Math.sin(Math.toRadians(this.degree));
    }

    //Makes the sprite go back - shaf
    public void goBack (double degree)
    {
        this.degree = degree;
        positionX -= velocity * Math.cos(Math.toRadians(this.degree));
        positionY -= velocity * Math.sin(Math.toRadians(this.degree));
    }

    //Makes the sprite go left - Shaf
    public void goLeft (double degree)
    {
        this.degree = degree;
        double tempDegree = degree + 90;
        positionX -= velocity * Math.cos(Math.toRadians(tempDegree));
        positionY -= velocity * Math.sin(Math.toRadians(tempDegree));
    }

    //Makes the sprite go right - Shaf
    public void goRight (double degree)
    {
        this.degree = degree;
        double tempDegree = degree + 90;
        positionX += velocity * Math.cos(Math.toRadians(tempDegree));
        positionY += velocity * Math.sin(Math.toRadians(tempDegree));
    }

//    public boolean shouldMove () {
//        return
//    }

    //Technically draws the sprite in the canvas - Shaf
    public void render(GraphicsContext gc)
    {
        gc.drawImage( image.setRotate(this.degree), positionX - image.getWidth()/2, positionY - image.getHeight()/2 );
    }
}
