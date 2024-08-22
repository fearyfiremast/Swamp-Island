package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.extras.Timer;
import org.game.MapManager;

/**
 * Has no special behaviour when walked over but implements timer function and has unique CellType enum so while the timer
 * value is greater than 0 cell that used to be Punishment is safe to walk over.
 * @author Xander Smith
 * @version 1.0
 * @see PunishmentCell
 */
public class PunishmentCell_Inactive extends Cell implements Timer {
    int timer = 0;

    /**
     * Initializes the CellType and sets Timer
     */
    private void paramList() {
        this.cellEnum = CellType.PunishmentCell_Inactive ;
        this.setTimer();
    }

    /**
     * Default constructor
     */
    public PunishmentCell_Inactive () {
        this.paramList();
    }

    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPosition Position of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public PunishmentCell_Inactive(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        this.paramList();
        groundEnum = setGround;
        cellPosition = setPosition;
        mapManager = setManager;
        setTimer();
    }

    /**
     * Sets the timer attribute to 120
     */
    @Override
    public void setTimer() {
        this.timer = 120;
    }

    /**
     * checks if timer is less than or equal 0. If so returns true else false
     * @return boolean
     */
    @Override
    public boolean isEvent() {
        return timer <= 0;
    }

    /**
     * Method that is called every tick to check if we can call TimerMethod function
     */
    @Override
    public void tick() {
        timer--;
        if (isEvent()) {
            timerMethod();
        }

    }

    /**
     * Updates this cell position within the CellArray to contain a punishmentCell again<p>
     * Removes the old PunishmentCell from ResetHandler within MapManager
     */
    @Override
    public void timerMethod() {
        mapManager.cellArray[cellPosition.getKey()][cellPosition.getValue()] =
                new PunishmentCell(groundEnum, cellPosition, mapManager);
        mapManager.resetHandler.remove(this);
    }
}
