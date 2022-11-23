package com.example.chessclient.utils;

import com.example.chessclient.controller.GameController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class StageUtils {
    private StageUtils(){}
    public static void transStage(URL url, Stage currentStage) throws IOException {
        Parent root = FXMLLoader.load(url);
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        currentStage.close();
        stage.show();
    }
    public static void addButton(List<String> list, GridPane pane) {
        int i=0;
        int j=0;
        for(String str:list){
            Button button=new Button();
            button.setText(str);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        SocketUtils.sendMess("action:attend");
                        SocketUtils.sendMess(str);
                        SocketUtils.sendMess(ConstantPool.userName);
                        String res=SocketUtils.receive();
                        if(res.equals("success")){
                            StageUtils.transStage(getClass().getResource("/com/example/chessclient/game-view.fxml"), (Stage) button.getScene().getWindow());
                            GameController.setCurrentPlayer(2);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            pane.add(button,i,j);
            j++;
            if(j==2){
                j=0;
                i++;
            }
        }
    }

}
