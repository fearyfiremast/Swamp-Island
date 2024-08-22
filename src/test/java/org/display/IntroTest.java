package org.display;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Tests the functionality of the Intro class
 * @author      Rob Cameron
 */
public class IntroTest {

    Intro intro;

    /**
     * Before each create a new intro object
     */
    @BeforeEach
    void init(){
        intro = new Intro();
    }

    /**
     * Test the constructor
     */
    @Test
    public void constructorTest() {
        Assertions.assertEquals(new Dimension(750,750),intro.getPreferredSize());
        Assertions.assertNull(intro.getLayout());
        Assertions.assertEquals(new Color(134,88,68),intro.getBackground());

        Assertions.assertEquals("<html>You shipwrecked on the mysterious Swamp Island!<br/><br/>This island is uninhabited apart from the evil Wild Moose<br/><br/>Collect wood to rebuild your ship and don't get caught!<html>",intro.message.getText());
        Assertions.assertEquals(new Rectangle(165,50,600,500),intro.message.getBounds());

        Assertions.assertEquals("Start Game",intro.goToGame.getText());
        Assertions.assertEquals(new Rectangle(330,500,300,100),intro.goToGame.getBounds());
    }
}
