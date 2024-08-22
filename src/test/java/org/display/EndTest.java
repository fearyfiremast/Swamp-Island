package org.display;

import org.entity.MainCharacter;
import org.game.GameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Tests the functionality in the End class
 * @author      Rob Cameron
 */
public class EndTest {

    End end;

    /**
     * Before each to initialize end object
     */
    @BeforeEach
    void init(){
        end = new End();
    }

    /**
     * Tests the constructor method
     */
    @Test
    void constructorTest() {
        assert(end != null);
        assert(end.playAgain != null);
        assert(end.toMenu != null);
        assert(End.finalMessage != null);
        assert(End.titleMessage != null);
        assert(end.getLayout() == null);
        assert(Objects.equals(end.getBackground(), new Color(134, 88, 68)));
    }

    /**
     * Tests the showWin method for time less than sixty seconds
     */
    @Test
    void lessSixtyShowWinTest() {
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        cards.setLayout(layout);
        cards.add("end",end);

        MainCharacter.setScore(0);
        GameManager.setElapsedTime(0);

        End.showWin(layout,cards);

        assert(Objects.equals(End.titleMessage.getText(), "VICTORY!"));
        assert(Objects.equals(End.finalMessage.getText(),"<html>Your score: 0<br/>Time elapsed: 0:00<html>"));
    }

    /**
     * Tests the showWin method for time more than 60 seconds
     */
    @Test
    void moreSixtyShowWinTest() {
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        cards.setLayout(layout);
        cards.add("end",end);

        GameManager.setElapsedTime(70);

        End.showWin(layout,cards);

        assert(Objects.equals(End.titleMessage.getText(), "VICTORY!"));
        assert(Objects.equals(End.finalMessage.getText(),"<html>Your score: 0<br/>Time elapsed: 1:10<html>"));
    }

    /**
     * Tests the showLoss method
     */
    @Test
    void showLossTest() {
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        cards.setLayout(layout);
        cards.add("end",end);
        End.showLoss(layout,cards);

        assert(Objects.equals(End.titleMessage.getText(), "GAME OVER"));
        assert(Objects.equals(End.finalMessage.getText(), "You did not make it to the end :("));
    }

}
