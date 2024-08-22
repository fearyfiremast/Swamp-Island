package org.entity.pathfinding;

import javafx.util.Pair;
import org.cell.Cell;
import org.entity.Enemy;
import org.game.GameManager;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Implementation of the A* search algorithm
 * @author Xander Smith
 * @version 1.0
 */
public class SearchAlgorithm {
    /**
     * Returns the position that is the bestMove for the Enemy
     * @param enemy: enemy to have position calculated
     * @param characterPosition: the position of the player character
     * @param levelOfDetail: for values greater than 0 increases accuracy of pathfinding algorithm
     * @return SearchUnit of best move.
     */
    public static SearchUnit bestMove(Enemy enemy, Pair<Integer, Integer> characterPosition, int levelOfDetail) {
        //TODO Something is rotten in the state of denmark. Recursive call cannot conclude without level of Detail
        // May need to rework to set cells to be the node and returns a direction that the entity must move by its speed

        // ArrayList that will store every point ever searched that was not a best move
        ArrayList<SearchUnit> openList = new ArrayList<>();

        // ArrayList that will store the best
        ArrayList<SearchUnit> closedList = new ArrayList<>();

        // Heuristic is essentially a best guess for the distance between the enemy to the player
        Pair<Integer, Integer> heuristic = calculateH(enemy.getPosPair(), characterPosition);

        // G is set to 0 because there is no movement cost to staying still
        // Starting point of enemy. Parent is null as there is no search that was prior to this
        SearchUnit initUnit = new SearchUnit(null, enemy.getPosPair(), heuristic, 0);

        // Searches: searches levelOfDetail moves ahead to find the optimal move towards the player, avoiding walls
        recSearch(enemy, initUnit, openList, closedList, characterPosition, levelOfDetail );

        // From the last node searched (Best guess at time of concluding search) traverses in reverse and returns
        // Best move
        SearchUnit toReturn = getFirstMove(initUnit, closedList);
        if (toReturn == null) {
            return initUnit;
        }
        return toReturn;

    }

    /**
     * Creates a path in the closedList arrayList that is the calculated route the enemy should take
     * @param enemy enemy that is getting its optimal path tracked
     * @param parent SearchUnit that was determined to be the next best move in the prev recurse
     * @param open  arrayList<SearchUnit> that contains all searched locations aside from ones in closedList
     * @param closed arrayList<SearchUnit> that contains all the calculated best moves
     * @param charPos position of the player on the map on a current tick
     * @param scope number of maximum recursive loops
     */
    private static void recSearch(Enemy enemy, SearchUnit parent, ArrayList<SearchUnit> open, ArrayList<SearchUnit> closed,
                                  Pair<Integer, Integer> charPos, int scope) {

        // Removes parent from OpenList and adds it to the closed list
        open.remove(parent);
        closed.add(parent);

        //TODO compareCellId not ideal fix. is based upon the fact that cell pos is in top left of pixel
        // Shouldnt result int stack overflow though if scope is removed though

        // Recursive base case. If scope is reduced to 0 or enemy has reached same cell as Character
        if (scope <= 0 || compareCellId(charPos, parent.location, GameManager.cellSize)) {
            return;
        }

        // From parent cell searches in the allowed directions and adds the move to OpenArray if the move is valid
        addUnits(enemy, parent, open, closed, charPos);

        // Out of the values in OpenArray finds the one that is judged to be closest to the target on this iteration
        SearchUnit minF = findMinF(open);

        if (minF == null) {
            return;
        }

        // Recursive call
        recSearch(enemy, minF, open, closed, charPos, scope-1);
    }

    /**
     * Traverses SearchUnits in the ArrayList until it finds a SearchUnit who's parent is the startPoint
     * @param startPoint Point in which the enemy is currently at
     * @param closed ArrayList(SearchUnit) which has the best path to player based on precision
     * @return null if closed list is empty, otherwise returns the best adjacent move to startPoint based on levelOfDetail
     */
    private static SearchUnit getFirstMove(SearchUnit startPoint, ArrayList<SearchUnit> closed) {
        if (closed.isEmpty()) {
            return null;
        }
        SearchUnit toReturn = closed.get(closed.size()-1);
        while ( toReturn.parent != null && toReturn.parent != startPoint) {
            toReturn = toReturn.parent;
        }
        return toReturn;
    }

