import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Connect4Controller {

    @FXML
    private GridPane boardGrid;

    @FXML
    private Button column1Btn;

    @FXML
    private Button column2Btn;

    @FXML
    private Button column3Btn;

    @FXML
    private Button column4Btn;

    @FXML
    private Button column5Btn;

    @FXML
    private Button column6Btn;

    @FXML
    private Button column7Btn;

    @FXML
    private Label statusLabel;

    private Connect4 game;

    public Connect4Controller() {
        this.game = new Connect4();
    }

    @FXML
    public void initialize() {
        drawBoard();
    }

    @FXML
    public void handleColumn1() {
        play(0);
    }

    @FXML
    public void handleColumn2() {
        play(1);
    }

    @FXML
    public void handleColumn3() {
        play(2);
    }

    @FXML
    public void handleColumn4() {
        play(3);
    }

    @FXML
    public void handleColumn5() {
        play(4);
    }

    @FXML
    public void handleColumn6() {
        play(5);
    }

    @FXML
    public void handleColumn7() {
        play(6);
    }

    private void play(int column) {
        if (!game.isGameOver()) {
            if (game.isColumnFull(column)) {
                showAlert(Alert.AlertType.WARNING, "Column is full", "Please select another column.");
            } else {
                game.play(column);
                drawBoard();
                if (game.isGameOver()) {
                    showGameOverMessage();
                }
            }
        }
    }

    private void drawBoard() {
        boardGrid.getChildren().clear();
        for (int row = 0; row < Connect4.ROWS; row++) {
            for (int col = 0; col < Connect4.COLUMNS; col++) {
                String value = game.getCellValue(row, col);
                Label label = new Label(value);
                label.getStyleClass().add("cell");
                boardGrid.add(label, col, row);
            }
        }
        updateStatusLabel();
    }

    private void updateStatusLabel() {
        if (!game.isGameOver()) {
            String player = game.getCurrentPlayer();
            statusLabel.setText("Turno del jugador " + player);
        } else {
            String winner = game.getWinner();
            if (winner == null) {
                statusLabel.setText("Empate");
            } else {
                statusLabel.setText("El ganador es el jugador " + winner);
            }
        }
    }

    private void handleColumnClick(int col) {
        if (!game.isGameOver()) {
            try {
                game.playPiece(col);
                drawBoard();
                if (game.isGameOver()) {
                    showGameOverDialog();
                }
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Columna llena");
                alert.setContentText("Por favor seleccione otra columna.");
                alert.showAndWait();
            }
        }
    }

    private void showGameOverDialog() {
        String winner = game.getWinner();
        if (winner == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Fin del juego");
            alert.setHeaderText("Empate");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Fin del juego");
            alert.setHeaderText("El ganador es el jugador " + winner);
            alert.showAndWait();
        }
    }
}