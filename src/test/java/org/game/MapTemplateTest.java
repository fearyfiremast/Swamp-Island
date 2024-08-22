package org.game;

import javafx.util.Pair;
import org.extras.CellType;
import org.extras.GroundType;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the MapTemplate class
 * @author Subhranil Dey
 */
class MapTemplateTest {
    MapTemplate testTemplate;
    @BeforeEach
    void std_mapTemplate_create()
    {
        testTemplate = new MapTemplate(50, 50);
    }
    @Test
    void generateEmptyCell_coordinate_check()
    { // we check if our generate map x and y are 50

        assertEquals(50, testTemplate.cellCountX);
        assertEquals(50, testTemplate.cellCountY);
    }
    @Test
    void generateEmptyCell_exit_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {6};
        int[] x2 = {1};
        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.ExitCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void generateEmptyCell_start_check()
    {
        //MapTemplate testTemplate = new MapTemplate();

        Pair<CellType, GroundType> expected = new Pair<>(CellType.StartCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(0, testTemplate.cellCountY-1));
    }
    @Test
    void generateEmptyCell_boundary_check()
    {   // The test will fail because of the two extra empty cells created to connect the start and exit to the main island.
        testTemplate.generateBasicMap();
        for(int i = 1; i < testTemplate.cellCountX - 1; ++i)
        {
            assertEquals(CellType.WallCell, testTemplate.getCell(0, i).getKey());
            assertEquals(GroundType.water, testTemplate.getCell(0, i).getValue());

            assertEquals(CellType.WallCell, testTemplate.getCell(i-1, 0).getKey());
            assertEquals(GroundType.water, testTemplate.getCell(i-1, 0).getValue());
        }
    }

    @Test
    void generateEmptyCell_inner_check()
    {   // Will also fail
        //testTemplate = new MapTemplate(50, 50);
        testTemplate.generateBasicMap();
        for(int i = 1; i < testTemplate.cellCountX-1; ++i)
        {
            for(int j = 1; j < testTemplate.cellCountY-1; ++j)
            {
                assertEquals(CellType.EmptyCell, testTemplate.getCell(i, j).getKey());
                assertEquals(GroundType.path, testTemplate.getCell(i, j).getValue());
            }
        }
    }

    @Test
    void init_Map_emptyBridge_check()
    { // we just have to test all combinations of CellTypes and GroundType
        //  ww ww ex
        //  ww eb ww
        //  st ww ww
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {0};
        int[] x2 = {0};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.bridge);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_emptyMarsh_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {0};
        int[] x2 = {1};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.marsh);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_emptyPath_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {0};
        int[] x2 = {2};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_emptyWater_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {0};
        int[] x2 = {3};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.water);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_wallBridge_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {1};
        int[] x2 = {0};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.WallCell, GroundType.bridge);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_wallPath_check()
    {   // The map is designed in a way that if the ground type suggests it should
        // be a path then then cell would be treated as a path
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {1};
        int[] x2 = {1};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_wallMarsh_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {1};
        int[] x2 = {2};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.WallCell, GroundType.marsh);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_wallWater_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {1};
        int[] x2 = {3};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.WallCell, GroundType.water);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_MarshBridge_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {2};
        int[] x2 = {0};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.bridge);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_MarshMarsh_check()
    {
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {2};
        int[] x2 = {1};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_MarshPath_check()
    {   // Regardless of the cellType the map will be initialized based on the ground type.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {2};
        int[] x2 = {2};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.marsh);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_marshWater_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {2};
        int[] x2 = {3};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.EmptyCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_startBridge_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {5};
        int[] x2 = {0};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.StartCell, GroundType.bridge);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_startPath_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {5};
        int[] x2 = {1};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.StartCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_startMarsh_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {5};
        int[] x2 = {2};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.StartCell, GroundType.marsh);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

//    @Test
//    void init_Map_startWater_check()
//    {
//        // If it is a path then we regardless of anything make it a empty cell with path.
//        testTemplate = new MapTemplate(3, 3);
//
//        int[] x1 = {5};
//        int[] x2 = {3};
//
//        testTemplate.init_Map(x1, x2);
//
//        Pair<CellType, GroundType> expected = new Pair<>(CellType.StartCell, GroundType.water);
//
//        assertEquals(expected, testTemplate.getCell(1, 1));
//    }

    @Test
    void init_Map_exitBridge_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {6};
        int[] x2 = {0};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.ExitCell, GroundType.bridge);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_exitPath_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {6};
        int[] x2 = {1};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.ExitCell, GroundType.path);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

    @Test
    void init_Map_exitMarsh_check()
    {
        // If it is a path then we regardless of anything make it a empty cell with path.
        testTemplate = new MapTemplate(3, 3);

        int[] x1 = {6};
        int[] x2 = {2};

        testTemplate.init_Map(x1, x2);

        Pair<CellType, GroundType> expected = new Pair<>(CellType.ExitCell, GroundType.marsh);

        assertEquals(expected, testTemplate.getCell(1, 1));
    }

//    @Test
//    void init_Map_exitWater_check()
//    {
//        // If it is a path then we regardless of anything make it a empty cell with path.
//        testTemplate = new MapTemplate(3, 3);
//
//        int[] x1 = {6};
//        int[] x2 = {3};
//
//        testTemplate.init_Map(x1, x2);
//
//        Pair<CellType, GroundType> expected = new Pair<>(CellType.ExitCell, GroundType.water);
//
//        assertEquals(expected, testTemplate.getCell(1, 1));
//    }

    @Test
    void readMapArray_check() throws IOException
    {
        File file = File.createTempFile("temp", ".txt");

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("1, 2, 3, 4, 5");
        }

        int[] readArray = MapTemplate.readMapArray(file.getAbsolutePath());

        for (int i = 0; i < 5; ++i) {
            assertEquals(i + 1, readArray[i]);
        }

        file.delete();
    }
}