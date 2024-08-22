package org.cell;

import javafx.util.Pair;
import org.display.GameScreen;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;
import org.extras.Timer;
import org.game.MapManager;

/**
 * Similar behaviour to RewardCell_Regular. When cell is stepped on provides player a massive reward boost. <p>
 * In addition cell will disappear after some amount of time has passed. Implements Timer interface.<p>
 * When class is removed from board is always replaced by EmptyCell
 * @author Xander Smith
 * @version 1.0
 */
public class RewardCell_Bonus extends ScoreCell implements Timer {

    int timer = 0;
    public static String alert;

    /**
     * Initializes the CellType, sets Score, and sets Timer
     */
    private void paramList() {
        setTimer();
        score = 200;
        cellEnum = CellType.RewardCell_Bonus;
    }

    /**
     * Constructor that should be used in almost all cases. Sets the GroundEnum, Position and MapManager of the cell.<p>
     * automatically adds the cell to MapManagers timerArray
     * @param setGround groundEnum that determines base sprite layer, and movement modifier
     * @param setPosition Position of the cell within MapManagers cellArray[][]
     * @param setManager MapManager of the Cell
     */
    public RewardCell_Bonus(GroundType setGround, Pair<Integer, Integer> setPosition, MapManager setManager) {
        paramList();
        groundEnum = setGround;
        cellPosition = setPosition;
        mapManager = setManager;
        mapManager.addToTimer(this);
        alert = "Coin in " + determineLocation();
        GameScreen.rewardM = true;
    }

    /**
     * Determines the quadrant in which the reward is located.
     * Used for the message that will be displayed to the user.
     *
     * @return      String with location of reward on map
     */
    private String determineLocation() {
        String s;
        if (cellPosition.getKey() <= 25 && cellPosition.getValue() <= 25) {
            s = "North West";
        } else if (cellPosition.getKey() <= 25 && cellPosition.getValue() > 25) {
            s = "South West";
        } else if (cellPosition.getKey() > 25 && cellPosition.getValue() <= 25) {
            s = "North East";
        } else {
            s = "South East";
        }
        return s;
    }

    /**
     * Updates score and updates CellState
     */
    @Override
    public void update() {
        int score = MainCharacter.getScore();
        MainCharacter.setScore(score + this.score);
        mapManager.bonusHandler.incrementBonusCollected();
        updateCellState();
    }

    /**
     * Sets Timer to 0 so will be ejected from timerArray in MapManager<p>
     * Position in cellArray in MapManager is replaced by EmptyCell.</p>
     *
     */
    @Override
    public void updateCellState() {
        // Sets timer to 0 so that if the cell is collected before the time expires it is removed from
        // Array list in MapManager
        timer = 0;
        EmptyCell toAdd = new EmptyCell(groundEnum, cellPosition, mapManager);
        mapManager.cellArray[cellPosition.getKey()][cellPosition.getValue()] = toAdd;

    }

    /**
     * Checked every tick. decreases timer. Calls timerMethod() if isEvent is true
     */
    @Override
    public void tick() {
        timer--;
        if (isEvent()) {
            timerMethod();
        }
    }

    /**
     * Calls the updateCellState method
     */
    @Override
    public void timerMethod() {
        updateCellState();
    }

    /**
     * If timer less than or equal 0 then returns true. Else false
     * @return boolean
     */
    @Override
    public boolean isEvent() {
        return timer <= 0;
    }

    /**
     * Sets timer to be 30 seconds
     */
    @Override
    public void setTimer() {
        // 30s = 60 * 30
        timer = 30 * 60;
    }
}
