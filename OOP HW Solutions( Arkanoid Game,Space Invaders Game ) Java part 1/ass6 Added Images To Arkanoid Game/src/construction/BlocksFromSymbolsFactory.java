
package construction;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import game.Parsers;
import geometry.Point;
import sprite.Block;

/**
 * a BlocksFromSymbolsFactory class.
 *
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Map<String, String>> blocksSpecs = new HashMap<String, Map<String, String>>();
    private Map<String, Integer> spacersSpecs = new HashMap<String, Integer>();
    private Map<String, Block> blocksFromSymbols = new HashMap<String, Block>();

    /**
     * creates blocks for the level associated by their symbol .
     *
     * @param blocksSpecsi - a Map(block symbol, Map(block parameter, block parameter value) with the level blocks
     *            definitions.
     * @param spacersSpecsi - a Map(spacer symbol,spacer width) with the level spacers definitions.
     */
    public BlocksFromSymbolsFactory(Map<String, Map<String, String>> blocksSpecsi, Map<String, Integer> spacersSpecsi) {
        this.blocksSpecs = blocksSpecsi;
        this.spacersSpecs = spacersSpecsi;
        try {
            blockFromSymbol();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creates a a map (String,Block) that keys are the block symbol and the value is a Block associated to that
     * symbol.
     *
     * @throws Exception - "Error : Failed to create Block - missing Block parameters" .
     */
    public void blockFromSymbol() throws Exception {
        Map<Integer, Object> hpToColor = new HashMap<Integer, Object>();
        Map<String, Block> blocks = new HashMap<String, Block>();
        double blockHeight = -1;
        double blockWidth = -1;
        int blockHp = -1;
        String fill = null;
        String stroke = null;
        Parsers parse = new Parsers();
        // Map <block Symbol,<block parameter,parameter value>>
        for (Map.Entry<String, Map<String, String>> entry : this.blocksSpecs.entrySet()) {
            for (Map.Entry<String, String> entry1 : entry.getValue().entrySet()) {
                if (entry1.getKey().contains("fill-")) {
                    String hpStr = entry1.getKey();
                    hpStr = hpStr.substring("fill-".length());
                    int hp = parse.parseIntWithDefault(hpStr, -1);
                    Object background = parse.getBackground(entry1.getValue());
                    hpToColor.put(hp, background);
                }
                switch (entry1.getKey()) {

                case "width":
                    blockWidth = parse.parseDoubleWithDefault(entry1.getValue(), blockWidth);
                    break;

                case "height":
                    blockHeight = parse.parseDoubleWithDefault(entry1.getValue(), blockHeight);
                    break;

                case "hit_points":
                    blockHp = parse.parseIntWithDefault(entry1.getValue(), blockHp);
                    break;

                case "fill":
                    fill = entry1.getValue();
                    break;

                case "stroke":
                    stroke = entry1.getValue();
                    break;

                default:
                    break;
                }
            }
            if (((fill == null) && hpToColor.isEmpty()) || (blockHp < 0) || (blockWidth < 0) || (blockHeight < 0)) {
                throw new Exception("Error : Failed to create Block - missing Block parameters");
            }

            Point initP = new Point(-400, -400);
            Block block = new Block(initP, blockWidth, blockHeight);
            block.setBlockHP(blockHp);
            if (fill != null) {
                Object background = parse.getBackground(fill);
                if (background != null) {
                    if (background.getClass().toString().contains("Color")) {
                        block.setColor((Color) background);
                    }
                    if (background.getClass().toString().contains("Image")) {
                        block.setBackgroundImage((Image) background);
                    }
                } else {
                    throw new RuntimeException("Error: Failed to create block backgound");
                }
            }
            if (stroke != null) {
                block.setStrokeColor((Color) parse.getBackground(stroke));
            }
            if (!hpToColor.isEmpty()) {
                block.setChangingBackground(hpToColor);
            }
            blocks.put(entry.getKey(), block);
        }
        this.blocksFromSymbols = blocks;
    }

    /**
     * Return a block according to the definitions associated with symbol s. The block will be located at position
     * (xpos, ypos).
     *
     * @param s - the symbol associated with the block to be returned.
     * @param blockX - the returned block top - left point x parameter.
     * @param blockY - the returned block top - left point y parameter.
     * @return block - a block according to the definitions associated with symbol s.
     */
    public Block getBlock(String s, double blockX, double blockY) {
        Block block = null;
        if (isBlockSymbol(s) && (blockX >= 0) && (blockY >= 0)) {
            block = this.blocksFromSymbols.get(s);
            if (block != null) {
                block = block.changePosition(new Point(blockX, blockY));
            }
        }
        return block;
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s - the block symbol.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        if (isSpaceSymbol(s)) {
            for (Map.Entry<String, Integer> entry : this.spacersSpecs.entrySet()) {
                if (entry.getKey().equals(s)) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param character - the space symbol to check.
     * @return - true if s is a valid space symbol false otherwise.
     */
    public boolean isSpaceSymbol(String character) {
        if (this.spacersSpecs.containsKey(character)) {
            return true;
        }
        return false;
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s - the block symbol to check.
     * @return - true if s is a valid block symbol false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        if (this.blocksSpecs.containsKey(s)) {
            return true;
        }
        return false;
    }
}
