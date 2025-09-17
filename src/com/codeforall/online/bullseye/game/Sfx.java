package com.codeforall.online.bullseye.game;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.BufferedInputStream;

/**
 * Utility class for loading and playing sound effects.
 */
public class Sfx {

    /** The audio clip to be played. */
    private Clip clip;

    /**
     * Loads a sound from the given path.
     *
     * @param classpathPath the path to the sound file
     * @return an Sfx object with the loaded clip
     */
    public static Sfx load (String classpathPath) {
        Sfx s = new Sfx();
        try {
            InputStream raw = Sfx.class.getResourceAsStream(classpathPath);
            if (raw != null) {
                try (AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(raw))) {
                    s.clip = AudioSystem.getClip();
                    s.clip.open(in);
                }
            }

            String fsPath = "resources"  + (classpathPath.startsWith("/") ? classpathPath : "/" + classpathPath);
            try (AudioInputStream in = AudioSystem.getAudioInputStream(new java.io.File(fsPath))) {
                s.clip = AudioSystem.getClip();
                s.clip.open(in);
            }
        } catch (Exception e) {
            System.out.println("Could not load " + classpathPath + ": " + e.getClass().getSimpleName());
        }
        System.out.println("Sound not found " + classpathPath);
        return s;
    }

    /** Plays the sound once from the start. */
    public void play() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /** Plays the sound in a continuous loop. */
    public void playLoop () {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** Stops the sound and resets to the start. */
    public void stop () {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
    }

    /** Primes the sound (starts and stops quickly to preload it). */
    public void prime () {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.start();
        clip.stop();
    }

}
