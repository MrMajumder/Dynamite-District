package example;


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Vector;

public class Multiplayer {
    public static Vector<PlayerNetworkEntity> activeShadow = new Vector<>();
    public static Vector<HandlePlayer> activeHandlers = new Vector<>();
    static int playerID = 1;
    static int ServerPort = 1234;
    public static ServerSocket ss;
    private Player tempPlayer;
    static boolean inLobby = true;
    static boolean isServer = false;

    public void serverLikeEntity () {
        try {
            ss = new ServerSocket(ServerPort);
            Socket s;
            System.out.println("Game Server awaiting clients...");
        }catch(IOException e){
            e.printStackTrace();
        }

        isServer = true;
        Random rand = new Random();
        Profile profile = Example.thisProfile;
        Player thisPlayer = new Player(0, profile, (double) (rand.nextInt(GameInfo.WorldWidth.getValue() - ((GameInfo.ScreenWidth.getValue()) -100)) + ((GameInfo.ScreenWidth.getValue()) / 2)), (double)  (rand.nextInt(GameInfo.WorldWidth.getValue() - ((GameInfo.ScreenWidth.getValue()) -100)) + ((GameInfo.ScreenHeight.getValue()) / 2)), 5.0, true);
        LobbyController.activePlayer.add(thisPlayer);


        LobbyController.player = thisPlayer;
        LobbyController.thisPlayerID = playerID - 1;

        Thread clientReceiver = new Thread(new Runnable() {
            @Override
            public void run() {
                while (inLobby) {
                    if (acceptedClient()) {
                        if (playerID == 2) {
                            //because no handler is needed for server player
                            HandlePlayer handler = new HandlePlayer(thisPlayer);
                            activeHandlers.add(handler);
                        }

                        System.out.println("Accepted client " + (playerID - 1));

                        System.out.println("Inside handleplayer thread");
                        LobbyController.activePlayer.elementAt(playerID - 1).getShadow().userOutput.println((playerID - 1));

                        for (int i = 1; i<playerID - 1; i++) {
                            System.out.println("Sending info to old clients of new client");
                            LobbyController.activePlayer.elementAt(i).getShadow().userOutput.println("PLAYER_ADD" + "#"+ (playerID-1) + "#" +LobbyController.activePlayer.elementAt(playerID - 1).profile.getName()+ "#" + LobbyController.activePlayer.elementAt(playerID - 1).profile.getImagenum() + "#" + LobbyController.activePlayer.elementAt(playerID - 1).getAbsolutePosX() + "#" + LobbyController.activePlayer.elementAt(playerID - 1).getAbsolutePosY());
                        }

                        for (int i = 0; i<playerID; i++) {
                            System.out.println("Sending info to new client of old client");
                            LobbyController.activePlayer.elementAt(playerID - 1).getShadow().userOutput.println("PLAYER_ADD" + "#"+ i + "#" +LobbyController.activePlayer.elementAt(i).profile.getName()+ "#" + LobbyController.activePlayer.elementAt(i).profile.getImagenum()+ "#" + LobbyController.activePlayer.elementAt(i).getAbsolutePosX() + "#" + LobbyController.activePlayer.elementAt(i).getAbsolutePosY());
                        }
                        Multiplayer.activeShadow.elementAt(playerID - 2).userOutput.println(0 + "#" + "TIME" + "#" + LobbyController.totaltime);
                    }
                }
            }
        });
        clientReceiver.start();

    }

