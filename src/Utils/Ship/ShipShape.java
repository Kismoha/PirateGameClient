/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.Ship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static GUI.Tile.TILE_SIZE;

/**
 *
 * @author kismo
 */
public class ShipShape {
    private int posX;
    private int posY;
    
    private Rectangle ship;
    
    public ShipShape(int x, int y, Color color){
        ship = new Rectangle(TILE_SIZE, TILE_SIZE ,color);
    }

    public void relocateShip(){
        ship.relocate(posX * TILE_SIZE, posY * TILE_SIZE);
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
    
    
}
