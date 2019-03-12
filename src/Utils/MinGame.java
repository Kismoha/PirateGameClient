/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Utils.Enums.TileType;
import java.net.Socket;

/**
 *
 * @author kismo
 */
public class MinGame {
    
    private int MAP_HEIGHT = 50;
    private int MAP_WIDTH = 50;

    private TileType[][] map;

    private int pOneCurrentPosX;
    private int pOneCurrentPosY;

    private int pOneOldPosX;
    private int pOneOldPosY;

    private int pTwoCurrentPosX;
    private int pTwoCurrentPosY;

    private int pTwoOldPosX;
    private int pTwoOldPosY;
    
    private Socket winner;

    public MinGame(String game){
        String[] mainSplit = game.split("-");
        String[] mapSizes = mainSplit[0].split(";");
        MAP_HEIGHT = Integer.parseInt(mapSizes[0]);
        MAP_WIDTH = Integer.parseInt(mapSizes[1]);
        map = new TileType[MAP_HEIGHT][MAP_WIDTH];
        String[] mapString = mainSplit[1].split(";");
        int i = 0;
        int j = 0;
        while(i < MAP_HEIGHT){
            map[i][j] = TileType.valueOf(mapString[i*MAP_WIDTH+j]);
            if(j == MAP_WIDTH-1){
                j = 0;
                i++;
            }else{
                j++;
            }
        }
        String[] positions = mainSplit[2].split(";");
        System.out.println(positions[0]+positions[1]+positions[2]+positions[3]);
        pOneCurrentPosX = Integer.parseInt(positions[0]);
        pOneCurrentPosY = Integer.parseInt(positions[1]);
        pTwoCurrentPosX = Integer.parseInt(positions[2]);
        pTwoCurrentPosY = Integer.parseInt(positions[3]);
    }
    
    //Getters and Setters
    public TileType[][] getMap() {
        return map;
    }

    public void setMap(TileType[][] map) {
        this.map = map;
    }

    public int getpOneCurrentPosX() {
        return pOneCurrentPosX;
    }

    public void setpOneCurrentPosX(int pOneCurrentPosX) {
        this.pOneCurrentPosX = pOneCurrentPosX;
    }

    public int getpOneCurrentPosY() {
        return pOneCurrentPosY;
    }

    public void setpOneCurrentPosY(int pOneCurrentPosY) {
        this.pOneCurrentPosY = pOneCurrentPosY;
    }

    public int getpOneOldPosX() {
        return pOneOldPosX;
    }

    public void setpOneOldPosX(int pOneOldPosX) {
        this.pOneOldPosX = pOneOldPosX;
    }

    public int getpOneOldPosY() {
        return pOneOldPosY;
    }

    public void setpOneOldPosY(int pOneOldPosY) {
        this.pOneOldPosY = pOneOldPosY;
    }

    public int getpTwoCurrentPosX() {
        return pTwoCurrentPosX;
    }

    public void setpTwoCurrentPosX(int pTwoCurrentPosX) {
        this.pTwoCurrentPosX = pTwoCurrentPosX;
    }

    public int getpTwoCurrentPosY() {
        return pTwoCurrentPosY;
    }

    public void setpTwoCurrentPosY(int pTwoCurrentPosY) {
        this.pTwoCurrentPosY = pTwoCurrentPosY;
    }

    public int getpTwoOldPosX() {
        return pTwoOldPosX;
    }

    public void setpTwoOldPosX(int pTwoOldPosX) {
        this.pTwoOldPosX = pTwoOldPosX;
    }

    public int getpTwoOldPosY() {
        return pTwoOldPosY;
    }

    public void setpTwoOldPosY(int pTwoOldPosY) {
        this.pTwoOldPosY = pTwoOldPosY;
    }

    public int getMAP_HEIGHT() {
        return MAP_HEIGHT;
    }

    public void setMAP_HEIGHT(int MAP_HEIGHT) {
        this.MAP_HEIGHT = MAP_HEIGHT;
    }

    public int getMAP_WIDTH() {
        return MAP_WIDTH;
    }

    public void setMAP_WIDTH(int MAP_WIDTH) {
        this.MAP_WIDTH = MAP_WIDTH;
    }

    public Socket getWinner() {
        return winner;
    }

    public void setWinner(Socket winner) {
        this.winner = winner;
    }

}
