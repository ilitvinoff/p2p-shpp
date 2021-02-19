package com.shpp.p2p.cs.ilitvinov.assignment17.silhouettes.postsilhouettes;

import com.shpp.p2p.cs.ilitvinov.assignment17.collections.MyHashMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BinaryImage {

    private static final int NODE_VALUE = 1;

    /**
     * Create Node[][], where node[x][y] value:
     * 0 - if color of image's pixel is background color
     * 1 - otherwise.
     * And so on for each pixel of the image.
     *
     * @param path - path to image; If wrong path - throws exception.
     * @return Node[][]
     */
    public static Node[][] binaryImage(String path) {
        BufferedImage image = getImage(path);
        Integer colorBG = getColorBG(image);

        Node[][] nodes = new Node[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                nodes[x][y] = image.getRGB(x, y) == colorBG ? new Node(0, x, y) : new Node(NODE_VALUE, x, y);
            }
        }
        return nodes;
    }

    /**
     * Create BufferedImage's object.
     *
     * @param path - path to image; If wrong path - throws exception.
     * @return BufferedImage's object
     */
    public static BufferedImage getImage(String path) {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myImage;
    }

    /**
     * Determines the background color of the image, counting the most popular border color of the image.
     *
     * @param image - BufferedImage's object
     * @return Color's object
     */
    public static Integer getColorBG(BufferedImage image) {
        MyHashMap<Integer, Integer> colorMap = new MyHashMap<>();

        getColorHorizontalBG(colorMap, image, 0);
        getColorHorizontalBG(colorMap, image, image.getHeight() - 1);
        getColorVerticalBG(colorMap, image, 0);
        getColorVerticalBG(colorMap, image, image.getWidth() - 1);

        int amountOfPixels = 0;
        Integer colorBG = 0;
        for (Integer color : colorMap.keySet()) {
            if (colorMap.get(color) > amountOfPixels) {
                amountOfPixels = colorMap.get(color);
                colorBG = color;
            }
        }
        return colorBG;
    }

    /**
     * Defines from what colors image's horizontal border consist of and put their values to Map.
     *
     * @param colorMap - HashMap<Color, Integer>, where:
     *                 key - color,
     *                 value - pixel's amount of this color at the border.
     * @param image    - BufferedImage's object
     * @param borderY  - y-coordinate of the border
     */
    private static void getColorHorizontalBG(MyHashMap<Integer, Integer> colorMap, BufferedImage image, int borderY) {

        for (int x = 0; x < image.getWidth(); x++) {
            Integer color = image.getRGB(x, borderY);
            if (colorMap.containsKey(color)) {
                colorMap.put(color, colorMap.get(color) + 1);
            } else {
                colorMap.put(color, 1);
            }
        }
    }

    /**
     * Defines from what colors image's vertical border consist of and put their values to Map.
     *
     * @param colorMap - HashMap<Color, Integer>, where:
     *                 key - color,
     *                 value - pixel's amount of this color at the border.
     * @param image    - BufferedImage's object
     * @param borderX  - x-coordinate of the border
     */
    private static void getColorVerticalBG(MyHashMap<Integer, Integer> colorMap, BufferedImage image, int borderX) {

        for (int y = 0; y < image.getHeight(); y++) {
            Integer color = image.getRGB(borderX, y);
            if (colorMap.containsKey(color)) {
                colorMap.put(color, colorMap.get(color) + 1);
            } else {
                colorMap.put(color, 1);
            }
        }
    }
}
