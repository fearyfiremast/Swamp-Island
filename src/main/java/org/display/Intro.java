package org.display;

import org.game.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gives the user an introduction to the game and sets the scene.
 * @author      Rob Cameron
 */
public class Intro extends Screens {

    JLabel message;
    JButton goToGame;

    /**
     * Constructor. Gives user beginning message and has button to start the game.
     * This creates labels and buttons, as well as sets their size and location.
     * Also sets panel layout and background.
     */
    public Intro() {
        super();

        message = makeLabel("<html>You shipwrecked on the mysterious Swamp Island!<br/><br/>This island is uninhabited apart from the evil Wild Moose<br/><br/>Collect wood to rebuild your ship and don't get caught!<html>",30, new Rectangle(165,50,600,500));

        goToGame = makeButton("Start Game", new Rectangle(330,500,300,100));

        this.setPreferredSize(new Dimension(750,750));
        this.setLayout(null);
        this.setBackground(new Color(134,88,68));
        this.add(message);
        this.add(goToGame);
    }

    /**
     * Makes action listener for go to game button.
     * Switches the panel to the game and starts the game thread.
     *
     * @param       l Card layout that the panel is on
     * @param       c Container that the panels are in
     * @param       gm Game manager that starts the game
     * @param       gs GameScreen to start the thread for the scoreboard
     */
    public void makeButtonListener(CardLayout l, Container c, GameManager gm, GameScreen gs) {
        goToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.resetGame();
                l.show(c,"game");
                gs.startThread();
                gm.startThread();
            }
        });
    }

}
