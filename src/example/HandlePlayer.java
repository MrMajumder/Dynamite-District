package example;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HandlePlayer {
    Player player;

    public HandlePlayer(Player player) {
        this.player = player;
    }

    public void handle() {

        Thread handlePlayer = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Entering handlePlayer1 thread");
                playerUpdate();
            }
        });
        handlePlayer.start();

//        Thread activity = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                activityUpdate();
//            }
//        });
//        activity.start();


    }

    public void playerUpdate () {
        while (player.getIsActive()) {
            try {
                for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
                    if (!Multiplayer.activeShadow.elementAt(i).userInput.ready()) {
                        continue;
                    }
                    String msg = Multiplayer.activeShadow.elementAt(i).userInput.readLine();
                    String [] spliced = msg.split("#", 0);

                    if (!spliced[1].equalsIgnoreCase("DEGREE") && spliced[1].equalsIgnoreCase("MOVE")) {
                        System.out.println(msg);
                    }

                    if (spliced[1].equalsIgnoreCase("KILL")) {
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).kill = Integer.parseInt(spliced[2]);
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).death = Integer.parseInt(spliced[3]);
                    }

                    if(player.getPlayerID() == 0) {
                        for (int j = 0; j < Multiplayer.activeShadow.size(); j++) {
                            if ((j + 1) == Integer.parseInt(spliced[0])) {
                                continue;
                            }
                            Multiplayer.activeShadow.elementAt(j).userOutput.println(msg);
                        }
                    }

                    if (Integer.parseInt(spliced[0]) == LobbyController.thisPlayerID) {
                        System.out.println("Keep this in mind");
                    }
                    //format ID#MOVE#playerPosX#PlayerPosY
                    //format ID#DEGREE#degree
                    //format ID#SHOOT#isShooting
                    //format ID#LIFE#isAlive#life
                    //format ID#SPAWN#isPicked#PosX#PosY
                    //format ID#KILL#kill#death


                    if (Integer.parseInt(spliced[0]) == LobbyController.thisPlayerID) {
                        continue;
                    }

                    if (spliced[1].equalsIgnoreCase("MOVE")) {
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).setAbsolutePosX(Double.parseDouble(spliced[2]));
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).setAbsolutePosY(Double.parseDouble(spliced[3]));
                    }
                    else if (spliced[1].equalsIgnoreCase("DEGREE")) {
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).setDegree(Double.parseDouble(spliced[2]));
                    }
                    else if (spliced[1].equalsIgnoreCase("SHOOT")) {
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).setIsShooting(Boolean.parseBoolean(spliced[2]));
                    }
                    else if (spliced[1].equalsIgnoreCase("LIFE")) {
                        LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).setIsAlive(Boolean.parseBoolean(spliced[2]));
                        if (Boolean.parseBoolean(spliced[2])) {
                            LobbyController.activePlayer.elementAt(Integer.parseInt(spliced[0])).setLife(Integer.parseInt(spliced[3]));
                        }
                    }
                    else if (spliced[1].equalsIgnoreCase("SPAWN")) {
                        LobbyController.spawnItems.elementAt(0).setPicked(Boolean.parseBoolean(spliced[2]));
                        LobbyController.spawnItems.elementAt(0).updateAbsolute(Double.parseDouble(spliced[3]), Double.parseDouble(spliced[4]));
                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMovement () {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(player.getPlayerID() + "#" + "MOVE" + "#" + player.getAbsolutePosX() + "#" + player.getAbsolutePosY());
            if (i == LobbyController.thisPlayerID) {

            }
        }
    }
    public void broadcastMovement (int index) {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(index + "#" + "MOVE" + "#" + LobbyController.activePlayer.elementAt(index).getAbsolutePosX() + "#" + LobbyController.activePlayer.elementAt(index).getAbsolutePosY());
        }
    }

    public void broadcastDegree () {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(player.getPlayerID() + "#" + "DEGREE" + "#" + player.getDegree());
        }
    }

    public void broadcastLife () {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(player.getPlayerID() + "#" + "LIFE" + "#" + player.getIsAlive() + "#" + player.getLife());
        }
    }

    public void broadcastLife (int index) {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(index + "#" + "LIFE" + "#" + LobbyController.activePlayer.elementAt(index).getIsAlive() + "#" + LobbyController.activePlayer.elementAt(index).getLife());
        }
    }

    public void broadcastShoot (String msg) {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(msg);
        }
    }

    public void broadcastSpawn (String msg) {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(msg);
        }
    }
    
    public void broadcastKill () {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(player.getPlayerID() + "#" + "KILL" + "#" + player.kill + "#" + player.death);
        }
    }

    public void broadcastKill (int index) {
        for (int i = 0; i < Multiplayer.activeShadow.size(); i++) {
            Multiplayer.activeShadow.elementAt(i).userOutput.println(index + "#" + "KILL" + "#" + LobbyController.activePlayer.elementAt(index).kill + "#" + LobbyController.activePlayer.elementAt(index).death);
        }
    }

    public void activityUpdate () {
        while (player.getIsActive()) {
            try {
                String msg = player.getShadow().userInput.readLine();
                String[] spliced = msg.split("#", 0);

                if(spliced[0].equalsIgnoreCase(String.valueOf(player.getPlayerID())) && spliced[1].equalsIgnoreCase("ACTIVITY")) {
                    if (!Boolean.parseBoolean(spliced[2])) {
                        //house keeping code
                        player.setIsAlive(false);
                        player.setIsActive(false);
                        Multiplayer.activeShadow.remove(player.getShadow());
                        LobbyController.activePlayer.remove(player);
                        player.getShadow().userInput.close();
                        player.getShadow().userOutput.close();
                        player.getShadow().getS().close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
