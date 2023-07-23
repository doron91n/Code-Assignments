
package game;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * a Parsers class.
 *
 */
public class Parsers {

    /**
     * gets a string representing a color or image and returns the correct form of the block background.
     *
     * @param background - a string representation of a block background (color||image) .
     * @return blockBground - an object of type (color||image).
     * @throws Exception - "Error: Failed to read image file".
     */
    public Object getBackground(String background) throws Exception {
        Object blockBground = null;
        if ((background.startsWith("color(RGB(")) && (background.endsWith("))"))) {
            String col = background.substring("color(RGB(".length()).trim();
            col = col.substring(0, col.length() - 2);
            String[] parts = col.split(",");
            int r = parseIntWithDefault(parts[0].trim(), -50);
            int g = parseIntWithDefault(parts[1].trim(), -50);
            int b = parseIntWithDefault(parts[2].trim(), -50);
            if ((r <= -50) || (g <= -50) || (b <= -50)) {
                throw new RuntimeException("Unsupported color name: " + col);
            }
            blockBground = new Color(r, g, b);
        } else if ((background.startsWith("image(")) && (background.endsWith(")"))) {
            String image = background.substring("image(".length()).trim();
            image = image.substring(0, image.length() - 1);
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(image);
                if (is == null) {
                    throw new Exception("Error: Failed to read image file");
                } else {
                    blockBground = ImageIO.read(is);
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception("Error: Failed to read image file");
            }

        } else if ((background.startsWith("color(")) && (background.endsWith(")"))) {
            String col = background.substring("color(".length()).trim();
            col = col.substring(0, col.length() - 1);
            try {
                Field field = Color.class.getField(col);
                blockBground = field.get(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException("Unsupported color name: " + col);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Unsupported color name: " + col);
            }
        }

        return blockBground;
    }

    /**
     * a method for parsing a string number to int ,if parsing fails returns given default int number.
     *
     * @param number - the string to parse to int.
     * @param defaultVal - the default value to return in case parsing fails.
     * @return defaultVal - if parsing fails otherwise return (int) number.
     */
    public int parseIntWithDefault(String number, int defaultVal) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * a method for parsing a string number to double ,if parsing fails returns given default double number.
     *
     * @param number - the string to parse to double.
     * @param defaultVal - the default value to return in case parsing fails.
     * @return defaultVal - if parsing fails otherwise return (double) number.
     */
    public double parseDoubleWithDefault(String number, double defaultVal) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    /**
     * gets a string and returns a map of key:value for each of the string parameters.
     *
     * @param str - the string representation of the level.
     * @param spliter1 - the first string to split by.
     * @param spliter2 - the second string to split by.
     * @return parsedMap - a map of each of the level parameters and their values.
     */
    public Map<String, String> parseLineParameters(String str, String spliter1, String spliter2) {
        Map<String, String> parsedMap = new HashMap<String, String>();
        String[] pairs = str.split(spliter1);
        for (String pair : pairs) {
            if (pair.trim().length() == 0) {
                continue;
            }
            String[] keyValue = pair.split(spliter2);
            if (keyValue.length != 2) {
                throw new RuntimeException(
                        "Error: Failed to parse ,Incorrect Format, Missing Key - Value Pair: at line" + str);
            }
            if (keyValue[0].equals("symbol") && keyValue[1].length() > 1) {
                parsedMap.put(keyValue[0], keyValue[1].substring(0, 1));
            } else {
                parsedMap.put(keyValue[0], keyValue[1]);
            }
        }
        return parsedMap;
    }
}
