/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Utils.Enums.TileType;
import Utils.MinGame;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import Utils.AnimationGenerator;
import Utils.Enums.MovementType;
import Utils.Timer;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

/**
 *
 * @author kismo
 */
public class GamePane extends VBox {

    public static final int TURN_TIMER = 10;

    private ScrollPane gameTable;
    private final StackPane gameState;
    private Pane tiles;
    private final Pane ships;
    private ShipControlls shipControlls;
    private Button endTurn;

    private Integer shotCounter;
    private Integer grappleCounter;
    private Integer damageCounter;

    private final Text shot;
    private final Text grapple;
    private final Text damage;

    private ShipShape shipOne;
    private ShipShape shipTwo;

    private Timer timer;

    public GamePane(int windowWidth, int windowHeight) {
        shotCounter = 6;
        grappleCounter = 4;
        damageCounter = 0;

        shot = new Text(shotCounter.toString());
        grapple = new Text(grappleCounter.toString());
        damage = new Text(damageCounter.toString()+"/6");

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
        timer = new Timer(endTurn);
    }

    public void shot() {
        shotCounter--;
        updateCounters();
    }

    public void shotGain(int ammount) {
        shotCounter += ammount;
        updateCounters();
    }

    public void grapple() {
        grappleCounter--;
        updateCounters();
    }

    public void grappleGain(int ammount) {
        grappleCounter += ammount;
        updateCounters();
    }

    private void updateCounters() {
        shot.setText(shotCounter.toString());
        grapple.setText(grappleCounter.toString());
        damage.setText(damageCounter.toString()+"/6");
    }

    public void setStatuses(String message) {
        String[] parts = message.split(",");
        shotCounter = Integer.parseInt(parts[1]);
        grappleCounter = Integer.parseInt(parts[2]);
        damageCounter = Integer.parseInt(parts[0]);
        updateCounters();
    }

    public void showEndScreen(String message) {
        if (!message.equals("NOPE")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("A játéknak vége");
            alert.setHeaderText(message);
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    public void setupShips(MinGame minGame, int self) {
        if (self == 1) {
            shipOne = new ShipShape(minGame.getpOneCurrentPosX(),
                    minGame.getpOneCurrentPosY(), Color.YELLOW,
                    minGame.getPOneDir());

            shipTwo = new ShipShape(minGame.getpTwoCurrentPosX(),
                    minGame.getpTwoCurrentPosY(), Color.RED,
                    minGame.getPTwoDir());
        } else {
            shipOne = new ShipShape(minGame.getpOneCurrentPosX(),
                    minGame.getpOneCurrentPosY(), Color.RED,
                    minGame.getPOneDir());

            shipTwo = new ShipShape(minGame.getpTwoCurrentPosX(),
                    minGame.getpTwoCurrentPosY(), Color.YELLOW,
                    minGame.getPTwoDir());
        }

        ships.getChildren().add(shipOne.getShip());
        ships.getChildren().add(shipTwo.getShip());

        shipOne.addToPane(ships);
        shipTwo.addToPane(ships);

        startTimer();
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
        VBox actions = new VBox();
        HBox shots = new HBox();
        shots.getChildren().addAll(new Text("Lövések:"), shot);
        HBox grapples = new HBox();
        grapples.getChildren().addAll(new Text("Csáklyázások:"), grapple);
        HBox damages = new HBox();
        damages.getChildren().addAll(new Text("Sérülés:"), damage);
        actions.getChildren().addAll(shots, grapples,damages);
        this.getChildren().add(new HBox(shipControlls, endTurn, actions));
    }
    
     public void animateTurn(String message, String status) {
        ParallelTransition[] mainAnimationElements = new ParallelTransition[4];
        calcSegmentAnimations(mainAnimationElements, message);
        SequentialTransition mainAnimation = new SequentialTransition(
                mainAnimationElements[0],
                mainAnimationElements[1],
                mainAnimationElements[2],
                mainAnimationElements[3]
        );
        mainAnimation.setOnFinished((ActionEvent actionEvent) -> {
                shipControlls.resetControlls();
                setStatuses(status);
                shipControlls.setDisable(false);
                endTurn.setDisable(false);
                timer = new Timer(endTurn);
                timer.start();
        });
        mainAnimation.play();
    }

    private void calcSegmentAnimations(ParallelTransition[] mainAnimation,
            String message) {
        String[] splitByShip = message.split("/");
        String[] shipOneParts = splitByShip[0].split("-");
        String[] shipTwoParts = splitByShip[1].split("-");
        mainAnimation[0] = calcASegmentAnimation(shipOneParts[0],
                shipTwoParts[0]);
        mainAnimation[1] = calcASegmentAnimation(shipOneParts[1],
                shipTwoParts[1]);
        mainAnimation[2] = calcASegmentAnimation(shipOneParts[2],
                shipTwoParts[2]);
        mainAnimation[3] = calcASegmentAnimation(shipOneParts[3],
                shipTwoParts[3]);
    }

    private ParallelTransition calcASegmentAnimation(String shipOneSegment,
            String shipTwoSegment) {
        ParallelTransition shipSegmentAnimation = new ParallelTransition(
                calcAShipSegmentAnimation(shipOne, shipOneSegment),
                calcAShipSegmentAnimation(shipTwo, shipTwoSegment)
        );
        return shipSegmentAnimation;
    }

    private SequentialTransition calcAShipSegmentAnimation(ShipShape ship, String animations) {
        String[] segments = animations.split(";"); // movements,action,action
        String[] movements = segments[0].split("="); //self move,current move
        String[] movement = movements[0].split(","); // move, coord, coord
        String[] current = movements[1].split(","); //current, coord coord

        ParallelTransition playerMove = new AnimationGenerator().movementAnimation(
                MovementType.valueOf(movement[0]),
                ship,
                Integer.parseInt(movement[1]),
                Integer.parseInt(movement[2])
        );

        ship.setPosX((int) Integer.parseInt(movement[1]));
        ship.setPosY((int) Integer.parseInt(movement[2]));

        Transition currentMove = new AnimationGenerator().currentAnimation(
                MovementType.valueOf(current[0]),
                ship,
                Integer.parseInt(current[1]),
                Integer.parseInt(current[2])
        );

        ship.setPosX((int) Integer.parseInt(current[1]));
        ship.setPosY((int) Integer.parseInt(current[2]));

        ship.relocateAssets();

        ParallelTransition actions = new AnimationGenerator().actionAnimations(
                segments[1], segments[2], ship
        );

        SequentialTransition shipSegmentAnimation // movement(seq), actions(par)
                = new SequentialTransition(
                        playerMove,
                        currentMove,
                        actions
                );

        return shipSegmentAnimation;
    }

    private void stopTimer() {
        timer.interrupt();
    }

    private void startTimer() {
        timer.start();
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

    public Thread getTimer() {
        return timer;
    }

    public Integer getShotCounter() {
        return shotCounter;
    }

    public Integer getGrappleCounter() {
        return grappleCounter;
    }
}
