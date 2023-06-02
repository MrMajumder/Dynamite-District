package example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LifeDraw {

    public void draw (GraphicsContext gc, int health) {
        double x = (2 * GameInfo.ScreenWidth.getValue()) / 15;
        double y = (2 * GameInfo.ScreenHeight.getValue()) / 15;
        double boxWidth = (2.5 * GameInfo.ScreenWidth.getValue()) / 15;
        double lifeWidth = (2.5 * health * GameInfo.ScreenWidth.getValue()) / (15 * 100);
        double boxHeight = (2 * GameInfo.ScreenHeight.getValue()) / 30;

        gc.setFill(Color.ROSYBROWN);
        gc.setStroke(Color.WHITESMOKE);
        gc.strokeRect(x, y, boxWidth, boxHeight);
        gc.fillRect(x, y, lifeWidth, boxHeight);
    }
}
