package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Map1 {
//    private double WorldInitPosX;
//    private double WorldFinPosX;
//    private double WorldInitPosY;
//    private double WorldFinPosY;
    private double playerPosX;
    private double playerPosY;
    private double mapMoveX;
    private double mapMoveY;
    private Tile boxTile;
    private Tile sandTile;
    private Tile stoneTile;
    private int InitX;
    private int FinX;
    private int InitY;
    private int FinY;
    private double degree;
    private double velocity;

    public Map1(int playerPosX, int playerPosY, double degree) {

        this.playerPosX = playerPosX;
        this.playerPosY = playerPosY;
        this.InitX = playerPosX - (GameInfo.ScreenWidth.getValue() / (2 * GameInfo.TileWidth.getValue())) - 1;
        this.InitY = playerPosY - (GameInfo.ScreenHeight.getValue() / (2 * GameInfo.TileHeight.getValue())) - 1;
        this.mapMoveX = 0;
        this.mapMoveY = 0;
        this.velocity = 5;
        this.degree = degree;
        this.boxTile = new Tile("images/box.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), 0, 0);
        this.sandTile = new Tile("images/sandCenter.png", 70, 70, 1, 0);
        this.stoneTile = new Tile("images/stoneCenter.png", 70, 70, 2, 0);
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public void mapGoFront(double degree)
    {
        this.degree = degree;
        mapMoveX -= (velocity * Math.cos(Math.toRadians(this.degree)));
        mapMoveY -= velocity * Math.sin(Math.toRadians(this.degree));

        if (Math.abs(mapMoveX) >= GameInfo.TileWidth.getValue()) {
            playerPosX++;
            mapMoveX = 0;
        }
        if (Math.abs(mapMoveY) >= GameInfo.TileHeight.getValue()) {
            playerPosY++;
            mapMoveY = 0;
        }
    }

    //Makes the sprite go back - shaf
    public void mapGoBack (double degree)
    {
        this.degree = degree;
        mapMoveX += velocity * Math.cos(Math.toRadians(this.degree));
        mapMoveY += velocity * Math.sin(Math.toRadians(this.degree));

        if (Math.abs(mapMoveX) >= GameInfo.TileWidth.getValue()) {
            playerPosX--;
            mapMoveX = 0;
        }
        if (Math.abs(mapMoveY) >= GameInfo.TileHeight.getValue()) {
            playerPosY--;
            mapMoveY = 0;
        }
    }

    //Makes the sprite go left - Shaf
    public void mapGoLeft (double degree)
    {
        this.degree = degree;
        double tempDegree = degree + 90;
        mapMoveX += velocity * Math.cos(Math.toRadians(tempDegree));
        mapMoveY += velocity * Math.sin(Math.toRadians(tempDegree));

        if (Math.abs(mapMoveX) >= GameInfo.TileWidth.getValue()) {
            playerPosX--;
            mapMoveX = 0;
        }
        if (Math.abs(mapMoveY) >= GameInfo.TileHeight.getValue()) {
            playerPosY--;
            mapMoveY = 0;
        }
    }

    //Makes the sprite go right - Shaf
    public void mapGoRight (double degree)
    {
        this.degree = degree;
        double tempDegree = degree + 90;
        mapMoveX -= velocity * Math.cos(Math.toRadians(tempDegree));
        mapMoveY -= velocity * Math.sin(Math.toRadians(tempDegree));

        if (Math.abs(mapMoveX) == GameInfo.TileWidth.getValue()) {
            playerPosX++;
            mapMoveX = 0;
        }
        if (Math.abs(mapMoveY) == GameInfo.TileHeight.getValue()) {
            playerPosY++;
            mapMoveY = 0;
        }
    }

//    public double getWorldInitPosX() {
////        return WorldInitPosX;
////    }
////
////    public void setWorldInitPosX(double worldInitPosX) {
////        WorldInitPosX = worldInitPosX;
////    }
////
////    public double getWorldInitPosY() {
////        return WorldInitPosY;
////    }
////
////    public void setWorldInitPosY(double worldInitPosY) {
////        WorldInitPosY = worldInitPosY;
////    }


    public void render (GraphicsContext gc) {
        this.InitX = (int) playerPosX - (GameInfo.ScreenWidth.getValue() / (2 * GameInfo.TileWidth.getValue())) ;
        this.InitY = (int) playerPosY - (GameInfo.ScreenHeight.getValue() / (2 * GameInfo.TileHeight.getValue()));

        this.FinX = (GameInfo.ScreenWidth.getValue() /  GameInfo.TileWidth.getValue()) + InitX +1;
        this.FinY = (GameInfo.ScreenHeight.getValue()) / GameInfo.TileHeight.getValue() + InitY +1;

        for (int i = -2; i<=FinX - InitX; i++) {
            for (int j = -2; j<=FinY - InitY; j++) {
                //if (j %3 ==0)
                boxTile.render(gc, i, j, mapMoveX, mapMoveY);
                //if (j %3 ==1) sandTile.render(gc, i, j, mapMoveX, mapMoveY);
                //if (j %3 ==2) stoneTile.render(gc, i, j, mapMoveX, mapMoveY);
                Font pos = Font.font( "Times New Roman", FontWeight.BOLD, 12 );
                gc.setFill(Color.BLACK );
                gc.setFont( pos );
                gc.fillText( "("+ (InitX + i) + ", " + (InitY + j) + ")", GameInfo.TileWidth.getValue()*i  + GameInfo.TileWidth.getValue()/2-5 + mapMoveX, GameInfo.TileHeight.getValue()*j + GameInfo.TileHeight.getValue()/2 -5 + mapMoveY);
            }
        }
    }
}
