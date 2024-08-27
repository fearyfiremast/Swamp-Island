package org.entity;

import javafx.util.Pair;
import org.game.GameManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents a generic entity in the game that has properties and sprites.
 * This class models a generic entity within the game, and includes attributes
 * and functionality shared among all entities.
 *
 * @author Bryan
 * @version 1.0
 */
public abstract class Entity {
    // Properties
    public GameManager gm;
    public int cellX, cellY;
    public int baseSpeed;
    public int moveSpeed;

    //TODO should change facing to an enum if given the chance
    public char facing;
    public boolean idleState;
    public Rectangle hitbox;

    public int screenX;
    public int screenY;
    public boolean movable = true;

    // Sprites
    public BufferedImage idle_u1, idle_u2, idle_d1, idle_d2, // Idle Sprites
                        idle_l1, idle_l2, idle_r1, idle_r2;
    public BufferedImage move_u1, move_u2, move_u3, move_u4, // Move Sprites
                        move_d1, move_d2, move_d3, move_d4,
                        move_l1, move_l2, move_l3, move_l4,
                        move_r1, move_r2, move_r3, move_r4;

    // Sprite no.
    public int idleSpriteNum = 0; // cycles between 0 and 1
    public int moveSpriteNum = 0; // cycles through 0 to 3


