package org.entity.pathfinding;

import javafx.util.Pair;
import org.entity.Enemy;
import org.entity.MainCharacter;
import org.game.GameManager;
import org.game.InputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

/**
 * Tests the functionality of the SearchAlgorithm class
 * @author      Rob Cameron
 */
public class SearchAlgorithmTest {

    GameManager gameManager;
    MainCharacter mainCharacter;
    Enemy enemy;

    /**
     * Before each create a new enemy object
     */
    @BeforeEach
    void init() {
        InputHandler inputHandler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        gameManager = new GameManager(inputHandler,layout,cards);

        mainCharacter = new MainCharacter(gameManager);

        enemy = new Enemy(gameManager,new Pair<>(1,2),mainCharacter);
    }

    /**
     * Tests the getBestMove method with levelOfDetail set to zero
     */
    @Test
    public void zeroDetailGetBestMoveTest() {
        SearchUnit unit = SearchAlgorithm.bestMove(enemy,new Pair<>(10,10),0);

        assert(unit.movementCost == 0);
        assert(unit.xHeuristic == 38);
        assert(unit.yHeuristic == 86);
        assert(unit.heuristic == 124);
        assert(unit.location.getKey() == 48);
        assert(unit.location.getValue() == 96);
    }

    /**
     * Tests the getBestMove method with levelOfDetail set to one
     */
    @Test
    public void oneDetailGetBestMoveTest() {
        SearchUnit unit = SearchAlgorithm.bestMove(enemy,new Pair<>(10,10),1);

        assert(unit.movementCost == 10);
        assert(unit.xHeuristic == 38);
        assert(unit.yHeuristic == 84);
        assert(unit.heuristic == 122);
        assert(unit.location.getKey() == 48);
        assert(unit.location.getValue() == 94);
    }

    /**
     * Tests the getBestMove method with levelOfDetail set to two
     */
    @Test
    public void twoDetailGetBestMoveTest() {
        SearchUnit unit = SearchAlgorithm.bestMove(enemy,new Pair<>(10,10),2);

        assert(unit.movementCost == 10);
        assert(unit.xHeuristic == 38);
        assert(unit.yHeuristic == 88);
        assert(unit.heuristic == 126);
        assert(unit.location.getKey() == 48);
        assert(unit.location.getValue() == 98);
    }

    /**
     * Tests the compareCellID method with the first value null
     */
    @Test
    public void oneNullCompareCellIDTest() {
        Pair<Integer,Integer> two = new Pair<>(1,2);

        assert(!SearchAlgorithm.compareCellId(null,two,48));
    }

    /**
     * Tests the compareCellID method with the second value null
     */
    @Test
    public void twoNullCompareCellIDTest() {
        Pair<Integer,Integer> one = new Pair<>(1,2);

        assert(!SearchAlgorithm.compareCellId(one,null,48));
    }

    /**
     * Tests the compareCellID method with the both values null
     */
    @Test
    public void bothNullCompareCellIDTest() {
        assert(!SearchAlgorithm.compareCellId(null,null,48));
    }

    /**
     * Tests the compareCellID method with both values false
     */
    @Test
    public void bothFalseCompareCellIDTest() {
        Pair<Integer,Integer> one = new Pair<>(48,96);
        Pair<Integer,Integer> two = new Pair<>(96,48);

        assert(!SearchAlgorithm.compareCellId(one,two,48));
    }

    /**
     * Tests the compareCellID method with the first value false
     */
    @Test
    public void firstFalseCompareCellIDTest() {
        Pair<Integer,Integer> one = new Pair<>(48,96);
        Pair<Integer,Integer> two = new Pair<>(96,96);

        assert(!SearchAlgorithm.compareCellId(one,two,48));
    }

    /**
     * Tests the compareCellID method with the second value false
     */
    @Test
    public void secondFalseCompareCellIDTest() {
        Pair<Integer,Integer> one = new Pair<>(48,96);
        Pair<Integer,Integer> two = new Pair<>(48,48);

        assert(!SearchAlgorithm.compareCellId(one,two,48));
    }

    /**
     * Tests the compareCellID method with a true result
     */
    @Test
    public void trueCompareCellIDTest() {
        Pair<Integer,Integer> one = new Pair<>(48,96);
        Pair<Integer,Integer> two = new Pair<>(48,96);

        assert(SearchAlgorithm.compareCellId(one,two,48));
    }

}