    /**
     * Calculates the movementCost of moving to a particulat node
     * @param parent movement cost is cumulative as to move to one node you must move to all the nodes before it
     * @return movement cost to go to next node
     */
    private static int calculateG(SearchUnit parent) {
        return 10 + parent.movementCost;
    }

    /**
     * Uses the Manhaten heuristic to guess the location/path of the maincharacter
     * @param destPos the position of the enemy on the map
     * @param characterMapPos the position of the player on the map
     * @return total distance of opposite and adjacent triangle between player and enemy. Absolute values.
     */
    private static Pair<Integer, Integer> calculateH (Pair<Integer, Integer> destPos, Pair<Integer, Integer> characterMapPos) {

        // Calculates the X distance and Y Distance from destination to searcher
        int xDistance = Math.abs(destPos.getKey() - characterMapPos.getKey());
        int yDistance = Math.abs(destPos.getValue() - characterMapPos.getValue());
        return  new Pair<>(xDistance, yDistance);
    }

    /**
     * Add units is responsible for adding valid SearchUnits to the openList arrayList and removing the parent node
     * from the list and adding it to the closedList once the entire process is completed.<p><p>
     *     To specify the method is going to search in the cardinal directions by the enemy's movement speed. If the
     *     location is valid then a searchUnit will be created for the destination point. Once a searchUnit is created
     *     it's location will need to compared to all searchUnits within the openList. If it is unique it will be added
     *     to the list. If it is not only the SearchUnit with the lowest G cost will remain in the arrayList</p></p>
     *
     * @param enemy Enemy
     * @param parent parent SearchUnit that is being used as the basis for the calculations
     * @param open openList of searchUnits
     * @param closed closedList of searchUnits
     * @param charPos Absolute scale pixel position
     */
    private static void addUnits(Enemy enemy, SearchUnit parent, ArrayList<SearchUnit> open,
                                 ArrayList<SearchUnit> closed, Pair<Integer, Integer> charPos) {

        // One cell of array will always be zero. Other switches between -1 or 1
        int[] displacementArray = {0, 0};

        for (int i = 0; i < 2; i++) {
            for (int j = -1; j < 2; j += 2) {

                /* Sets either the first or second slot of array to have a value of +/- 1 while the other has a value
                     of 0. Value of enemy move speed multiplied by array is added to parent X and Y to calculate
                     The coordinates of next move
                 */
                displacementArray[i] = j;

                // xOffset and yOffset only exist to make the destination initializer fit on one line
                int xOffset = parent.location.getKey() + enemy.moveSpeed * displacementArray[0];
                int yOffset = parent.location.getValue() + enemy.moveSpeed * displacementArray[1];

                // New position
                Pair<Integer, Integer> destination = new Pair<>(xOffset, yOffset);

                // Checks if the position is valid. Meaning the Cell it lands on is not immovable and is not
                // Already in thr closedArray
                if (!isValid(destination, closed, enemy.gm)) {
                    continue;
                }

                // Creates new SearchUnit as position is valid
                Pair<Integer, Integer> heuristic = calculateH(destination, charPos);
                SearchUnit toCheck = new SearchUnit(parent, destination, heuristic, calculateG(parent));

                // Checks if the new unit is already in openArray. If it is checks which of the two copies is the better
                // path and keeps the best one. Otherwise, adds SearchUnit to open array
                if (!compareAndSwap(open, toCheck)) {
                    open.add(toCheck);
                }
            }

            // Sets the displacement array back to 0
            displacementArray[i] = 0;

        }
        // By this point all 4 directions should have had an opportunity to be added to the open array
    }

    /**
     * Checks to see if the suggested move choice isn't impassable or in the closedList
     * @param position suggested movement choice
     * @param closed ArrayList<SearchUnit> to be searched through
     * @param gm GameManager to be used to find CellSize
     * @return whether this is a SearchUnit to be further pursued, or not
     */
    private static boolean isValid(Pair<Integer, Integer> position, ArrayList<SearchUnit> closed, GameManager gm) {
        Cell type = gm.getCell(position);

        // If it is passable and not in closed list return true else false
        return type.passable && !unitInList(position, closed);
    }

