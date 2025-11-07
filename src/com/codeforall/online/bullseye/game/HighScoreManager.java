package com.codeforall.online.bullseye.game;

import java.io.*;

/**
 * Manages saving and loading the highest score of the game.
 */
public class HighScoreManager {

    /** Private constructor to prevent instantiation. */
    private HighScoreManager() {};

    /**
     * Saves a new high score to the file.
     *
     * @param score the score to save
     */
    public static void saveHighScore(int score) {

        File scoreFile = new File("highscore.dat");
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;


        try {
            writeFile = new FileWriter(scoreFile);
            writer = new BufferedWriter(writeFile);

            if (score != -1) {
                writer.write(String.valueOf(score));
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (writer != null) {
                cleanUp(writer);
            }
        }
    }

    /**
     * Reads and returns the saved high score.
     *
     * @return the high score, or 0 if the file could not be read
     */
    public static int getHighScore () {
        BufferedReader reader = null;

        try {
            FileReader readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            String highscore = reader.readLine();

            return Integer.parseInt(highscore.trim());

        } catch (IOException e) {
            return 0;

        } finally {
            if (reader != null) {
                cleanUp(reader);
            }
        }

    }

    /**
     * Closes a stream safely.
     *
     * @param resource the resource to close
     */
    private static void cleanUp(Closeable resource) {
        try {
            resource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