    /**
     * Load sprites for the corresponding entity. Uses the name of the entity
     * to access the sprite folder that corresponds to that entity.
     *
     * @param entityName    Name of entity class in camelcase (to match folders)
     */
    public void loadSprites(String entityName) {
        try {
            // Base path
            String basePath = "src/main/Images/sprites/";
            // Idle Sprites
            idle_u1 = ImageIO.read(new File(basePath + entityName + "/idle/idle_u1.png"));
            idle_u2 = ImageIO.read(new File(basePath + entityName + "/idle/idle_u2.png"));

            idle_d1 = ImageIO.read(new File(basePath + entityName + "/idle/idle_d1.png"));
            idle_d2 = ImageIO.read(new File(basePath + entityName + "/idle/idle_d2.png"));

            idle_l1 = ImageIO.read(new File(basePath + entityName + "/idle/idle_l1.png"));
            idle_l2 = ImageIO.read(new File(basePath + entityName + "/idle/idle_l2.png"));

            idle_r1 = ImageIO.read(new File(basePath + entityName + "/idle/idle_r1.png"));
            idle_r2 = ImageIO.read(new File(basePath + entityName + "/idle/idle_r2.png"));

            // Move Sprites
            move_u1 = ImageIO.read(new File(basePath + entityName + "/move/move_u1.png"));
            move_u2 = ImageIO.read(new File(basePath + entityName + "/move/move_u2.png"));
            move_u3 = ImageIO.read(new File(basePath + entityName + "/move/move_u3.png"));
            move_u4 = ImageIO.read(new File(basePath + entityName + "/move/move_u4.png"));

            move_d1 = ImageIO.read(new File(basePath + entityName + "/move/move_d1.png"));
            move_d2 = ImageIO.read(new File(basePath + entityName + "/move/move_d2.png"));
            move_d3 = ImageIO.read(new File(basePath + entityName + "/move/move_d3.png"));
            move_d4 = ImageIO.read(new File(basePath + entityName + "/move/move_d4.png"));

            move_l1 = ImageIO.read(new File(basePath + entityName + "/move/move_l1.png"));
            move_l2 = ImageIO.read(new File(basePath + entityName + "/move/move_l2.png"));
            move_l3 = ImageIO.read(new File(basePath + entityName + "/move/move_l3.png"));
            move_l4 = ImageIO.read(new File(basePath + entityName + "/move/move_l4.png"));

            move_r1 = ImageIO.read(new File(basePath + entityName + "/move/move_r1.png"));
            move_r2 = ImageIO.read(new File(basePath + entityName + "/move/move_r2.png"));
            move_r3 = ImageIO.read(new File(basePath + entityName + "/move/move_r3.png"));
            move_r4 = ImageIO.read(new File(basePath + entityName + "/move/move_r4.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the hitbox of the Entity, initializing the size and location
     * of the hitbox relative to the Entity location.
     *
     * @param       xPos X value of the corner of the hitbox
     * @param       yPos Y value of the corner of the hitbox
     * @param       boxWidth Width of the hitbox
     * @param       boxHeight Height of the hitbox
     */
    public void setHitBox(int xPos, int yPos, int boxWidth, int boxHeight) {
        hitbox = new Rectangle();
        // Position relative to entity
        hitbox.x = xPos;
        hitbox.y = yPos;

        // Hitbox properties
        hitbox.width = boxWidth;
        hitbox.height = boxHeight;
    }

    /**
     * Gets the 4 corners of the hitbox. Corners are returned by a Pair of
     * integers that represent the x-coordinate and y-coordinate.
     *
     * @return    The 4 corners of the hitbox
     */
    public Pair<Integer, Integer>[] getHitBox() {
        Pair<Integer, Integer>[] hitboxPositions= new Pair[4];
        hitboxPositions[0] = new Pair<>(hitbox.x, hitbox.y);
        hitboxPositions[1] = new Pair<>(hitbox.x + hitbox.width, hitbox.y);
        hitboxPositions[2] = new Pair<>(hitbox.x + hitbox.width, hitbox.y + hitbox.height);
        hitboxPositions[3] = new Pair<>(hitbox.x, hitbox.y + hitbox.height);

        return hitboxPositions;
    }


    /**
     * Transforms the CellX and CellY attributes into a Pair
     * @return Pair (cellX, cellY)
     */
    public Pair<Integer, Integer> getPosPair() {
        return new Pair<>(cellX, cellY);
    }

    public abstract void update();

    public abstract void draw(Graphics2D drawer);


    /**
     * Cycles between the various sprites to create idle animations, and move
     * animations.
     * <p>
     * Cycles through idle sprites every 300ms = 18 ticks
     * Cycles through move sprites every 200ms = 12 ticks
     */
    public void spriteCycler() {
        // Idle animation cycle
        if(gm.elapsedTick % 18 == 0) { // Executes every 300ms/18ticks
            idleSpriteNum = (idleSpriteNum + 1) % 2;
        }
        // Move animation cycle
        if(gm.elapsedTick % 12 == 0) { // Executes every 200ms/12ticks
            moveSpriteNum = (moveSpriteNum + 1) % 4;
        }
    }


    /**
     * Sets the idle sprite image based on the direction faced by the entity.
     * Alternates between two variants of each directional sprite, which is set
     * by spriteCycler().
     *
     * @param facing    Direction faced by entity
     * @return    Sprite image corresponding to direction faced and sprite cycle
     */
    public BufferedImage idleSprite(char facing) {
        BufferedImage sprite = null;
        switch(facing) {
            case 'u':
                if(idleSpriteNum == 0) {sprite = idle_u1;}
                else {sprite = idle_u2;}
                break;
            case 'd':
                if(idleSpriteNum == 0) {sprite = idle_d1;}
                else {sprite = idle_d2;}
                break;
            case 'l':
                if(idleSpriteNum == 0) {sprite = idle_l1;}
                else {sprite = idle_l2;}
                break;
            case 'r':
                if(idleSpriteNum == 0) {sprite = idle_r1;}
                else {sprite = idle_r2;}
                break;
        }
        return sprite;
    }


    /**
     * Sets the move sprite image based on the direction faced by the entity.
     * Alternates between four variants of each directional sprite, which is set
     * by {@code spriteCycler()}.
     *
     * @param facing    Direction faced by entity
     * @return    Sprite image corresponding to direction faced and sprite cycle
     */
    public BufferedImage moveSprite(char facing) {
        BufferedImage sprite = null;
        switch(facing) {
            case 'u':
                switch(moveSpriteNum) {
                    case 0: sprite = move_u1; break;
                    case 1: sprite = move_u2; break;
                    case 2: sprite = move_u3; break;
                    case 3: sprite = move_u4; break;
                }
                break;
            case 'd':
                switch(moveSpriteNum) {
                    case 0: sprite = move_d1; break;
                    case 1: sprite = move_d2; break;
                    case 2: sprite = move_d3; break;
                    case 3: sprite = move_d4; break;
                }
                break;
            case 'l':
                switch(moveSpriteNum) {
                    case 0: sprite = move_l1; break;
                    case 1: sprite = move_l2; break;
                    case 2: sprite = move_l3; break;
                    case 3: sprite = move_l4; break;
                }
                break;
            case 'r':
                switch(moveSpriteNum) {
                    case 0: sprite = move_r1; break;
                    case 1: sprite = move_r2; break;
                    case 2: sprite = move_r3; break;
                    case 3: sprite = move_r4; break;
                }
                break;
        }
        return sprite;
    }

    /**
     * Gets the center of a 48x48 entity. cellX and cellY are the top left corner
     * of the entity (due to the nature of the system), and thus adds 24 to each
     * attribute to obtain the center.
     *
     * @return    The center of the entity
     */
    public Pair<Integer, Integer> getCenter() {
        return new Pair<>(cellX + 24, cellY + 24);
    }


}