    /**
     *Compares the SearchUnit arrayList and the passed in SearchUnit. If there is a match then the function
     * return true. If the movementCost in toCompare is lower than it's match in the ArrayList, the ArrayList
     * value copies the movement cost of toCompare
     *
     * @param open arrayList of SearchUnits. Should only be openList
     * @param toCompare specific SearchUnit
     * @return SearchUnit with the smallest G value
     */
    private static boolean compareAndSwap(ArrayList<SearchUnit> open, SearchUnit toCompare) {

        // Searches every point in openArray
        for (SearchUnit i : open) {

            // If they have the same pixel position
            if (toCompare.compareLocationID(i.location)) {

                // Checks if passed in similar SearchUnit has a lower movementCost
                if (i.movementCost > toCompare.movementCost) {
                    open.remove(i);
                    open.add(toCompare);
                }

                // Match was found
                return true;
            }
        }
        // Match was not found
        return false;
    }

    /**
     * Checks if the location is inside the ArrayList or not
     * @param isMatch Pair(xCell, yCell)
     * @param toExamine ArrayList(SearchUnit) that will be used to check location
     * @return true if unit is in list, false otherwise
     */
    private static boolean unitInList(Pair<Integer, Integer> isMatch, ArrayList<SearchUnit> toExamine) {

        // Searches every value in Closed List
        for (SearchUnit i : toExamine) {

            // If isMatch has the same pixel position as a value in the closed list.
            if (i.compareLocationID(isMatch)) {
                return true;
            }
        }
        // Match not found
        return false;
    }

    /**
     * Finds and returns the minimum value of F within ArrayList
     * @param array ArrayList(SearchUnit) to be searched through
     * @return null if ArrayList is empty otherwise returns the minimal element
     */
    private static SearchUnit findMinF(ArrayList<SearchUnit> array) {

        // In case the openList is empty
        if (array.isEmpty()) {
            return null;
        }

        // Gets first value in array to start
        SearchUnit toReturn = array.get(0);

        // Searches every Value in OpenArray
        for (SearchUnit i : array) {

            // If i is less than current min
            if (i.compareTo(toReturn) < 0) {

                // Sets the new lowest value as toReturn
                toReturn = i;
            }
            // Special case to make the AI move more realistically
             else if (i.compareTo(toReturn) == 0) {
                 toReturn = handleEqualHeuristic(i, toReturn);
             }
        }
        return toReturn;
    }

    //TODO refactor of entire handle equal heuristic needed. Possibly causes issues in the case where depth of search > 1
    // Could potentially be added to the heuristic

    /**
     * Checks if the 2 search units checked the players position initially from different axis. If that is the case then
     * sets minVal as the one that the player is closest to.
     * @param fromArray openArray
     * @param minVal current minimum value found in findMinF
     * @return SearchUnit that fits above criteria
     */
    private static SearchUnit handleEqualHeuristic(SearchUnit fromArray, SearchUnit minVal) {
        if (fromArray.searchedXandY(minVal) && fromArray.xHeuristic > minVal.yHeuristic )
            minVal = fromArray;
        if (fromArray.searchedYandX(minVal) && fromArray.yHeuristic > minVal.xHeuristic) {
            minVal = fromArray;
        }
        return minVal;
    }

    /**
     * Calculates the Cell within the Cell array that the two pairs would occupy. If they are identical then returns true
     * else returns false
     * @param toCompare1 Position of an object in pixels
     * @param toCompare2 Position if an object in pixels
     * @param pixelSize Side length of a Cell in pixels
     * @return boolean value: True if cell indices X, Y of Both pairs match false otherwise
     */
    public static boolean compareCellId (Pair<Integer, Integer> toCompare1, Pair<Integer, Integer> toCompare2, int pixelSize) {

        // Null and therefore cannot share same ID
        if (toCompare1 == null || toCompare2 == null) {
            System.out.println("Null");
            return false;
        }

        // Gets the X and Y indices of both pairs and checks if they are equal
        return ((toCompare1.getKey()/pixelSize == toCompare2.getKey()/pixelSize) &&
                (toCompare1.getValue()/pixelSize == toCompare2.getValue()/pixelSize));
    }
}
