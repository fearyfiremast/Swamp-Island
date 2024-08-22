package org.display;

import org.cell.RewardCell_Bonus;
import org.entity.MainCharacter;
import org.game.GameManager;
import org.game.MapManager;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel containing the game screen as well as the score board.
 * Initializes and sets up scoreboard, most of the game is set up through GameManager.
 * @author      Rob Cameron
 */
public class GameScreen extends Screens implements Runnable {

    static JLabel message, juice, trap;
    JPanel scoreBoard;
    String basePath = "Swamp Island/src/main/Images/UI/";

    public static Thread bottomPanel;
    public static boolean gameOn = false, rewardM = false, exitM = false;
    static final Object lock = new Object();

    /**
     * Constructor. Creates scoreboard and its components.
     * Adds them all to the panel.
     *
     * @param       main JPanel that contains the main game element.
     */
    public GameScreen(JPanel main) {
        this.setLayout(new BorderLayout());

        String text = "Time: 0    Score: 0    Rewards Left: " + MapManager.numRewards + "    Power-ups:";
        message = new JLabel(text,JLabel.CENTER);
        message.setMinimumSize(new Dimension(800,60));
        message.setFont(new Font("Art Nuvo Stamp",Font.BOLD,30));
        message.setForeground(new Color(222,214,190));

        scoreBoard = new JPanel();
        scoreBoard.setBackground(new Color(134,88,68));
        scoreBoard.add(message);

        juice = getSizedImage(basePath + "MushroomJuice.png",new Rectangle(800,600,30,30));
        juice.setVisible(false);

        trap = getSizedImage(basePath + "Trap.png",new Rectangle(830,600,30,30));
        trap.setVisible(false);

        scoreBoard.add(juice);
        scoreBoard.add(trap);

        this.add(scoreBoard,BorderLayout.PAGE_END);
        this.add(main,BorderLayout.CENTER);
    }

    /**
     * Updates the scoreboard to the correct time and score.
     *
     * @param       time integer of the seconds of elapsed time so far in the game.
     * @param       score integer of the user's score so far
     */
    public static void update(int time, int score) {
        int mins = 0;
        int secs = time;

        if (time >= 60) {
            mins = time / 60;
            secs = time % 60;
        }

        String text = "Time: " + mins + ":" + String.format("%02d",secs) + "    Score: " + score + "    Rewards Left: " + MapManager.numRewards + "    Power-ups:";

        message.setText(text);
    }

    /**
     * Updates the scoreboard with a message for the user if they are on the exit cell without collecting rewards.
     */
    public static void exitUpdate() {
        String text = "Must collect all rewards before exiting";

        message.setText(text);
        juice.setVisible(false);
        trap.setVisible(false);
    }

    /**
     * Updates the scoreboard with a message when a bonus reward appears.
     *
     * @param       s String with location of reward
     */
    public static void rewardUpdate(String s){

        message.setText(s);
        juice.setVisible(false);
        trap.setVisible(false);
    }

    /**
     * Changes the visibility of the power up icons on the scoreboard,
     * depending on whether the main character has it or not.
     */
    public static void updatePowerUP() {

        juice.setVisible(MainCharacter.isHasJuice());
        trap.setVisible(MainCharacter.isHasTrap());
    }

    /**
     * Start the thread for the scoreboard.
     * Sets gameOn boolean to true and calls bottom panel's run method.
     */
    public void startThread() {
        gameOn = true;
        bottomPanel = new Thread(this);
        bottomPanel.start();
    }

    /**
     * Run method for bottom panel. Controls the scoreboard so messages are displayed properly.
     * Reward and exit messages will remain for 2 seconds, update will continually run.
     */
    @Override
    public void run() {

        while (gameOn) {

            synchronized (lock) {

                if (rewardM) {
                    rewardUpdate(RewardCell_Bonus.alert);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                    rewardM = false;
                } else if (exitM) {
                    exitUpdate();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                    exitM = false;
                } else {
                    update(GameManager.getElapsedTime(), MainCharacter.getScore());
                    updatePowerUP();
                }

            }
        }
    }
}
