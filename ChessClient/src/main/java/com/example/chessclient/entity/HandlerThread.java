package com.example.chessclient.entity;

import com.example.chessclient.controller.GameController;
import com.example.chessclient.utils.SocketUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;

import static com.example.chessclient.controller.GameController.*;

public class HandlerThread extends Thread{

    GameController controller;
    public HandlerThread(GameController controller){
        this.controller=controller;
    }


    @Override
    public void run() {
        try {
            while (true) {
                String str = SocketUtils.receive();
                if (str.equals("ready")) GameController.isValid = true;
                else if(str.equals("finish")){
                    String winner = SocketUtils.receive();
                    Platform.runLater(()->{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Finished");
                        alert.setHeaderText(null);
                        if(Integer.parseInt(winner)==getCurrentPlayer()){
                            alert.setContentText("You win!");
                        }else if(Integer.parseInt(winner)==0){
                            alert.setContentText("Tie!");
                        }else {
                            alert.setContentText("You loss!");
                        }
                        alert.showAndWait();
                        System.exit(0);
                    });
                }else if(str.equals("exception")){
                    Platform.runLater(()->{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Exception");
                        alert.setHeaderText(null);
                        alert.setContentText("Connection Exception!");
                        alert.showAndWait();
                        System.exit(0);
                    });
                }
                else {
                    String[] info=str.split(" ");
                    int player;
                    if(GameController.getCurrentPlayer() == 1) player=2;
                    else player=1;
                    int x=Integer.parseInt(info[0]);
                    int y = Integer.parseInt(info[1]);
                    GameController.chessBoard[x][y] = player;
                    Platform.runLater(()->{
                        controller.refreshBoard(x,y);
                    });
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
