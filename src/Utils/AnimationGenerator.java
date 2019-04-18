/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Utils.Enums.MovementType;
import GUI.ShipShape;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import static GUI.Tile.TILE_SIZE;
import Utils.Enums.ActionType;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Shape;

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
        ship.setPosX(toX);
        ship.setPosY(toY);
        return movement;
    }

    private Path generateForwardPath(ShipShape ship, int toX, int toY) {
        Path path = new Path();
        path.getElements().addAll(
                new MoveTo((ship.getPosX() * TILE_SIZE) + (TILE_SIZE / 2) + 1, (ship.getPosY() * TILE_SIZE) + (TILE_SIZE / 2) + 1),
                new LineTo((toX * TILE_SIZE) + (TILE_SIZE / 2) + 1, (toY * TILE_SIZE) + (TILE_SIZE / 2) + 1)
        );
        return path;
    }

    private Path generateTurnPath(String direction, ShipShape ship, int toX, int toY) {
        Path path = new Path();
        ArcTo arc = new ArcTo();
        arc.setX((toX * TILE_SIZE) + (TILE_SIZE / 2) + 1);
        arc.setY((toY * TILE_SIZE) + (TILE_SIZE / 2) + 1);
        arc.setRadiusX(TILE_SIZE);
        arc.setRadiusY(TILE_SIZE);
        if (direction.equals("RIGHT")) {
            arc.setSweepFlag(true);
        }
        ship.changeDirection(direction.equals("LEFT"));
        path.getElements().addAll(
                new MoveTo((ship.getPosX() * TILE_SIZE) + (TILE_SIZE / 2) + 1, (ship.getPosY() * TILE_SIZE) + (TILE_SIZE / 2) + 1),
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

    public Transition currentAnimation(MovementType type, ShipShape ship, int toX, int toY) {
        if (type == MovementType.NONE) {
            return new PauseTransition(Duration.millis(1500));
        } else {
            PathTransition move = new PathTransition();
            move.setAutoReverse(false);
            move.setDuration(Duration.millis(1500));
            move.setNode(ship.getShip());

            Path path = generateForwardPath(ship, toX, toY);

            move.setPath(path);

            ship.setPosX(toX);
            ship.setPosY(toY);

            return move;
        }
    }

    public ParallelTransition actionAnimations(String left,
            String right, ShipShape ship) {
        return new ParallelTransition(
                singleAction(ship, left, true),
                singleAction(ship, right, false)
        );
    }

    private Transition singleAction(ShipShape ship,
            String animation, boolean isLeft) {
        Transition action = null;
        String[] parts = animation.split(",");
        Shape target;
        ActionType type = ActionType.valueOf(parts[0]);
        switch (type) {
            case SHOOTMISS:
                target = isLeft ? ship.getLeftShot() : ship.getRightShot();
                action = shotAnimation(type, target,
                        Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                        ship.getPosX(), ship.getPosY()
                );
                break;
            case SHOOTHIT:
                target = isLeft ? ship.getLeftShot() : ship.getRightShot();
                action = shotAnimation(type, target,
                        Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
                        ship.getPosX(), ship.getPosY()
                );
                break;
            case GRAPPLE:
                //target = isLeft ? ship.getLeftGrapple() : ship.getRightGrapple();
                break;
            case NONE:
                break;
        }
        return action == null ? new PauseTransition(Duration.millis(1500))
                : action;
    }

    private SequentialTransition shotAnimation(ActionType type, Shape target,
            int toX, int toY, int fromX, int fromY) {
        SequentialTransition start = shotStart(target, toX, toY, fromX, fromY);
        SequentialTransition end = null;
        switch (type) {
            case SHOOTMISS:
                end = shotEndMiss(target, fromX, fromY);
                break;
            case SHOOTHIT:
                end = shotEndHit(target, fromX, fromY);
                break;
        }
        return new SequentialTransition(
                start, end
        );
    }

    private SequentialTransition shotStart(Shape target, int toX, int toY,
            int fromX, int fromY) {
        TranslateTransition shoot = new TranslateTransition(Duration.millis(500),
                target);
        shoot.setToX((toX * TILE_SIZE) + (TILE_SIZE / 2) + 1);
        shoot.setToY((toY * TILE_SIZE) + (TILE_SIZE / 2) + 1);
        return new SequentialTransition(returnToShip(target, fromX, fromY),
                fadeIn(target), shoot);
    }

    private SequentialTransition shotEndHit(Shape target, int fromX, int fromY) {
        FillTransition fillRed = new FillTransition(Duration.millis(1),
                target, Color.BLACK, Color.RED);
        FillTransition fillBlack = new FillTransition(Duration.millis(1),
                target, Color.RED, Color.BLACK);
        ScaleTransition scaleUp
                = new ScaleTransition(Duration.millis(500), target);
        scaleUp.setToX(2);
        scaleUp.setToY(2);
        ScaleTransition scaleDown
                = new ScaleTransition(Duration.millis(1), target);
        scaleDown.setToX(1);
        scaleDown.setToY(1);
        return new SequentialTransition(fillRed, scaleUp, fadeOut(target),
                scaleDown, fillBlack, returnToShip(target, fromX, fromY));
    }

    private SequentialTransition shotEndMiss(Shape target, int fromX, int fromY) {
        return new SequentialTransition(fadeOut(target),
                returnToShip(target, fromX, fromY));
    }

    private FadeTransition fadeIn(Shape target) {
        FadeTransition fadeIn = new FadeTransition(
                Duration.millis(1), target);
        fadeIn.setToValue(1.0);
        return fadeIn;
    }

    private FadeTransition fadeOut(Shape target) {
        FadeTransition fadeIn = new FadeTransition(
                Duration.millis(100), target);
        fadeIn.setToValue(0.0);
        return fadeIn;
    }

    private TranslateTransition returnToShip(Shape target, int toX, int toY) {
        TranslateTransition back = new TranslateTransition(Duration.millis(1), target);
        back.setToX((toX * TILE_SIZE) + (TILE_SIZE / 2) + 1);
        back.setToY((toY * TILE_SIZE) + (TILE_SIZE / 2) + 1);
        return back;
    }
}
