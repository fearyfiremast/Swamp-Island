package org.game;

import javafx.util.Pair;
import org.cell.Cell;
import org.entity.Enemy;
import org.entity.MainCharacter;
import org.extras.CellType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stores attributes required for resetting the game once a terminal condition has been met.
 * @author Xander Smith
 * @version 1.0
 */
public class ResetHandler {
    private GameManager game = null;
    private ArrayList<Cell> toTrackCell = null;
    private ArrayList<Pair<Integer, Integer>> toTrackEnemy = null;


    /**
     * Constructor which stores the ArrayList of pairs used to store enemy starting positions and
     * stores the GameManager
     * @param       game GameManager in charge of all game operations
     * @param       enemyPosArray ArrayList of enemies
     */
    public ResetHandler(GameManager game, ArrayList<Pair<Integer, Integer>> enemyPosArray) {
        this.toTrackEnemy = enemyPosArray;
        this.game = game;
        toTrackCell = new ArrayList<>();
    }

    /**
     * Adds a specific cell to the ResetHandlers CellArray
     * @param       toAdd Cell which will be added to CellArray
     */
    public void add(Cell toAdd) {
        toTrackCell.add(toAdd);
    }

    /**
     * Will convert the Cell to its Coordinates on the map. If a cell in the list has matching coordinates to
     * the paramater removes it from list.
     * @param toRemove Cell
     */
    public void remove(Cell toRemove) {
        Pair<Integer, Integer> position = toRemove.cellPosition;
        int x, y;
        ArrayList<Cell> temp = new ArrayList<>();
        for (Cell i : toTrackCell) {
            x = i.cellPosition.getKey();
            y = i.cellPosition.getValue();
            if (x == position.getKey() && y == position.getValue()) {
                temp.add(i);
            }
        }

        //Can't remove during loop so we create a list and then remove outside of loop
        for (Cell i : temp) {
            toTrackCell.remove(i);
        }
    }

    /**
     * Resets most values in the game state when called back to how it was when level was first initialized.<p>
     * These Values include:
     * numRewards, RewardCell_Regular position, PunishmentCell, Enemy position, BonusHandler, and exitStateArray
     */
    public void ResetGame() {
        int rewardCount = 0;

        for (Cell i : toTrackCell) {
            game.mapManager.cellArray[i.cellPosition.getKey()][i.cellPosition.getValue()] = i;
            if (i.cellEnum == CellType.RewardCell_Regular) {
                rewardCount++;
            }
        }

        game.enemyList = new ArrayList<>();
        for (Pair<Integer, Integer> i : toTrackEnemy) {
            game.enemyList.add(new Enemy(game, i, game.mc));
        }
        toTrackCell = new ArrayList<>();
        // Adds the rewards removed from the mapManager back
        MapManager.numRewards += rewardCount;
        game.mapManager.bonusHandler = new BonusRewardHandler(game.mapManager);
        Arrays.fill(game.mapManager.exitState, true);
        game.getMainCharacter().moveSpeed = game.getMainCharacter().baseSpeed;
        Pair<Integer, Integer> temp = game.mapManager.getExitCellPos();
        MapManager.cellArray[temp.getKey()][temp.getValue()].cellEnum = CellType.ExitCell;
        MainCharacter.hasJuice = false;
        MainCharacter.hasTrap = false;
    }

    /**
     * Returns the size of the CellArray. Only used in testing
     * @return int
     */
    public int getSizeCell() {
        return this.toTrackCell.size();
    }

    /**
     * returns the size of the EnemyArray. Only used in testing
     * @return int
     */
    public int getSizeEnemyArrayGame() {
        return game.enemyList.size();
    }

}
