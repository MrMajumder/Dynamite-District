package example;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerNetworkEntity {
    private int playerID;
    final BufferedReader userInput;
    final PrintWriter userOutput;
    private Socket s;

    public PlayerNetworkEntity(int playerID, Socket s, BufferedReader userInput, PrintWriter userOutput) {
        this.userInput = userInput;
        this.userOutput = userOutput;
        this.s = s;
        this.playerID = playerID;
    }

    public Socket getS() {
        return s;
    }
}
