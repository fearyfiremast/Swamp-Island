package org.cell;

import javafx.util.Pair;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;
import org.game.MapManager;

/**
 * EnemyTrap object that kills an Enemy that steps on it, and increase the
 * Main Character's score.
 * <p>
 * Will be replaced with the previous cell that occupied this position when
 * stepped on by the Enemy.
 *
 * @author Bryan
 * @version 1.0
 */
public class EnemyTrap extends ScoreCell{

    /**
     * Initializes the CellType and Score
     */
    private void paramList() {
        cellEnum = CellType.EnemyTrap;
        score = 150;
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
    public EnemyTrap(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        this.paramList();
        this.groundEnum = setGround;
        this.cellPosition = setPosition;
        this.mapManager = setManager;
    }

    /**
     * Increases score, and calls updateCellState (killEnemy will be in Enemy class)
     */
    @Override
    public void update() {
        // Update score
        int currentScore = MainCharacter.getScore();
        MainCharacter.setScore(currentScore + score);

        // Update cell state (to inactive)
        updateCellState();
    }

    /**
     * Creates a new EmptyCell object and inserts it into the calling EnemyTrap's
     * position in the cell array.
     * <p>
     * Adds the calling object to the resetHandler.
     */
    @Override
    public void updateCellState() {
        EmptyCell emptyCell = new EmptyCell(groundEnum, cellPosition, mapManager);
        MapManager.cellArray[this.cellPosition.getKey()][this.cellPosition.getValue()] = emptyCell;

        // Removes the previous EmptyCell from the resetHandler
        mapManager.resetHandler.remove(this);
    }
}
