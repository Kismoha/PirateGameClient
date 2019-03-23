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
import javafx.util.Duration;

/**
 *
 * @author kismo
 */
public class ShipShape {

    private int posX;
    private int posY;

    private Rectangle ship;

    private String dir;

    public ShipShape(int x, int y, Color color, String dir) {
        this.posX = x;
        this.posY = y;
        this.dir = dir;
        ship = new Rectangle(TILE_SIZE + 1, TILE_SIZE + 1, color);
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
                    this.dir ="WEST";
                    break;
                case "EAST":
                    this.dir ="SOUTH";
                    break;
                case "WEST":
                    this.dir ="NORTH";
                    break;
            }
        }
    }
    
    

    public void relocateShip() {
        //#mágia hogy működjön
        TranslateTransition asd = new TranslateTransition(Duration.millis(1));
        asd.setNode(ship);
        asd.setToX(posX * TILE_SIZE);
        asd.setToY(posY * TILE_SIZE);
        asd.play();
        //geci --> ship.relocate(posX * TILE_SIZE, posY * TILE_SIZE);
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

    public Rectangle getShip() {
        return ship;
    }

    public void setShip(Rectangle ship) {
        this.ship = ship;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    
}
