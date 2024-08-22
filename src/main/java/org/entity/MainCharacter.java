package org.entity;

import javafx.util.Pair;
import org.cell.Cell;
import org.cell.EnemyTrap;
import org.extras.CellType;
import org.game.GameManager;
import org.game.InputHandler;
import org.game.MapManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the Main Character in the game. Extends the {@link Entity} abstract class
 * and implements functionality specific to the Main Character.
 *
 * @author Bryan
 * @version 1.0
 */
public class MainCharacter extends Entity {
    InputHandler inputHandler;
    static int score;

    public static void setScore(int score) {
        MainCharacter.score = score;
    }
    public static boolean hasJuice, hasTrap;  //TODO update naming
    public int speedBuffDuration;
    public boolean onMarsh;

    /**
     * Basic Constructor
     * Constructs a new MainCharacter. Passes the corresponding GameManager
     * and InputHandler.
     * <p>
     * Initializes properties of the Main Character such as the sprites and
     * hitbox.
     *
     * @param gm    Instance of GameManager
     */
    public MainCharacter(GameManager gm) {
        this.gm = gm;
        this.inputHandler = gm.inputHandler;

        score = 0;
        hasJuice = false;
        hasTrap = false;

        setHitBox(12, 24, 24, 18);

        loadSprites("mainCharacter");
    }


    /**
     * Initializes properties of Main Character not handled by the constructor,
     * particularly relating to position-related and movement-related values.
     *
     * @param startPos    Starting position of Main Character
     */
    public void initializeValues(Pair<Integer, Integer> startPos) {
        cellX = startPos.getKey() * GameManager.cellSize;
        cellY = startPos.getValue() * GameManager.cellSize;
        screenX = gm.panelWidth/2 - (GameManager.cellSize/2);
        screenY = gm.panelLength/2 - (GameManager.cellSize/2);
        baseSpeed = 4;
        moveSpeed = baseSpeed;
        facing = 'd';
    }


    /**
     * Executes code and methods to update the state of the Main Character.
     * Specifically handles direction, movement, and cycling of sprites.
     * <p>
     * This method is called in the GameManager's update() method, which runs
     * once every tick.
     */
    public void update() {

        // Calculate moveSpeed
        calculateMoveSpeed();

        // Check powerup use
        if(inputHandler.useSpeed && hasJuice) {activateJuice();}
        if(inputHandler.useTrap && hasTrap) {activateTrap();}

        // Check if idle
        idleState = !(inputHandler.keyUp || inputHandler.keyDown || inputHandler.keyLeft || inputHandler.keyRight);

        if(!idleState) {
            // Check direction
            if(inputHandler.keyUp) {
                facing = 'u';
            } else if(inputHandler.keyDown) {
                facing = 'd';
            } else if(inputHandler.keyLeft) {
                facing = 'l';
            } else {
                facing = 'r';
            }

            // Check if a move in direction faced is valid
            movable = true;
            gm.collisionHandler.checkPassable(this);

            // Move if valid
            if(movable) {
                switch (facing) {
                    case 'u':
                        cellY -= moveSpeed;
                        break;
                    case 'd':
                        cellY += moveSpeed;
                        break;
                    case 'l':
                        cellX -= moveSpeed;
                        break;
                    case 'r':
                        cellX += moveSpeed;
                        break;
                }
            }
        }

        // Decrement buff duration
        if(speedBuffDuration > 0) {speedBuffDuration -= 1;}

        // Cycle through sprite numbers for idle & move sprites
        spriteCycler();
    }


    /**
     * Executes code and methods to update the appearance of the Main Character.
     * Mainly handles the animation of sprites, and updating the location of the
     * Main Character's sprites to reflect its position.
     * <p>
     * This method is called in the GameManager's update() method, which runs
     * once every tick.
     *
     * @param drawer    The Graphics2D instance used for drawing
     */
    public void draw(Graphics2D drawer) {

        BufferedImage sprite;

        // Set sprite based on idleState & direction faced
        if(idleState) {
            sprite = idleSprite(facing);
        } else {
            sprite = moveSprite(facing);
        }

        // Draw sprite
        drawer.drawImage(sprite, screenX, screenY, GameManager.cellSize, GameManager.cellSize, null);
    }


    /**
     * Sets the position of main character to a specific cell. Takes x and y
     * as in terms of cell, and converts it to the pixel position of the main
     * character.
     *
     * @param x    x-coordinate in terms of cells (corresponds to cellArray)
     * @param y    y-coordinate in terms of cells (corresponds to cellArray)
     */
    public void setPosition(int x, int y) {
        this.cellX = x * GameManager.getCellSize();
        this.cellY = y * GameManager.getCellSize();
    }


    /**
     * Consumes the juice powerup, and activates the speed buff.
     */
    private void activateJuice() {
        // 3s buff duration
        speedBuffDuration = 3 * 60;

        // Removes juice from inventory
        hasJuice = false;
    }

    /**
     * Consumes the trap powerup, and places down the trap at the Main Character's
     * current position.
     * <p>
     * If trap cannot be placed, doesn't remove trap from inventory
     */
    private void activateTrap() {
        int cSize = GameManager.cellSize;

        // Get position of the center of Main Character
        int centeredX = this.getCenter().getKey();
        int centeredY = this.getCenter().getValue();

        // Get cell at center of Main Character
        Cell currentCell = MapManager.cellArray[centeredX/cSize][centeredY/cSize];

        // Checks if current pos is EmptyCell
        if(currentCell.cellEnum == CellType.EmptyCell) {
            // Replaces currentCell with a EnemyTrap
            EnemyTrap trap = new EnemyTrap(currentCell.groundEnum, currentCell.cellPosition, currentCell.mapManager);
            MapManager.cellArray[currentCell.cellPosition.getKey()][currentCell.cellPosition.getValue()] = trap;

            // Adds current cell to resetHandler
            currentCell.mapManager.resetHandler.add(currentCell);

            // Removes trap from inventory if used
            hasTrap = false;
        }
    }


    /**
     * Calculates the Main Character's current moveSpeed based on several factors
     * including the groundType and active speed buffs.
     */
    public void calculateMoveSpeed() {
        int speedModifier = 0;

        // Check if destination is of "marsh" GroundType
        gm.collisionHandler.checkMarsh(this);

        // Calculate speed modifier //TODO values are subject to change
        if(onMarsh) {speedModifier -= 1;}
        if(speedBuffDuration > 0) {speedModifier += 2;}

        // Set moveSpeed
        moveSpeed = baseSpeed + speedModifier;
    }


    /**
     * Setter for score.
     *
     * @param       s Integer that is the new score
     */
    public static void incrementScore(int s) {
        score += s;
    }

    /**
     * Getter for score.
     *
     * @return      The user's current score
     */
    public static int getScore() {
        return score;
    }

    /**
     * Setter for hasJuice.
     *
     * @param       b Boolean for whether the user has a mushroom juice or not
     */
    public static void setHasJuice(boolean b) {
        hasJuice = b;
    }

    /**
     * Getter for hasJuice.
     *
     * @return      hasJuice. True if user does, false otherwise.
     */
    public static boolean isHasJuice() {
        return hasJuice;
    }

    /**
     * Setter for hasTrap.
     *
     * @param       b Boolean for whether the user has a trap or not
     */
    public static void setHasTrap(boolean b) {
        hasTrap = b;
    }

    /**
     * Getter for hasTrap.
     *
     * @return      hasTrap. True if user does, false otherwise.
     */
    public static boolean isHasTrap() {
        return hasTrap;
    }

}
