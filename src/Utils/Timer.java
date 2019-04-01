/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static GUI.GamePane.TURN_TIMER;
import static java.lang.Thread.sleep;
import javafx.scene.control.Button;

/**
 *
 * @author kismo
 */
public class Timer extends Thread {

    private Button btn;

    public Timer(Button btn){
        this.btn = btn;
    }
   
    @Override

    public void run() {
        try {
            sleep(TURN_TIMER * 1000);
        } catch (InterruptedException ex) {
            System.out.println("Timer interrupted");
        }
        btn.fire();
    }
}
