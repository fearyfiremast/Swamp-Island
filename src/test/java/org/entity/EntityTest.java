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
 * Tests the functionality of the Entity class
 * @author      Rob Cameron
 */
class EntityTest {

    GameManager gameManager;
    MainCharacter mainCharacter;

    /**
     * Before each create a new main character object
     */
    @BeforeEach
    void init() {
        InputHandler inputHandler = new InputHandler();
        Container cards = new Container();
        CardLayout layout = new CardLayout();
        gameManager = new GameManager(inputHandler,layout,cards);

        mainCharacter = new MainCharacter(gameManager);
    }

    /**
     * tests setHitBox method
     */
    @Test
    public void setHitBoxTest() {

        mainCharacter.setHitBox(10,20,30,40);
        assert(mainCharacter.hitbox.x == 10);
        assert(mainCharacter.hitbox.y == 20);
        assert(mainCharacter.hitbox.width == 30);
        assert(mainCharacter.hitbox.height == 40);

    }

    /**
     * Tests getHitBox method
     */
    @Test
    public void getHitBoxTest() {
        mainCharacter.hitbox.x = 10;
        mainCharacter.hitbox.y = 20;
        mainCharacter.hitbox.width = 30;
        mainCharacter.hitbox.height = 40;

        Pair<Integer, Integer> [] hitboxes = mainCharacter.getHitBox();

        Assertions.assertEquals(new Pair<>(10,20),hitboxes[0]);
        Assertions.assertEquals(new Pair<>(40,20),hitboxes[1]);
        Assertions.assertEquals(new Pair<>(40,60),hitboxes[2]);
        Assertions.assertEquals(new Pair<>(10,60),hitboxes[3]);
    }

    /**
     * Tests getPosPair method
     */
    @Test
    public void getPosPairTest() {
        mainCharacter.cellX = 4;
        mainCharacter.cellY = 7;
        Pair<Integer,Integer> pos = mainCharacter.getPosPair();
        Assertions.assertEquals(new Pair<>(mainCharacter.cellX, mainCharacter.cellY),pos);
    }

    /**
     * Tests spriteCycler method when it doesn't modify it
     */
    @Test
    public void unmodifiedSpriteCyclerTest() {

        gameManager.elapsedTick = 1;
        mainCharacter.idleSpriteNum = 3;
        mainCharacter.moveSpriteNum = 3;

        mainCharacter.spriteCycler();

        assert(mainCharacter.idleSpriteNum == 3);
        assert(mainCharacter.moveSpriteNum == 3);

    }

    /**
     * Tests spriteCycler method when changing idle sprite
     */
    @Test
    public void idleSpriteCyclerTest() {
        gameManager.elapsedTick = 18;
        mainCharacter.idleSpriteNum = 3;
        mainCharacter.moveSpriteNum = 3;

        mainCharacter.spriteCycler();

        assert(mainCharacter.idleSpriteNum == 0);
        assert(mainCharacter.moveSpriteNum == 3);
    }

    /**
     * Tests spriteCycler method when changing moving sprite
     */
    @Test
    public void moveSpriteCyclerTest() {
        gameManager.elapsedTick = 12;
        mainCharacter.idleSpriteNum = 3;
        mainCharacter.moveSpriteNum = 3;

        mainCharacter.spriteCycler();

        assert(mainCharacter.idleSpriteNum == 3);
        assert(mainCharacter.moveSpriteNum == 0);
    }

    /**
     * Tests spriteCycler method when changing both sprites
     */
    @Test
    public void bothSpriteCyclerTest() {
        gameManager.elapsedTick = 36;
        mainCharacter.idleSpriteNum = 3;
        mainCharacter.moveSpriteNum = 3;

        mainCharacter.spriteCycler();

        assert(mainCharacter.idleSpriteNum == 0);
        assert(mainCharacter.moveSpriteNum == 0);
    }


    /**
     * Tests idleSprite method when number is zero and facing up
     */
    @Test
    public void zeroUIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 0;
        BufferedImage sprite = mainCharacter.idleSprite('u');

