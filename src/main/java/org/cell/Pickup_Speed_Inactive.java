package org.cell;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.extras.Timer;
import org.game.MapManager;

/**
 * Has no special behaviour when walked over but implements timer function and
 * has unique CellType enum.
 * <p>
 * While the timer value greater than 0, the cell that used to be Pickup_Speed will not award
 * the speed powerup.
 *
 * @author Bryan Widjaja
 * @version 1.0
 * @see Pickup_Speed
 */
public class Pickup_Speed_Inactive extends Cell implements Timer {
    int timer = 0;

    /**
     * Initializes the CellType and sets Timer
     */
    private void paramList() {
        this.cellEnum = CellType.Pickup_Speed_Inactive ;
        this.setTimer();
    }

    /**
     * Default constructor
     */
    public Pickup_Speed_Inactive () {
        this.paramList();
    }

    /**
     * Parameterized constructor
     * Main constructor that should be used in almost all cases. Sets the
     * GroundEnum, Position and MapManager of the cell.
     *
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPosition Position of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public Pickup_Speed_Inactive(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        this.paramList();
        groundEnum = setGround;
        cellPosition = setPosition;
        mapManager = setManager;
        setTimer();
    }

    /**
     * Sets the timer attribute 60*60
     */
    @Override
    public void setTimer() {
        this.timer = 60 * 60;
    }

    /**
     * Checks whether timer less than or equal 0, returning true if so. Returns false otherwise.
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
     * Updates this cell position within the CellArray to contain a Pickup_Speed
     * cell again
     * <p>
     * Removes the old Pickup_Speed cell from ResetHandler within MapManager
     */
    @Override
    public void timerMethod() {
        MapManager.cellArray[cellPosition.getKey()][cellPosition.getValue()] =
                new Pickup_Speed(groundEnum, cellPosition, mapManager);
        mapManager.resetHandler.remove(this);
    }
}
