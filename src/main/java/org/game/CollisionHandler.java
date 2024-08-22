package org.game;

import org.cell.ActivityCell;
import org.cell.Cell;
import org.cell.WallCell;
import org.entity.Entity;
import org.entity.MainCharacter;
import org.extras.CellType;
import org.extras.GroundType;


/**
 * Handles collision-based behavior of entities. It detects the oncoming cell
 * based on the direction of the entity.
 *
 * @author Bryan
 * @version 1.0
 */
public class CollisionHandler {

    GameManager gm;

    Cell impassableCell =  new WallCell();

    /**
     * Basic Constructor
     * Constructs a new CollisionHandler. Passes the corresponding Game Manager
     *
     * @param gm    Instance of GameManager
     */
    public CollisionHandler(GameManager gm) {
        this.gm = gm;
    }


    /**
     * Checks the cells that the entity will occupy after movement. Validates that
     * accesses of cellArray are within bounds, and prevents movement outside the
     * area predetermined by the cellArray.
     * <p>
     * This method is a helper method intended exclusively for internal use by
     * other methods within this class.
     *
     * @param entity     The entity whose movement will be checked for collision
     * @return    A Cell array containing the two cells at the entity's destination
     */
    private Cell[] checkCell(Entity entity) {

        // Set bounds
        int upHitbox = entity.cellY + entity.hitbox.y;
        int downHitbox = entity.cellY + entity.hitbox.y + entity.hitbox.height;
        int leftHitbox = entity.cellX + entity.hitbox.x;
        int rightHitbox = entity.cellX + entity.hitbox.x + entity.hitbox.width;

        // Variable declaration/initialization
        Cell cell1, cell2;
        int destX, destY;
        int cSize = GameManager.cellSize;

        // Check destination cells
        switch(entity.facing) {
            case 'u':
                destY = upHitbox - entity.moveSpeed;
                if(destY > 0) {
                    cell1 = MapManager.cellArray[leftHitbox/cSize][destY/cSize];
                    cell2 = MapManager.cellArray[rightHitbox/cSize][destY/cSize];
                }
                else {
                    cell1 = impassableCell;
                    cell2 = impassableCell;
                }
                break;

            case 'd':
                destY = downHitbox + entity.moveSpeed;
                if(destY < 50*cSize) {
                    cell1 = MapManager.cellArray[leftHitbox/cSize][destY/cSize];
                    cell2 = MapManager.cellArray[rightHitbox/cSize][destY/cSize];
                }
                else {
                    cell1 = impassableCell;
                    cell2 = impassableCell;
                }
                break;

            case 'l':
                destX = leftHitbox - entity.moveSpeed;
                if(leftHitbox > 0) {
                    cell1 = MapManager.cellArray[destX/cSize][upHitbox/cSize];
                    cell2 = MapManager.cellArray[destX/cSize][downHitbox/cSize];
                }
                else {
                    cell1 = impassableCell;
                    cell2 = impassableCell;
                }
                break;

            case 'r':
                destX = rightHitbox + entity.moveSpeed;
                if(destX < 50*cSize) {
                    cell1 = MapManager.cellArray[destX/cSize][upHitbox/cSize];
                    cell2 = MapManager.cellArray[destX/cSize][downHitbox/cSize];
                }
                else {
                    cell1 = impassableCell;
                    cell2 = impassableCell;
                }
                break;

            default:
                throw new IllegalStateException("Unexpected direction: " + entity.facing);
        }

        return new Cell[]{cell1, cell2};
    }


    /**
     * Checks if the destination cells are passable. If either cell is not passable,
     * prevents the character from moving.
     *
     * @param entity    The entity that will occupy the destination cells
     */
    public void checkPassable(Entity entity) {
        Cell[] targetcells = checkCell(entity);

        // Check if both cells passable.
        if(!targetcells[0].passable || !targetcells[1].passable) {
            entity.movable = false;
        }
    }


    /**
     * Checks if either of the destination cells have an associated behavior, and
     * invokes the corresponding update() method.
     *
     * @param entity    The entity that will occupy the destination cells
     */
    public void checkBehavior(Entity entity) {
        Cell[] targetCells = checkCell(entity);

        // Check if both targetcells are the same
        boolean duplicateCell = targetCells[0] == targetCells[1];

        // Check behaviour of both cells
        for(Cell cell : targetCells) {

            // Check type of cell
            CellType type = cell.cellEnum;

            // Call update method for cellType
            if(type == CellType.RewardCell_Regular ||
                    type == CellType.RewardCell_Bonus || type == CellType.PunishmentCell ||
                    type == CellType.Pickup_Trap || type == CellType.Pickup_Speed) {
                ActivityCell ac = (ActivityCell) cell;
                ac.update();
            }
            // Interrupts loop if destination is the same cell
            if(duplicateCell) {return;}
        }
    }


    /**
     * Checks if both destination cells are of the "marsh" ground type. If so,
     * sets the Main Character's onMarsh boolean to true.
     * <p>
     * Intended for use only for behavior specific to Main Character
     *
     * @param mc    The main character that will occupy the destination cells
     */
    public void checkMarsh(MainCharacter mc) {
        Cell[] targetCells = checkCell(mc);

        // Checks if ground type of cells are both marsh
        mc.onMarsh = (targetCells[0].groundEnum == GroundType.marsh && targetCells[1].groundEnum == GroundType.marsh);
    }
}
