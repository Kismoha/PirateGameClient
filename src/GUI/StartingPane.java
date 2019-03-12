/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author kismo
 */
public class StartingPane extends GridPane {

    public static final int START_PAGE_HEIGHT = 125;
    public static final int START_PAGE_WIDTH = 210;

    private final Button startBTN = new Button("Start");
    private final Button exitBTN = new Button("Exit");
    private final Text fogLabel = new Text("Köd");
    private final Text maneuverLabel = new Text("Manöverek");
    private final Text pickupLabel = new Text("Pick-up");
    private final CheckBox fogCB = new CheckBox();
    private final CheckBox maneuverCB = new CheckBox();
    private final CheckBox pickupCB = new CheckBox();
    private final Button next = new Button("Next");

    public StartingPane() {
        this.setPrefSize(START_PAGE_WIDTH, START_PAGE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        this.add(fogLabel, 1, 0);
        this.add(fogCB, 2, 0);
        this.add(maneuverLabel, 1, 1);
        this.add(maneuverCB, 2, 1);
        this.add(pickupLabel, 1, 2);
        this.add(pickupCB, 2, 2);
        this.add(startBTN, 0, 3);
        this.add(next, 2, 3);
        this.add(exitBTN, 3, 3);
        startBTN.setDisable(true);
    }

    public Button getStartBTN() {
        return startBTN;
    }

    public Button getExitBTN() {
        return exitBTN;
    }

    public CheckBox getFogCB() {
        return fogCB;
    }

    public CheckBox getManeuverCB() {
        return maneuverCB;
    }

    public CheckBox getPickupCB() {
        return pickupCB;
    }

    public Button getNext() {
        return next;
    }

}
