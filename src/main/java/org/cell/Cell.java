package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * The basis on which every cell is built upon. Key features are cellPosition: the index points the cell occupies in the
 * MapManagers cellArray, as well as the two enums types, GroundType and CellType: useful for identification as well as
 * specifying what sprite to display on screen.
 * @author Xander Smith
 * @version 1.0
 */
abstract public class Cell {
    public boolean passable = true;     // used in the collision mechanics.

    public Pair<Integer,Integer> cellPosition = null;
    public CellType cellEnum = null;
    public GroundType groundEnum = null;
    public MapManager mapManager = null;

    public String spriteFilePath = null;

    /**
     * Used to change the x coordinate of the Cell position attribute
     * @param x sets the x position of the cell
     */
    public void setXPos(int x) {
        if (cellPosition == null) {
            return;
        }
        int temp = cellPosition.getValue();
        cellPosition = new Pair<>(x, temp);
    }

    /**
     * Used to change the Y coordinate of the Cell position attribute
     * @param y sets the y position of the cell
     */
    public void setYPos(int y) {
        if (cellPosition == null) {
            return;
        }
        int temp = cellPosition.getKey();
        cellPosition = new Pair<>(temp, y);
    }

    /**
     * Updates the cellPosition attribute to the passed in ints
     * @param x is the new x coordinate for cell position.
     * @param y is the new y coordinate for cell position.
     *          Sets a new position for the existent cell.
     */
    public void setPos(int x, int y) {
        cellPosition = new Pair<>(x, y);
    }
}
