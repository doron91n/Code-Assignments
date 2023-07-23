package game;

/**
 * a ScoreInfo class.
 *
 */
public class ScoreInfo implements SerializableObj<ScoreInfo> {
    private int playerScore;
    private String playerName;

    /**
     * collect each player game score information.
     *
     * @param name - the player name.
     * @param score - the player final game score.
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }

    /**
     * @return return the score info player name.
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * @return return the score info player final score.
     */
    public int getScore() {
        return this.playerScore;
    }

    /**
     * @return a String format of the scoreInfo.
     */
    @Override
    public String serialize() {
        return new String("ScoreInfo: playerName: /./ " + getName() + " /./ playerScore: /./ " + getScore() + " /./ ");
    }

    /**
     * translate the given string into a ScoreInfo object.
     *
     * @param s - a string format of the scoreInfo.
     * @return a Object type of the scoreInfo , translated from the given string (s).
     * @throws Exception - Deserialization Exception , failed to deserialize the given string.
     */
    @Override
    public ScoreInfo deserialize(String s) throws Exception {
        String[] splited = s.split(" /./ ");
        int score = -1;
        try {
            score = Integer.parseInt(splited[3]);
        } catch (Exception e) {
            ;
        }
        return new ScoreInfo(splited[1], score);
    }
}