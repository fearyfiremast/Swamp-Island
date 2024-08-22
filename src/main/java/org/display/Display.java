package org.display;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame that contains all elements of the user interface
 * @author      Xander Smith
 */
public class Display extends JFrame {

    final int WIDTH = 960;
    final int HEIGHT = 720;

    /**
     * Initializes the display to have certain abilities and attributes.
     * Sets the title and size.
     * Also closes the program when the window is closed.
     * Acts as a constructor.
     */
    private void paramInit() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setTitle("Swamp Island");

    }

    /**
     * Constructor for Display. Simply calls paramInit function.
     */
    public Display() {
        paramInit();
    }

    /**
     * Packs the display, sets it in the middle of the screen and makes it visible.
     * This sets up the display and all its components before it is made visible to the user.
     */
    public void revealScreen() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
