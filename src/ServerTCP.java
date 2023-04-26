import java.net.*;
import java.io.*;

public class ServerTCP {
    private int port;
    private Connect4 connect4;
    private ServerSocket serverSocket;

    public ServerTCP(int port) {
        this.port = port;
        this.connect4 = new Connect4();
        this.serverSocket = null;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            Socket[] playerSockets = new Socket[2];
            BufferedReader[] in = new BufferedReader[2];
            PrintWriter[] out = new PrintWriter[2];
            int currentPlayer = 1;

            for (int i = 0; i < 2; i++) {
                playerSockets[i] = serverSocket.accept();
                System.out.println("Player " + (i + 1) + " connected");

                in[i] = new BufferedReader(new InputStreamReader(playerSockets[i].getInputStream()));
                out[i] = new PrintWriter(playerSockets[i].getOutputStream(), true);
                out[i].println("You are player " + (i + 1) + ". Waiting for opponent to connect...");
            }

            out[0].println("Opponent connected. You are player 1 and you play first");
            out[1].println("Opponent connected. You are player 2 and you play second");

            boolean gameEnded = false;
            while (!gameEnded) {
                String input = in[currentPlayer - 1].readLine();

                if (input != null) {
                    int column = Integer.parseInt(input);

                    boolean validMove = connect4.addPiece(column);

                    if (!validMove) {
                        out[currentPlayer - 1].println("Invalid move. Try again.");
                    } else {
                        out[0].println(connect4);
                        out[1].println(connect4);

                        if (connect4.isGameOver()) {
                            gameEnded = true;
                            out[0].println("Game over!");
                            out[1].println("Game over!");
                        } else {
                            currentPlayer = (currentPlayer == 1) ? 2 : 1;
                        }
                    }
                }
            }

            playerSockets[0].close();
            playerSockets[1].close();
            System.out.println("Game ended. Players disconnected.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
