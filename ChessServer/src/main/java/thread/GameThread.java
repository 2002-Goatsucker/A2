package thread;

import entity.ChessBoard;
import lombok.Data;
import utils.StreamUtils;

import java.io.*;
import java.net.Socket;

@Data
public class GameThread extends Thread {
    private int index;
    private String player1;
    private String player2;
    private Socket socket1;
    private Socket socket2;
    private boolean isFree;

    private String pos;
    ChessBoard chessBoard;

    public GameThread() {
        isFree = true;
    }

    @Override
    public void run() {
        chessBoard = new ChessBoard();
        BufferedReader reader1 = StreamUtils.getReader(socket1);
        BufferedWriter writer1 = StreamUtils.getWriter(socket1);
        BufferedReader reader2 = StreamUtils.getReader(socket2);
        BufferedWriter writer2 = StreamUtils.getWriter(socket2);
        try {
            String str1 = reader1.readLine();
            String str2 = reader2.readLine();
            if (str1.equals("ready") && str2.equals("ready")) {
                for (int i = 0; i < 9; ++i) {
                    write(writer1, "ready");
                    if (dealInfo(reader1,writer2, chessBoard, 1)) break;
                    if(isFinish()) {
                        chessBoard.setWinner(0);
                        break;
                    }
                    write(writer2, "ready");
//                write(writer2,);
                    if (dealInfo(reader2,writer1, chessBoard, 2)) break;
                }
            }
            write(writer1, "finish");
            write(writer2, "finish");
            write(writer1, chessBoard.getWinner() + "");
            write(writer2, chessBoard.getWinner() + "");

        } catch (IOException e) {
            System.out.println("连接中断");
            try {
                write(writer1,"exception");
            } catch (IOException ex) {
                System.out.println("player1断开");
            }
            try {
                write(writer2,"exception");
            } catch (IOException ex) {
                System.out.println("Player2断开");
            }

        }
    }

    public void write(BufferedWriter writer, String info) throws IOException {
        writer.write(info);
        writer.newLine();
        writer.flush();
    }

    public boolean isFinish(){
        int cnt=0;
        for(int[] arr:chessBoard.getChessboard()){
            for(int num:arr){
                if(num==0) cnt++;
            }
        }
        return cnt == 0;
    }

    public boolean dealInfo(BufferedReader reader, BufferedWriter writer, ChessBoard chessBoard, int player) throws IOException {
        String pos = reader.readLine();
        String[] info = pos.split(" ");
        int x = Integer.parseInt(info[0]);
        int y = Integer.parseInt(info[1]);
        chessBoard.setPoint(x, y, player);
        if (chessBoard.isFinish(x, y, player)) {
            chessBoard.setWinner(player);
            return true;
        }
        write(writer, x + " " + y);
        return false;
    }
}
