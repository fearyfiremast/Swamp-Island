package org.game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// Import entity package
import javafx.util.Pair;
import org.cell.Cell;
import org.display.End;
import org.display.GameScreen;
import org.display.MainMenu;
import org.entity.*;
import org.extras.CellType;

/**
 * This Class organizes the two aspects of the game, Cells and Entities. Schedules their Updates and Drawing components.
 * @author Xander Smith
 * @version 1.0
 * @since 0.1
 */
public class GameManager extends JPanel implements Runnable {

    public static int getCellSize() {
        return cellSize;
    }

    public static int cellSize = 48;
    int maxCol = 20;
    int maxRow = 15;

    // PixelSize
    public int panelWidth = cellSize * maxCol;
    public int panelLength = cellSize * maxRow;

    public int fps = 60;
    boolean gameActive = false;

    public boolean gameLost = false;
    static int elapsedTime;
    public int elapsedTick = 0;
    public int highScore;

    public InputHandler inputHandler;
    public CollisionHandler collisionHandler;
    Thread thread = null;
    static MainCharacter mc;
    MapManager mapManager;
    ArrayList<Enemy> enemyList = null;
    ArrayList<Enemy> removalStorage;
    static CardLayout layout;
    static Container cards;

    /**
     * Basic constructor
     */
    public GameManager() {

    }

    /**
     * Constructor that should be used in almost every case
     * @param ih InputHandler
     * @param l CardLayout
     * @param c Container
     */
    public GameManager(InputHandler ih, CardLayout l, Container c) {

        layout = l;
        cards = c;

        elapsedTime = 0;
        highScore = getHighScore();
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        // Beginning to set up Game
        int x = 50;
        int y = 50;
        System.out.println("GameManager constructed");
        MapTemplate template = new MapTemplate(x, y);
        this.collisionHandler = new CollisionHandler(this);
        this.mapManager = new MapManager(template, this);

        inputHandler = ih;
        mc = new MainCharacter(this);

        enemyList = new ArrayList<>();
        this.removalStorage = new ArrayList<>();
        for (Pair<Integer, Integer> i : template.enemyPosArray) {
            enemyList.add(new Enemy(this, i, mc));
        }
        //bgMusic = new Audio("SwampIslandRefactor/Swamp Island/src/main/Images/music/what-makes-you-beautiful.wav");
    }

    /**
     * Starts the thread after initializing the main characters values
     */
    public void startThread() {
        mc.initializeValues(mapManager.getStartCellPos());
        this.thread = new Thread(this);
        thread.start();
    }

    /**
     * Returns the MainCharacter attribute
     * @return MainCharacter
     */
    public MainCharacter getMainCharacter()
    {
        return this.mc;
    }

    /**
     * Schedule method that calls various aspects of the code and allows them to perform some function and draw on screen<p>
     * Scheduled aspects are: MapManager, Entities <p>
     * main body of method is called 60 times every seconds
     */
    public void run() {
        double globalTimer = 0;

        double drawInterval = 1000000000 / (double)fps;
        double prevTime = System.nanoTime();
        double curTime;
        double delta = 0;
        gameActive = true;
        while (gameActive) {

            curTime = System.nanoTime();

            delta += (curTime - prevTime) / drawInterval;
            globalTimer += (curTime - prevTime);

            prevTime = curTime;

            if (delta >= 1) {

                update();

                collisionHandler.checkBehavior(mc);

                repaint();

                delta--;
                elapsedTick++;
            }

            if (globalTimer >= 1000000000) {
                globalTimer = 0;
                elapsedTime++;
            }
            CellType toCheck = getCell(mc.cellX,mc.cellY).cellEnum;
            if ( (toCheck == CellType.ExitCell_Boat || toCheck == CellType.ExitCell_Raft)
                    && MapManager.numRewards == 0) {
                gameActive = false;
                GameScreen.gameOn = false;
                this.writeHighScore(MainCharacter.getScore());
                MainMenu.updateHScore("High Score: " + getHighScore());
                winGame();
            }

            if (MainCharacter.getScore() < 0 || gameLost) {
                gameLost = false;
                GameScreen.gameOn = false;
                gameActive = false;
                loseGame();
            }
        }
    }

