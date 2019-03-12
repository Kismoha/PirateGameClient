/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utils.Enums.TileType;
import Utils.MinGame;
import Utils.Ship.ShipShape;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
/**
 *
 * @author kismo
 */
public class GamePane extends VBox {
    
    private ScrollPane gameTable;
    private StackPane gameState;
    private Pane tiles;
    private Pane ships;
    private ShipControlls shipControlls;
    private Button endTurn;
    
    private ShipShape shipOne;
    private ShipShape shipTwo;
    

    public GamePane(int windowWidth, int windowHeight) {
        gameTable = new ScrollPane();
        tiles = new Pane();
        ships = new Pane();
        gameState = new StackPane();

        //setting up scrollpane
        gameState.getChildren().addAll(tiles, ships);
        gameTable.setContent(gameState);
        gameTable.setPrefSize(windowWidth, windowHeight);
        gameTable.setMaxSize(windowWidth, windowHeight * 0.8);
        this.getChildren().add(gameTable);
        //Controlls
        setupControlls();
        this.setAlignment(Pos.CENTER);
    }
    
    public void setupShips(MinGame minGame) {
        shipOne = new ShipShape(GUI.Tile.TILE_SIZE, GUI.Tile.TILE_SIZE, Color.YELLOW);
        shipOne.setPosX(minGame.getpOneCurrentPosX());
        shipOne.setPosY(minGame.getpOneCurrentPosY());
        shipOne.relocateShip();
        
        shipTwo = new ShipShape(GUI.Tile.TILE_SIZE, GUI.Tile.TILE_SIZE, Color.YELLOW);
        shipTwo.setPosX(minGame.getpTwoCurrentPosX());
        shipTwo.setPosY(minGame.getpTwoCurrentPosY());
        shipTwo.relocateShip();
        
        ships.getChildren().add(shipOne.getShip());
        ships.getChildren().add(shipTwo.getShip());
    }
    
    public void setupTiles(MinGame minGame) {
        //init
        TileType[][] map = minGame.getMap();
        //populating game map
        for (int i = 0; i < minGame.getMAP_HEIGHT(); i++) {
            for (int j = 0; j < minGame.getMAP_WIDTH(); j++) {
                Tile tile = new Tile(map[i][j], i, j);
                tiles.getChildren().add(tile);
            }
        }
    }
    
    private void setupControlls() {
        shipControlls = new ShipControlls();
        endTurn = new Button("End Turn");
        this.getChildren().add(new HBox(shipControlls, endTurn));
    }
    
    public ScrollPane getGameTable() {
        return gameTable;
    }
    
    public void setGameTable(ScrollPane gameTable) {
        this.gameTable = gameTable;
    }
    
    public ShipControlls getShipControlls() {
        return shipControlls;
    }
    
    public void setShipControlls(ShipControlls controlls) {
        this.shipControlls = controlls;
    }
    
    public Pane getTiles() {
        return tiles;
    }
    
    public void setTiles(Pane tiles) {
        this.tiles = tiles;
    }
    
    public Button getEndTurn() {
        return endTurn;
    }
    
    public void setEndTurn(Button endTurn) {
        this.endTurn = endTurn;
    }
    
}
