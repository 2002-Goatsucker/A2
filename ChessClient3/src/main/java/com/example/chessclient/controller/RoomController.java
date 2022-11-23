package com.example.chessclient.controller;

import com.example.chessclient.utils.SocketUtils;
import com.example.chessclient.utils.StageUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RoomController {

    @FXML
    private Button newRoom;

    @FXML
    private Button refresh;

    @FXML
    private GridPane pane;

    String room;

    @FXML
    void newRoomAction(ActionEvent event) {
        newRoom.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                TextInputDialog dialog = new TextInputDialog("room");
                dialog.setTitle("Create Room");
                dialog.setHeaderText("Room Creating");
                dialog.setContentText("Please enter your room:");
// Traditional way to get the response value.
                Optional<String> result = dialog.showAndWait();
                room = result.get();
                try {
                    SocketUtils.sendMess("action:create");
                    SocketUtils.sendMess("player");
                    SocketUtils.sendMess(room);
                    String info = SocketUtils.receive();
                    if (info.equals("fail")) {
                        return;
                    } else if (info.equals("success")) {
                        StageUtils.transStage(getClass().getResource("/com/example/chessclient/game-view.fxml"), (Stage) newRoom.getScene().getWindow());
//                        ChessBoard chessBoard = (ChessBoard) SocketUtils.receiveObj();
                        GameController.setCurrentPlayer(1);
                    }
                } catch (Exception e) {
                    System.out.println("未连接");
                }
            }
        });
    }

    @FXML
    void refreshAction() {
        List<String> rooms = new LinkedList<>();
        try {
            SocketUtils.sendMess("room");
            int num = Integer.parseInt(SocketUtils.receive());
            for (int i = 0; i < num; ++i) {
                String room = SocketUtils.receive();
                rooms.add(room);
            }
            for (int x = 0; x < num; ++x) {
                StageUtils.addButton(rooms, pane);
            }
        } catch (IOException e) {
            System.out.println("游戏异常关闭");
            System.exit(0);
        }

    }

}
