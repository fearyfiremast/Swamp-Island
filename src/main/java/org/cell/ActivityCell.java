package org.cell;

/**
 * Abstract class that provides the abstract function: update()
 * @author Xander Smith
 * @version 1.0
 */
public abstract class ActivityCell extends Cell {

    /**
     * When executed results in some change to the larger game state. Specifics are implemented in class
     */
    public abstract void update();

}
