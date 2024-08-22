package org.entity;

import javafx.util.Pair;
import org.game.GameManager;
import org.game.InputHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Tests the functionality of the MainCharacter class
 * @author      Rob Cameron
 */
public class MainCharacterTest {

    InputHandler inputHandler;
    CardLayout layout;
    Container cards;
    GameManager gameManager;
    MainCharacter mainCharacter;

    /**
     * Before each create a new main character
     */
    @BeforeEach
    void init() {
        inputHandler = new InputHandler();
        layout = new CardLayout();
        cards = new Container();
        gameManager = new GameManager(inputHandler,layout,cards);
        mainCharacter = new MainCharacter(gameManager);
    }

    /**
     * Tests constructor
     */
    @Test
    public void constructorTest() {
        Assertions.assertEquals(gameManager,mainCharacter.gm);
        Assertions.assertEquals(inputHandler,mainCharacter.inputHandler);

        Assertions.assertEquals(0,MainCharacter.getScore());
        assert(!MainCharacter.isHasJuice());
        assert(!MainCharacter.isHasTrap());

        assert(mainCharacter.hitbox.x == 12);
        assert(mainCharacter.hitbox.y == 24);
        assert(mainCharacter.hitbox.width == 24);
        assert(mainCharacter.hitbox.height == 18);
    }

    /**
     * Tests initialize values method
     */
    @Test
    public void initializeValuesTest() {
        mainCharacter.initializeValues(new Pair<>(1,2));

        Assertions.assertEquals(48,mainCharacter.cellX);
        Assertions.assertEquals(96,mainCharacter.cellY);
        Assertions.assertEquals(456,mainCharacter.screenX);
        Assertions.assertEquals(336,mainCharacter.screenY);
        Assertions.assertEquals(4,mainCharacter.baseSpeed);
        Assertions.assertEquals(4,mainCharacter.moveSpeed);
        Assertions.assertEquals('d',mainCharacter.facing);
    }

    /**
     * Tests update method with character idle
     */
    @Test
    public void idleUpdateTest() {

        mainCharacter.initializeValues(new Pair<>(1,2));
        mainCharacter.update();

        Assertions.assertEquals(48,mainCharacter.cellX);
        Assertions.assertEquals(96,mainCharacter.cellY);

    }

    /**
     * Tests update method moving up
     */
    @Test void upUpdateTest() {

        mainCharacter.initializeValues(new Pair<>(1,2));
        inputHandler.keyUp = true;

        mainCharacter.update();


        assert(mainCharacter.facing == 'u');
        Assertions.assertEquals(48,mainCharacter.cellX);
        Assertions.assertEquals(93,mainCharacter.cellY);

    }

    /**
     * Tests update method moving down
     */
    @Test void downUpdateTest() {

        mainCharacter.initializeValues(new Pair<>(1,2));
        inputHandler.keyDown = true;

        mainCharacter.update();


        assert(mainCharacter.facing == 'd');
        Assertions.assertEquals(48,mainCharacter.cellX);
        Assertions.assertEquals(99,mainCharacter.cellY);

    }

    /**
     * Tests update method moving left
     */
    @Test void leftUpdateTest() {

        mainCharacter.initializeValues(new Pair<>(1,2));
        inputHandler.keyLeft = true;

        mainCharacter.update();


        assert(mainCharacter.facing == 'l');
        Assertions.assertEquals(45,mainCharacter.cellX);
        Assertions.assertEquals(96,mainCharacter.cellY);

    }

    /**
     * Tests update method moving right
     */
    @Test void rightUpdateTest() {

        mainCharacter.initializeValues(new Pair<>(1,2));
        inputHandler.keyRight = true;

        mainCharacter.update();


        assert(mainCharacter.facing == 'r');
        Assertions.assertEquals(51,mainCharacter.cellX);
        Assertions.assertEquals(96,mainCharacter.cellY);

    }

    /**
     * Tests the calculateMoveSpeed method without modifying it
     */
    @Test
    public void unmodifiedCalculateMoveSpeed() {

        mainCharacter.initializeValues(new Pair<>(10,5));

        mainCharacter.calculateMoveSpeed();

        Assertions.assertEquals(mainCharacter.baseSpeed,mainCharacter.moveSpeed);


    }

