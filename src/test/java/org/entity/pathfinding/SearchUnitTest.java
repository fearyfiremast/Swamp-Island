package org.entity.pathfinding;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the SearchUnit class
 * @author      Rob Cameron
 */
public class SearchUnitTest {

    SearchUnit unit;
    Pair<Integer, Integer> location;
    Pair<Integer, Integer> heuristic;

    /**
     * Before each create a new SearchUnit object
     */
    @BeforeEach
    void init() {
        location = new Pair<>(1,2);
        heuristic = new Pair<>(10,20);
        unit = new SearchUnit(null,location,heuristic,10);
    }

    /**
     * Tests the constructor
     */
    @Test
    public void constructorTest() {
        assert(unit.parent == null);
        assert(unit.location.getKey() == 1);
        assert(unit.location.getValue() == 2);
        assert(unit.xHeuristic == 10);
        assert(unit.yHeuristic == 20);
        assert(unit.heuristic == 30);
        assert(unit.movementCost == 10);
    }

    /**
     * Tests the getFValue method
     */
    @Test
    public void getFValueTest() {
        assert(unit.getFValue() == 40);
    }

    /**
     * Tests the compareLocationID method with null
     */
    @Test
    public void nullCompareLocationIDTest() {
        Pair<Integer, Integer> toCheck = new Pair<>(1,2);
        unit.location = null;
        assert(!unit.compareLocationID(toCheck));
    }

    /**
     * Tests the compareLocationID method with first value false
     */
    @Test
    public void firstFalseCompareLocationIDTest() {
        Pair<Integer, Integer> toCheck = new Pair<>(2,2);
        assert(!unit.compareLocationID(toCheck));
    }

    /**
     * Tests the compareLocationID method with second value false
     */
    @Test
    public void secondFalseCompareLocationIDTest() {
        Pair<Integer, Integer> toCheck = new Pair<>(1,1);
        assert(!unit.compareLocationID(toCheck));
    }

    /**
     * Tests the compareLocationID method with both values false
     */
    @Test
    public void bothFalseCompareLocationIDTest() {
        Pair<Integer, Integer> toCheck = new Pair<>(2,1);
        assert(!unit.compareLocationID(toCheck));
    }

    /**
     * Tests the compareLocationID method with true result
     */
    @Test
    public void trueCompareLocationIDTest() {
        Pair<Integer, Integer> toCheck = new Pair<>(1,2);
        assert(unit.compareLocationID(toCheck));
    }

    /**
     * Tests compareTo method
     */
    @Test
    public void compareToTest() {
        SearchUnit newUnit = new SearchUnit(null,location,heuristic,20);

        assert(unit.compareTo(newUnit) < 0);
    }

    /**
     * Tests SearchedYandX method with both values false
     */
    @Test
    public void bothFalseSearchedYandXTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(3,4),heuristic,30),new Pair<>(2,3),heuristic,20);
        unit.parent = newUnit;

        assert(!unit.searchedYandX(newUnit));
    }

    /**
     * Tests SearchedYandX method with y false
     */
    @Test
    public void yFalseSearchedYandXTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(3,4),heuristic,30),new Pair<>(1,2),heuristic,20);
        unit.parent = newUnit;

        assert(!unit.searchedYandX(newUnit));
    }

    /**
     * Tests SearchedYandX method with x false
     */
    @Test
    public void xFalseSearchedYandXTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(2,3),heuristic,30),new Pair<>(2,3),heuristic,20);
        unit.parent = newUnit;

        assert(!unit.searchedYandX(newUnit));
    }

    /**
     * Tests SearchedYandX method with true result
     */
    @Test
    public void trueSearchedYandXTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(3,4),heuristic,30),new Pair<>(1,4),heuristic,20);
        unit.parent = newUnit;

        assert(unit.searchedYandX(newUnit));
    }

    /**
     * Tests SearchedXandY method with both values false
     */
    @Test
    public void bothFalseSearchedXandYTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(3,4),heuristic,30),new Pair<>(2,3),heuristic,20);
        unit.parent = newUnit;

        assert(!unit.searchedXandY(newUnit));
    }

    /**
     * Tests SearchedXandY method with y false
     */
    @Test
    public void yFalseSearchedXandYTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(3,4),heuristic,30),new Pair<>(3,4),heuristic,20);
        unit.parent = newUnit;

        assert(!unit.searchedXandY(newUnit));
    }

    /**
     * Tests SearchedXandY method with x false
     */
    @Test
    public void xFalseSearchedXandYTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(2,3),heuristic,30),new Pair<>(3,2),heuristic,20);
        unit.parent = newUnit;

        assert(!unit.searchedXandY(newUnit));
    }

    /**
     * Tests SearchedXandY method with true result
     */
    @Test
    public void trueSearchedXandYTest() {

        SearchUnit newUnit = new SearchUnit(new SearchUnit(null,new Pair<>(3,4),heuristic,30),new Pair<>(3,2),heuristic,20);
        unit.parent = newUnit;

        assert(unit.searchedXandY(newUnit));
    }

    /**
     * Tests getDirection method with null parent
     */
    @Test
    public void nullParentGetDirectionTest() {
        assert(unit.getDirection() == 'u');
    }

    /**
     * Tests getDirection method with result left
     */
    @Test
    public void lGetDirectionTest() {
        SearchUnit newUnit = new SearchUnit(null,new Pair<>(2,2),heuristic,20);
        unit.parent = newUnit;

        assert(unit.getDirection() == 'l');
    }

    /**
     * Tests getDirection method with result right
     */
    @Test
    public void rGetDirectionTest() {
        SearchUnit newUnit = new SearchUnit(null,new Pair<>(0,2),heuristic,20);
        unit.parent = newUnit;

        assert(unit.getDirection() == 'r');
    }

    /**
     * Tests getDirection method with result up
     */
    @Test
    public void uGetDirectionTest() {
        SearchUnit newUnit = new SearchUnit(null,new Pair<>(1,3),heuristic,20);
        unit.parent = newUnit;

        assert(unit.getDirection() == 'u');
    }

    /**
     * Tests getDirection method with result down
     */
    @Test
    public void dGetDirectionTest() {
        SearchUnit newUnit = new SearchUnit(null,new Pair<>(1,1),heuristic,20);
        unit.parent = newUnit;

        assert(unit.getDirection() == 'd');
    }

    /**
     * Tests getXPos method
     */
    @Test
    public void getXPosTest() {
        assert(unit.getXPos() == 1);
    }

    /**
     * Tests getYPos method
     */
    @Test
    public void getYPosTest() {
        assert(unit.getYPos() == 2);
    }


}
