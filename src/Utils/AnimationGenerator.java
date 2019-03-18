/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Utils.Enums.MovementType;
import Utils.Ship.ShipShape;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import static GUI.Tile.TILE_SIZE;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

/**
 *
 * @author kismo
 */
public class AnimationGenerator {

    public ParallelTransition movementAnimation(MovementType type, ShipShape ship, int toX, int toY) {
        ParallelTransition movement = new ParallelTransition();
        PathTransition move = new PathTransition();
        move.setAutoReverse(false);
        move.setDuration(Duration.millis(1500));
        move.setNode(ship.getShip());

        Path path = null;
        RotateTransition rotate = null;
        switch (type) {
            case NONE:
                break;
            case FORWARD:
                path = generateForwardPath(ship, toX, toY);
                break;
            case FORWARD_CRASH:
                break;
            case RIGHT:
                rotate = generateRotation(ship, type.toString().split("_")[0]);
                path = generateTurnPath(type.toString().split("_")[0], ship, toX, toY);
                break;
            case RIGHT_MID_CRASH:
                rotate = generateRotation(ship, type.toString().split("_")[0]);
                path = generateForwardPath(ship, toX, toY);
                break;
            case RIGHT_CRASH:
                rotate = generateRotation(ship, type.toString().split("_")[0]);
                break;
            case LEFT:
                rotate = generateRotation(ship, type.toString().split("_")[0]);
                path = generateTurnPath(type.toString().split("_")[0], ship, toX, toY);
                break;
            case LEFT_MID_CRASH:
                rotate = generateRotation(ship, type.toString().split("_")[0]);
                path = generateForwardPath(ship, toX, toY);
                break;
            case LEFT_CRASH:
                rotate = generateRotation(ship, type.toString().split("_")[0]);
                break;
        }
        move.setPath(path);

        movement.getChildren().add(path == null
                ? new PauseTransition(Duration.millis(1500))
                : move);
        movement.getChildren().add(rotate == null
                ? new PauseTransition(Duration.millis(1500))
                : rotate);
        return movement;
    }

    private Path generateForwardPath(ShipShape ship, int toX, int toY) {
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo((ship.getPosX() * TILE_SIZE) + (TILE_SIZE / 2), (ship.getPosY() * TILE_SIZE) + (TILE_SIZE / 2)),
                new LineTo((toX * TILE_SIZE) + (TILE_SIZE / 2), (toY * TILE_SIZE) + (TILE_SIZE / 2))
        );
        return path;
    }

    private Path generateTurnPath(String direction, ShipShape ship, int toX, int toY) {
        Path path = new Path();
        ArcTo arc = new ArcTo();
        arc.setX((toX * TILE_SIZE) + (TILE_SIZE / 2));
        arc.setY((toY * TILE_SIZE) + (TILE_SIZE / 2));
        arc.setRadiusX(TILE_SIZE);
        arc.setRadiusY(TILE_SIZE);
        if (direction.equals("RIGHT")) {
            arc.setSweepFlag(true);
        }
        ship.changeDirection(direction.equals("LEFT") ? true : false);
        path.getElements().addAll(
                new MoveTo((ship.getPosX() * TILE_SIZE) + (TILE_SIZE / 2), (ship.getPosY() * TILE_SIZE) + (TILE_SIZE / 2)),
                arc
        );
        return path;
    }

    private RotateTransition generateRotation(ShipShape ship, String direction) {
        RotateTransition rotate
                = new RotateTransition(Duration.millis(1500), ship.getShip());
        if (direction.equals("RIGHT")) {
            rotate.setByAngle(90);
        } else {
            rotate.setByAngle(-90);
        }
        return rotate;
    }

}
