package org.game;

import static org.junit.jupiter.api.Assertions.*;

import javafx.util.Pair;
import org.cell.*;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Test class for ResetHandler
 */
class ResetHandlerTest {

    /**
     * Creates an empty ResetHandler
     * @return ResetHandler
     */
    public ResetHandler returnNullHandler() {
        return new ResetHandler(null, null);
    }

    /**
     * Tests the resetHandler constructor
     */
    @Test
    public void constructor() {
        ResetHandler toTest = returnNullHandler();
        assertNotEquals(null, toTest);
    }

    /**
     * Tests if the resetHandler array
     */
    @Test
    public void didNotAdd() {
        ResetHandler toTest = returnNullHandler();
        assertEquals(0, toTest.getSizeCell());
    }

    /**
     * Tests if the resetHandler method adds to array
     */
    @Test
    public void add() {
        ResetHandler toTest = returnNullHandler();
        toTest.add(new EmptyCell());
        assertEquals(1, toTest.getSizeCell());
    }

    /**
     * Tests if the remove method removes an exactly identical Cell
     */
    @Test
    public void removeExactCopy() {
        ResetHandler toTest = returnNullHandler();
        Cell toAddEmpty = new EmptyCell();

        toAddEmpty.setPos(1, 1);

        toTest.add(toAddEmpty);

        toTest.remove(toAddEmpty);
        assertEquals(0, toTest.getSizeCell());
    }

    /**
     * Tests if the Remove method ignores a Cell with different X and Y
     */
    @Test
    public void removeIgnoreDifferentXandYFalse() {
        ResetHandler toTest = returnNullHandler();

        Cell toAddEmpty = new EmptyCell();
        toAddEmpty.setPos(1, 1);
        Cell toRemoveWall = new WallCell();
        toRemoveWall.setPos(2, 2);

        toTest.add(toAddEmpty);
        toTest.remove(toRemoveWall);

        assertEquals(1, toTest.getSizeCell());

    }

    /**
     * Tests if the remove method fails to remove a Cell with same X and different Y
     */
    @Test
    public void removeIgnoreDifferentYFalse() {
        ResetHandler toTest = returnNullHandler();

        Cell toAddEmpty = new EmptyCell();
        toAddEmpty.setPos(1, 1);
        Cell toRemoveWall = new WallCell();
        toRemoveWall.setPos(1, 2);

        toTest.add(toAddEmpty);
        toTest.remove(toRemoveWall);

        assertEquals(1, toTest.getSizeCell());
    }

    /**
     * Removes different cells that have the same position
     */
    @Test
    public void removeDifferentTypeSamePos() {
        ResetHandler toTest = returnNullHandler();

        Cell toAddEmpty = new EmptyCell();
        toAddEmpty.setPos(1, 1);
        Cell toRemoveWall = new WallCell();
        toRemoveWall.setPos(1, 1);

        toTest.add(toAddEmpty);
        toTest.remove(toRemoveWall);

        assertEquals(0, toTest.getSizeCell());
    }

    /**
     * Tests if a resetMethod call works without an empty ResetHandler Method
     */
    @Test
    public void resetEmpty() {
        GameManager temp = new GameManager();
        temp.mapManager = new MapManager(new MapTemplate(2, 2), temp);
        GameManager.mc = new MainCharacter(temp);
        ResetHandler toTest = new ResetHandler(temp, new ArrayList<>());

        Cell[][] toCompare = MapManager.cellArray;
        toTest.ResetGame();
        assertArrayEquals(MapManager.cellArray, toCompare );
    }

    /**
     * Tests if the reset Method works on the map.
     */
    @Test
    public void resetMapCell() {
        GameManager temp = new GameManager();
        temp.mapManager = new MapManager(new MapTemplate(2, 2), temp);
        GameManager.mc = new MainCharacter(temp);
        ResetHandler toTest = new ResetHandler(temp, new ArrayList<>());
        Cell[][] toChange = new Cell[2][2];
        Cell[][] oneDime = new Cell[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                toChange[i][j] = new EmptyCell();
                oneDime[i][j] = new PunishmentCell_Inactive();
                oneDime[i][j].setPos(i, j);
            }
        }
        MapManager.cellArray = toChange;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                toTest.add(oneDime[i][j]);
            }
        }

        toTest.ResetGame();
        assertArrayEquals(MapManager.cellArray, oneDime );
    }

    /**
     * Tests if the resetMethod works when reward cells are added. Checks if amount of rewards in the resetMethod
     * are added to  MapManager properly
     */
    @Test
    public void resetAddingRewards() {GameManager temp = new GameManager();
        temp.mapManager = new MapManager(new MapTemplate(2, 2), temp);
        GameManager.mc = new MainCharacter(temp);
        ResetHandler toTest = new ResetHandler(temp, new ArrayList<>());
        Cell[][] toChange = new Cell[2][2];
        Cell[][] oneDime = new Cell[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                toChange[i][j] = new EmptyCell();
                oneDime[i][j] = new RewardCell_Regular();
                oneDime[i][j].setPos(i, j);
            }
        }
        oneDime[0][0] = new WallCell();
        oneDime[0][0].setPos(0, 0);

        MapManager.cellArray = (toChange);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                toTest.add(oneDime[i][j]);
            }
        }

        toTest.ResetGame();
        assertEquals(3, MapManager.numRewards );
    }

    /**
     * Tests if the reset method works for enemy positions
     */
    @Test
    public void resetAddEnemies() {
        GameManager temp = new GameManager();
        temp.mapManager = new MapManager(new MapTemplate(2, 2), temp);
        ArrayList<Pair<Integer, Integer>> enemyPos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                enemyPos.add(new Pair<>(i, j));
            }
        }

        GameManager.mc = new MainCharacter(temp);
        ResetHandler toTest = new ResetHandler(temp, enemyPos);

        toTest.ResetGame();
        assertEquals(4, toTest.getSizeEnemyArrayGame() );
    }
}