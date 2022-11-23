package com.example.chessclient.controller;
import com.example.chessclient.utils.StageUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class HelloController {

    @FXML
    private Button HelloButton;

    @FXML
    private Label welcomeText;

    @FXML
    private Stage primaryStage;

    @FXML
    void onHelloButtonClick() throws IOException {
        HelloButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                URL url = getClass().getResource("/com/example/chessclient/login-view.fxml");
                try {
                    StageUtils.transStage(url, (Stage) HelloButton.getScene().getWindow());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}