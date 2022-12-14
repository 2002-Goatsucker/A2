package com.example.chessclient.controller;

import com.example.chessclient.entity.HandlerThread;
import com.example.chessclient.utils.SocketUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {
    public static final int PLAY_1 = 1;
    public static final int PLAY_2 = 2;
    public static final int EMPTY = 0;
    public static final int BOUND = 90;
    public static final int OFFSET = 15;
    HandlerThread handlerThread;
    @FXML
    private Button ready;

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(int currentPlayer) {
        GameController.currentPlayer = currentPlayer;
    }

    private static int currentPlayer;

    @FXML
    private Pane base_square;

    @FXML
    private Rectangle game_panel;


    public static int[][] chessBoard = new int[3][3];
    private static final boolean[][] flag = new boolean[3][3];
    public static boolean isValid=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game_panel.setOnMouseClicked(event -> {
            if(isValid) {
                int x = (int) (event.getX() / BOUND);
                int y = (int) (event.getY() / BOUND);
                if (chessBoard[x][y] == 0) {
                    try {
                        SocketUtils.sendMess(x + " " + y);
                        if(chessBoard[x][y]==EMPTY) {
                            chessBoard[x][y] = getCurrentPlayer();
                            refreshBoard(x, y);
                            isValid=false;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
    public void refreshBoard (int x, int y) {
            drawChess();
//        try {
//            String str = SocketUtils.receive();
//            if(str.equals("ready")){
//                isValid=true;
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void drawChess () {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[0].length; j++) {
                if (flag[i][j]) {
                    // This square has been drawing, ignore.
                    continue;
                }
                switch (chessBoard[i][j]) {
                    case PLAY_1:
                        drawCircle(i, j);
                        break;
                    case PLAY_2:
                        drawLine(i, j);
                        break;
                    case EMPTY:
                        // do nothing
                        break;
                    default:
                        System.err.println("Invalid value!");
                }
            }
        }
    }

    private void drawCircle (int i, int j) {
        Circle circle = new Circle();
        base_square.getChildren().add(circle);
        circle.setCenterX(i * BOUND + BOUND / 2.0 + OFFSET);
        circle.setCenterY(j * BOUND + BOUND / 2.0 + OFFSET);
        circle.setRadius(BOUND / 2.0 - OFFSET / 2.0);
        circle.setStroke(Color.RED);
        circle.setFill(Color.TRANSPARENT);
        flag[i][j] = true;
    }

    private void drawLine (int i, int j) {
        Line line_a = new Line();
        Line line_b = new Line();
        base_square.getChildren().add(line_a);
        base_square.getChildren().add(line_b);
        line_a.setStartX(i * BOUND + OFFSET * 1.5);
        line_a.setStartY(j * BOUND + OFFSET * 1.5);
        line_a.setEndX((i + 1) * BOUND + OFFSET * 0.5);
        line_a.setEndY((j + 1) * BOUND + OFFSET * 0.5);
        line_a.setStroke(Color.BLUE);

        line_b.setStartX((i + 1) * BOUND + OFFSET * 0.5);
        line_b.setStartY(j * BOUND + OFFSET * 1.5);
        line_b.setEndX(i * BOUND + OFFSET * 1.5);
        line_b.setEndY((j + 1) * BOUND + OFFSET * 0.5);
        line_b.setStroke(Color.BLUE);
        flag[i][j] = true;
    }

    public void readyAction(ActionEvent actionEvent) {
        ready.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                SocketUtils.sendMess("ready");
                ready.setDisable(true);
                handlerThread=new HandlerThread(this);
                handlerThread.setDaemon(true);
                handlerThread.start();
            } catch (IOException e) {
                System.out.println("????????????????????????");
                System.exit(0);
            }
        });

    }
}
