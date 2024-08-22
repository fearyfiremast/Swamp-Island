package org.animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Static class that contains methods to determine number of files within a directory as well as to return a BufferedImage[]
 */
public class SpriteReader {

    /**
     * Returns an int that corresponds to the number of files within a folder
     * @param filepath String: Path from content root to folder
     * @return int: Corresponds to number of files within directory
     */
    public static int quantityInDir(String filepath) {
        int toReturn = 0;
        File toRead = new File(filepath);
        try {
            toReturn = Objects.requireNonNull(toRead.list()).length;
        } catch (NullPointerException e) {
            //e.printStackTrace();
            //System.out.println("Invalid filepath");
        }
        return toReturn;
    }

    /**
     * Returns an int that corresponds to the number of files within a folder
     * @param file directory. Will determine the number of files and Directories within
     * @return int: Corresponds to number of files within directory
     */
    public static int quantityInDir(File file) {
        int toReturn = 0;
        try {
            toReturn = Objects.requireNonNull(file.list()).length;
        } catch (NullPointerException e) {
            //e.printStackTrace();
            //System.out.println("Invalid filepath");
        }
        return toReturn;
    }

    /**
     * This function will search in a director and return all its contents as a BufferedImage array
     * @param filepath String that represents the path from content root to the file folder that is to be extracted
     *                 as a BufferedImage array
     * @return BufferedImage[]
     */
    public static BufferedImage[] getBufferedImageArray(String filepath) {
        int picNum = quantityInDir(filepath);
        if (picNum == 0) {
            return  null;
        }
        BufferedImage[] toReturn = new BufferedImage[picNum];
        File directory = new File(filepath);
        File[] list = directory.listFiles();
        for (int i = 0; i < picNum; i++) {
            try {
                assert list != null;
                toReturn[i] = ImageIO.read(list[i]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("IllegalArgumentException in getBufferedImageArray");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IOException error in getBufferedImageArray");
            }
        }
        return toReturn;
    }
}
