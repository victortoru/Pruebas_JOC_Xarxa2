import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String serverResponse;

            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);

                if (serverResponse.equals("JUEGO_TERMINADO")) {
                    break;
                }

                if (serverResponse.equals("TU_TURNO")) {
                    System.out.println("Ingresa el nº de columna donde quieres tirar (0-6):");
                    String userInput = stdIn.readLine();
                    out.println(userInput);
                }

            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
