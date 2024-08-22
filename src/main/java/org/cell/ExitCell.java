package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * Cell that is used as the goal post for player. Can be in one of 3 states determined by CellType.<p>
 * default: Victory condition is inaccessible as player has not yet collected all rewards<p>
 * Raft: Player has collected all rewards without setting a new highscore<p>
 * Ship: Player has collected all rewards and set a new highscore <p>
 * Enum updating is handled in MapManager
 * @author Xander Smith
 * @version 1.0
 * @see MapManager
 */
public class ExitCell extends Cell{


    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPosition Position of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public ExitCell(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        this.paramList();
        this.groundEnum = setGround;
        this.cellPosition = setPosition;
        this.mapManager = setManager;
    }
    /**
     * Initializes the CellType
     */
    private void paramList() {
        cellEnum = CellType.ExitCell;
    }

}
