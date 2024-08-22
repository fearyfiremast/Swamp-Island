package org.entity.pathfinding;

import javafx.util.Pair;
import org.entity.Enemy;

import java.util.Objects;

/**
 * Class used to store important values used in the Search Algorithm class
 * @author Xander Smith
 * @version 1.0
 */
public class SearchUnit implements Comparable<SearchUnit>{
    SearchUnit parent;
    Integer heuristic;
    Integer xHeuristic;
    Integer yHeuristic;
    Integer movementCost;
    Pair<Integer, Integer> location;

    /**
     * Creates a search unit based on a parent SearchUnit, A position, as well as a calculated Heuristic and cost
     * @param       setParent Search unit that is the parent of the new unit
     * @param       setLocation Pair of integers that is the location of the searchUnit
     * @param       setH Pair of integers that will set the heuristics
     * @param       setG Integer of the movement cost of the ground
     */
    public SearchUnit(SearchUnit setParent, Pair<Integer, Integer> setLocation, Pair<Integer, Integer> setH, Integer setG) {
        parent = setParent;
        location = setLocation;
        xHeuristic = setH.getKey();
        yHeuristic = setH.getValue();
        heuristic = xHeuristic + yHeuristic;
        movementCost = setG;
    }

    /**
     * Returns the F value of the SearchUnit
     * @return Integer
     */
    public Integer getFValue() {
        return heuristic + movementCost;
    }

    /**
     * Compares the location pair in calling SearchUnit, and the passed in Pair. If they are equal as is then returns true
     * else returns false
     * @param toCheck Pair (Integer, Integer)
     * @return boolean
     */
    public boolean compareLocationID(Pair<Integer, Integer> toCheck) {
        if (location == null) {
            System.out.println("Null");
            return false;
        }
        return (Objects.equals(location.getKey(), toCheck.getKey()) &&
                Objects.equals(location.getValue(), toCheck.getValue()));
    }

    /**
     * Implements the compareTo method. Compares SearchUnits by their FValues
     * @param o the object to be compared.
     * @return int: 0 if equal, less than 0 if calling function is smaller than passed in object, greater than 0 if calling function is larger
     */
    @Override
    public int compareTo(SearchUnit o) {
        return this.getFValue() - o.getFValue();
    }

    /**
     * Checks if initial search was in the X direction
     * @return if in X direction true, false otherwise
     */
    private boolean searchedInXDirection() {
        return (parent.location.getKey() - location.getKey()) == 0;
    }

    /**
     * Checks if initial search was in the Y direction
     * @return if in Y direction true, false otherwise
     */
    private boolean searchedInYDirection() {
        return (parent.location.getValue() - location.getValue()) == 0;
    }

    /**
     * Checks to see if calling object searched in Y direction while passed in object searched in X direction
     * @param toTest  SearchUnit
     * @return boolean
     */
    public boolean searchedYandX(SearchUnit toTest) {
        return (toTest.searchedInYDirection() && this.searchedInXDirection());
    }

    /**
     * Checks to see if calling object searched in Y direction while passed in object searched in X direction
     * @param toTest  SearchUnit
     * @return boolean
     */
    public boolean searchedXandY(SearchUnit toTest) {
        return (toTest.searchedInXDirection() && this.searchedInYDirection());
    }

    /**
     * Method that returns a char of the direction that is being faced.
     * @return char
     */
    public char getDirection() {
        //TODO WOW REFACTOR
        if (parent == null) {
            return 'u';
        }
        char toReturn = 'd';
        int xDispl = parent.location.getKey() - location.getKey();
        int yDispl =parent.location.getValue() - location.getValue();

        if (xDispl > 0) {
            toReturn = 'l';
        }
        if (xDispl < 0) {
            toReturn = 'r';
        }
        if(yDispl > 0) {
            toReturn = 'u';
        }
        if (yDispl < 0) {
            toReturn = 'd';
        }

        return toReturn;

    }

    /**
     * Returns Xposition of SearchUnit
     * @return Integer
     */
    public Integer getXPos() {
        return location.getKey();
    }

    /**
     * Returns Yposition of SearchUnit
     * @return Integer
     */
    public Integer getYPos() {
        return  location.getValue();
    }
}
