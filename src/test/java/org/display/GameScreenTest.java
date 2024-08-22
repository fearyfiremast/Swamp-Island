package org.display;

import org.entity.MainCharacter;
import org.game.MapManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Tests the functionality in the GameScreen class
 * @author      Rob Cameron
 */
public class GameScreenTest {

    JPanel main;
    GameScreen screen;

    /**
     * Before each initializes a GameScreen object
     */
    @BeforeEach
    void init(){
        main = new JPanel();
        screen = new GameScreen(main);
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {

        Assertions.assertEquals(new Color(134,88,68), screen.scoreBoard.getBackground());
        Assertions.assertEquals(new Color(222,214,190), GameScreen.message.getForeground());
        Assertions.assertEquals(new Font("Art Nuvo Stamp",Font.BOLD,30), GameScreen.message.getFont());
        Assertions.assertEquals("Time: 0    Score: 0    Rewards Left: 0    Power-ups:",GameScreen.message.getText());
        Assertions.assertEquals(JLabel.CENTER, GameScreen.message.getHorizontalAlignment());
        Assertions.assertEquals(new Dimension(800,60), GameScreen.message.getMinimumSize());

        assert(!GameScreen.juice.isVisible());
        assert(!GameScreen.trap.isVisible());
        assert(GameScreen.juice.getIcon().getIconHeight() == 30);
        assert(GameScreen.juice.getIcon().getIconWidth() == 30);
        assert(GameScreen.trap.getIcon().getIconHeight() == 30);
        assert(GameScreen.trap.getIcon().getIconWidth() == 30);
    }

    /**
     * Tests the update function for less than sixty seconds
     */
    @Test
    public void lessSixtyUpdateTest() {

        MapManager.numRewards = 5;
        GameScreen.update(5,100);

        String s = "Time: 0:05    Score: 100    Rewards Left: 5    Power-ups:";
        Assertions.assertEquals(s, GameScreen.message.getText());
    }

    /**
     * Tests the update function for more than sixty seconds
     */
    @Test
    public void moreSixtyUpdateTest() {

        MapManager.numRewards = 25;
        GameScreen.update(70,960);

        String s = "Time: 1:10    Score: 960    Rewards Left: 25    Power-ups:";
        Assertions.assertEquals(s, GameScreen.message.getText());
    }

    /**
     * Tests the exit update method
     */
    @Test
    public void exitUpdateTest() {
        GameScreen.exitUpdate();
        Assertions.assertEquals("Must collect all rewards before exiting",GameScreen.message.getText());
    }

    /**
     * Tests the updatePowerUP method
     */
    @Test
    public void updatePowerUPTest() {

        assert(!GameScreen.juice.isVisible());
        assert(!GameScreen.trap.isVisible());

        MainCharacter.setHasTrap(true);
        MainCharacter.setHasJuice(true);
        GameScreen.updatePowerUP();

        assert(GameScreen.juice.isVisible());
        assert(GameScreen.trap.isVisible());
    }
}
