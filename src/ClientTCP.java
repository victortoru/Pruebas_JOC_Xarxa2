import java.io.*;
import java.net.*;

public class ClientTCP {

    public static void main(String args[]) {

        String serverHostname = "localhost";
        int serverPort = 8000;

        try {
            Socket socket = new Socket(serverHostname, serverPort);
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);

            // Enviamos un mensaje al servidor para solicitar el tablero
            writer.println("GET_BOARD");

            // Leemos la respuesta del servidor (el tablero)
            String boardString = reader.readLine();

            // Imprimimos el tablero en la consola del cliente en un formato adecuado
            System.out.println("+---+---+---+");
            for (int row = 0; row < 3; row++) {
                System.out.print("| ");
                for (int col = 0; col < 3; col++) {
                    int pos = row * 3 + col;
                    char mark = boardString.charAt(pos);
                    System.out.print(mark + " | ");
                }
                System.out.println("\n+---+---+---+");
            }

            // Cerramos la conexión
            socket.close();

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S en la conexión al servidor: " + e.getMessage());
        }
    }
}