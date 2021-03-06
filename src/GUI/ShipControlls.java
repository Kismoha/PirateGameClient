/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utils.Enums.MovementType;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author kismo
 */
public class ShipControlls extends GridPane {

    public static final int MOVEMENT_FIELD_SIZE = 35;
    public static final int MOVEMENT_COUNT = 4;

    public static Image none;
    public static Image forward;
    public static Image left;
    public static Image right;

    private final ArrayList<ImageView> movementSlots = new ArrayList(); //SIZE: MOVEMENT_COUNT
    private final ArrayList<ToggleGroup> toggleGroups = new ArrayList(); //SIZE: MOVEMENT_COUNT*2
    private final ArrayList<RadioButton> radioButtons = new ArrayList(); //SIZE: MOVEMENT_COUNT*2*2

    public ShipControlls() {
        prepareImages();
        prepareFields();
        addElements();
    }

    private void prepareImages() {
        none=setImage("no_moves.png");
        forward=setImage("forward_75x75_v3.jpg");
        right=setImage("arrow_right_final.png");
        left=setImage("arrow_left_final.png");
    }

    private Image setImage(String imageName) {
        Image img = null;
        try {
            img = new Image(getClass()
                    .getResource("/resources/" + imageName)
                    .toURI().toString(),
                    MOVEMENT_FIELD_SIZE, MOVEMENT_FIELD_SIZE,
                    false, false);
        } catch (URISyntaxException ex) {
            System.out.println("ImageView error");
        }
        return img;
    }

    private void prepareFields() {
        //mopvement fields and statuses
        for (int i = 0; i < MOVEMENT_COUNT; i++) {
            ImageView temp = new ImageView(none);
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
        movementSlots.stream().map((iv) -> {
            iv.setImage(none);
            return iv;
        }).forEachOrdered((iv) -> {
            iv.setUserData(MovementType.NONE);
        });
        radioButtons.forEach((rb) -> {
            rb.setSelected(false);
        });
    }

    public ArrayList<ImageView> getMovementSlots() {
        return movementSlots;
    }

    public ArrayList<RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public ArrayList<ToggleGroup> getToggleGroups() {
        return toggleGroups;
    }
}
