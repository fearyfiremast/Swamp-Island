package org.entity;

import javafx.util.Pair;
import org.game.CollisionHandler;
import org.game.GameManager;
import org.game.InputHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Tests the functionality of the Enemy class
 * @author      Rob Cameron
 */
public class EnemyTest {

    GameManager gameManager;
    Enemy enemy;
    MainCharacter mainCharacter;

    /**
     * Before each create an enemy object
     */
    @BeforeEach
    void init() {
        InputHandler inputHandler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        gameManager = new GameManager(inputHandler,layout,cards);

        mainCharacter = new MainCharacter(gameManager);

        enemy = new Enemy(gameManager,new Pair<>(1,2),mainCharacter);
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {
        Assertions.assertEquals(mainCharacter,enemy.mc);
        Assertions.assertEquals(gameManager,enemy.gm);

        assert(enemy.cellX == 48);
        assert(enemy.cellY == 96);
    }

    /**
     * Tests the initializeValues method
     */
    @Test
    public void initializeValuesTest() {
        assert(enemy.facing == 'u');
        assert(enemy.baseSpeed == 2);
        assert(enemy.moveSpeed == 2);
        Assertions.assertEquals(new Rectangle(12, 24, 24, 18), enemy.hitbox);
    }

    /**
     * Tests the update method with positive move time
     */
    @Test
    public void posMoveTimeUpdateTest() {
        enemy.testMoveTime = 4;
        enemy.screenX = 0;
        enemy.screenY = 0;
        mainCharacter.cellX = 24;
        mainCharacter.cellY = 48;
        mainCharacter.screenX = 10;
        mainCharacter.screenY = 12;

        enemy.update();

        assert(enemy.screenX == 34);
        assert(enemy.screenY == 60);
    }

    /**
     * Tests the update method with negative move time
     */
    @Test
    public void negMoveTimeUpdateTest() {

        gameManager.collisionHandler = new CollisionHandler(gameManager);

        enemy.testMoveTime = -4;
        enemy.screenX = 0;
        enemy.screenY = 0;
        enemy.cellX = 48;
        enemy.cellY = 99;
        mainCharacter.cellX = 48;
        mainCharacter.cellY = 93;
        mainCharacter.screenX = 456;
        mainCharacter.screenY = 336;

        enemy.update();

        assert(enemy.cellX == 48);
        assert(enemy.cellY == 97);
        assert(enemy.screenX == 456);
        assert(enemy.screenY == 340);
        assert(enemy.facing == 'u');
    }

    /**
     * Tests the update method with zero move time
     */
    @Test
    public void zeroMoveTimeUpdateTest() {

        gameManager.collisionHandler = new CollisionHandler(gameManager);

        enemy.testMoveTime = 0;
        enemy.screenX = 0;
        enemy.screenY = 0;
        enemy.cellX = 48;
        enemy.cellY = 99;
        mainCharacter.cellX = 48;
        mainCharacter.cellY = 93;
        mainCharacter.screenX = 456;
        mainCharacter.screenY = 336;

        enemy.update();

        assert(enemy.cellX == 48);
        assert(enemy.cellY == 97);
        assert(enemy.screenX == 456);
        assert(enemy.screenY == 340);
        assert(enemy.facing == 'u');
    }

    /**
     * Tests the loadSprites method
     */
    @Test
    public void loadSpritesTest() {
        enemy.loadSprites("enemy");

        BufferedImage[] sprites = new BufferedImage[] {enemy.idle_u1, enemy.idle_u2,
                enemy.idle_d1, enemy.idle_d2, enemy.idle_l1, enemy.idle_l2,
                enemy.idle_r1, enemy.idle_r2, enemy.move_u1, enemy.move_u2,
                enemy.move_u3, enemy.move_u4, enemy.move_d1, enemy.move_d2,
                enemy.move_d3, enemy.move_d4, enemy.move_l1, enemy.move_l2,
                enemy.move_l3, enemy.move_l4, enemy.move_r1, enemy.move_r2,
                enemy.move_r3, enemy.move_r4};

        for (BufferedImage i : sprites) {
            assert(i != null);
        }

    }
}
