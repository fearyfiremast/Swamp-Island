package org.game;

import javafx.util.Pair;
import org.animation.SpriteReader;
import org.cell.*;
import org.entity.MainCharacter;
import org.extras.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Stores values that make up a level and help it to function. Is responsible for cell operations
 * @author Xander Smith
 * @version 1.0
 */
public class MapManager {

    //Sprites for maps are set here. If we ever want to do map animations they may need to be moved to the cell
    ArrayList<Timer> timedArray;
    BufferedImage[] groundSprite = null;
    BufferedImage[] cellSprite = null;
    static GameManager gameManager = null;
    public static Cell[][] cellArray = null;
    public ResetHandler resetHandler = null;
    public BonusRewardHandler bonusHandler = null;
    public static int numRewards = 0;
    public static boolean[] exitState = {true, true};
    public static Pair<Integer, Integer> getDimensions() {
        return dimensions;
    }
    // Total map size
    static Pair<Integer, Integer> dimensions = null;

    private Pair<Integer, Integer> startCellPos = null;

    public Pair<Integer, Integer> getExitCellPos() {
        return exitCellPos;
    }
    private Pair<Integer, Integer> exitCellPos = null;

    /**
     * Constructor: Based on MapTemplate and GameManager, generate values.<p>
     * Automatically loads sprites and initializes the map data
     * @param       template MapTemplate used for the map
     * @param       toSet GameManager to be used in the game
     */
    public MapManager(MapTemplate template, GameManager toSet) {
        //animationManager = new AnimationManager(toSet.fps, this);
        groundSprite = new BufferedImage[GroundType.values().length];
        cellSprite = new BufferedImage[CellType.values().length];
        setSprite();
        gameManager = toSet;
        dimensions = new Pair<>(template.cellCountX, template.cellCountY);
        cellArray = new Cell[template.cellCountX][template.cellCountY];
        timedArray = new ArrayList<>();
        resetHandler = new ResetHandler(this.gameManager, template.enemyPosArray);
        generateMap(template);
        bonusHandler = new BonusRewardHandler(this);
    }

    /**
     * Returns the GameManager for this class
     * @return GameManager
     */
    public GameManager getGameManager()
    {
        return this.gameManager;
    }

    /**
     * For every value in timedArray at time of call, calls their tick function through the common Timer interface<p>
     * Calls the tick update on BonusRewardHandler<p>
     * Finally updates sprite for exit cell upon hitting certain criteria
     */
    public void update() {
        ArrayList<Timer> outOfTime = new ArrayList<>();
        for(Timer t : timedArray) {
            t.tick();
            if (t.isEvent()) {
                outOfTime.add(t);
            }
        }
        for (Timer t : outOfTime) {
            timedArray.remove(t);
        }
        bonusHandler.tick();

        // Exit Cell states depending on amount of rewards collected
        if (MapManager.numRewards <= 0 && exitState[0]) {
            exitState[0] = false;
            cellArray[exitCellPos.getKey()][exitCellPos.getValue()].cellEnum = CellType.ExitCell_Raft;
        }
        // Might be fun to change it the ship if a new highScore was achieved
        if (gameManager.highScore < MainCharacter.getScore() && exitState[1] && MapManager.numRewards <= 0) {
            exitState[1] = false;
            cellArray[exitCellPos.getKey()][exitCellPos.getValue()].cellEnum = CellType.ExitCell_Boat;
        }
    }

    /**
     * Draws everyCell in the CellArray
     * @param drawer Graphics2D
     * @return 1 if successful
     */
    public int draw(Graphics2D drawer) {

        drawEnum(drawer);
        return 1;
    }

    /**
     * Draws everyCell in the CellArray in their position relative to the player. Draws a cells GroundEnum and CellType sprite
     * @param drawer Graphics2D
     */
    private void drawEnum(Graphics2D drawer) {
        //TODO Make it so this function will only draw cells that are on the display
        if(drawer == null)
            return;
        int cellSize = gameManager.cellSize;
        GroundType getGround = null;
        CellType getCell = null;

        int screenX;
        int screenY;

        // Order: for Y { For X {
        for (int i = 0; i < dimensions.getValue(); i++) {
            for (int j = 0; j < dimensions.getKey(); j++) {

                getGround = cellArray[j][i].groundEnum;
                getCell = cellArray[j][i].cellEnum;

                //TODO Shorten the following lines. Make into function?

                screenX = (j * gameManager.cellSize) - gameManager.mc.cellX +gameManager.mc.screenX;
                screenY = (i * gameManager.cellSize) - gameManager.mc.cellY +gameManager.mc.screenY;

                drawer.drawImage(groundSprite[(getGround.ordinal())],screenX, screenY, cellSize, cellSize, null );

                //EmptyCell has no special graphic, so we can skip the draw step in this case
                if (!(getCell == CellType.EmptyCell || getCell == CellType.WallCell) ) {
                    drawer.drawImage(cellSprite[getCell.ordinal()],screenX, screenY, cellSize, cellSize, null );
                }
            }
        }
    }

    /**
     * Static method to return a Cell from CellArray at the i, jth index
     * @param i x position
     * @param j y position
     * @return Cell
     */
    public static Cell getCell(int i, int j)
    {
        if(0 <= i && i < dimensions.getKey() && 0 <= j && j < dimensions.getValue())
            return cellArray[i][j];
        else
            return cellArray[0][0];
    }

