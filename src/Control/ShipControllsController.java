/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import GUI.GamePane;
import Utils.Communication;
import Utils.Enums.MovementType;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import static GUI.ShipControlls.none;
import static GUI.ShipControlls.forward;
import static GUI.ShipControlls.left;
import static GUI.ShipControlls.right;

/**
 *
 * @author kismo
 */
public class ShipControllsController {

    public ShipControllsController(Communication model, GamePane gamePane) {
        ArrayList<ImageView> movementSlots = gamePane.getShipControlls().getMovementSlots();
        for (Iterator<ImageView> it = movementSlots.iterator(); it.hasNext();) {
            ImageView iv = it.next();
            iv.setOnMouseClicked((MouseEvent mouseEvent) -> {
                MouseButton button = mouseEvent.getButton();
                if (button == MouseButton.PRIMARY) {
                    switch ((MovementType) iv.getUserData()) {
                        case NONE:
                            iv.setImage(forward);
                            iv.setUserData(MovementType.FORWARD);
                            break;
                        case FORWARD:
                            iv.setImage(left);
                            iv.setUserData(MovementType.LEFT);
                            break;
                        case LEFT:
                            iv.setImage(right);
                            iv.setUserData(MovementType.RIGHT);
                            break;
                        case RIGHT:
                            iv.setImage(none);
                            iv.setUserData(MovementType.NONE);
                            break;
                    }
                } else if (button == MouseButton.SECONDARY) {
                    iv.setImage(none);
                    iv.setUserData(MovementType.NONE);
                }

            });
        }

        for (Iterator<RadioButton> it = gamePane.getShipControlls().getRadioButtons().iterator(); it.hasNext();) {
            RadioButton button = it.next();
            button.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.isSecondaryButtonDown()) {
                    button.setSelected(false);
                }
            });
            button.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) -> {
                if (button.getUserData().equals("GRAPPLE")) {
                    if (isNowSelected && !wasPreviouslySelected) {
                        gamePane.grapple();
                        if (gamePane.getGrappleCounter() <= -1) {
                            button.setSelected(false);
                        }
                    } else if (!isNowSelected && wasPreviouslySelected) {
                        gamePane.grappleGain(1);
                    }
                } else if (button.getUserData().equals("SHOOT")) {
                    if (isNowSelected && !wasPreviouslySelected) {
                        gamePane.shot();
                        if (gamePane.getShotCounter() <= -1) {
                            button.setSelected(false);
                        }
                    } else if (!isNowSelected && wasPreviouslySelected) {
                        gamePane.shotGain(1);
                    }
                }
            });
        }

        gamePane.getEndTurn().setOnAction((ActionEvent actionEvent) -> {
            gamePane.getTimer().interrupt();
            gamePane.getEndTurn().setDisable(true);
            gamePane.getShipControlls().setDisable(true);
            model.writeToServer(model.genTurnMessage());
            model.readFromServer();
        });

    }

}
