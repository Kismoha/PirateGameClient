/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static GUI.Tile.TILE_SIZE;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 *
 * @author kismo
 */
public class ShipShape {

    private int posX;
    private int posY;

    private ImageView ship;

    private Circle leftShot;
    private Circle rightShot;

    private Rectangle leftGrapple;
    private Rectangle rightGrapple;

    private String dir;

    public ShipShape(int x, int y, Color color, String dir) {
        this.posX = x;
        this.posY = y;
        this.dir = dir;
        ship = new ImageView();
        try {
            ship.setImage(color == Color.YELLOW
                    ? new Image(getClass().getResource("/resources/ships.png").toURI().toString(), TILE_SIZE, TILE_SIZE, false, false)
                    : new Image(getClass().getResource("/resources/enemy_ship_black_red.png").toURI().toString(), TILE_SIZE, TILE_SIZE, false, false));
        } catch (Exception e) {

        }

        leftShot = new Circle(5, Color.BLACK);
        rightShot = new Circle(5, Color.BLACK);
        leftGrapple = new Rectangle(10, 10, Color.GRAY);
        rightGrapple = new Rectangle(10, 10, Color.GRAY);
        leftShot.setOpacity(0.0);
        rightShot.setOpacity(0.0);
        leftGrapple.setOpacity(0.0);
        rightGrapple.setOpacity(0.0);
        //relocateShip();
        switch (this.dir) {
            case "SOUTH":
                ship.setRotate(180);
                break;
            case "NORTH":
                ship.setRotate(0);
                break;
            case "WEST":
                ship.setRotate(270);
                break;
            case "EAST":
                ship.setRotate(90);
                break;
        }
        relocateShip();
    }

    public void changeDirection(boolean isLeft) {
        if (isLeft) {
            switch (this.dir) {
                case "NORTH":
                    this.dir = "WEST";
                    break;
                case "SOUTH":
                    this.dir = "EAST";
                    break;
                case "EAST":
                    this.dir = "NORTH";
                    break;
                case "WEST":
                    this.dir = "SOUTH";
                    break;
            }
        } else {
            switch (this.dir) {
                case "NORTH":
                    this.dir = "EAST";
                    break;
                case "SOUTH":
                    this.dir = "WEST";
                    break;
                case "EAST":
                    this.dir = "SOUTH";
                    break;
                case "WEST":
                    this.dir = "NORTH";
                    break;
            }
        }
    }

    public void addToPane(Pane pane) {
        //pane.getChildren().add(ship);
        pane.getChildren().add(leftShot);
        pane.getChildren().add(rightShot);
        pane.getChildren().add(leftGrapple);
        pane.getChildren().add(rightGrapple);
    }

    public void relocateShip() {
        //Hogy ha relocate-et használok, az eltolja a transitionök koordinátáit
        TranslateTransition temp = new TranslateTransition(Duration.millis(1));
        temp.setNode(ship);
        temp.setToX(posX * TILE_SIZE);
        temp.setToY(posY * TILE_SIZE);
        temp.play();
        relocateAssets();
    }

    public void relocateAssets() {
        relocateAsset(leftShot);
        relocateAsset(rightShot);
        relocateAsset(leftGrapple);
        relocateAsset(rightGrapple);
    }

    private void relocateAsset(Shape target) {
        TranslateTransition temp = new TranslateTransition(Duration.millis(1));
        temp.setNode(target);
        temp.setToX(posX * TILE_SIZE);
        temp.setToY(posY * TILE_SIZE);
        temp.play();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public ImageView getShip() {
        return ship;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Circle getLeftShot() {
        return leftShot;
    }

    public Circle getRightShot() {
        return rightShot;
    }

    public Rectangle getLeftGrapple() {
        return leftGrapple;
    }

    public Rectangle getRightGrapple() {
        return rightGrapple;
    }

}
