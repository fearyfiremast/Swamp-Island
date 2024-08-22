package org.cell;

import javafx.util.Pair;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * When player walks over the Cell containing this type Score is increased, MapManager has its number of rewardCells
 * decreased. <p>
 * When stepped on decomposes into an EmptyCell
 */
public class RewardCell_Regular extends ScoreCell {

    /**
     * Initializes the CellType, and Score
     */
    private void paramList() {
        this.score = 100;
        this.cellEnum = CellType.RewardCell_Regular;
    }

    /**
     * Default constructor
     */
    public RewardCell_Regular() {
        this.paramList();
    }

    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setLocation of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public RewardCell_Regular(GroundType setGround, Pair<Integer, Integer> setLocation, MapManager setManager) {
        this.paramList();
        groundEnum = setGround;
        cellPosition = setLocation;
        mapManager = setManager;
    }

    /**
     * Updates player score, Reduces MapManager numRewards by 1, and calls updateCellState
     */
    @Override
    public void update() {
        int currentScore = MainCharacter.getScore();
        MainCharacter.setScore(currentScore + this.score);
        MapManager.numRewards -= 1;
        updateCellState();
    }

    /**
     * Adds this object to the resetManager object in MapManager<p>
     * Replaces Cell with an EmptyCell
     */
    @Override
    public void updateCellState()
    {   // Basically we just update the cells type in the mapManager from
        mapManager.resetHandler.add(this);
        this.mapManager.cellArray[this.cellPosition.getKey()][this.cellPosition.getValue()] =
                new EmptyCell(groundEnum, cellPosition, mapManager);
    }
}
