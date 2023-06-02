package example;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {
    private Image tileImage;
    private double tileWidth;
    private double tileHeight;
    private boolean isObstacle;


    public Tile(String imageURL, double tileWidth, double tileHeight, boolean isObstacle) {
        this.tileImage = new Image(imageURL, tileWidth, tileHeight, true, true);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.isObstacle = isObstacle;
    }


    public void render(GraphicsContext gc, int x, int y, double mapMoveX, double mapMoveY)
    {
        gc.drawImage(tileImage, tileWidth*x + mapMoveX, tileHeight*y + mapMoveY);
    }
}
