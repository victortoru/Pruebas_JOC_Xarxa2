import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerTCP {
 private static final int COLUMNAS = 7;
 private static final int FILAS = 6;
 private static char[][] tablero;

 public static void main(String[] args) {
  try {
   ServerSocket serverSocket = new ServerSocket(1234);
   System.out.println("Servidor conectado. Esperando cliente...");

   Socket clientSocket = serverSocket.accept();
   System.out.println("Cliente conectado.");

   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

   tablero = new char[FILAS][COLUMNAS];
   reiniciarTablero();

   String clientInput;
   String serverResponse;

   // Enviar tablero inicial al cliente, incluyendo la señal de turno
   serverResponse = obtenerTableroComoString() + "TU_TURNO";
   out.println(serverResponse);

   while ((clientInput = in.readLine()) != null) {
    if (clientInput.equals("SALIR")) {
     break;
    }

    int columna = Integer.parseInt(clientInput);
    if (esJugadaValida(columna)) {
     realizarJugada(columna, 'X');

     if (hayGanador('X')) {
      serverResponse = "¡Has ganado! Felicidades.";
      out.println(serverResponse);
      out.println("JUEGO_TERMINADO");
      break;
     }

     realizarJugadaAleatoria('O');
     if (hayGanador('O')) {
      serverResponse = "¡Has perdido! Intenta de nuevo.";
      out.println(serverResponse);
      out.println("JUEGO_TERMINADO");
      break;
     }

     serverResponse = obtenerTableroComoString() + "TU_TURNO";
     out.println(serverResponse);
    } else {
     serverResponse = "Columna inválida. Intenta de nuevo.";
     out.println(serverResponse);
    }
   }

   clientSocket.close();
   serverSocket.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 private static void reiniciarTablero() {
  for (int i = 0; i < FILAS; i++) {
   for (int j = 0; j < COLUMNAS; j++) {
    tablero[i][j] = '-';
   }
  }
 }

 private static boolean esJugadaValida(int columna) {
  return columna >= 0 && columna < COLUMNAS && tablero[0][columna] == '-';
 }

 private static void realizarJugada(int columna, char ficha) {
  for (int i = FILAS - 1; i >= 0; i--) {
   if (tablero[i][columna] == '-') {
    tablero[i][columna] = ficha;
    break;
   }
  }
 }

 private static void realizarJugadaAleatoria(char ficha) {
  Random random = new Random();
  int columna;
  do {
   columna = random.nextInt(COLUMNAS);
  } while (!esJugadaValida(columna));
  realizarJugada(columna, ficha);
 }

 private static boolean hayGanador(char ficha) {
  // Comprobar en vertical
  for (int i = 0; i <= FILAS - 4; i++) {
   for (int j = 0; j < COLUMNAS; j++) {
    if (tablero[i][j] == ficha && tablero[i + 1][j] == ficha && tablero[i + 2][j] == ficha && tablero[i + 3][j] == ficha) {
     return true;
    }
   }
  }

  // Comprobar en horizontal
  for (int i = 0; i < FILAS; i++) {
   for (int j = 0; j <= COLUMNAS - 4; j++) {
    if (tablero[i][j] == ficha && tablero[i][j + 1] == ficha && tablero[i][j + 2] == ficha && tablero[i][j + 3] == ficha) {
     return true;
    }
   }
  }

  // Comprobar en diagonal ascendente (/)
  for (int i = 3; i < FILAS; i++) {
   for (int j = 0; j <= COLUMNAS - 4; j++) {
    if (tablero[i][j] == ficha && tablero[i - 1][j + 1] == ficha && tablero[i - 2][j + 2] == ficha && tablero[i - 3][j + 3] == ficha) {
     return true;
    }
   }
  }

  // Comprobar en diagonal descendente (\)
  for (int i = 3; i < FILAS; i++) {
   for (int j = 3; j < COLUMNAS; j++) {
    if (tablero[i][j] == ficha && tablero[i - 1][j - 1] == ficha && tablero[i - 2][j - 2] == ficha && tablero[i - 3][j - 3] == ficha) {
     return true;
    }
   }
  }

  return false;
 }

 private static String obtenerTableroComoString() {
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < FILAS; i++) {
   for (int j = 0; j < COLUMNAS; j++) {
    sb.append(tablero[i][j]).append(' ');
   }
   sb.append('\n');
  }
  return sb.toString();
 }
}
