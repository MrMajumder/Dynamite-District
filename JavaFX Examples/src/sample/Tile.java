package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Tile {
    private Image tileImage;
    private double tileWidth;
    private double tileHeight;
    private int posX;
    private int posY;

    public Tile(String imageURL, double tileWidth, double tileHeight, int posX, int posY) {
        this.tileImage = new Image(imageURL, tileWidth, tileHeight, true, true);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.posX = posX;
        this.posY = posY;
    }

    public void render(GraphicsContext gc, int x, int y, double mapMoveX, double mapMoveY)
    {
        gc.drawImage(tileImage, tileWidth*x + mapMoveX, tileHeight*y + mapMoveY);
    }
}
