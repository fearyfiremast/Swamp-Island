package org.display;

import org.entity.MainCharacter;
import org.game.GameManager;

import javax.swing.*;
import java.awt.*;

/**
 * Displays the end screen when the game is over
 * @author      Rob Cameron
 */
public class End extends Screens {

    static JLabel titleMessage,finalMessage;
    public JButton playAgain,toMenu;

    /**
     * Constructor for End.
     * Initializes all the components and adds them to the screen.
     * Sets the labels and buttons' bounds and text.
     * Also sets the layout and background of the panel
     */
    public End() {
        super();

        playAgain = makeButton("Play Again", new Rectangle(330,400,300,100));

        toMenu = makeButton("Exit to Menu", new Rectangle(330,550,300,100));

        titleMessage = makeLabel("GAME OVER",60, new Rectangle(280,75,400,100));

        finalMessage = makeLabel("You did not make it to the end :(",40, new Rectangle(180,200,600,100));

        this.setLayout(null);
        this.setBackground(new Color(134,88,68));
        this.add(titleMessage);
        this.add(finalMessage);
        this.add(playAgain);
        this.add(toMenu);

    }

    /**
     * If a user wins the game, switches to the end screen and displays a message.
     * Displays user's score and time.
     * Also gives them the option to play again or exit to menu.
     *
     * @param       l CardLayout being used for display
     * @param       c Container with all the JPanels
     */
    public static void showWin(CardLayout l, Container c) {
        int mins = 0;
        int secs = GameManager.getElapsedTime();

        if (secs >= 60) {
            mins = secs / 60;
            secs = secs % 60;
        }

        String s = "<html>Your score: " + MainCharacter.getScore() + "<br/>Time elapsed: " + mins + ":" + String.format("%02d",secs) + "<html>";
        titleMessage.setText("VICTORY!");
        finalMessage.setText(s);
        l.show(c,"end");
    }

    /**
     * If a user loses the game, switches to end screen and displays a message.
     * Also gives them the option to play again or exit to menu.
     *
     * @param       l CardLayout object which is being used for the display
     * @param       c Container which contains all JPanels
     */
    public static void showLoss(CardLayout l, Container c) {
        titleMessage.setText("GAME OVER");
        finalMessage.setText("You did not make it to the end :(");
        l.show(c,"end");
    }
}
