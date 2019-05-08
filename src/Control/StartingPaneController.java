/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import GUI.GamePane;
import GUI.StartingPane;
import Main.PirateGameClient;
import Utils.Communication;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kismo
 */
public class StartingPaneController {

    public StartingPaneController(Communication model, Stage primaryStage,
            GamePane gamePane, StartingPane startingPane,
            Scene gameScene, PirateGameClient me) {
        //Game start mechanic
        startingPane.getStartBTN().setOnAction((ActionEvent actionEvent) -> {
            startingPane.getStartBTN().setDisable(true);
            startingPane.getStatus().setText("Várakozás a másik félre");
            model.writeToServer("READY:IM_READY");
            model.readFromServer();
            gamePane.startTimer();
        });

        //Exitting
        startingPane.getExitBTN().setOnAction((ActionEvent actionEvent) -> {
            Platform.exit();
        });

        startingPane.getNext().setOnAction((ActionEvent actionEvent) -> {

            try {
                startingPane.getNext().setDisable(true);
                startingPane.getIp().setDisable(true);
                me.connect(startingPane.getIp().getText());
                model.createStreams();
                startingPane.getNext().setDisable(true);
                startingPane.getStatus().setText("Pálya legenerálása és"
                        + "várakozás a másik félre");
                model.writeToServer("SIGN:SEND MAP PLEASEEE");
                model.readFromServer();
            } catch (IOException ex) {
                startingPane.getNext().setDisable(false);
                startingPane.getIp().setDisable(false);
                startingPane.getIp().setText("Az IP-n nem található szerver");
            }

        });

    }
}
