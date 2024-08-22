package org.cell;

import javafx.util.Pair;
import org.extras.*;
import org.game.MapManager;

/**
 * Wall Cell has no special methods however it's passable boolean is set to false, thus making the cell unwalkable
 * By entities
 * @author Xander Smith
 * @version 1.0
 */
public class WallCell extends Cell{
    /**
     * Initializes the CellType and sets passable to false
     */
    private void paramList() {
        cellEnum = CellType.WallCell;
        passable = false;
    }

    /**
     * Default constructor
     */
    public WallCell(){
        this.paramList();
    }

    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPosition Position of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public WallCell(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        paramList();
        this.groundEnum = setGround;
        this.cellPosition = setPosition;
        this.mapManager = setManager;

    }

}