    /**
     * from the MapTemplate enumArray uses reflection to create Cells correct cells with correct attributes using CellType
     * and GroundType enums
     * @param template
     */
    void generateMap(MapTemplate template) {
        int cellCount = 0;
        Pair<CellType, GroundType>[][] enumArray = template.enumArray;

        // getDeclaredConstructor parameter array
        Class<?>[] paramType = new Class[3];
        paramType[0] = GroundType.class;
        paramType[1] = Pair.class;
        paramType[2] = MapManager.class;

        // Order: for Y { For X {
        for (int i = 0; i < dimensions.getValue(); i++)
        {
            //System.out.println("Dimensions get value " + dimensions.getValue());
            for (int j = 0; j < dimensions.getKey(); j++)
            {
                try {

                    CellType cellEnum = enumArray[j][i].getKey();
                    GroundType groundEnum = enumArray[j][i].getValue();

                    // Catches the position of the start cell and stores in the private attribute
                    if (cellEnum == CellType.StartCell) {
                        this.startCellPos = new Pair<>(j,i);
                    }
                    if (cellEnum == CellType.ExitCell) {
                        this.exitCellPos = new Pair<>(j, i);
                    }

                    //TODO All of these items are reallocated every loop. Not necessary, Swap to outside

                    // Dynamically calls correct constructor for the kind of cell based on cellType
                    String className = cellEnum.name();
                    String qualifiedName = "org.cell." + className;
                    Class<?> toCall = Class.forName(qualifiedName);
                    ClassLoader toLoad = toCall.getClassLoader();

                    Pair<Integer, Integer> setPos = new Pair<>(j, i);

                    this.cellArray[j][i] = (Cell)(Class.forName(qualifiedName, true, toLoad)).
                            getDeclaredConstructor(paramType).newInstance(groundEnum, setPos, this);

                    //.out.println(cellArray[i][j].cellEnum.toString());
                    //System.out.println(cellArray[i][j].groundEnum.toString());
                    cellCount++;

                }
                catch (ClassNotFoundException e) { // when some class is not found it makes it an empty cell
                    this.cellArray[j][i] = new EmptyCell(GroundType.marsh, new Pair<>(i, j), this );
                    System.out.println("Cell at position: " + i + ", " + j + " is unrecognized, replaced with EmptyCell");
                }
                catch(Exception e)
                {
                    // To handle
                    /* Instantiate Exception
                     * Invocation Target Exception
                     * NoSuchMethodException
                     */

                    System.out.println("Exception in MapManager");
                    System.out.println(e.toString());
                }
            }
        }
        System.out.println("Cell count: " + cellCount);
        System.out.println("Dimensions X: "+ dimensions.getKey() + ", Y: " + dimensions.getValue());
    }

    /**
     * Automatically adds Cell sprites to the cellSprite arrayList. Searches sprites.cell for a sprite that matches the
     * same name as the cell enum. Sprite position in arrayList matches enum ordinal. 1 sprite per enum constant.
     * handles a NullPointerException if sprite cannot be found.
     */
    private void setSprite() {
        addSprites(GroundType.values(), groundSprite, "src/main/Images/ground");
        addSprites(CellType.values(), cellSprite, "src/main/Images/cell");
    }

    /**
     * Goes through Enum types and checks to see if a sprite corresponds to an enum constant. If so adds it to the index
     * of the ordinal of the enum constant. Else at the ordinal value will place an Error type cell sprite to indicate
     * something went wrong
     * @param toPass Enum array
     * @param toSet BufferedImage array
     * @param parentDirectory String that leads to the images folder
     */
    private void addSprites(Enum<?>[] toPass, BufferedImage[] toSet, String parentDirectory) {
        for (Enum<?> pass : toPass) {
            try {
                File filepath = new File(parentDirectory + "/" + pass.name());
                int directoryQuantity = SpriteReader.quantityInDir(filepath);
                BufferedImage[] toAdd = SpriteReader.getBufferedImageArray(parentDirectory + "/" + pass.name());
                for (int j = 0; j < directoryQuantity; j++) {

                    assert toAdd != null;
                    toSet[pass.ordinal()] = toAdd[j];
                }
                if (directoryQuantity == 0 && !(pass == CellType.EmptyCell | pass == CellType.WallCell)) {
                    System.out.println("hi");
                    toSet[pass.ordinal()] = setMissingSprites(pass);
                }
            } catch (NullPointerException e) {
                System.out.println("Exception in setSprite\n" + e.toString());
                toSet[pass.ordinal()] = setMissingSprites(pass);
            }
        }
    }

    /**
     * Gets placeholder sprites that will only be used if a sprite for a ground or cell enum is unable to be found
     */
    private BufferedImage setMissingSprites(Enum<?> type) {
        File filepath;
        BufferedImage toReturn = null;
        try {
            filepath = new File("src/main/Images/Error/" + type.getDeclaringClass().getSimpleName() + "Error.png");
            toReturn = ImageIO.read(filepath);

        } catch (IOException e) {
            System.out.println("IOException in setMissingSprites\n" + e.toString());

        }
        return toReturn;
    }

    /**
     * Returns Pair that stores the location of the StartCell within array
     * @return Pair (Integer, Integer)
     */
    public Pair<Integer, Integer> getStartCellPos() {
        return this.startCellPos;
    }

    /**
     * Adds an object that implements Timer to the timedArray in MapManager
     * @param       toAdd Timer to add to array
     */
    public void addToTimer(Timer toAdd) {
        timedArray.add(toAdd);
    }
}
