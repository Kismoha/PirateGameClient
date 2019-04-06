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

    public static final int START_PAGE_HEIGHT = 100;
    public static final int START_PAGE_WIDTH = 350;

    private final Button startBTN = new Button("Start");
    private final Button exitBTN = new Button("Exit");
    private final Text status = new Text("Nyomjon a Ready-re ha készen áll");
    private final Button next = new Button("Next");

    public StartingPane() {
        this.setPrefSize(START_PAGE_WIDTH, START_PAGE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        this.add(status, 1, 0);
        this.add(startBTN, 0, 1);
        this.add(next, 1, 1);
        this.add(exitBTN, 2, 1);
        startBTN.setDisable(true);
    }

    public Button getStartBTN() {
        return startBTN;
    }

    public Button getExitBTN() {
        return exitBTN;
    }

    public Text getStatus() {
        return status;
    }

    public Button getNext() {
        return next;
    }

}
