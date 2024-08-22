package org.display;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

/**
 * Tests the functionality in the display class
 * @author      Rob Cameron
 */
public class DisplayTest {

    Display display;

    /**
     * Before each to initialize a display object
     */
    @BeforeEach
    void init() {
        display = new Display();
    }

    /**
     * Tests the paramInit method
     */
    @Test
    public void paramInitTest() {
        assert(display.getDefaultCloseOperation() == WindowConstants.EXIT_ON_CLOSE);
        assert(!display.isResizable());
        Assertions.assertEquals(new Dimension(960,720),display.getPreferredSize());
        Assertions.assertEquals("Swamp Island",display.getTitle());
    }

    /**
     * Tests the revealScreen method
     */
    @Test
    public void revealScreenTest() {
        display.revealScreen();
        assert(display.isVisible());
    }
}