/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utils.Enums.TileType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author kismo
 */
public class Tile extends Rectangle{
    
    public static final int TILE_SIZE = 35;
    
    public Tile(TileType type, int x, int y){
        this.setHeight(TILE_SIZE);
        this.setWidth(TILE_SIZE);
        this.setStroke(Color.GRAY);
        this.relocate(x * TILE_SIZE, y * TILE_SIZE);
        
        switch(type){
            case WATER : 
                this.setFill(Color.LIGHTBLUE);
                break;
            case ROCK : 
                this.setFill(Color.LIGHTGRAY);
                break;
            default :
                this.setFill(Color.WHITE);
                break;
        }
    }
    
}
