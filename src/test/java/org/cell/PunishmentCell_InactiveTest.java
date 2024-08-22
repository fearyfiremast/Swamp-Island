package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.GameManager;
import org.game.InputHandler;
import org.game.MapManager;
import org.game.MapTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Tests the functionality of the PunishmentCell_Inactive class
 * @author      Rob Cameron
 */
public class PunishmentCell_InactiveTest {

    PunishmentCell_Inactive cell;
    MapManager mapManager;
    GroundType ground;
    Pair<Integer,Integer> position;

    /**
     * Before each creates a new punishmentCell_Inactive object
     */
    @BeforeEach
    void init() {
        ground = GroundType.path;
        position = new Pair<>(1,2);
        MapTemplate template = new MapTemplate(4,4);
        InputHandler handler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        GameManager gameManager = new GameManager(handler,layout,cards);
        mapManager = new MapManager(template,gameManager);

        cell = new PunishmentCell_Inactive(ground, position, mapManager);
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {

        assert(cell.cellEnum == CellType.PunishmentCell_Inactive);
        assert(cell.groundEnum == GroundType.path);
        assert(cell.cellPosition.getKey() == 1);
        assert(cell.cellPosition.getValue() == 2);
        Assertions.assertEquals(mapManager, cell.mapManager);

    }

    /**
     * tests the setTimer method
     */
    @Test
    public void setTimerTest() {
        cell.setTimer();
        assert(cell.timer == 120);
    }

    /**
     * Tests the isEvent method with false result
     */
    @Test
    public void falseIsEventTest() {
        cell.setTimer();
        assert(!cell.isEvent());
    }

    /**
     * Tests the isEvent method with true result
     */
    @Test
    public void trueIsEventTest() {
        cell.timer = 0;
        assert(cell.isEvent());
    }

    /**
     * Tests the tick method with no event
     */
    @Test
    public void noEventTickTest() {
        cell.setTimer();
        cell.tick();
        assert(cell.timer == 119);
    }

    /**
     * Tests the tick method with an event
     */
    @Test
    public void eventTickTest() {
        cell.timer = 1;
        cell.tick();
        assert(cell.timer == 0);
    }

    /**
     * Tests the timerMethod method
     */
    @Test
    public void timerMethodTest() {
        cell.timerMethod();
        Assertions.assertEquals(CellType.PunishmentCell,MapManager.cellArray[cell.cellPosition.getKey()][cell.cellPosition.getValue()].cellEnum);
    }

}
