package com.example.chessclient.controller;

import com.example.chessclient.utils.StageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    void loginAction(ActionEvent event) {
        login.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                StageUtils.transStage(getClass().getResource("/com/example/chessclient/room-view.fxml"), (Stage) login.getScene().getWindow());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void registerAction(ActionEvent event) {

    }

}
