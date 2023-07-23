
/**
 * ColorUtils.java
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.paint.Color;

/**
 * ColorUtils class - handles player Colors.
 */
public class ColorUtils {
    // map <Color name,Color> holding available colors
    private static final Map<String, Color> COLORS = new HashMap<String, Color>() {
        {
            put("BLACK", Color.BLACK);
            put("WHITE", Color.WHITE);
            put("BLUE", Color.BLUE);
            put("RED", Color.RED);
            put("BLUEVIOLET", Color.BLUEVIOLET);
            put("GOLD", Color.GOLD);
            put("DEEPPINK", Color.DEEPPINK);
            put("LIMEGREEN", Color.LIMEGREEN);
            put("ORANGERED", Color.ORANGERED);
        }
    };

    /**
     * Function: getColorsNamesList
     * Description: returns a list of all available player colors names.
     *
     * @param void.
     * @return color_list - a list of all available player colors names.
     */
    public static ArrayList<String> getColorsNamesList() {
        ArrayList<String> color_list = new ArrayList<String>();
        Iterator<Entry<String, Color>> it = COLORS.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Color> pair = it.next();
            color_list.add(pair.getKey());
        }
        return color_list;
    }

    /**
     * Function: getColorNamefromColor
     * Description: returns a the name of the given color.
     *
     * @param color - the color whose name we want.
     * @return color_name - the given color, color name.
     */
    public static String getColorNamefromColor(Color color) {
        Iterator<Entry<String, Color>> it = COLORS.entrySet().iterator();
        String color_name = "Not Found";
        while (it.hasNext()) {
            color_name = "";
            Entry<String, Color> pair = it.next();
            if (pair.getValue().equals(color)) {
                return pair.getKey();
            }
        }
        return color_name;
    }

    /**
     * Function: getColorFromColorName
     * Description: returns the color that Corresponds with the given color name.
     *
     * @param color_name - the color name we want to get the color for.
     * @return ret_color - the color that Corresponds with the given color name.
     */
    public static Color getColorFromColorName(String color_name) {
        Color ret_color = Color.AQUA;
        Iterator<Entry<String, Color>> it = COLORS.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Color> pair = it.next();
            if (pair.getKey().equals(color_name.trim())) {
                ret_color = pair.getValue();
            }
        }
        return ret_color;
    }
}