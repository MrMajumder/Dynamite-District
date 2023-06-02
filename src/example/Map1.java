package example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map1 {
    private double absolutePosX;
    private double absolutePosY;
    private double tempabsolutePosX;
    private double tempabsolutePosY;
    private double playerPosX;
    private double playerPosY;
    private double mapMoveX;
    private double mapMoveY;
    private int InitX;
    private int InitY;
    private int FinX;
    private int FinY;
    private int playerID;
    private double degree;
    private double velocity;
    private int[][] tileLayer1;
    private int[][] tileLayer2;
    private boolean isXObstructed;
    private boolean isYObstructed;
    protected double bulletx = 0;
    protected double bullety = 0;
    private boolean hit;
    Tile fenceLowObstacle;
    Tile boxObstacle;
    Tile rockMossObstacle;
    Tile rockMossAltObstacle;
    Tile grassCenter;
    Tile bushCenter;


    public Map1(double absolutePosX, double absolutePosY, double degree) {

        this.absolutePosX = absolutePosX;
        this.absolutePosY = absolutePosY;


        this.playerPosX = absolutePosX / GameInfo.TileWidth.getValue();
        this.playerPosY = absolutePosY / GameInfo.TileHeight.getValue();

        this.tempabsolutePosX = absolutePosX;
        this.tempabsolutePosY = absolutePosY;

        this.velocity = 8;
        this.degree = degree;
        this.isXObstructed = false;
        this.isYObstructed = false;


        this.grassCenter = new Tile("image/grassCenter.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), false);
        this.bushCenter = new Tile("image/bush.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), false);
        this.fenceLowObstacle = new Tile("image/fenceLow.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), true);
        this.boxObstacle = new Tile("image/box.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), true);
        this.rockMossObstacle = new Tile("image/rockMoss.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), true);
        this.rockMossAltObstacle = new Tile("image/rockMossAlt.png", GameInfo.TileWidth.getValue(), GameInfo.TileHeight.getValue(), true);
        getMapLayout();
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public void mapTraverse(double degree, String keyPressed)
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

        tempabsolutePosX = absolutePosX;
        absolutePosX += (velocity * Math.cos(Math.toRadians(tempDegree)));
        mapMoveX = -(absolutePosX % GameInfo.TileWidth.getValue());
        playerPosX = absolutePosX / GameInfo.TileWidth.getValue();

        if((tileLayer2[(int) Math.floor(playerPosY)][(int) Math.floor(playerPosX + 0.5)] != 0|| (tileLayer2[(int) Math.floor(playerPosY)][(int) Math.floor(playerPosX - 0.5)] != 0))) {
            isXObstructed = true;
        }
        else {
            isXObstructed = false;
        }

        if (isXObstructed) {
            absolutePosX = tempabsolutePosX;
            mapMoveX = -(absolutePosX % GameInfo.TileWidth.getValue());
        }

        tempabsolutePosY = absolutePosY;
        absolutePosY+= velocity * Math.sin(Math.toRadians(tempDegree));
        mapMoveY = -(absolutePosY % GameInfo.TileHeight.getValue());
        playerPosY = absolutePosY / GameInfo.TileHeight.getValue();

        if((tileLayer2[(int) Math.floor(playerPosY + 0.25)][(int) Math.floor(playerPosX)] != 0) || (tileLayer2[(int) Math.floor(playerPosY - 0.5)][(int) Math.floor(playerPosX)] != 0)) {
            isYObstructed = true;
        }
        else {
            isYObstructed = false;
        }

        if (isYObstructed) {
            absolutePosY = tempabsolutePosY;
            mapMoveY = -(absolutePosY % GameInfo.TileHeight.getValue());
        }

        if (!LobbyController.spawnItems.isEmpty()) {
            if (LobbyController.spawnItems.elementAt(0).getBounds(absolutePosX, absolutePosY).intersects(GameInfo.ScreenWidth.getValue() / 2, GameInfo.ScreenHeight.getValue() / 2, GameInfo.PlayerWidth.getValue(), GameInfo.PlayerHeight.getValue())) {
                LobbyController.activePlayer.elementAt(LobbyController.thisPlayerID).setLife(100);
                LobbyController.spawnItems.elementAt(0).setPicked(true);
                if (Multiplayer.playerID != 1) {
                    Multiplayer.activeHandlers.elementAt(0).broadcastLife();
                    Multiplayer.activeHandlers.elementAt(0).broadcastSpawn(LobbyController.thisPlayerID + "#"+ "SPAWN" + "#" + LobbyController.spawnItems.elementAt(0).isPicked() + "#" + LobbyController.spawnItems.elementAt(0).getAbsolutePosX() + "#" + LobbyController.spawnItems.elementAt(0).getAbsolutePosY());
                }
            }
        }

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

    public void getMapLayout () {

        this.tileLayer1 = new int[100][100];
        this.tileLayer2 = new int[100][100];

        System.out.println(System.getProperty("user.dir") + "\\src\\map\\layout1Layer1.txt");
        File loadedFile = new File(System.getProperty("user.dir") + "\\src\\map\\layout1Layer1.txt");

        Scanner scanner = null;
        try {
            scanner = new Scanner(loadedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";
        int rowNumber = 0;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            //System.out.println(line);
            String[] elements = line.split(" ");
            int elementCount = 0;
            for(String element : elements) {
                int elementValue = Integer.parseInt(element);
                tileLayer1[rowNumber][elementCount] = elementValue;
                elementCount++;
            }
            rowNumber++;
        }

        loadedFile = new File(System.getProperty("user.dir") + "\\src\\map\\Layout1Layer2.txt");
        scanner = null;
        try {
            scanner = new Scanner(loadedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        line = "";
        rowNumber = 0;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            //System.out.println(line);
            String[] elements = line.split(" ");
            int elementCount = 0;
            for(String element : elements) {
                int elementValue = Integer.parseInt(element);
                tileLayer2[rowNumber][elementCount] = elementValue;
                elementCount++;
            }
            rowNumber++;
        }

    }
    void setBulletValue()
    {
        bulletx = playerPosX;
        bullety = playerPosY;
    }
    
    public boolean hitcheck(double degree, double bulletAbsoluteX, double bulletAbsoluteY)
    {
        hit = false;
        bulletx = bulletAbsoluteX / GameInfo.TileWidth.getValue();
        bullety = bulletAbsoluteY / GameInfo.TileHeight.getValue();
//        bulletx = bulletx + (0.2857143* Math.cos(Math.toRadians(this.degree)));
//        bullety = bullety + (0.2857143 * Math.sin(Math.toRadians(this.degree)));

        if(((tileLayer2[(int) Math.floor(bullety)][(int) Math.floor(bulletx-0.5)] != 0)||(tileLayer2[(int) Math.floor(bullety)][(int) Math.floor(bulletx+0.5)] != 0))&&((tileLayer2[(int) Math.floor(bullety-0.5)][(int) Math.floor(bulletx)] != 0)||(tileLayer2[(int) Math.floor(bullety+0.25)][(int) Math.floor(bulletx)] != 0))){
           hit = true;
        }
        
        return hit;
        
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void render (GraphicsContext gc) {
        this.InitX = (int) ((absolutePosX / (GameInfo.TileWidth.getValue() * 1.0)) - (GameInfo.ScreenWidth.getValue() / (2.0 * GameInfo.TileWidth.getValue())));
        this.InitY = (int) ((absolutePosY / (GameInfo.TileHeight.getValue() * 1.0)) - (GameInfo.ScreenHeight.getValue() / (2.0 * GameInfo.TileHeight.getValue())));


        this.FinX = (int) ((GameInfo.ScreenWidth.getValue() /  (1.0 * GameInfo.TileWidth.getValue())) + InitX) ;
        this.FinY = (int) ((GameInfo.ScreenHeight.getValue() / (1.0 * GameInfo.TileHeight.getValue())) + InitY) ;

        for (int j = 0; j<=FinX - InitX; j++) {
            for (int i = 0; i <= FinY - InitY; i++) {

                if ((InitX + i)>=100) continue;
                if ((InitY + j)>=100) continue;

                switch (tileLayer1[InitY + j][InitX + i]) {
                    // (absolutePosX % GameInfo.TileWidth.getValue())
                    case 2:
                        grassCenter.render(gc, i, j, -(absolutePosX % GameInfo.TileWidth.getValue()), -(absolutePosY % GameInfo.TileHeight.getValue()));
                        break;
                    case 3:
                        bushCenter.render(gc, i, j, -(absolutePosX % GameInfo.TileWidth.getValue()), -(absolutePosY % GameInfo.TileHeight.getValue()));
                        break;
                    case 4:
                        break;
                }

                switch (tileLayer2[InitY + j][InitX + i]){
                    case 1: boxObstacle.render(gc, i, j, -(absolutePosX % GameInfo.TileWidth.getValue()), -(absolutePosY % GameInfo.TileHeight.getValue()));
                        break;
                    case 2: rockMossObstacle.render(gc, i, j, -(absolutePosX % GameInfo.TileWidth.getValue()), -(absolutePosY % GameInfo.TileHeight.getValue()));
                        break;
                    case 3: rockMossAltObstacle.render(gc, i, j, -(absolutePosX % GameInfo.TileWidth.getValue()), -(absolutePosY % GameInfo.TileHeight.getValue()));
                        break;
                    case 4:
                        fenceLowObstacle.render(gc, i, j, -(absolutePosX % GameInfo.TileWidth.getValue()), -(absolutePosY % GameInfo.TileHeight.getValue()));
                        break;
                }

//                Font pos = Font.font( "Times New Roman", FontWeight.BOLD, 12 );
//                gc.setFill(Color.BLACK );
//                gc.setFont( pos );
//                gc.fillText( "("+ (InitX + i) + ", " + (InitY + j) + ")", GameInfo.TileWidth.getValue()*i  + GameInfo.TileWidth.getValue()/3 -(absolutePosX % GameInfo.TileWidth.getValue()), GameInfo.TileHeight.getValue()*j + GameInfo.TileHeight.getValue()/3 -(absolutePosY % GameInfo.TileHeight.getValue()));
            }
        }
    }
}
