package org.cell;

import javafx.util.Pair;
import org.entity.MainCharacter;
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
 * Tests the functionality of the PunishmentCell class
 * @author      Rob Cameron
 */
public class PunishmentCellTest {

    PunishmentCell cell;
    MapManager mapManager;
    GroundType ground;
    Pair<Integer,Integer> position;

    /**
     * Before each creates a punishmentCell object
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

        cell = new PunishmentCell(ground, position, mapManager);
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {

        assert(cell.cellEnum == CellType.PunishmentCell);
        assert(cell.groundEnum == GroundType.path);
        assert(cell.cellPosition.getKey() == 1);
        assert(cell.cellPosition.getValue() == 2);
        Assertions.assertEquals(mapManager, cell.mapManager);
        assert(cell.score == -200);
    }

    /**
     * Tests the update method
     */
    @Test
    public void updateTest() {
        MainCharacter.setScore(500);
        cell.update();
        assert(MainCharacter.getScore() == 300);
    }

    /**
     * Tests the updateCellState method
     */
    @Test
    public void updateCellStateTest() {
        cell.updateCellState();
        Assertions.assertEquals(CellType.PunishmentCell_Inactive,MapManager.cellArray[cell.cellPosition.getKey()][cell.cellPosition.getValue()].cellEnum);
    }

}
