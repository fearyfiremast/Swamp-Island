package org.cell;

/**
 * abstract class the has a score attribute and an updateCellState method. Extends ActivityCell
 * @author Xander Smith
 * @version 1.0
 */
public abstract class ScoreCell extends ActivityCell{
    int score = 0;

    /**
     * Should be used to change the calling cell into another type
     */
    public abstract void updateCellState();
}
