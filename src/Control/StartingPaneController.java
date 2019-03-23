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
            model.writeToServer("READY:IM_READY");
            model.readFromServer();
        });

        //Exitting
        startingPane.getExitBTN().setOnAction((ActionEvent actionEvent) -> {
            Platform.exit();
        });
        
        startingPane.getNext().setOnAction((ActionEvent actionEvent) -> {
            startingPane.getNext().setDisable(true);
            boolean fog = startingPane.getFogCB().isSelected();
            boolean maneuver = startingPane.getManeuverCB().isSelected();;
            boolean pickup = startingPane.getPickupCB().isSelected();
            model.writeToServer(model.genOptionsMessage(fog, maneuver, pickup));
            model.readFromServer();
        });

    }
}
