module com.example.chessclient {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires eu.hansolo.tilesfx;
            requires com.almasb.fxgl.all;

    opens com.example.chessclient to javafx.fxml;
    exports com.example.chessclient;
    exports com.example.chessclient.controller;
    opens com.example.chessclient.controller to javafx.fxml;
}