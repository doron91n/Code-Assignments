package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import biuoop.KeyboardSensor;
import menu.ShowHiScoresTask;

/**
 * a HighScoresTable class.
 *
 */
public class HighScoresTable implements SerializableObj<HighScoresTable> {
    private List<ScoreInfo> scoreList;
    private int listSize;

    /**
     * Create an empty high-scores table with the specified size.
     *
     * @param size -the number of top scores the table will holds up.
     */
    public HighScoresTable(int size) {
        this.scoreList = new ArrayList<ScoreInfo>(size);
        this.listSize = size;
    }

    /**
     * Create an empty high-scores table with a size of 5.
     */
    public HighScoresTable() {
        this.scoreList = new ArrayList<ScoreInfo>(5);
        this.listSize = 5;
    }

    /**
     * Add a high-score to the high score table.
     *
     * @param score - the player score to be added to this high score table.
     */
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        this.scoreList = this.getHighScores();
        if (rank <= this.listSize) {
            if (this.scoreList.size() == this.listSize) {
                this.scoreList.remove(this.listSize - 1);
            }
            this.scoreList.add(score);
        }
    }

    /**
     * @return the high score table size,the number of top player scores it can hold.
     */
    public int size() {
        return this.listSize;
    }

    /**
     * returns a sorted list of the players scores, such that the highest scores come first.
     *
     * @return scoreListCopy - a sorted copy of the list with the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        List<ScoreInfo> scoreListCopy = new ArrayList<ScoreInfo>(this.scoreList);
        // sort the ScoreListCopy in descending order using the provided score compare method.
        Collections.sort(scoreListCopy, new Comparator<ScoreInfo>() {
            @Override
            public int compare(ScoreInfo score1, ScoreInfo score2) {
                if (score1.getScore() > score2.getScore()) {
                    return -1;
                }
                if (score1.getScore() == score2.getScore()) {
                    return 0;
                }
                // if (score1.getScore() < score2.getScore())
                return 1;
            }
        });
        return scoreListCopy;
    }

    /**
     * return the rank of the given score among the scores in this top scores table.
     * Rank 1 means the score will be highest on the list,Rank `size` means the score will be lowest,Rank > `size` means
     * the score is too low and will not be added to the list.
     *
     * @param score - the score we want to get the rank for among the top score table.
     * @return the rank of the current score: where will it be on the list if added?
     */
    public int getRank(int score) {
        int rank = 1;
        if (this.getHighScores().isEmpty()) {
            return rank;
        }
        for (int i = 1; i < this.getHighScores().size() + 1; i++) {
            if (this.getHighScores().get(i - 1).getScore() >= score) {
                rank = i + 1;
            }
        }
        return rank;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreList.clear();
    }

    /**
     * Load table data from given file (filename),Current table data is cleared.
     *
     * @param filename - the given file to load score table data from.
     * @throws IOException - "Error - the load from a file has failed".
     */
    public void load(File filename) throws IOException {
        String s = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                s = s.concat(System.lineSeparator() + line);
            }
        }
        try {
            HighScoresTable h = this.deserialize(s);
            this.clear();
            this.scoreList = new ArrayList<ScoreInfo>(h.scoreList);
        } catch (Exception e) {
            throw new IOException("Error - the load from a file has failed");
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename - the given file to save score table data to.
     * @throws IOException - "Error - the save to a file has failed".
     */
    public void save(File filename) throws IOException {
        BufferedWriter writer = null;
        try {
            String s = this.serialize();
            // create the save file only if the string is not empty(no scores in the table).
            if (!s.equals("")) {
                writer = new BufferedWriter(new FileWriter(filename));
                writer.write(s);
            }
        } catch (IOException e) {
            throw new IOException("Error - the save to a file has failed");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                ;
            }
        }
    }

    /**
     * Read a table from file and return it.If the file does not exist, or there is a problem with reading it, an empty
     * table is returned.
     *
     * @param filename - the given file to read score table data from.
     * @return a score table from given file.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable h = new HighScoresTable();
        try {
            h.load(filename);
        } catch (IOException e) {
            ;
        }
        return h;
    }

    /**
     * @return a String format of the Serializable Object (HighScoresTable).
     */
    @Override
    public String serialize() {
        List<ScoreInfo> scoreListt = this.getHighScores();
        String s = "";
        if (!scoreListt.isEmpty()) {
            s = new String("HighScore Table:" + "   Table size: .'." + this.size() + ".'.");
            for (ScoreInfo score : scoreListt) {
                s = s.concat(System.lineSeparator() + score.serialize());
            }
        }
        return s;
    }

    /**
     * translate the given string into a Serializable object (HighScoresTable).
     *
     * @param s - a string format of the object.
     * @return a Serializable object (HighScoresTable) , translated from the given string (s).
     * @throws Exception - Deserialization Exception , failed to deserialize the given string.
     */
    @Override
    public HighScoresTable deserialize(String s) throws Exception {
        String[] splitedForSize = s.split(".'.");
        String[] splited = s.split(System.lineSeparator());
        int size = Integer.parseInt(splitedForSize[1]);
        HighScoresTable h = new HighScoresTable(size);
        for (int i = 1; i < splited.length; i++) {
            try {
                ScoreInfo score = new ScoreInfo("null", -300);
                score = score.deserialize(splited[i]);
                h.add(score);
            } catch (Exception e) {
                ;
            }
        }
        return h;
    }

    /**
     * @return creates and returns a HighScoresAnimation for this High Scores table.
     */
    public HighScoresAnimation createAnimation() {
        return new HighScoresAnimation(this);
    }

    /**
     * creates and returns a task that displays this High Scores Table.
     *
     * @param runner - the Animation Runner to run this high score display animation.
     * @param keyboard - the animation Keyboard Sensor.
     * @return a task for displaying this High Scores Table.
     */
    public ShowHiScoresTask createShowScoresTask(AnimationRunner runner, KeyboardSensor keyboard) {
        return new ShowHiScoresTask(runner, this.createAnimation(), keyboard);
    }
}
