/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import GUI.GamePane;
import GUI.ShipControlls;
import Main.Communication;
import Utils.Enums.MovementType;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author kismo
 */
public class ShipControllsController {

    public ShipControllsController(Communication model,GamePane gamePane) {
        ArrayList<Rectangle> movementSlots = gamePane.getShipControlls().getMovementSlots();
        for (Iterator<Rectangle> it = movementSlots.iterator(); it.hasNext();) {
            Rectangle rect = it.next();
            rect.setOnMouseClicked((MouseEvent mouseEvent) -> {
                switch ((MovementType) rect.getUserData()) {
                    case NONE :
                        rect.setFill(Color.GREEN);
                        rect.setUserData(MovementType.FORWARD);
                        break;
                    case FORWARD :
                        rect.setFill(Color.BLUE);
                        rect.setUserData(MovementType.LEFT);
                        break;
                    case LEFT :
                        rect.setFill(Color.ORANGE);
                        rect.setUserData(MovementType.RIGHT);
                        break;
                    case RIGHT :
                        rect.setFill(Color.GRAY);
                        rect.setUserData(MovementType.NONE);
                        break;
                }
            });
        }
        
        gamePane.getEndTurn().setOnAction((ActionEvent actionEvent) -> {
            gamePane.getEndTurn().setDisable(true);
            gamePane.getShipControlls().setDisable(true);
            model.writeToServer(model.genTurnMessage());
            model.readFromServer();
        });
        
    }

}
