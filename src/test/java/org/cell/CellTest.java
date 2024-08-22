package org.cell;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the Cell class
 * @author      Rob Cameron
 */
public class CellTest {

    /**
     * tests the setXPos method with null position
     */
    @Test
    public void nullSetXPosTest() {
        EmptyCell cell = new EmptyCell();
        cell.cellPosition = null;

        cell.setXPos(2);

        assert(cell.cellPosition == null);
    }

    /**
     * tests the setXPos method with non-null position
     */
    @Test
    public void setXPosTest() {
        EmptyCell cell = new EmptyCell();
        cell.cellPosition = new Pair<>(1,2);

        cell.setXPos(5);

        assert(cell.cellPosition.getKey() == 5);
        assert(cell.cellPosition.getValue() == 2);
    }

    /**
     * tests the setYPos method with null position
     */
    @Test
    public void nullSetYPosTest() {
        EmptyCell cell = new EmptyCell();
        cell.cellPosition = null;

        cell.setYPos(2);

        assert(cell.cellPosition == null);
    }

    /**
     * tests the setYPos method with non-null position
     */
    @Test
    public void setYPosTest() {
        EmptyCell cell = new EmptyCell();
        cell.cellPosition = new Pair<>(1,2);

        cell.setYPos(5);

        assert(cell.cellPosition.getKey() == 1);
        assert(cell.cellPosition.getValue() == 5);
    }

    /**
     * tests the setPos method
     */
    @Test
    public void setPosTest() {
        EmptyCell cell = new EmptyCell();
        cell.cellPosition = new Pair<>(1,2);

        cell.setPos(2,5);

        assert(cell.cellPosition.getKey() == 2);
        assert(cell.cellPosition.getValue() == 5);
    }

}