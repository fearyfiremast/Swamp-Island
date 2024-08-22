package org.display;

import org.game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Displays the main menu, where the user can choose to quit, view instructions, or play
 * @author      Rob Cameron
 */
public class MainMenu extends Screens {

    JLabel title;
    public static JLabel hScore;
    public JButton exit, playGame, controls;
    String scoreLabel;

    /**
     * Constructor. Makes the main menu of the game.
     * Initializes and sets all the labels and buttons.
     * Also sets layout and background.
     */
    public MainMenu() {
        super();

        title = makeLabel("The Adventures of Swamp Island",55, new Rectangle(20,100,920,100));
        title.setVerticalAlignment(JLabel.TOP);

        int highScore = GameManager.getHighScore();

        scoreLabel = "High Score: " + highScore;

        hScore = makeLabel(scoreLabel,30, new Rectangle(330,500,300,100));

        exit = makeButton("Quit", new Rectangle(830,620,100,50));

        playGame = makeButton("Play Game", new Rectangle(330,275,300,100));

        controls = makeButton("Instructions", new Rectangle(330,400,300,100));

        this.setLayout(null);
        this.setBackground(new Color(134,88,68));

        this.add(title);
        this.add(playGame);
        this.add(controls);
        this.add(hScore);
        this.add(exit);

    }

    /**
     * Makes action listener for exit button.
     * Sets the JFrame to invisible, disposes of it, exits program.
     *
     * @param       f JFrame that button is on
     */
    public void makeButtonListener(JFrame f) {
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                f.dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Sets the high score on the label
     *
     * @param       s String that contains the message to be displayed
     */
    public static void updateHScore(String s) {
        hScore.setText(s);
    }

}
