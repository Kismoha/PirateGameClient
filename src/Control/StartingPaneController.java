/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import GUI.StartingPane;
import Utils.Communication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kismo
 */
public class StartingPaneController {

    public StartingPaneController(Communication model, Stage primaryStage, StartingPane startingPane, Scene gameScene) {
        //Game start mechanic
        startingPane.getStartBTN().setOnAction((ActionEvent actionEvent) -> {
            startingPane.getStartBTN().setDisable(true);
            startingPane.getStatus().setText("Várakozás a másik félre");
            model.writeToServer("READY:IM_READY");
            model.readFromServer();
        });

        //Exitting
        startingPane.getExitBTN().setOnAction((ActionEvent actionEvent) -> {
            Platform.exit();
        });
        
        startingPane.getNext().setOnAction((ActionEvent actionEvent) -> {
            startingPane.getNext().setDisable(true);
            startingPane.getStatus().setText("Pálya legenerálása és várakozás a "
                    + "másik félre");
            model.writeToServer("SIGN:SEND MAP PLEASEEE");
            model.readFromServer();
        });

    }
}