    /**
     * update method houses the order in which actors independent update methods are called
     */
    public void update() {
        mc.update();
        for (Enemy i : enemyList) {
            i.update();
            //System.out.println(i.getPosPair());
        }
        for (Enemy i :removalStorage) {
            enemyList.remove(i);
        }
        removalStorage.clear();
        //System.out.println("--------------------");
        mapManager.update();
    }

    /**
     * paintComponent method houses the order in which actors independent draw methods are called<p> works akin to layers on a canvas.
     * Methods called later are drawn on top of methods called earlier
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D drawer = (Graphics2D)g;

        // Components to repaint

        mapManager.draw(drawer);
        mc.draw(drawer);
        for (Enemy i : enemyList) {
            i.draw(drawer);
        }

        if (getCell(mc.cellX, mc.cellY).cellEnum == CellType.ExitCell && MapManager.numRewards != 0) {
            GameScreen.exitM = true;
        }

        drawer.dispose();
    }

    /**
     * Setter for elapsed time attribute.
     *
     * @param       time integer to set elapsed time to
     */
    public static void setElapsedTime(int time) {
        elapsedTime = time;
    }

    /**
     * Getter for elapsed time.
     *
     * @return      Integer of the elapsed time
     */
    public static int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * When a user loses the game, changes the screen.
     * All functionality handled by end screen.
     */
    public static void loseGame() {
        End.showLoss(layout,cards);
    }

    /**
     * When a user wins the game, changes the screen.
     * All functionality handled by end screen.
     */
    public static void winGame() {
        End.showWin(layout,cards);
    }

    /**
     * Resets key attributes for the game.
     * Mainly resetting the scoreboard.
     */
    public void resetGame() {

        setElapsedTime(0);
        MainCharacter.incrementScore(-MainCharacter.getScore());
        this.mapManager.resetHandler.ResetGame();
    }

    /**
     * Reads highScore from fileName.
     * @return Integer of the high score
     * */
    public static int getHighScore()
    {
        int highScore;
        try{    // the file gets opened for reading
            BufferedReader buffReader = new BufferedReader(new FileReader("SwampIslandRefactor/Swamp Island/src/main/textFiles/HighScore.txt"));   // can throw IOException when input file does not exists.
            String line = buffReader.readLine();
            if(line != null) {//if something was actually read from the file.
                highScore = Integer.parseInt(line.trim());  // can throw numberFormatException
            } else {
                highScore = 0;
                System.out.println("Couldn't read high score");
            }
        }
        catch (IOException | NumberFormatException e)
        {
            System.out.println("ERROR");
            highScore = 0;
        }
        return highScore;
    }

    /**
     * Function to write new highScore to fileName.
     *
     * @param score user's final score
     */
    public void writeHighScore(int score)
    {
        // Check if the new value is better than the old one
        if(score > this.highScore)
        {   // open the file

            this.highScore = score;

            try {
                FileWriter scoreWriter = new FileWriter("SwampIslandRefactor/Swamp Island/src/main/textFiles/HighScore.txt");
                scoreWriter.write(Integer.toString(highScore));
                scoreWriter.close();
            }
            catch (IOException | NumberFormatException e)
            {
                System.err.println("Error BufferedWriter: " + e.getMessage() + "\n");
            }
        }
        // if the score is not better, then nothing needs to change.
    }

    /**
     * Given an x and y value returns the Cell it corresponds to.<p>
     * Values must be in terms of Map Pixel position. Can cause an outOfBounds exception if weird values are put in
     * @param x coordinate  int
     * @param y coordinate  int
     * @return Cell
     */
    public Cell getCell(int x, int y) {
        x /= cellSize;
        y /= cellSize;
        return MapManager.cellArray[x][y];
    }

    /**
     * Given pair value returns the Cell it corresponds to.
     * Values must be in terms of Map Pixel position. Can cause an outOfBounds exception if weird values are put in
     * @param index Pair (Integer, Integer)
     * @return Cell
     */
    public Cell getCell(Pair<Integer, Integer> index) {
        int x = index.getKey() / cellSize;
        int y = index.getValue() / cellSize;
        return mapManager.cellArray[x][y];
    }

    /**
     * Sets the gameLost value to be some boolean
     * @param gameLost boolean
     */
    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    /**
     * Adds the Enemy to the toKill array which will be removed from the enemy list after every Enemy has had their
     * update function called.
     * @param toKill
     */
    public void killEnemy(Enemy toKill) {
        this.removalStorage.add(toKill);
    }

}
