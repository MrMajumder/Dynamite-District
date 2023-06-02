package example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MiniMap {
    private int miniWidth;
    private int miniHeight;
    private int mapRatio;
    public MiniMap () {
        this.mapRatio = 40;
        this.miniWidth = GameInfo.WorldWidth.getValue() / mapRatio;
        this.miniHeight = GameInfo.WorldHeight.getValue() / mapRatio;
    }
    public void render (GraphicsContext gc) {
        gc.setFill( new Color(0.467, 0.533, 0.6, 0.4) );
        gc.fillRect(20, GameInfo.ScreenHeight.getValue() - miniHeight - 20, miniWidth, miniHeight);

        for (Player player: LobbyController.activePlayer) {
            if (!player.getIsAlive() || !player.getIsActive()) {
                continue;
            }

            double playerX  = (player.getAbsolutePosX() / mapRatio) + 20;
            double playerY = GameInfo.ScreenHeight.getValue() - miniHeight + (player.getAbsolutePosY() / mapRatio) - 20;
            double rectX  = ((player.getAbsolutePosX() - (GameInfo.ScreenWidth.getValue() / 2)) / mapRatio) + 20;
            double rectY  = GameInfo.ScreenHeight.getValue() - miniHeight + ((player.getAbsolutePosY() - GameInfo.ScreenHeight.getValue() / 2) / mapRatio) - 20;
            double rectWidth = GameInfo.ScreenWidth.getValue() / mapRatio;
            double rectHeight = GameInfo.ScreenHeight.getValue() / mapRatio;

            if (player.getPlayerID() == LobbyController.thisPlayerID) {
                gc.setFill(Color.BLUE);
                gc.setStroke(Color.ALICEBLUE);
                gc.strokeRect(rectX, rectY, rectWidth, rectHeight);
            }
            else {
                gc.setFill(Color.RED);
                gc.setStroke(Color.ROSYBROWN);
            }
            gc.fillOval(playerX - 4, playerY - 4, 8, 8);
            gc.strokeOval(playerX - 4.5, playerY - 4.5, 9, 9);

        }

        for (Spawn spawn: LobbyController.spawnItems) {
            double spriteX = (spawn.getAbsolutePosX() / mapRatio) + 20;
            double spriteY =  GameInfo.ScreenHeight.getValue() - miniHeight + (spawn.getAbsolutePosY() / mapRatio) - 20;
            if (!spawn.isPicked()) {
                gc.setFill(Color.YELLOW);
                gc.setStroke(Color.WHITE);
                gc.fillOval(spriteX - 3, spriteY - 3, 6, 6);
                gc.strokeOval(spriteX - 3.5, spriteY - 3.5, 7, 7);
            }
        }

    }
}