    public void clientLikeEntity () {
        isServer = false;
        try {
            System.out.println("Inside client like entity");
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, ServerPort);
            // obtaining input and output streams
            System.out.println("Client connected");
            InputStream in = s.getInputStream();
            BufferedReader userInput = new BufferedReader(new InputStreamReader(in));
            PrintWriter userOutput = new PrintWriter(s.getOutputStream(), true);
            userOutput.println("PLAYER_ADD#" + Example.thisProfile.getName() + "#" + Example.thisProfile.getImagenum() + "#" + Example.thisProfile.getLevel());
            System.out.println(s);
           
            MyInteger playercount = new MyInteger();
            Random rand = new Random();
           
            try {
                int playerCount = Integer.parseInt(userInput.readLine());
                System.out.println(playerCount + " received from server");
                for (int i = 0; i< playerCount; i++){

                    String playerInfo = userInput.readLine();
                    System.out.println(playerInfo);
                    String [] spliced = playerInfo.split("#", 0);

                    if(spliced[0].equalsIgnoreCase("PLAYER_ADD")) {
                        Profile profile = new Profile(spliced[2], Integer.parseInt(spliced[3]), 1); //change level
                        Player player = new Player(Integer.parseInt(spliced[1]), profile, Double.parseDouble(spliced[4]),  Double.parseDouble(spliced[5]), 5.0, true);
                        LobbyController.activePlayer.add(player);
                        playercount.setInteger(i);


//                        HandlePlayer handler = new HandlePlayer(player);
//                        activeHandlers.add(handler);

                        playerID++;
                    }
                }
                Profile profile = Example.thisProfile;
                String playerInfo = userInput.readLine();
                System.out.println(playerInfo);
                String [] spliced = playerInfo.split("#", 0);
                Player thisPlayer = new Player(Integer.parseInt(spliced[1]), profile, Double.parseDouble(spliced[4]),  Double.parseDouble(spliced[5]), 5.0, true);

                LobbyController.activePlayer.add(thisPlayer);
                LobbyController.player = thisPlayer;
                LobbyController.thisPlayerID = playerID - 1;
                HandlePlayer handler = new HandlePlayer(thisPlayer);
                PlayerNetworkEntity shadow = new PlayerNetworkEntity(playerID-1, s, userInput, userOutput);
                thisPlayer.setShadow(shadow);
                activeShadow.add(shadow);
                activeHandlers.add(handler);

                Thread addOtherplayers = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(inLobby) {

                            try {
                                String playerInfo = userInput.readLine();
                                String[] spliced = playerInfo.split("#", 0);
                                System.out.println(playerInfo);

                                if (spliced[1].equalsIgnoreCase("EXIT")) {
                                    continue;
                                }
                                else if (spliced[1].equalsIgnoreCase("TIME")) {
                                    LobbyController.totaltime = Integer.parseInt(spliced[2]);
                                    LobbyController.endTime = LobbyController.totaltime;
                                }
                                else if (spliced[1].equalsIgnoreCase("START")) {
                                    System.out.println("trying to fire");
                                    LobbyController lobby = JoinGameController.loader.getController();
                                    lobby.Gstart();

                                }


                                if (spliced[0].equalsIgnoreCase("PLAYER_ADD")) {
                                    Profile profile = new Profile(spliced[2], Integer.parseInt(spliced[3]), 1); //change level
                                    Player player = new Player(Integer.parseInt(spliced[1]), profile, Double.parseDouble(spliced[4]),  Double.parseDouble(spliced[5]), 5.0, true);
                                    LobbyController.activePlayer.add(player);


//                                    HandlePlayer handler = new HandlePlayer(player);
//                                    activeHandlers.add(handler);
                                }
                            } catch (IOException e) {
                            }
                        }
                        }
                    });
                addOtherplayers.start();

            } catch (IOException e) {}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean acceptedClient() {
        try {
            Socket acceptedClientSocket = ss.accept();
            System.out.println("New client request received : " + acceptedClientSocket);

            // obtain input and output streams
            BufferedReader userInput = new BufferedReader(new InputStreamReader(acceptedClientSocket.getInputStream()));
            PrintWriter userOutput = new PrintWriter(acceptedClientSocket.getOutputStream(), true);

            // Create a new handler object for handling this request.

            String playerInfo = userInput.readLine();
            System.out.println(playerInfo);
            String [] spliced = playerInfo.split("#", 0);

            if(spliced[0].equalsIgnoreCase("PLAYER_ADD")) {
                Random rand = new Random();
                Profile profile = new Profile(spliced[1], Integer.parseInt(spliced[2]), Integer.parseInt(spliced[3]));
                Player newPlayer = new Player(playerID, profile, (((rand.nextInt(8) * ( GameInfo.WorldWidth.getValue() - GameInfo.ScreenWidth.getValue() - (30 * GameInfo.TileWidth.getValue()))) / 10) + (GameInfo.ScreenWidth.getValue() / 2) + (12 * GameInfo.TileWidth.getValue())), (((rand.nextInt(8) * ( GameInfo.WorldHeight.getValue() - GameInfo.ScreenHeight.getValue() - (30 * GameInfo.TileHeight.getValue()))) / 10) + (GameInfo.ScreenHeight.getValue() / 2) + (12 * GameInfo.TileHeight.getValue())), 5.0, true);

                PlayerNetworkEntity shadow = new PlayerNetworkEntity(playerID, acceptedClientSocket, userInput, userOutput);
                newPlayer.setShadow(shadow);
                activeShadow.add(shadow);
                LobbyController.activePlayer.add(newPlayer);


                tempPlayer = newPlayer;

                playerID++;

                return true;
            }
            else {
                return false;
            }

        } catch (IOException e) {
            System.out.println("Failed to connect to client.");
            return false;
        }
    }
}
