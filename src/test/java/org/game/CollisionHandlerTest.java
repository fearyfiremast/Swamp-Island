package org.game;

import org.cell.Cell;
import org.entity.MainCharacter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the collisionHandler test
 * @author Bryan
 */
class CollisionHandlerTest {

    InputHandler inputHandler;
    CardLayout layout;
    Container cards;
    CollisionHandler collisionHandler;
    GameManager gameManager;
    MainCharacter mainCharacter;
    MapManager mapManager;

    /**
     * Sets up the various objects that relate to CollisionHandler
     */
    @BeforeEach
    public void setup() {
        inputHandler = new InputHandler();
        layout = new CardLayout();
        cards = new Container();
        gameManager = new GameManager(inputHandler, layout, cards);
        collisionHandler = new CollisionHandler(gameManager);
        mainCharacter = new MainCharacter(gameManager);

        mainCharacter.facing ='u';
    }

    /**
     * Tests checkPassable() when character is facing a Wall
     */
    @Test
    void testCheckPassableWall() {
        // Cell[2][0] = WallCell
        mainCharacter.cellX = 96; // 96/48 = 2
        mainCharacter.cellY = 0; // 0/48 = 0

        collisionHandler.checkPassable(mainCharacter);
        assertFalse(mainCharacter.movable);
    }

    /**
     * Tests checkPassable() when character is not facing a Wall
     */
    @Test
    void testCheckPassableEmptyCell() {
        // Cell[2][2] = EmptyCell
        mainCharacter.cellX = 96; // 96/48 = 2
        mainCharacter.cellY = 96; // 96/48 = 2

        collisionHandler.checkPassable(mainCharacter);
        assertTrue(mainCharacter.movable);
    }

    /**
     * Tests if checkBehavior() calls an update() when on an ActivityCell
     * (PunishmentCell in this case)
     */
    @Test
    void testCheckBehavior() {
        // Cell[25][41] = PunishmentCell
        mainCharacter.cellX = 1200; // 1200/48 = 25
        mainCharacter.cellY = 1968; // 1968/48 = 41
        MainCharacter.setScore(300);

        collisionHandler.checkBehavior(mainCharacter);
        int finalScore = MainCharacter.getScore();
        assertEquals(300, finalScore+200);
    }


    /**
     * Tests if checkMarsh() properly detects when it is on a cell with GroundType
     * marsh.
     */
    @Test
    void testCheckMarsh() {
        // Cell[2][2] = marsh
        mainCharacter.cellX = 96;
        mainCharacter.cellY = 96;

        collisionHandler.checkMarsh(mainCharacter);
        assertTrue(mainCharacter.onMarsh);
    }

}