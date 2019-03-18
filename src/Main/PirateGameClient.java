/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

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

    private Socket me;
    //Pane of the game UI
    private GamePane gamePane;
    //Pane of the starting UI
    private StartingPane startingPane;
    //game scene
    private Scene gameScene;
    //starting scene
    private Scene startingScene;
    //starting page controller
    private StartingPaneController SPControl;
    //Ship controlls controller
    private ShipControllsController SCControl;
    
    private Communication model;

    @Override
    public void start(Stage primaryStage) {
        //Connecting
        try {
            me = new Socket("localhost",65535);
        } catch (IOException ex) {
            Logger.getLogger(PirateGameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        model = new Communication(me,gamePane,startingPane,primaryStage);
        //Setting up startingPane controller
        SPControl = new StartingPaneController(model, primaryStage, startingPane, gameScene);
        //setting up ship controlls controller
        SCControl = new ShipControllsController(model,gamePane);

        
        
        //Window options
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Pirate Game");
        primaryStage.setScene(startingScene);
        primaryStage.show();
        
       
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    } 
}
