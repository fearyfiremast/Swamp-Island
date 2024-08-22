package org.game;

import javafx.util.Pair;
import org.cell.Cell;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Sets up the unit test environment before each test method.
 * @author Subranil Dey
 */
class MapManagerTest {
    static MapManager testMapManager = null;
    static MapTemplate testMapTemplate = null;
    static GameManager testGameManager = null;

    /**
     * Sets up the environment for testing.
     */
    @BeforeEach
    void constructorParameters()
    {
        testMapTemplate = new MapTemplate(50, 50);
        testGameManager = new GameManager(new InputHandler(), new CardLayout(), new Container());
        testMapManager = new MapManager(testMapTemplate, testGameManager);

        testMapManager = new MapManager(testMapTemplate, testGameManager);
    }

    /**
     * The MapManager constructor test.
     */
    @Test
    void constructor_check()
    {
        assertNotEquals(null, testMapManager.groundSprite);
        assertNotEquals(null, testMapManager.cellSprite);
        assertNotEquals(null, testMapManager.gameManager);
        assertNotEquals(null, testMapManager.cellArray);
        assertNotEquals(null, testMapManager.dimensions);
        assertNotEquals(null, testMapManager.cellArray);
        assertNotEquals(null, testMapManager.timedArray);
    }

    /**
     * update function test for the first if condition.
     */
    @Test
    void update_test1() {
        MapManager.numRewards = 0;
        MapManager.exitState[0] = true;
        testMapManager.update();
    }

    /**
     * update function test for the secound if condition.
     */
    @Test
    void update_test2() {
        MapManager.gameManager.highScore = MainCharacter.getScore() - MapManager.gameManager.highScore;
        MapManager.exitState[0] = true;
        MapManager.numRewards = 0;
        testMapManager.update();
    }

    /**
     * Test for draw function completion.
     */
    @Test
    void drawEnum_screenCoord_check()
    {
        int x = testMapManager.draw(null);
        assertEquals(1, x);
    }


    /**
     * Checking that the start position is set up properly.
     */
    @Test
    void generateMap_Start_check()
    { // cell array and start position.
        Pair<Integer, Integer> position = testMapManager.getStartCellPos();
        Pair<Integer, Integer> expectation = new Pair<>(0,49);
        assertEquals(expectation, position);
    }

    /**
     * Unit testing to make sure mapManager setted up the map objects according to the mapTemplate.
     */
    @Test
    void generateMap_CellArray_check()
    {   // CellArray Test
        Pair<CellType, GroundType> eachCell_template = null;

        for(int i = 0; i < MapManager.getDimensions().getValue(); ++i)
        {
            for(int j = 0; j < MapManager.getDimensions().getKey(); ++j)
            {
                eachCell_template = testMapTemplate.getCell(i, j);

                Cell eachCell_mapManager = MapManager.getCell(i, j);

                assertEquals("class org.cell." + eachCell_template.getKey().toString(), eachCell_mapManager.getClass().toString());
            }
        }
    }
}