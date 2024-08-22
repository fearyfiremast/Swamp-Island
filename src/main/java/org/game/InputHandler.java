package org.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input by implementing the KeyListener interface. It detects
 * key events, and executes the appropriate response to it.
 *
 * @author Bryan
 * @version 1.0
 */
public class InputHandler implements KeyListener {

    public boolean keyUp, keyDown, keyLeft, keyRight;
    public boolean useSpeed, useTrap;

    /**
     * Invoked when a key has been pressed.
     * This method is a part of the KeyListener interface.
     *
     * @param e    The KeyEvent associated with the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }


    /**
     * Invoked when a key press results in a Unicode character being entered.
     * This method is a part of the KeyListener interface.
     *
     * @param e    The KeyEvent associated with the key press.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println("Key Typed");
        switch(e.getKeyChar()) {
            case 'w':
                keyUp = true;
                //System.out.println("Key up");
                break;
            case 'a':
                keyLeft = true;
                //System.out.println("Key left");
                break;
            case 's':
                keyDown = true;
                //System.out.println("Key down");
                break;
            case 'd':
                keyRight = true;
                //System.out.println("Key right");
                break;
            case 'j':
                // Use speed buff
                useSpeed = true;
                break;
            case 'k':
                // Use trap
                useTrap = true;
                break;
        }
    }

    /**
     * Invoked when a key has been released.
     * This method is a part of the KeyListener interface.
     *
     * @param e    The KeyEvent associated with the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("Key Released");
        switch(e.getKeyChar()) {
            case 'w':
                keyUp = false;
                //System.out.println("Key not up");
                break;
            case 'a':
                keyLeft = false;
                //System.out.println("Key not left");
                break;
            case 's':
                keyDown = false;
                //System.out.println("Key not down");
                break;
            case 'd':
                keyRight = false;
                //System.out.println("Key not right");
                break;
            case 'j':
                useSpeed = false;
                break;
            case 'k':
                useTrap = false;
                break;
        }
    }
}
