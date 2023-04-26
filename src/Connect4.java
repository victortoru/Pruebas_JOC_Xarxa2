public class Connect4 {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private int[][] board = new int[ROWS][COLS];
    private int currentPlayer;
    private boolean gameOver;
    private boolean gameDrawn;

    public Connect4() {
        currentPlayer = 1;
    }

    public boolean addPiece(int col) {
        if (col < 0 || col >= COLS) {
            return false;
        }

        int row = getFirstEmptyRow(col);
        if (row == -1) {
            return false;
        }

        board[row][col] = currentPlayer;

        if (checkForWinner(row, col)) {
            gameOver = true;
        } else if (checkForDraw()) {
            gameDrawn = true;
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }

        return true;
    }

    public boolean isGameOver() {
        return isBoardFull() || checkForWinner();
    }

    private boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForWinner() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (checkHorizontal(row) || checkVertical(col) || checkDiagonal1(row, col) || checkDiagonal2(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }


    private int getFirstEmptyRow(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                return row;
            }
        }
        return -1;
    }

    private boolean checkHorizontal(int row) {
        int count = 0;
        for (int i = 0; i < COLS; i++) {
            if (board[row][i] == currentPlayer) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical(int col) {
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][col] == currentPlayer) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal1(int row, int col) {
        int count = 0;
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0) {
            if (board[i][j] == currentPlayer) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
            i--;
            j--;
        }
        i = row + 1;
        j = col + 1;
        while (i < ROWS && j < COLS) {
            if (board[i][j] == currentPlayer) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
            i++;
            j++;
        }
        return false;
    }

    private boolean checkDiagonal2(int row, int col) {
        int count = 0;
        int i = row;
        int j = col;
        while (i >= 0 && j < COLS) {
            if (board[i][j] == currentPlayer) {
                count++;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
            i--;
            j++;
        }
        i = row + 1;
        j = col - 1;
        while (i < ROWS && j >= 0) {
            if (board[i][j] == currentPlayer) {
                count
                        += 1;
            } else {
                count = 0;
            }
            if (count == 4) {
                return true;
            }
            i++;
            j--;
        }
        return false;
    }

    private boolean checkForWinner(int row, int col) {
        if (checkHorizontal(row) || checkVertical(col) || checkDiagonal1(row, col) || checkDiagonal2(row, col)) {
            return true;
        }
        return false;
    }

    private boolean checkForDraw() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}