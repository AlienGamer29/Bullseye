package com.codeforall.online.bullseye.game;

import java.io.*;


public class HighScoreManager {

    private HighScoreManager() {};


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
                writeFile.write(String.valueOf(score));
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (writeFile != null) {
                cleanUpWriter(writer);
            }
        }
    }

    private static void cleanUpWriter(BufferedWriter writer) {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getHighScore () {
        FileReader readFile = null;
        BufferedReader reader = null;

        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            String highscore = reader.readLine();

            return Integer.parseInt(highscore.trim());

        } catch (IOException e) {
            return 0;

        } finally {
            if (readFile != null && reader != null) {
                cleanUpReader(reader);
            }
        }

    }

    private static void cleanUpReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
