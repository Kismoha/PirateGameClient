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

    private int pTwoCurrentPosX;
    private int pTwoCurrentPosY;

    private String pOneDir;
    private String pTwoDir;

    public MinGame(String game) {
        String[] mainSplit = game.split("-");
        String[] mapSizes = mainSplit[0].split(";");
        MAP_HEIGHT = Integer.parseInt(mapSizes[0]);
        MAP_WIDTH = Integer.parseInt(mapSizes[1]);
        map = new TileType[MAP_HEIGHT][MAP_WIDTH];
        String[] mapString = mainSplit[1].split(";");
        int i = 0;
        int j = 0;
        while (i < MAP_HEIGHT) {
            map[i][j] = TileType.valueOf(mapString[i * MAP_WIDTH + j]);
            if (j == MAP_WIDTH - 1) {
                j = 0;
                i++;
            } else {
                j++;
            }
        }
        String[] positions = mainSplit[2].split(";");
        pOneCurrentPosX = Integer.parseInt(positions[0]);
        pOneCurrentPosY = Integer.parseInt(positions[1]);
        pOneDir = positions[2];
        pTwoCurrentPosX = Integer.parseInt(positions[3]);
        pTwoCurrentPosY = Integer.parseInt(positions[4]);
        pTwoDir = positions[5];
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

    public String getPOneDir() {
        return pOneDir;
    }

    public void setPOneDir(String POneDir) {
        this.pOneDir = POneDir;
    }

    public String getPTwoDir() {
        return pTwoDir;
    }

    public void setPTwoDir(String PTwoDir) {
        this.pTwoDir = PTwoDir;
    }

}
