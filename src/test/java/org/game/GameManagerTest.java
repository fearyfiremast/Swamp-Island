package org.game;

import javafx.util.Pair;
import org.display.*;
import org.entity.Enemy;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Testcases designed for GameManager class in game package.
 * @author Subhranil Dey
 */
class GameManagerTest {
    int actual_highScore = 0;

    /**
     * Testcase for StartThread function.
     */
    @Test
    void startThread()
    {
        //MapTemplate testMapTemplate = new MapTemplate(50, 50);

        GameManager testGameManager1 = new GameManager(new InputHandler(), new CardLayout(), new Container());
        GameManager testGameManager2 = testGameManager1;

        testGameManager1.startThread();

        testGameManager2.getMainCharacter().initializeValues(testGameManager2.mapManager.getStartCellPos());

        assertEquals(testGameManager1, testGameManager2);
    }

    /**
     * Testcase for winning the game scenario which uses the run function.
     */
    @Test
    void run_winGame_check()
    {
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        GameManager testGameManager = new GameManager(new InputHandler(), layout, cards);
        MapTemplate testMapTemplate = new MapTemplate(50, 50);
        MapManager testMapManager = new MapManager(testMapTemplate , testGameManager);

        MainMenu menu = new MainMenu();
        End end = new End();

        cards.setLayout(layout);

        cards.add("main menu",menu);
        cards.add("end", end);

        Pair<Integer, Integer> exitCellPos = testMapManager.getExitCellPos();
        System.out.println("Exit cell position - " + exitCellPos.getKey() + " " + exitCellPos.getValue());

        testGameManager.getMainCharacter().facing = 'u';
        for(int i = 0; i < testGameManager.enemyList.size(); ++i)
        {
            testGameManager.enemyList.get(i).facing = 'u';
        }
        //MapManager.getCell()
        testGameManager.getMainCharacter().setPosition(exitCellPos.getKey(), exitCellPos.getValue());
        MapManager.numRewards = 0;

        //update_forFacing_l();
        testGameManager.run();

        assertEquals(1, testGameManager.elapsedTick);
        CellType toCheck = testGameManager.getCell(GameManager.mc.cellX, GameManager.mc.cellY).cellEnum;
        //assertEquals(toCheck, testGameManager.getCell(exitCellPos.getKey(), exitCellPos.getValue()).cellEnum);
        //assertEquals(toCheck,  testGameManager.getCell(exitCellPos.getKey(), exitCellPos.getValue()).cellEnum) ;
        //assertEquals();)
        //assert(CellType.ExitCell_Boat == testGameManager.getCell(exitCellPos.getKey(), exitCellPos.getValue()).cellEnum || CellType.ExitCell_Raft == testGameManager.getCell(exitCellPos.getKey(), exitCellPos.getValue()).cellEnum ) ;
    }

    /**
     * Testcase for loosing the game scenario which uses the run function.
     */
    @Test
    void run_looseGameScore_check()
    {
        CardLayout layout = new CardLayout();
        Container cards = new Container();

        GameManager testGameManager = new GameManager(new InputHandler(), layout, cards);
        MapTemplate testMapTemplate = new MapTemplate(50, 50);
        MapManager testMapManager = new MapManager(testMapTemplate , testGameManager);

        MainMenu menu = new MainMenu();
        End end = new End();

        cards.setLayout(layout);

        cards.add("main menu",menu);
        cards.add("end", end);

        testGameManager.getMainCharacter().facing = 'u';
        for(int i = 0; i < testGameManager.enemyList.size(); ++i)
        {
            testGameManager.enemyList.get(i).facing = 'u';
        }

        MainCharacter.setScore(-MainCharacter.getScore()-1);
        testGameManager.run();
        //update_forFacing_l();


        assertEquals(0, testGameManager.elapsedTick);
//        testGameManager.gameLost = false;
//        testGameManager.gameActive = false;
        assertFalse(testGameManager.gameLost);
        assertFalse(testGameManager.gameActive);
    }

