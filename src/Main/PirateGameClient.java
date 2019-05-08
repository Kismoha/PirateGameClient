/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utils.Communication;
import Control.ShipControllsController;
import Control.StartingPaneController;
import GUI.GamePane;
import GUI.StartingPane;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static Utils.ScreenInfo.windowHeight;
import static Utils.ScreenInfo.windowWidth;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kismo
 */
public class PirateGameClient extends Application {

    public static Socket me;
    //Pane of the game UI
    private GamePane gamePane;
    //Pane of the starting UI
    private StartingPane startingPane;
    //game scene
    private Scene gameScene;
    //starting scene
    private Scene startingScene;
    
    private Communication comms;

    @Override
    public void start(Stage primaryStage) {
        
        //Sizing the stage to the resolution
        primaryStage.setMaxWidth(windowWidth);
        primaryStage.setMaxHeight(windowHeight);
        
        
        
         //setting up GameScene
        gamePane = new GamePane(windowWidth, windowHeight);
        //gameScene = new Scene(gamePane, windowWidth - 50, windowHeight - 50);
        

        //setting up starting Scene
        startingPane = new StartingPane();
        startingScene = new Scene(startingPane, StartingPane.START_PAGE_WIDTH, StartingPane.START_PAGE_HEIGHT);
        //setting up startingPane UI controller
        
        comms = new Communication(me,gamePane,startingPane,primaryStage);
        
        //Setting up startingPane controller
        new StartingPaneController(comms, primaryStage, gamePane, startingPane, gameScene, this);
        //setting up ship controlls controller
        new ShipControllsController(comms,gamePane);

        
        
        //Window options
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Pirate Game");
        primaryStage.setScene(startingScene);
        primaryStage.show();
        
       
    }
 
    public void connect(String ip) throws IOException{
        me = new Socket(ip,65535);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    } 
}
