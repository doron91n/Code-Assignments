
package construction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import game.Parsers;

/**
 * a Block File Reader class.
 *
 */
public class BlocksDefinitionReader {
    /**
     * reads given block definitions file and creates blocksSpecs and spacersSpecs maps from the file.
     *
     * @param reader - the levels reader for the block Definition file path.
     * @return BlocksFromSymbolsFactory class for creating the blocks based on their symbol.
     * @throws Exception - "Error : Failed to create block - block definition file not found"
     * @throws Exception - "Error : Failed to create block - failed IO read file"
     */

    public static BlocksFromSymbolsFactory fromReader(Reader reader) throws Exception {
        Parsers parse = new Parsers();
        Map<String, Map<String, String>> blocksSpecs = new HashMap<String, Map<String, String>>();
        Map<String, Integer> spacersSpecs = new HashMap<String, Integer>();
        Map<String, String> defaultMap = new HashMap<String, String>();
        Map<String, String> bDefMap = new HashMap<String, String>();
        Map<String, String> sDefMap = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                // skip empty or comment lines
                if ((line.trim().indexOf('#') == 0) || (line.trim().isEmpty())) {
                    continue;
                }
                // Separate on each line its definition and parameters strings .
                String splitedLine;
                if (line.trim().contains("default")) {
                    splitedLine = line.substring("default".length()).trim();
                    defaultMap = parse.parseLineParameters(splitedLine, " ", ":");
                }
                if (line.trim().contains("bdef")) {
                    Map<String, String> mergedMap = new HashMap<String, String>();
                    splitedLine = line.substring("bdef".length()).trim();
                    bDefMap = parse.parseLineParameters(splitedLine, " ", ":");
                    mergedMap.putAll(defaultMap);
                    mergedMap.putAll(bDefMap);
                    blocksSpecs.put(mergedMap.get("symbol"), mergedMap);
                }
                if (line.trim().contains("sdef")) {
                    splitedLine = line.substring("sdef".length()).trim();
                    sDefMap = parse.parseLineParameters(splitedLine, " ", ":");
                    spacersSpecs.put(sDefMap.get("symbol"), parse.parseIntWithDefault(sDefMap.get("width"), -5));
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            throw new Exception("Error : Failed to create block - block definition file not found");
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new Exception("Error : Failed to create block -  failed IO read file");
        }
        return new BlocksFromSymbolsFactory(blocksSpecs, spacersSpecs);
    }

}
