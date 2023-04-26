import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("Connected to server");

        Scanner scanner = new Scanner(System.in);

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            // Print the board sent by the server
            String boardString = input.readLine();
            System.out.println(boardString);

            // Ask the user for a move
            System.out.println("Enter the column where you want to drop your piece (1-7): ");
            int column = scanner.nextInt();

            // Send the move to the server
            output.println(column);
        }
    }
}