    /**
     * Tests the calculateMoveSpeed method applying speed buff
     */
    @Test
    public void speedBuffOnCalculateMoveSpeed() {

        mainCharacter.initializeValues(new Pair<>(10,5));
        MainCharacter.hasJuice = true;
        inputHandler.useSpeed = true;
        mainCharacter.speedBuffDuration = 60;

        mainCharacter.calculateMoveSpeed();

        Assertions.assertEquals(mainCharacter.baseSpeed+2,mainCharacter.moveSpeed);


    }

    /**
     * Tests the calculateMoveSpeed method on marsh
     */
    @Test
    public void marshOnCalculateMoveSpeed() {

        mainCharacter.initializeValues(new Pair<>(1,2));

        mainCharacter.calculateMoveSpeed();

        Assertions.assertEquals(mainCharacter.baseSpeed-1,mainCharacter.moveSpeed);


    }

    /**
     * Tests the calculateMoveSpeed method on marsh with speed buff
     */
    @Test
    public void bothOnCalculateMoveSpeed() {

        mainCharacter.initializeValues(new Pair<>(1,2));
        MainCharacter.hasJuice = true;
        inputHandler.useSpeed = true;
        mainCharacter.speedBuffDuration = 60;

        mainCharacter.calculateMoveSpeed();

        Assertions.assertEquals(mainCharacter.baseSpeed+1,mainCharacter.moveSpeed);


    }

    /**
     * Tests the loadSprites method
     */
    @Test
    public void loadSpritesTest() {
        mainCharacter.loadSprites("mainCharacter");

        BufferedImage[] sprites = new BufferedImage[] {mainCharacter.idle_u1, mainCharacter.idle_u2,
                mainCharacter.idle_d1, mainCharacter.idle_d2, mainCharacter.idle_l1, mainCharacter.idle_l2,
                mainCharacter.idle_r1, mainCharacter.idle_r2, mainCharacter.move_u1, mainCharacter.move_u2,
                mainCharacter.move_u3, mainCharacter.move_u4, mainCharacter.move_d1, mainCharacter.move_d2,
                mainCharacter.move_d3, mainCharacter.move_d4, mainCharacter.move_l1, mainCharacter.move_l2,
                mainCharacter.move_l3, mainCharacter.move_l4, mainCharacter.move_r1, mainCharacter.move_r2,
                mainCharacter.move_r3, mainCharacter.move_r4};

        for (BufferedImage i : sprites) {
            assert(i != null);
        }

    }

    /**
     * Tests the incrementScore method
     */
    @Test
    public void incrementScoreTest() {

        MainCharacter.score = 0;
        MainCharacter.incrementScore(4);
        assert(MainCharacter.score == 4);

    }

    /**
     * Tests the getScore method
     */
    @Test
    public void getScoreTest() {

        MainCharacter.score = 7;
        assert(MainCharacter.getScore() == 7);

    }

    /**
     * tests the setScore method
     */
    @Test
    public void setScoreTest() {

        MainCharacter.setScore(20);
        assert(MainCharacter.score == 20);

    }

    /**
     * Tests the setHasJuice method
     */
    @Test
    public void setHasJuiceTest() {
        MainCharacter.hasJuice = false;
        MainCharacter.setHasJuice(true);
        assert(MainCharacter.hasJuice);
    }

    /**
     * Tests the isHasJuice method
     */
    @Test
    public  void isHasJuiceTest() {
        MainCharacter.hasJuice = false;
        assert(!MainCharacter.isHasJuice());
    }

    /**
     * Tests the setHasTrap method
     */
    @Test
    public void setHasTrapTest() {
        MainCharacter.hasTrap = false;
        MainCharacter.setHasTrap(true);
        assert(MainCharacter.hasTrap);
    }

    /**
     * Tests the isHasTrap method
     */
    @Test
    public  void isHasTrapTest() {
        MainCharacter.hasTrap = false;
        assert(!MainCharacter.isHasTrap());
    }

}
