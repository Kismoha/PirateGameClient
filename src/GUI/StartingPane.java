/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author kismo
 */
public class StartingPane extends GridPane {

    public static final int START_PAGE_HEIGHT = 100;
    public static final int START_PAGE_WIDTH = 450;

    private final Button startBTN = new Button("Start");
    private final Button exitBTN = new Button("Exit");
    private final Text status = new Text("Írja be a szerver IP-t és nyomjon a Next-re ha készen áll");
    private final Button next = new Button("Next");
    private final TextField ip = new TextField();

    public StartingPane() {
        this.setPrefSize(START_PAGE_WIDTH, START_PAGE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        addAssets();
        startBTN.setDisable(true);
    }
    
    private void addAssets(){
        this.add(status, 1, 0);
        this.add(startBTN, 0, 1);
        this.add(next, 1, 1);
        this.add(exitBTN, 2, 1);
        this.add(new Text("IP:"),0,2);
        this.add(ip,1,2);
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
    public TextField getIp() {
        return ip;
    }

}
