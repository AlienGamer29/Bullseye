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
                writer.write(String.valueOf(score));
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (writeFile != null) {
                cleanUp(writer);
            }
        }
    }

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

    private static void cleanUp(Closeable resouce) {
        try {
            resouce.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
