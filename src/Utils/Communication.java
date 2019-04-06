/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import GUI.GamePane;
import GUI.ShipControlls;
import GUI.StartingPane;
import Utils.Enums.MessageType;
import static Utils.ScreenInfo.windowHeight;
import static Utils.ScreenInfo.windowWidth;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Toggle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author kismo
 */
public class Communication {

    private final Socket client;
    private final GamePane gamePane;
    private final StartingPane startingPane;
    private final Stage primaryStage;
    private final BufferedReader in;
    private final PrintWriter out;

    public Communication(Socket client, GamePane gamePane, StartingPane startingPane, Stage stage) {
        this.client = client;
        this.gamePane = gamePane;
        this.startingPane = startingPane;
        this.primaryStage = stage;
        BufferedReader tempIn = BRMagicTrick();
        PrintWriter tempOut = PWMagicTrick();
        try {
            tempIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            tempOut = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            System.out.println("Fail a streamek létrehozásakor");

        } finally {
            this.in = tempIn;
            this.out = tempOut;
        }
    }

    private PrintWriter PWMagicTrick() {
        return null;
    }

    private BufferedReader BRMagicTrick() {
        return null;
    }

    public void writeToServer(String message) {
        System.out.println("Writing message:" + message);
        out.println(message);
        out.flush();

    }

    public String genTurnMessage() {
        StringBuilder sb = new StringBuilder("");
        ShipControlls sp = gamePane.getShipControlls();
        for (int i = 0; i < ShipControlls.MOVEMENT_COUNT; i++) {
            sb.append(sp.getMovementSlots().get(i).getUserData()).append(";");
            Toggle toggle = sp.getToggleGroups().get((i * 2) + 0).getSelectedToggle();
            sb.append(toggle == null ? "NONE;" : toggle.getUserData() + ";");
            toggle = sp.getToggleGroups().get((i * 2) + 1).getSelectedToggle();
            sb.append(toggle == null ? "NONE-" : toggle.getUserData() + "-");
        }
        System.out.println(MessageType.GAMESTATE.toString() + ":" + sb.toString());
        return MessageType.GAMESTATE.toString() + ":" + sb.toString();
    }

    private void showServerCommAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Kommunikációs probléma");
        alert.setContentText("A kliens nem tudok kommunikációt "
                + "véghez vinni a szerverrel!");

        alert.showAndWait();
        Platform.exit();
    }

    private void showPlayerLeftAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Csatlakozási probléma");
        alert.setContentText("A szerver levesztette a "
                + "kapcsolatot a másik játékossal");

        alert.showAndWait();
        Platform.exit();
    }

    public void readFromServer() {
        //Ezt a Thread-et ki kéne vinni egy class-ba
        (new Thread() {
            @Override
            public void run() {
                String message = "";
                try {
                    System.out.println("Waiting for message");
                    message = in.readLine();
                    System.out.println("Message Beolvasva: " + message);
                } catch (IOException e) {
                    Platform.runLater(() -> {
                        showServerCommAlert();
                    });
                }
                processMessage(message);
            }

            private void processMessage(String message) {
                String[] messageParts = message.split(":");
                switch (MessageType.valueOf(messageParts[0])) {
                    case FIRST_GAMESTATE:
                        firstGameStateHandler(messageParts[1]);
                        break;
                    case GAMESTATE:
                        Platform.runLater(() -> {
                            gamePane.animateTurn(messageParts[1],messageParts[2]);
                        });
                        break;
                    case STARTGAME:
                        Platform.runLater(() -> {
                            primaryStage.setScene(new Scene(gamePane, windowWidth - 50, windowHeight - 50));
                            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                            primaryStage.setX((screenBounds.getWidth() - windowWidth) / 2);
                            primaryStage.setY((screenBounds.getHeight() - windowHeight) / 2 + 20);
                        });
                        break;
                    case ERROR:
                        Platform.runLater(() -> {
                            showPlayerLeftAlert();
                        });
                        break;
                }
            }

            private void firstGameStateHandler(String message) {
                String[] parts = message.split("/");
                MinGame minGame = new MinGame(parts[0]);
                System.out.println(minGame.toString());
                gamePane.setupTiles(minGame);
                gamePane.setupShips(minGame, Integer.parseInt(parts[1]));
                Platform.runLater(() -> {
                    startingPane.getStartBTN().setDisable(false);
                    startingPane.getStatus().setText("Nyomjon a Start-ra, ha készen áll");
                });
            }
        }).start();
    }
}
