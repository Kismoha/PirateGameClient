/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utils.Enums.TileType;
import java.net.URISyntaxException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author kismo
 */
public class Tile extends ImageView {

    public static final int TILE_SIZE = 40;

    public Tile(TileType type, int x, int y) {
        this.relocate(x * TILE_SIZE, y * TILE_SIZE);
        
        switch (type) {
            case WATER:
                setImage("sea_3.png");
                break;
            case ROCK:
                setImage("rocks_newsea_3.png");
                break;
            case CURRENT_NORTH:
                setImage("whirlforward_sea3.png");
                this.setRotate(0);
                break;
            case CURRENT_EAST:
                setImage("whirlforward_sea3.png");
                this.setRotate(90);
                break;
            case CURRENT_SOUTH:
                setImage("whirlforward_sea3.png");
                this.setRotate(180);
                break;
            case CURRENT_WEST:
                setImage("whirlforward_sea3.png");
                this.setRotate(270);
                break;
        }
    }

    private void setImage(String imageName) {
        Image img = null;
        try {
            img = new Image(getClass().getResource("/resources/" + imageName).toURI().toString(),TILE_SIZE,TILE_SIZE,false,false);
        } catch (URISyntaxException ex) {
            System.out.println("ImageView error");
        }
        this.setImage(img);
    }

}
