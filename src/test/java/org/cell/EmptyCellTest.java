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
 * Tests the functionality of the EmptyCell class
 * @author      Rob Cameron
 */
public class EmptyCellTest {

    /**
     * Tests the paramList method
     */
    @Test
    public void paramListTest() {
        EmptyCell cell = new EmptyCell();
        assert(cell.cellEnum == CellType.EmptyCell);
    }

    /**
     * Tests the parametrized constructor
     */
    @Test
    public void parametrizedConstructorTest() {
        GroundType ground = GroundType.path;
        Pair<Integer,Integer> position = new Pair<>(1,2);
        MapTemplate template = new MapTemplate(4,4);
        InputHandler handler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        GameManager gameManager = new GameManager(handler,layout,cards);
        MapManager mapManager = new MapManager(template,gameManager);

        EmptyCell cell = new EmptyCell(ground,position,mapManager);

        assert(cell.cellEnum == CellType.EmptyCell);
        assert(cell.groundEnum == GroundType.path);
        assert(cell.cellPosition.getKey() == 1);
        assert(cell.cellPosition.getValue() == 2);
        Assertions.assertEquals(mapManager, cell.mapManager);

    }

}
