package org.game;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Subhranil Dey
 * The MapTemplate is the foundational building block for the game
 * It is responsible for initialization of map tiles
 * The initializations are later utilized for Visuals, spawing game elements and tile behaviour.
 */
public class MapTemplate {
    private final static String fromContentRoot = "src/main/textFiles";
    private Integer levelID;
    // X: Columns, Y: Rows
    private Pair<Integer, Integer> lvlDim;
    int cellCountX; // number of columns, we can read this as input too
    int cellCountY; // number of rows, we can read this as input too
    Pair[][] enumArray;
    ArrayList<Pair<Integer, Integer>> enemyPosArray;

    /**
     * Constructor for MapTemplate class
     * @param levelPath format: LevelTypeDesignator/LevelName
     */
    public MapTemplate(String levelPath)
    {
        String address = fromContentRoot + "/" + levelPath;
        readMisc(address);

        //TODO temp fix remove vars
        cellCountX = lvlDim.getKey();
        cellCountY = lvlDim.getValue();

        enemyPosArray = new ArrayList<>();

        enumArray = new Pair[lvlDim.getKey()][lvlDim.getValue()];
        generateBasicMap();

        int[] cellType_array = readMapArray("src/main/textFiles/Level/CellType.txt");
        int[] groundType_array = readMapArray("src/main/textFiles/Level/GroundType.txt");

        System.out.println(cellType_array.length);
        init_Map(cellType_array, groundType_array);
        initEnemyPos();

    }

    /**
     * Returns the X size of map template object
     * @return Integer
     */
    public Integer getXSize() {
        return this.lvlDim.getKey();
    }

