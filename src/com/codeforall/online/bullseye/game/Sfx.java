package com.codeforall.online.bullseye.game;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.BufferedInputStream;

public class Sfx {

    private Clip clip;

    private Sfx () {
        this.clip = clip;
    }

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

    public void play() {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void playLoop () {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop () {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
    }

    public void pause () {
        if (clip == null) return;
        clip.stop();
    }

    public void resume () {
        if (clip == null) return;
        clip.start();
    }

    public void setVolume () {

    }

    public void prime () {
        if (clip == null) return;
        clip.setFramePosition(0);
        clip.start();
        clip.stop();
    }

}
