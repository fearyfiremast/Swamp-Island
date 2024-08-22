package org.display;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Tests the functionality of the instructions class
 * @author      Rob Cameron
 */
public class InstructionsTest {

    Instructions instructions;

    /**
     * Before each create a new instructions object
     */
    @BeforeEach
    void init(){
        instructions = new Instructions();
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {
        assert(instructions.getLayout() == null);
        Assertions.assertEquals(new Color(134,88,68), instructions.getBackground());

        Assertions.assertEquals("Instructions", instructions.controlTitle.getText());
        Assertions.assertEquals(JLabel.TOP, instructions.controlTitle.getVerticalAlignment());
        Assertions.assertEquals(new Rectangle(180,10,600,100), instructions.controlTitle.getBounds());

        Assertions.assertEquals("Collect all logs and make it to the exit to win", instructions.goal.getText());
        Assertions.assertEquals(new Rectangle(80,560,800,100), instructions.goal.getBounds());

        Assertions.assertEquals("Back to Menu", instructions.backToMenu.getText());
        Assertions.assertEquals(new Rectangle(330,630,300,40), instructions.backToMenu.getBounds());

        Assertions.assertEquals(new Rectangle(125,105,150,100), instructions.arrows.getBounds());

        Assertions.assertEquals(new Rectangle(300,128,50,50), instructions.mcReg.getBounds());

        Assertions.assertEquals(new Rectangle(350,125,50,50), instructions.mcMove.getBounds());

        Assertions.assertEquals("Use WASD keys to move", instructions.keys.getText());
        Assertions.assertEquals(new Rectangle(425,100,300,100), instructions.keys.getBounds());

        Assertions.assertEquals(new Rectangle(145,225,50,50), instructions.log.getBounds());

        Assertions.assertEquals(new Rectangle(210,225,50,50), instructions.coin.getBounds());

        Assertions.assertEquals("Collect logs and coins to improve your score", instructions.rewards.getText());
        Assertions.assertEquals(new Rectangle(300,195,600,100), instructions.rewards.getBounds());

        Assertions.assertEquals(new Rectangle(175,295,50,50), instructions.mushroom.getBounds());

        Assertions.assertEquals("Mushrooms reduce your score", instructions.punish.getText());
        Assertions.assertEquals(new Rectangle(325,265,450,100), instructions.punish.getBounds());

        Assertions.assertEquals(new Rectangle(175,360,50,50), instructions.wildMoose.getBounds());

        Assertions.assertEquals("Wild moose will chase and kill you", instructions.moving.getText());
        Assertions.assertEquals(new Rectangle(300,335,500,100), instructions.moving.getBounds());

        Assertions.assertEquals("Power Ups:", instructions.powerUps.getText());
        Assertions.assertEquals(new Rectangle(375,390,400,100), instructions.powerUps.getBounds());

        Assertions.assertEquals(new Rectangle(175,470,50,50), instructions.juice.getBounds());

        Assertions.assertEquals(new Rectangle(175,525,50,50), instructions.trap.getBounds());

        Assertions.assertEquals("<html>Press J to drink Mushroom Juice and boost your speed<br/><br/>Press K to lay a Moose Trap which can kill a Wild Moose</html>", instructions.powerInfo.getText());
        Assertions.assertEquals(new Rectangle(250,420,700,200), instructions.powerInfo.getBounds());

    }

}
