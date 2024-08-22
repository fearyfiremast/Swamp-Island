package org.cell;

import javafx.util.Pair;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * Punishment object that reduces Players score by some amount when stepped on. Will temporarily replace itself
 * with an inactive version of itself.
 * @author Xander Smith
 * @version 1,0
 * @see PunishmentCell_Inactive
 */
public class PunishmentCell extends ScoreCell{

    /**
     * Initializes the CellType and Score
     */
    private void paramList() {
        cellEnum = CellType.PunishmentCell;
        score = -200;
    }

    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPosition Position of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public PunishmentCell(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        this.paramList();
        this.groundEnum = setGround;
        this.cellPosition = setPosition;
        this.mapManager = setManager;
    }

    /**
     * Decreases the score and calls updateCellState
     */
    @Override
    public void update() {
        // this changes the characteristics of the gameManager and mapManager.
        int currentScore = MainCharacter.getScore();
        MainCharacter.setScore(currentScore + score);
        updateCellState();
    }

    /**
     * Creates a new PunishmentCell_Inactive object and inserts it into the calling PunishmentCell's position
     * in the cell array. Adds the Inactive cell to the MapManagers timerArray<p>
     * Adds the calling object to the resetHandler.
     */
    @Override
    public void updateCellState() {
        PunishmentCell_Inactive toAdd = new PunishmentCell_Inactive(groundEnum, cellPosition, mapManager);
        MapManager.cellArray[this.cellPosition.getKey()][this.cellPosition.getValue()] = toAdd;
        mapManager.addToTimer(toAdd);

        // Adds PunishmentCell. Not the Inactive version, to the resetHandler
        mapManager.resetHandler.add(this);
    }
}