    /**
     * Reads all data in misc file and loads data into template object.
     * Assumes that relevent data immediatly follows a tag: #TAGID
     * #ENEMYPOS tag must also be the last
     * @param levelPath format: LevelTypeDesignator/LevelName
     */
    private void readMisc(String levelPath) {
        String address = levelPath + "/misc.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(address))) {

            // Goes through file in order of tag appearance and writes info to class
            if (readFileForTag(br, "#ID"))
                writeLevelId(br);
            if (readFileForTag(br, "#SIZE")) {
                writeLevelSize(br);
            }
            if (readFileForTag(br, "#ENEMYPOS")) {
                writeEnemyPos(br);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches File for a passed in tag. Returns a boolean value depending on whether tag was found.
     * @param br
     * @param tag
     * @return boolean
     */
    private boolean readFileForTag(BufferedReader br, String tag) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contentEquals(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets the leveId from the file
     * @param br Holds position of tag above line of interest.
     */
    private void writeLevelId (BufferedReader br) throws IOException {
        String ID = br.readLine();
        this.levelID = Integer.parseInt(ID);
    }
    /**
     * gets the leveId from the file
     * @param br Holds position of tag above line of interest.
     */
    private void writeLevelSize (BufferedReader br) throws IOException {
       // reads line from br then trims off all whitespaces and splits from ","
        String[] filteredLine = (br.readLine()).trim().split(",");
        Integer[] intLine = new Integer[2] ;
        // Loop through
        for(int i = 0; i < filteredLine.length; i++) {
            intLine[i] = Integer.parseInt(filteredLine[i]);
        }
        this.lvlDim = new Pair<>(intLine[0],intLine[1]);
        System.out.println(intLine[0]);
    }
    /**
     * gets the leveId from the file
     * @param br Holds position of tag above line of interest.
     */
    private void writeEnemyPos(BufferedReader br) throws IOException {

    }
    /**
     * Returns an ArrayList of Pairs of EnemySpawnSquares
     * @param levelPath
     */
    private void getEnemySpawn(String levelPath) {}


    /**
     * @param x - x axis index for cell(must be in range)
     * @param y - y axis index for cell(must be in range)
     * @return Returns a pair object with the
     *             getKey() is the CellType
     *             getValue() is the GroundType
     */
    public Pair<CellType, GroundType> getCell(int x, int y)
    {
        if((x >= 0 && x < this.lvlDim.getKey()) && ((y >= 0 && y < this.lvlDim.getValue())))
            return this.enumArray[x][y];
        else
            return this.enumArray[0][0];
    }

    /**
     * generateBasicMap() job is to set up a bare bones map for the game.
     * It establishes the 'water' border for the game map which acts as the boundary
     * and other tiles as empty tiles with Ground type set to path.
     * The method also initializes starting and end tiles for game.
     */
    void generateBasicMap()
    {
        for (int i = 0; i < this.lvlDim.getKey(); ++i)
        {
            //ArrayList<Pair<CellType, GroundType>> newRow_of_cells = new ArrayList<>();
            //Pair<CellType, GroundType>[] newRow_of_cells;
            for (int j = 0; j < this.lvlDim.getValue(); ++j)   // we ignore
            {// Originally setting everything to be a path as setting them up as empty cells, and we can change things
                Pair<CellType, GroundType> new_pair;
                if( ((i == 0) && (j == this.lvlDim.getValue() - 1)) ) {
                    new_pair = new Pair<>(CellType.StartCell, GroundType.path);
                }
                else if ( ((i == this.lvlDim.getKey() - 1) && (j == 0)) )  // idea being we leave the start and the end cell alone.
                {
                    new_pair = new Pair<>(CellType.ExitCell, GroundType.water);
                }
                else if( (i == 0) || (i == lvlDim.getKey() - 1) || (j == 0) || (j == lvlDim.getValue() - 1) ) {
                    new_pair = new Pair<>(CellType.WallCell, GroundType.water);
                }
                else {
                    new_pair = new Pair<>(CellType.EmptyCell, GroundType.path);
                }
                this.enumArray[i][j] = new_pair;
            }
            //this.enumArray.add(newRow_of_cells);
        }
    }

    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(emptyCells) and GroundType based on the combinations.
     *          Sets more weight to CellType.
     */
    private void generateEmptyCell1(int groundtype, int j, int k)
    {
        if(groundtype == 0)
        { // always a bridge
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 1)
        {   // it is a marsh
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 2)
        {//d
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 3) // never gets executed
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.water);
            this.enumArray[j][k] = cell_config;
        }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(emptyCells) and GroundType based on the combinations.
     *          Sets more weight to GroundType.
     */
    private void generateEmptyCell2(int groundtype, int j, int k)
    {
        if(groundtype == 0)
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 1)
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 2)
        { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 3)
        { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(wallCells) and GroundType based on the combinations.
     *          Sets more weight to CellType.
     */
    private void generateWallCell1(int groundtype, int j, int k)
    {
        if(groundtype == 0)
        { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 1)
        {   // it is a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.EmptyCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 2)
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 3) // only this must get executed
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.water);
            this.enumArray[j][k] = cell_config;
        }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(wallCells) and GroundType based on the combinations.
     *          Sets more weight to GroundType.
     */
    private void generateWallCell2(int groundtype, int j, int k)
    {
//        if (groundtype == 0) {
//            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.bridge);
//            this.enumArray[j][k] = cell_config;
//        }
//        else if (groundtype == 1) {
//            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.path);
//            this.enumArray[j][k] = cell_config;
//        }
//        else if (groundtype == 2) {//d
//            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.marsh);
//            this.enumArray[j][k] = cell_config;
//        }
        if (groundtype == 3) {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.WallCell, GroundType.water);
            this.enumArray[j][k] = cell_config;
        }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(StartCell) and GroundType based on the combinations.
     */
    private void generateStartCell(int groundtype, int j, int k)
    {// just leave it as is because we have already initialized it.
        if(groundtype == 0)
        { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.StartCell, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 1)
        {   // it is a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.StartCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 2)
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.StartCell, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        }
//                    else if(groundType_array[i] == 3)
//                    {   // Start cell will never be with the ground type set to wall and never gets called
//                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.StartCell, GroundType.water);
//                        this.enumArray[j][k] = cell_config;
//                    }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(ExitCell) and GroundType based on the combinations.
     */
    private void generateExitCell(int groundtype, int j, int k)
    {// we want to switch to marsh
        //System.out.println("EXIT CELL- " + j + " " + k);
        if(groundtype == 0)
        { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.ExitCell, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 1)
        {   // it is a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.ExitCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 2)
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.ExitCell, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        }
//                    else if(groundType_array[i] == 3)
//                    {
//                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.ExitCell, GroundType.water);
//                        this.enumArray[j][k] = cell_config;
//                    }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(RewardCell_regular) and GroundType based on the combinations.
     */
    private void generateRewardCell_regular(int groundtype, int j, int k)
    {
        MapManager.numRewards += 1;
        if (groundtype == 0) { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Regular, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        } else if (groundtype == 1) {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Regular, GroundType.path);
            this.enumArray[j][k] = cell_config;
        } else if (groundtype == 2) {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Regular, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        } else if (groundtype == 3) {//d
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Regular, GroundType.water);
            this.enumArray[j][k] = cell_config;
        }
    }
    /**
     * @param groundtype - The goundType for the corresponding cell
     * @param j - x coordinate
     * @param k - y coordinate
     *          Sets up the CellType(PunishmentCell) and GroundType based on the combinations.
     */
    private void generatePunishmentCell(int groundtype, int j, int k)
    {
        if(groundtype == 0)
        { // always a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.PunishmentCell, GroundType.bridge);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 1)
        {   // it is a path
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.PunishmentCell, GroundType.path);
            this.enumArray[j][k] = cell_config;
        }
        else if(groundtype == 2)
        {
            Pair<CellType, GroundType> cell_config = new Pair<>(CellType.PunishmentCell, GroundType.marsh);
            this.enumArray[j][k] = cell_config;
        }
        //else if(groundType_array[i] == 3)
//                    {
//                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.PunishmentCell, GroundType.water);
//                        this.enumArray[j][k] = cell_config;
//                    }
    }
//    private void generateBonusRewardCell(int groundtype, int j, int k)
    //                {
//                    if (groundType_array[i] == 0) { // always a path
//                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Bonus, GroundType.bridge);
//                        this.enumArray[j][k] = cell_config;
//                    } else if (groundType_array[i] == 1) {   // it is a path
//                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Bonus, GroundType.path);
//                        this.enumArray[j][k] = cell_config;
//                    } else if (groundType_array[i] == 2) {
//                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Bonus, GroundType.marsh);
//                        this.enumArray[j][k] = cell_config;
//                    }
////                    else if (groundType_array[i] == 3) {
////                        Pair<CellType, GroundType> cell_config = new Pair<>(CellType.RewardCell_Bonus, GroundType.water);
////                        this.enumArray[j][k] = cell_config;
////                    }
//                }

    /**
     * Creates a Pickup_Speed CellType, with a specified groundType that is passed in as a parameter
     * @param groundType Ordinal value of the GroundType
     * @param j X-Index point of the enumArray
     * @param k Y-Index point of the enumArray
     */
    private void generateSpeedPowerUp(int groundType, int j, int k) {
        GroundType[] array = GroundType.values();
        this.enumArray[j][k] = new Pair<>(CellType.Pickup_Speed, array[groundType]);
    }

    /**
     * Creates a Pickup_Trap CellType, with a specified groundType that is passed in as a parameter
     * @param groundType Ordinal value of the GroundType
     * @param j X-Index point of the enumArray
     * @param k Y-Index point of the enumArray
     */
    private void generateTrapPowerUp(int groundType, int j, int k) {
        GroundType[] array = GroundType.values();
        this.enumArray[j][k] = new Pair<>(CellType.Pickup_Trap, array[groundType]);
    }


    /**
     * @param cellType_array - cellType array read in from the text document
     * @param groundType_array  - groundType array read in from the text document
     *                 Requirement - Both these array are of the same size
     * Iterates through the arrays and calls appropriate generate methods for setting up
     * the enumArray elements.
     */
    public void init_Map(int[] cellType_array, int[] groundType_array)
    {
        /*The idea is to loop to call generate_cell first and then we only iterate the inner section of all our cellArray and ground array.
         * if (ground[] is 0) then continue (emptyCell, path)
         * if (ground[] is 1) then continue (emptyCell, path)
         * if (ground[] is 2) then change type to ground.
         * if (ground[] is 3) then create new pair and make it wall.
         */

        int i = 0;
        for(int j = 1; j < this.lvlDim.getKey() - 1; ++j)
        {
            for(int k = 1; k < this.lvlDim.getValue() - 1; ++k)
            {


                if(cellType_array[i] == CellType.EmptyCell.ordinal())    // empty cell
                    generateEmptyCell1(groundType_array[i], j, k);
                else if(cellType_array[i] == GroundType.water.ordinal())   // Wall Cell
                    generateWallCell1(groundType_array[i], j, k);
                else if(cellType_array[i] == 2)
                    generateEmptyCell2(groundType_array[i], j, k);
                else if(cellType_array[i] == 3)   // walls
                    generateWallCell2(groundType_array[i], j, k);
                else if(cellType_array[i] == 5)   // StartCell
                    generateStartCell(groundType_array[i], j, k);
                else if(cellType_array[i] == 6)   // Exit Cell
                    generateExitCell(groundType_array[i], j, k);
                else if(cellType_array[i] == 8)    // reward_regular
                    generateRewardCell_regular(groundType_array[i], j, k);
//                else if(cellType_array[i] == 9)    // RewardCell_bonus
//                generateBonusReward(groundType_array[i], i, j);
                else if(cellType_array[i] == 10)
                    generatePunishmentCell(groundType_array[i], j, k);
                else if(cellType_array[i] == 11)
                    generateSpeedPowerUp(groundType_array[i], j, k);
                else if (cellType_array[i] == 12)
                    generateTrapPowerUp(groundType_array[i], j, k);
                //System.out.println("i " + i + " j " + j + " k " + k);
                i++;
            }
        }
        Pair<CellType, GroundType> temp = new Pair<>(CellType.EmptyCell, GroundType.bridge);
        enumArray[this.lvlDim.getKey() - 1][1] = temp;
        enumArray[0][this.lvlDim.getValue()-2] = temp;
    }

    /**
     * Reads a text file and turns it into an int array. The text file must be of format <number>,<number>
     * @param path String from ContentRoot that leads to text file
     * @return int[]
     */
    public static int[] readMapArray(String path)
    {
        ArrayList<Integer> integers = new ArrayList<>();
        System.out.println(path);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] nums = line.trim().split(",");
                for (String num : nums) {
                    integers.add(Integer.parseInt(num.trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert List<Integer> to int[]
        int[] result = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            result[i] = integers.get(i);
        }

        return result;
    }

    /**
     * Adds Pair<Integer, Integer> of positions to the enemyPosArray
     */
    public void initEnemyPos() {
        this.enemyPosArray.add(new Pair<>(7, 35));
        this.enemyPosArray.add(new Pair<>(15, 7));
        this.enemyPosArray.add(new Pair<>(35, 27));
        this.enemyPosArray.add(new Pair<>(43, 8));
        this.enemyPosArray.add(new Pair<>(45, 20));
        this.enemyPosArray.add(new Pair<>(30, 7));
    }
}
