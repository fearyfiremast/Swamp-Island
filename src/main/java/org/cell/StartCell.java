package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * Cell that has no special behaviour aside from being the place that the MainCharacter spawns at when the game begins
 * @author Xander Smith
 * @version 1.0
 */
public class StartCell extends Cell{
    /**
     * Initializes the CellType
     */
    private void paramList() {
        this.cellEnum = CellType.StartCell;
    }

    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPos Position of the cell within MapManagers cellArray[][]
     * @param setMap MapManager of the Cell
     */
    public StartCell(GroundType setGround, Pair<Integer, Integer> setPos, MapManager setMap) {
        paramList();
        this.groundEnum = setGround;
        this.cellPosition = setPos;
        this.mapManager = setMap;
    }
}