        Assertions.assertEquals(mainCharacter.idle_u1,sprite);
    }

    /**
     * Tests idleSprite method when number is not zero and facing up
     */
    @Test
    public void nonzeroUIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 1;
        BufferedImage sprite = mainCharacter.idleSprite('u');

        Assertions.assertEquals(mainCharacter.idle_u2,sprite);
    }

    /**
     * Tests idleSprite method when number is zero and facing down
     */
    @Test
    public void zeroDIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 0;
        BufferedImage sprite = mainCharacter.idleSprite('d');

        Assertions.assertEquals(mainCharacter.idle_d1,sprite);
    }

    /**
     * Tests idleSprite method when number is not zero and facing down
     */
    @Test
    public void nonzeroDIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 1;
        BufferedImage sprite = mainCharacter.idleSprite('d');

        Assertions.assertEquals(mainCharacter.idle_d2,sprite);
    }

    /**
     * Tests idleSprite method when number is zero and facing left
     */
    @Test
    public void zeroLIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 0;
        BufferedImage sprite = mainCharacter.idleSprite('l');

        Assertions.assertEquals(mainCharacter.idle_l1,sprite);
    }

    /**
     * Tests idleSprite method when number is not zero and facing left
     */
    @Test
    public void nonzeroLIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 1;
        BufferedImage sprite = mainCharacter.idleSprite('l');

        Assertions.assertEquals(mainCharacter.idle_l2,sprite);
    }

    /**
     * Tests idleSprite method when number is zero and facing right
     */
    @Test
    public void zeroRIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 0;
        BufferedImage sprite = mainCharacter.idleSprite('r');

        Assertions.assertEquals(mainCharacter.idle_r1,sprite);
    }

    /**
     * Tests idleSprite method when number is not zero and facing right
     */
    @Test
    public void nonzeroRIdleSpriteTest() {
        mainCharacter.idleSpriteNum = 1;
        BufferedImage sprite = mainCharacter.idleSprite('r');

        Assertions.assertEquals(mainCharacter.idle_r2,sprite);
    }

    /**
     * Tests moveSprite method when number is zero and facing up
     */
    @Test
    public void zeroUMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 0;
        BufferedImage sprite = mainCharacter.moveSprite('u');

        Assertions.assertEquals(mainCharacter.move_u1,sprite);
    }

    /**
     * Tests moveSprite method when number is one and facing up
     */
    @Test
    public void oneUMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 1;
        BufferedImage sprite = mainCharacter.moveSprite('u');

        Assertions.assertEquals(mainCharacter.move_u2,sprite);
    }

    /**
     * Tests moveSprite method when number is two and facing up
     */
    @Test
    public void twoUMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 2;
        BufferedImage sprite = mainCharacter.moveSprite('u');

        Assertions.assertEquals(mainCharacter.move_u3,sprite);
    }

    /**
     * Tests moveSprite method when number is three and facing up
     */
    @Test
    public void threeUMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 3;
        BufferedImage sprite = mainCharacter.moveSprite('u');

        Assertions.assertEquals(mainCharacter.move_u4,sprite);
    }

    /**
     * Tests moveSprite method when number is zero and facing down
     */
    @Test
    public void zeroDMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 0;
        BufferedImage sprite = mainCharacter.moveSprite('d');

        Assertions.assertEquals(mainCharacter.move_d1,sprite);
    }

    /**
     * Tests moveSprite method when number is one and facing down
     */
    @Test
    public void oneDMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 1;
        BufferedImage sprite = mainCharacter.moveSprite('d');

        Assertions.assertEquals(mainCharacter.move_d2,sprite);
    }

    /**
     * Tests moveSprite method when number is two and facing down
     */
    @Test
    public void twoDMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 2;
        BufferedImage sprite = mainCharacter.moveSprite('d');

        Assertions.assertEquals(mainCharacter.move_d3,sprite);
    }

    /**
     * Tests moveSprite method when number is three and facing down
     */
    @Test
    public void threeDMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 3;
        BufferedImage sprite = mainCharacter.moveSprite('d');

        Assertions.assertEquals(mainCharacter.move_d4,sprite);
    }

    /**
     * Tests moveSprite method when number is zero and facing left
     */
    @Test
    public void zeroLMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 0;
        BufferedImage sprite = mainCharacter.moveSprite('l');

        Assertions.assertEquals(mainCharacter.move_l1,sprite);
    }

    /**
     * Tests moveSprite method when number is one and facing left
     */
    @Test
    public void oneLMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 1;
        BufferedImage sprite = mainCharacter.moveSprite('l');

        Assertions.assertEquals(mainCharacter.move_l2,sprite);
    }

    /**
     * Tests moveSprite method when number is two and facing left
     */
    @Test
    public void twoLMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 2;
        BufferedImage sprite = mainCharacter.moveSprite('l');

        Assertions.assertEquals(mainCharacter.move_l3,sprite);
    }

    /**
     * Tests moveSprite method when number is three and facing left
     */
    @Test
    public void threeLMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 3;
        BufferedImage sprite = mainCharacter.moveSprite('l');

        Assertions.assertEquals(mainCharacter.move_l4,sprite);
    }

    /**
     * Tests moveSprite method when number is zero and facing right
     */
    @Test
    public void zeroRMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 0;
        BufferedImage sprite = mainCharacter.moveSprite('r');

        Assertions.assertEquals(mainCharacter.move_r1,sprite);
    }

    /**
     * Tests moveSprite method when number is one and facing right
     */
    @Test
    public void oneRMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 1;
        BufferedImage sprite = mainCharacter.moveSprite('r');

        Assertions.assertEquals(mainCharacter.move_r2,sprite);
    }

    /**
     * Tests moveSprite method when number is two and facing right
     */
    @Test
    public void twoRMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 2;
        BufferedImage sprite = mainCharacter.moveSprite('r');

        Assertions.assertEquals(mainCharacter.move_r3,sprite);
    }

    /**
     * Tests moveSprite method when number is three and facing right
     */
    @Test
    public void threeRMoveSpriteTest() {
        mainCharacter.moveSpriteNum = 3;
        BufferedImage sprite = mainCharacter.moveSprite('r');

        Assertions.assertEquals(mainCharacter.move_r4,sprite);
    }

    /**
     * Tests getCenter method
     */
    @Test
    public void getCenterTest() {
        mainCharacter.cellX = 24;
        mainCharacter.cellY = 48;
        Pair<Integer,Integer> center = mainCharacter.getCenter();

        Assertions.assertEquals(center.getKey(), 48);
        Assertions.assertEquals(center.getValue(), 72);
    }

}