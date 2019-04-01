/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utils.Enums.MovementType;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author kismo
 */
public class ShipControlls extends GridPane {

    public static final int MOVEMENT_FIELD_SIZE = 35;
    public static final int MOVEMENT_COUNT = 4;

    ArrayList<Rectangle> movementSlots = new ArrayList(); //SIZE: MOVEMENT_COUNT
    ArrayList<ToggleGroup> toggleGroups = new ArrayList(); //SIZE: MOVEMENT_COUNT*2
    ArrayList<RadioButton> radioButtons = new ArrayList(); //SIZE: MOVEMENT_COUNT*2*2

    public ShipControlls() {
        prepareFields();
        addElements();
    }

    private void prepareFields() {
        //mopvement fields and statuses
        for (int i = 0; i < MOVEMENT_COUNT; i++) {
            Rectangle temp = new Rectangle();
            temp.setHeight(MOVEMENT_FIELD_SIZE);
            temp.setWidth(MOVEMENT_FIELD_SIZE);
            temp.setFill(Color.GRAY);
            temp.setUserData(MovementType.NONE);
            movementSlots.add(temp);
        }

        //Special Actions
        for (int i = 0; i < MOVEMENT_COUNT * 2; i++) {
            ToggleGroup temp = new ToggleGroup();
            RadioButton shoot = new RadioButton();
            RadioButton grapple = new RadioButton();
            shoot.setUserData("SHOOT");
            grapple.setUserData("GRAPPLE");
            shoot.setToggleGroup(temp);
            grapple.setToggleGroup(temp);
            toggleGroups.add(temp);
            radioButtons.add(shoot);
            radioButtons.add(grapple);
        }
    }

    private void addElements() {
        for (int i = 0; i < MOVEMENT_COUNT; i++) {
            this.add(radioButtons.get((i * 4)), 0, i);
            this.add(radioButtons.get((i * 4) + 1), 1, i);
            this.add(movementSlots.get(i), 2, i);
            this.add(radioButtons.get((i * 4) + 3), 3, i);
            this.add(radioButtons.get((i * 4) + 2), 4, i);
        }
    }

    public void resetControlls() {
        for (Iterator<Rectangle> it = movementSlots.iterator(); it.hasNext();) {
            Rectangle rect = it.next();
            rect.setFill(Color.GRAY);
            rect.setUserData(MovementType.NONE);
        }
        for (Iterator<RadioButton> it = radioButtons.iterator(); it.hasNext();) {
            RadioButton rb = it.next();
            rb.setSelected(false);
        }
    }

    public ArrayList<Rectangle> getMovementSlots() {
        return movementSlots;
    }

    public void setMovementSlots(ArrayList<Rectangle> movementSlots) {
        this.movementSlots = movementSlots;
    }

    public ArrayList<ToggleGroup> getToggleGroups() {
        return toggleGroups;
    }

    public void setToggleGroups(ArrayList<ToggleGroup> toggleGroups) {
        this.toggleGroups = toggleGroups;
    }

    public ArrayList<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public void setRadioButtons(ArrayList<RadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

}
