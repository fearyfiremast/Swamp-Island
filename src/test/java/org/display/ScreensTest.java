package org.display;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Tests the functionality of the screens class
 * @author      Rob Cameron
 */
public class ScreensTest {

    Screens screen;

    /**
     * Before each create a new screens object
     */
    @BeforeEach
    void init(){
        screen = new Screens();
    }

    /**
     * Tests the makeLabel method
     */
    @Test
    public void makeLabelTest() {
         JLabel lab;
         lab = screen.makeLabel("test",30, new Rectangle(20,30,40,50));

         Assertions.assertEquals(new Color(222, 214, 190), lab.getForeground());
         assert(lab.getHorizontalAlignment() == JLabel.CENTER);
         Assertions.assertEquals(new Font("Art Nuvo Stamp", Font.BOLD, 30),lab.getFont());
         Assertions.assertEquals("test",lab.getText());
         Assertions.assertEquals(new Rectangle(20,30,40,50),lab.getBounds());
    }

    /**
     * Tests the makeButton method
     */
    @Test
    public void makeButtonTest() {
        JButton button;
        button = screen.makeButton("test", new Rectangle(20,30,40,50));

        Assertions.assertEquals(new Color(222, 214, 190), button.getBackground());
        Assertions.assertEquals(new Color(134,88,68), button.getForeground());
        Assertions.assertEquals(new Font("Art Nuvo Stamp", Font.BOLD, 30),button.getFont());
        Assertions.assertEquals("test",button.getText());
        Assertions.assertEquals(new Rectangle(20,30,40,50),button.getBounds());
    }

    /**
     * Tests the getSizedImage method
     */
    @Test
    public void getSizedImageTest() {
        JLabel lab;
        lab = screen.getSizedImage("SwampIslandRefactor/Swamp Island/src/test/java/test.png",new Rectangle(10,40,30,20));

        assert(lab.getIcon().getIconHeight() == 20);
        assert(lab.getIcon().getIconWidth() == 30);
        assert(lab.getX() == 10);
        assert(lab.getY() == 40);

    }

}
