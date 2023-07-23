
package construction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import game.Parsers;
import game.Velocity;
import geometry.Point;
import level.Level;
import level.LevelInformation;
import sprite.Block;

/**
 * a Level Specification File Reader class.
 *
 */
public class LevelSpecificationReader {
    /**
     * use given reader(was build with the right levels Specification file path), creates and returns a list with all
     * the levels in the file .
     *
     * @param reader - the levels reader for the level Specification file path.
     * @return - a list of levels.
     * @throws Exception - "Error : Failed to add level to levelset level list".
     */
    public static List<LevelInformation> fromReader(Reader reader) throws Exception {
        LevelSpecificationReader r = new LevelSpecificationReader();
        List<LevelInformation> levelList = new ArrayList<LevelInformation>();
        // map (key = level parameters string,value = blocks and spacers positions string)
        Map<String, String> levels = r.seperateLevels(reader);
        int i = 0;
        for (Map.Entry<String, String> entry1 : levels.entrySet()) {
            try {
                levelList.add(i, r.createLevel(entry1.getKey(), entry1.getValue()));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error : Failed to add level to levelset level list");
            }
        }
        return levelList;
    }

    /**
     * reads the file inside the reader and separates each level to a different strings of level parameters and blocks
     * which will be stored inside a map as key = level parameters string and value - level blocks string.
     *
     * @param reader - the levels reader for the level Specification file path.
     * @return levelsMapStr - a map of string representation of each level parameters and blocks.
     * @throws Exception -"Error : Failed to create levelSet level - could not find file"
     * @throws Exception -"Error : Failed to create levelSet level - failed IO read file"
     */
    public Map<String, String> seperateLevels(Reader reader) throws Exception {
        Map<String, String> levelsMapStr = new LinkedHashMap<String, String>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String line = null;
            String levelParam = "";
            String blocks = "";
            while ((line = br.readLine()) != null) {
                if ((line.trim().indexOf('#') == 0) || (line.trim().isEmpty())) {
                    continue;
                }
                if (line.contains(":")) {
                    levelParam = levelParam.concat(System.lineSeparator() + line);
                } else {
                    if ((!line.contains("END_LEVEL")) && (!line.contains("START_LEVEL"))
                            && (!line.contains("END_BLOCKS")) && (!line.contains("START_BLOCKS"))) {
                        blocks = blocks.concat(System.lineSeparator() + line);
                    }
                }
                if (line.contains("END_LEVEL")) {
                    levelsMapStr.put(levelParam, blocks);
                    levelParam = "";
                    blocks = "";
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            throw new Exception("Error : Failed to create levelSet level - could not find file");
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new Exception("Error : Failed to create levelSet level - failed IO read file");
        }
        return levelsMapStr;
    }

    /**
     * gets a string representing a complete level and returns a map of key:value of each level parameter.
     *
     * @param levelParam - the string representation of the level.
     * @param blockPos - the string representation of the level blocks and spacers positions.
     * @return level - a complete level from given parameters.
     * @throws Exception - "Error : Failed to create level - missing level parameters".
     */
    public Level createLevel(String levelParam, String blockPos) throws Exception {
        Level level = null;
        String noInput = "No Input Was Given";
        String lvlName = noInput;
        List<Velocity> ballVelocityList = new ArrayList<Velocity>();
        String lvlBackground = noInput;
        int paddleSpeed = -1;
        int paddleWidth = -1;
        String blockDefPath = noInput;
        double firstBlockX = -1;
        double firstBlockY = -1;
        int rowHeight = -1;
        int blockNum = -1;
        Parsers parse = new Parsers();
        // level Parameters map
        Map<String, String> levelParamMap = parse.parseLineParameters(levelParam, System.lineSeparator(), ":");
        for (Map.Entry<String, String> entry : levelParamMap.entrySet()) {
            switch (entry.getKey()) {
            case "level_name":
                lvlName = entry.getValue();
                break;

            case "ball_velocities":
                // Map<String,String> velocityMap is a Map of <velocity angle,velocity speed>.
                Map<String, String> velocityMap = parse.parseLineParameters(entry.getValue(), " ", ",");
                for (Map.Entry<String, String> vel : velocityMap.entrySet()) {
                    double angle = parse.parseDoubleWithDefault(vel.getKey(), -99999);
                    double speed = parse.parseDoubleWithDefault(vel.getValue(), -7);
                    if ((speed < 0) || (angle == -99999)) {
                        throw new Exception("Error : Failed to create level - illegal ball velocity parameters");
                    }
                    ballVelocityList.add(Velocity.fromAngleAndSpeed(angle, speed));
                }
                break;

            case "background":
                lvlBackground = entry.getValue();
                break;

            case "paddle_speed":
                paddleSpeed = parse.parseIntWithDefault(entry.getValue(), paddleSpeed);
                break;

            case "paddle_width":
                paddleWidth = parse.parseIntWithDefault(entry.getValue(), paddleWidth);
                break;

            case "block_definitions":
                blockDefPath = entry.getValue();
                break;

            case "blocks_start_x":
                firstBlockX = parse.parseDoubleWithDefault(entry.getValue(), firstBlockX);
                break;

            case "blocks_start_y":
                firstBlockY = parse.parseDoubleWithDefault(entry.getValue(), firstBlockY);
                break;

            case "row_height":
                rowHeight = parse.parseIntWithDefault(entry.getValue(), rowHeight);
                break;

            case "num_blocks":
                blockNum = parse.parseIntWithDefault(entry.getValue(), blockNum);
                break;

            default:

                break;
            }
        }
        if ((blockNum < 0) || (paddleSpeed < 0) || (paddleWidth < 0) || (firstBlockX < 0) || (firstBlockY < 0)
                || (rowHeight < 0) || (lvlName.equals(noInput)) || (lvlBackground.equals(noInput))
                || (blockDefPath.equals(noInput)) || (ballVelocityList.isEmpty())) {
            throw new Exception("Error : Failed to create level - missing level parameters");
        }
        Point firstBlockP = new Point(firstBlockX, firstBlockY);
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockDefPath);
        if (is == null) {
            throw new Exception("Error: Failed to find Block definitions file.");
        }
        BlocksFromSymbolsFactory blocksFactory = BlocksDefinitionReader.fromReader(new InputStreamReader(is));
        List<Block> blockList = createBlocks(blocksFactory, firstBlockP, blockPos, rowHeight);
        level = new Level(lvlName, ballVelocityList, blockList, blockNum, paddleWidth, paddleSpeed, lvlBackground);
        is.close();
        return level;
    }