    /**
     * Loosing game scenario based on lostGame variable in GameManager.
     */
    @Test
    void run_looseGame_gameLost_check()
    {
        CardLayout layout = new CardLayout();
        Container cards = new Container();

        GameManager testGameManager = new GameManager(new InputHandler(), layout, cards);
        MapTemplate testMapTemplate = new MapTemplate(50, 50);
        //MapManager testMapManager = new MapManager(testMapTemplate , testGameManager);

        MainMenu menu = new MainMenu();
        End end = new End();

        cards.setLayout(layout);

        cards.add("main menu",menu);
        cards.add("end", end);

        testGameManager.getMainCharacter().facing = 'u';
        for(int i = 0; i < testGameManager.enemyList.size(); ++i)
        {
            testGameManager.enemyList.get(i).facing = 'u';
        }

        testGameManager.setGameLost(true);

        testGameManager.run();

        assertEquals(0, testGameManager.elapsedTick);
        assertEquals(0, testGameManager.elapsedTick);
        assertFalse(testGameManager.gameLost);
        assertFalse(testGameManager.gameActive);
    }

    /**
     * Tick value check in run function.
     */
    @Test
    void run_Tick_check()
    {
        CardLayout layout = new CardLayout();
        Container cards = new Container();

        GameManager testGameManager = new GameManager(new InputHandler(), layout, cards);
        MapTemplate testMapTemplate = new MapTemplate(50, 50);
        MapManager testMapManager = new MapManager(testMapTemplate , testGameManager);

        MainMenu menu = new MainMenu();
        End end = new End();

        cards.setLayout(layout);

        cards.add("main menu",menu);
        cards.add("end", end);

        testGameManager.getMainCharacter().facing = 'u';
        for(int i = 0; i < testGameManager.enemyList.size(); ++i)
        {
            testGameManager.enemyList.get(i).facing = 'u';
        }

        testGameManager.setGameLost(true);
        testGameManager.run();
        assertFalse(testGameManager.gameActive);

        assertNotEquals(1, testGameManager.elapsedTick);
    }

    /**
     * Update testing when facing up character.
     */
    @Test
    void update_forFacing_u() {
        GameManager testGameManager1 = new GameManager(new InputHandler(), new CardLayout(), new Container());
        testGameManager1.getMainCharacter().facing = 'u';
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            testGameManager1.enemyList.get(i).facing = 'u';
        }
        GameManager testGameManager2 = testGameManager1;
        testGameManager1.update();

        testGameManager2.getMainCharacter().update();
        MainCharacter mc2 = testGameManager2.getMainCharacter();
        assertEquals(mc2, testGameManager1.getMainCharacter());

        testGameManager2.mapManager.update();
        MapManager mm2 = testGameManager2.mapManager;
        assertEquals(mm2, testGameManager1.mapManager);

        // now do the same for the enemy list.
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            Enemy updated_enemy1 = testGameManager1.enemyList.get(i);

            testGameManager2.enemyList.get(i).update();
            Enemy updated_enemy2 = testGameManager1.enemyList.get(i);

