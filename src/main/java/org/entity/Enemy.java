package org.entity;

import javafx.util.Pair;
import org.cell.Cell;
import org.cell.EnemyTrap;
import org.entity.pathfinding.SearchAlgorithm;
import org.entity.pathfinding.SearchUnit;
import org.extras.CellType;
import org.game.GameManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Enemy Class that extends Entity. Will always move towards the player and if the entity and main character class
 * exist within the same cell will kill them.
 * @author Xander Smith
 * @version 1.0
 */
public class Enemy extends Entity{

    int testMoveTime = 0;
    MainCharacter mc = null;

    /**
     * Initializes values for the enemy class that will always start the same
     */
    private void initializeValues() {
        facing ='u';
        baseSpeed = 2;
        moveSpeed = baseSpeed;

        // Values copied from MainCharacter
        this.setHitBox(12, 24, 24, 18);
    }

    /**
     * Basic constructor
     */
    public Enemy() {
        initializeValues();
    }

    /**
     * Default Constructor that should always be used. Sets position, setManager, and mainCharacter
     * @param setManager GameManager
     * @param setPosition Pair<Integer, Integer>
     * @param setCharacter MainCharacter
     */
    public Enemy(GameManager setManager, Pair<Integer, Integer> setPosition, MainCharacter setCharacter) {
        initializeValues();
        this.mc = setCharacter;
        this.gm = setManager;
        cellX = setPosition.getKey() * gm.cellSize;
        cellY = setPosition.getValue() * gm.cellSize;

        loadSprites("enemy");
    }

    /**
     * Specific update method for enemy classes. Finds best move, checks if new pos is on a trap cell and updates
     * metric used to represent position relative to camera. Ends the game if the player and enemy exist on the same
     * cell
     */
    @Override
    public void update() {
        //TODO make death more consistent
        if (testMoveTime <= 0) {
            SearchUnit move = SearchAlgorithm.bestMove(this, mc.getPosPair(), 1);
            movable = true;
            gm.collisionHandler.checkPassable(this);

            if (movable && (move.getXPos() != cellX || move.getYPos() != cellY)) {
                cellY = move.getYPos();
                cellX = move.getXPos();
            }
            facing = move.getDirection();

            spriteCycler();

            //testMoveTime = 120;
        }
        //testMoveTime--;

        screenX = (cellX - mc.cellX) + mc.screenX;
        screenY = (cellY - mc.cellY)+ mc.screenY;

        // Check for EnemyTrap
        checkTrap();

        //TODO likely there is a slight offset from where the character actually is vs where they are presented basically
        // copied from both mapManager and MainCharacter
        //if (CollisionHandler.checkEntityCollision(this, mc)) {
        if (SearchAlgorithm.compareCellId(mc.getPosPair(), this.getPosPair(), GameManager.cellSize)){
            gm.gameLost = true;
        }
    }

    /**
     * Selects the correct type of sprite to draw and the draws it relative to the camera.
     * @param drawer Graphics2D
     */
    @Override
    public void draw(Graphics2D drawer) {
        //TODO make it so the system doesn't draw an enemy if they aren't on screen

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
     * Checks if the enemy collides with any EnemyTraps. Calls killEnemy()
     * if any of the 4 corners of the Enemy's hitbox touches an EnemyTrap.
     */
    public void checkTrap() {

        // get position of all 4 corners of the hitbox
        Pair<Integer, Integer>[] corners = new Pair[4];
        corners[0] = new Pair<>(cellX + hitbox.x, cellY + hitbox.y);  // top-left
        corners[1] = new Pair<>(cellX + hitbox.x + hitbox.width, cellY + hitbox.y);  // top-right
        corners[2]= new Pair<>(cellX + hitbox.x + hitbox.width, cellY + hitbox.y + hitbox.height);  // bottom-right
        corners[3] = new Pair<>(cellX + hitbox.x, cellY + hitbox.y + hitbox.height);  // bottom-left

        // Check EnemyTrap for each corner
        for(Pair<Integer, Integer> c : corners) {
            Cell cell = gm.getCell(c);
            if(cell.cellEnum == CellType.EnemyTrap) {
                killEnemy((EnemyTrap) cell);

                // Interrupt for-loop on first entry into if-block
                return;
            }
        }
    }

    /**
     * Removes the enemy. Called when the enemy steps on an EnemyTrap cell.
     */
    private void killEnemy(EnemyTrap trap){
        trap.update();
        gm.killEnemy(this);
    }
}