    /**
     * creates and returns a list of all the level blocks according to the given blocks layout (blocksPos).
     *
     * @param blocksFactory - the level blocks Factory , creates blocks with parameters associated with a certain
     *            symbol.
     * @param firstBlockP - the top - left point for the first block.
     * @param blocksPos - a string representing the level blocks and spacers layout.
     * @param rowHeight - the number of pixels in each row.
     * @return a list of the level blocks.
     * @throws Exception - "Error: Failed to create block "
     */
    public List<Block> createBlocks(BlocksFromSymbolsFactory blocksFactory, Point firstBlockP, String blocksPos,
            int rowHeight) throws Exception {
        List<Block> blocks = new ArrayList<Block>();
        String[] splitedLines = blocksPos.split(System.lineSeparator());
        int rowIndex = 0;
        String character;
        for (String line : splitedLines) {
            double blockX = firstBlockP.getX();
            int lineLen = line.trim().length();
            for (int i = 0; i < lineLen; i++) {
                double blockY = firstBlockP.getY() + (rowIndex * rowHeight);
                character = line.trim().substring(i, i + 1);
                if (blocksFactory.isSpaceSymbol(character)) {
                    blockX += blocksFactory.getSpaceWidth(character);
                }
                // create the block and add him to the blocks list.
                if (blocksFactory.isBlockSymbol(character)) {
                    Block block = blocksFactory.getBlock(character, blockX, blockY);
                    if (block == null) {
                        throw new Exception("Error: Failed to create block " + character);
                    }
                    blocks.add(block);
                    blockX += block.getCollisionRectangle().getWidth();
                }
            }
            rowIndex++;
        }
        return blocks;
    }
}
