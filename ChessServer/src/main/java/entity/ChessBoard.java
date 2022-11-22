package entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChessBoard implements Serializable {
    private int[][] chessboard = new int[3][3];
    private int winner;
    private int player1 = 1;
    private int player2 = 2;
    private int blank = 0;

    public boolean isFinish(int x, int y, int player) {
        if (chessboard[x][0] == player && chessboard[x][1] == player && chessboard[x][2] == player) return true;
        if (chessboard[0][y] == player && chessboard[1][y] == player && chessboard[2][y] == player) return true;
        if (((x == 2 && y == 2) || (x == 0 && y == 0) || (x == 1 && y == 1)) && chessboard[0][0] == player && chessboard[1][1] == player && chessboard[2][2] == player)
            return true;
        if (((x == 2 && y == 0) || (x == 0 && y == 2) || (x == 1 && y == 1)) && chessboard[2][0] == player && chessboard[1][1] == player && chessboard[0][2] == player)
            return true;
        return false;
    }

    public void setPoint(int x, int y, int player) {
        if(chessboard[x][y]!=blank) throw new RuntimeException();
        chessboard[x][y]=player;
    }


}
