package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.GameManager;
import org.game.InputHandler;
import org.game.MapManager;
import org.game.MapTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Tests the functionality of the WallCell class
 * @author      Rob Cameron
 */
public class WallCellTest {

    WallCell cell;
    MapManager mapManager;
    GroundType ground;
    Pair<Integer,Integer> position;

    /**
     * Tests the parametrized constructor
     */
    @Test
    public void parametrizedConstructorTest() {

        ground = GroundType.path;
        position = new Pair<>(1,2);
        MapTemplate template = new MapTemplate(4,4);
        InputHandler handler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        GameManager gameManager = new GameManager(handler,layout,cards);
        mapManager = new MapManager(template,gameManager);

        cell = new WallCell(ground, position, mapManager);

        assert(cell.cellEnum == CellType.WallCell);
        assert(cell.groundEnum == GroundType.path);
        assert(cell.cellPosition.getKey() == 1);
        assert(cell.cellPosition.getValue() == 2);
        Assertions.assertEquals(mapManager, cell.mapManager);
        assert(!cell.passable);
    }

    /**
     * Tests the paramList method
     */
    @Test
    public void paramListTest() {
        cell = new WallCell();
        assert(cell.cellEnum == CellType.WallCell);
        assert(!cell.passable);
    }
}
