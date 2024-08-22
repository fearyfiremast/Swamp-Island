package org.game;

import org.cell.Cell;
import org.cell.RewardCell_Bonus;
import org.extras.CellType;
import org.extras.Timer;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Extracted Method from MapManager that is responsible for knowing when and where to spawn a BonusReward Cell<p>
 * Implements the Timer interface
 * @author Xander Smith
 * @version 1.0
 * @since 1.0
 */
public class BonusRewardHandler implements Timer {
    MapManager map = null;
    private int timeNextBonus;
    public int bonusCollected = 0;

    /**
     * Constructor. Creates the handler and sets the timer for when a new BonusReward will be spawned
     * @param mapManager MapManager
     */
    public BonusRewardHandler(MapManager mapManager) {
        this.map = mapManager;
        setTimer();
    }

    /**
     * Increases the paramater storing the number of bonus rewards collected by 1
     */
    public void incrementBonusCollected() {
        bonusCollected++;
    }

    /**
     * Method original written by Nil<p>\
     * Randomly picks a value between 1 and Dimension of map - 2 for both X and Y values<p>
     * If the value happens to be an emptyCell updates it to be a bonus reward instead in the CellArray. Exits while loop
     * If the value is not an empty cell repeat step 1 until an emptyCell is found
     */
    @Override
    public void timerMethod() {
        boolean flag = true;
        while(flag)
        {
            // The idea is to randomly pick things in the 2d array and check if it satisfies the conditions
            // required to be spawned into the cell. If yes, we go forward with it and calls the bonus_reward
            // cell constructor on it.


            // Constraints: Bonus rewards can only spawn on emptyCells

            // First, defining the range for the random picks.
            int randomNumberX = ThreadLocalRandom.current().nextInt(1, map.dimensions.getKey()-2);
            int randomNumberY = ThreadLocalRandom.current().nextInt(1, map.dimensions.getValue()-2);

            CellType random_cell = map.cellArray[randomNumberX][randomNumberY].cellEnum;

            if(random_cell == CellType.EmptyCell) {
                // EmptyCell Found
                // Stores the cell in toSwap
                Cell toSwap = map.cellArray[randomNumberX][randomNumberY];

                // Uses attributes of toSwap to create a new Bonus Reward stored in toAdd
                RewardCell_Bonus toAdd = new RewardCell_Bonus(toSwap.groundEnum,
                        toSwap.cellPosition, toSwap.mapManager);

                // New Bonus reward is added to old emptyCell position in array and added to timed cells list
                map.cellArray[randomNumberX][randomNumberY] = toAdd;

                // Process complete
                flag = false;
            }
        }
    }

    /**
     * If isEvent is true calls the timerMethod and sets the timer again
     */
    @Override
    public void tick() {
        if (isEvent()) {
            timerMethod();
            setTimer();

        }
    }

    /**
     * Picks a random value between 5 and 20 and adds it to elapsedTime to figure out when the next bonus reward will spawn
     */
    @Override
    public void setTimer() {
        timeNextBonus = ThreadLocalRandom.current().nextInt(5, 20) + GameManager.elapsedTime;
        System.out.println("Time of next bonus spawn: " + timeNextBonus);
    }

    /**
     * If the elapsed time in GameManager is greater than or equal to timeNextBonus return true, else return false
     * @return      boolean
     */
    @Override
    public boolean isEvent() {
        return (GameManager.elapsedTime >= timeNextBonus);
    }
}