            assertEquals(updated_enemy1, updated_enemy2);
        }
    }

    /**
     * Update testing when facing down character.
     */
    @Test
    void update_forFacing_d() {
        GameManager testGameManager1 = new GameManager(new InputHandler(), new CardLayout(), new Container());
        testGameManager1.getMainCharacter().facing = 'd';
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            testGameManager1.enemyList.get(i).facing = 'd';
        }
        GameManager testGameManager2 = testGameManager1;
        testGameManager1.update();

        testGameManager2.getMainCharacter().update();
        MainCharacter mc2 = testGameManager2.getMainCharacter();
        assertEquals(mc2, testGameManager1.getMainCharacter());

        testGameManager2.mapManager.update();
        MapManager mm2 = testGameManager2.mapManager;
        assertEquals(mm2, testGameManager1.mapManager);

        // now do the same for the enemy list.
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            Enemy updated_enemy1 = testGameManager1.enemyList.get(i);

            testGameManager2.enemyList.get(i).update();
            Enemy updated_enemy2 = testGameManager1.enemyList.get(i);

            assertEquals(updated_enemy1, updated_enemy2);
        }
    }

    /**
     * Update testing when facing left character.
     */
    @Test
    void update_forFacing_l() {
        GameManager testGameManager1 = new GameManager(new InputHandler(), new CardLayout(), new Container());
        testGameManager1.getMainCharacter().facing = 'l';
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            testGameManager1.enemyList.get(i).facing = 'l';
        }
        GameManager testGameManager2 = testGameManager1;
        testGameManager1.update();

        testGameManager2.getMainCharacter().update();
        MainCharacter mc2 = testGameManager2.getMainCharacter();
        assertEquals(mc2, testGameManager1.getMainCharacter());

        testGameManager2.mapManager.update();
        MapManager mm2 = testGameManager2.mapManager;
        assertEquals(mm2, testGameManager1.mapManager);

        // now do the same for the enemy list.
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            Enemy updated_enemy1 = testGameManager1.enemyList.get(i);

            testGameManager2.enemyList.get(i).update();
            Enemy updated_enemy2 = testGameManager1.enemyList.get(i);

            assertEquals(updated_enemy1, updated_enemy2);
        }
    }

    /**
     * Update testing when facing right character.
     */
    @Test
    void update_forFacing_r() {
        GameManager testGameManager1 = new GameManager(new InputHandler(), new CardLayout(), new Container());
        testGameManager1.getMainCharacter().facing = 'r';
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            testGameManager1.enemyList.get(i).facing = 'r';
        }
        GameManager testGameManager2 = testGameManager1;
        testGameManager1.update();

        testGameManager2.getMainCharacter().update();
        MainCharacter mc2 = testGameManager2.getMainCharacter();
        assertEquals(mc2, testGameManager1.getMainCharacter());

        testGameManager2.mapManager.update();
        MapManager mm2 = testGameManager2.mapManager;
        assertEquals(mm2, testGameManager1.mapManager);

        // now do the same for the enemy list.
        for(int i = 0; i < testGameManager1.enemyList.size(); ++i)
        {
            Enemy updated_enemy1 = testGameManager1.enemyList.get(i);

            testGameManager2.enemyList.get(i).update();
            Enemy updated_enemy2 = testGameManager1.enemyList.get(i);

            assertEquals(updated_enemy1, updated_enemy2);
        }
    }

    /**
     * get elapsed time check.
     */
    @Test
    void getElapsedTime_check() {
        //GameManager testGameManager = new GameManager(new InputHandler(), new CardLayout(), new Container());
        //testGameManager.run();
        assertEquals(GameManager.elapsedTime, GameManager.getElapsedTime());
    }

    /**
     * SetElapsed time testing.
     */
    @Test
    void setElapsedTime() {
        int currentElapsedTime = GameManager.elapsedTime;
        int set_time = 99999;
        GameManager.setElapsedTime(set_time);
        assertEquals(set_time, GameManager.elapsedTime);

        GameManager.setElapsedTime(currentElapsedTime);
    }

    /**
     * Reset_game check testing.
     */
    @Test
    void resetGame_check() {
        GameManager testGameManager = new GameManager(new InputHandler(), new CardLayout(), new Container());
        //testGameManager.run();
        GameScreen gameScreen = new GameScreen(testGameManager);
        testGameManager.resetGame();
        assertEquals(0, GameManager.elapsedTime);
        assertEquals(0, MainCharacter.getScore());
        assertFalse(MainCharacter.isHasJuice());
        assertFalse(MainCharacter.isHasTrap());

        assertEquals(75, MapManager.numRewards);
        //assertEquals();
    }

    /**
     * check to see if getCell converts the pixel values to cells
     */
    @Test
    void getCellPair_check()
    {
        // assuming the game is 50/50
        GameManager testGameManager = new GameManager(new InputHandler(), new CardLayout(), new Container());

        assertEquals(MapManager.cellArray[0][0], testGameManager.getCell(new Pair<>(47, 47)));
        assertEquals(MapManager.cellArray[1][1], testGameManager.getCell(new Pair<>(48, 48)));
        assertEquals(MapManager.cellArray[6][6], testGameManager.getCell(new Pair<>(300, 300)));
        assertEquals(MapManager.cellArray[49][49], testGameManager.getCell(new Pair<>(49*48, 49*48)));
    }

    /**
     * Check for reading and writing function for highscore.
     */
    @Test
    void highScore_ReadWrite_check()
    {
        GameManager testGameManager = new GameManager(new InputHandler(), new CardLayout(), new Container());
        actual_highScore = GameManager.getHighScore();
        testGameManager.writeHighScore(0);

        assertEquals(0, GameManager.getHighScore());

        testGameManager.writeHighScore(actual_highScore);
    }
}