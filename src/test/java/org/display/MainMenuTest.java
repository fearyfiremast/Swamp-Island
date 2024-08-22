package org.display;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;

/**
 * Tests the functionality of the main menu class
 * @author      Rob Cameron
 */
public class MainMenuTest {

    MainMenu menu;

    /**
     * Before each create a new main menu object
     */
    @BeforeEach
    void init(){
        menu = new MainMenu();
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {
        assert(menu != null);

        assert(menu.exit != null);
        Assertions.assertEquals("Quit", menu.exit.getText());
        Assertions.assertEquals(new Rectangle(830,620,100,50), menu.exit.getBounds());

        assert(menu.controls != null);
        Assertions.assertEquals("Instructions", menu.controls.getText());
        Assertions.assertEquals(new Rectangle(330,400,300,100), menu.controls.getBounds());

        assert(menu.playGame != null);
        Assertions.assertEquals("Play Game", menu.playGame.getText());
        Assertions.assertEquals(new Rectangle(330,275,300,100), menu.playGame.getBounds());

        assert(menu.title != null);
        Assertions.assertEquals("The Adventures of Swamp Island", menu.title.getText());
        Assertions.assertEquals(new Rectangle(20,100,920,100), menu.title.getBounds());
        Assertions.assertEquals(JLabel.TOP, menu.title.getVerticalAlignment());


        assert(MainMenu.hScore != null);
        Assertions.assertEquals("High Score: 0", MainMenu.hScore.getText());
        Assertions.assertEquals(new Rectangle(330,500,300,100), MainMenu.hScore.getBounds());

        assert(menu.scoreLabel != null);
        Assertions.assertEquals("High Score: 0", menu.scoreLabel);

        assert(Objects.equals(menu.getBackground(), new Color(134, 88, 68)));
        assert(menu.getLayout() == null);
    }

    /**
     * Tests the updateHScore method
     */
    @Test
    public void updateHScoreTest() {
        MainMenu.updateHScore("High Score: 42");
        Assertions.assertEquals("High Score: 42", MainMenu.hScore.getText());
    }

}
