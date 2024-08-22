package org.cell;

import javafx.util.Pair;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * Pickup object that increases Players score by a small amount when stepped on.
 * Will temporarily replace itself with an inactive version of itself when
 * stepped on.
 * <p>
 * Gives the Main Character a speed powerup when stepped on
 *
 * @author Bryan
 * @version 1.0
 * @see Pickup_Speed_Inactive
 */
public class Pickup_Speed extends ScoreCell{

    /**
     * Initializes the CellType and Score
     */
    private void paramList() {
        cellEnum = CellType.Pickup_Speed;
        score = 50;
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
    public Pickup_Speed(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        this.paramList();
        this.groundEnum = setGround;
        this.cellPosition = setPosition;
        this.mapManager = setManager;
    }

    /**
     * Increases score, gives a powerup, and calls updateCellState
     */
    @Override
    public void update() {
        if (!MainCharacter.hasJuice) {
            // Update score
            int currentScore = MainCharacter.getScore();
            MainCharacter.setScore(currentScore + score);

            // Give speed powerup
            MainCharacter.hasJuice = true;

            // Update cell state (to inactive)
            updateCellState();
        }
    }

    /**
     * Creates a new Pickup_Speed_Inactive object and inserts it into the calling
     * Pickup_Speed's position in the cell array. Adds the Inactive cell to the
     * MapManager's timerArray
     * <p>
     * Adds the calling object to the resetHandler.
     */
    @Override
    public void updateCellState() {
        Pickup_Speed_Inactive toAdd = new Pickup_Speed_Inactive(groundEnum, cellPosition, mapManager);
        MapManager.cellArray[this.cellPosition.getKey()][this.cellPosition.getValue()] = toAdd;
        mapManager.addToTimer(toAdd);

        // Adds Pickup_speed to the resetHandler
        mapManager.resetHandler.add(this);
    }

}
