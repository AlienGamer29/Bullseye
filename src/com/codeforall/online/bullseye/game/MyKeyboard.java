package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.Arrows;
import com.codeforall.online.bullseye.playables.Player;
import com.codeforall.simplegraphics.keyboard.Keyboard;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.simplegraphics.keyboard.KeyboardHandler;

public class MyKeyboard implements KeyboardHandler {
    private Keyboard myKeyboard;
    private Player player;

    public MyKeyboard(Player player) { // associar o Player dentro do MyKeyboard
        myKeyboard = new Keyboard(this);

        initKeys();
        this.player = player;
    }

    public void initKeys(){
        KeyboardEvent UP = new KeyboardEvent();
        UP.setKey(KeyboardEvent.KEY_UP);
        UP.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(UP);

        KeyboardEvent DOWN = new KeyboardEvent();
        DOWN.setKey(KeyboardEvent.KEY_DOWN);
        DOWN.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(DOWN);

        KeyboardEvent start = new KeyboardEvent();
        start.setKey(KeyboardEvent.KEY_S);
        start.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(start);

        KeyboardEvent quit = new KeyboardEvent();
        quit.setKey(KeyboardEvent.KEY_Q);
        quit.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(quit);

        KeyboardEvent release = new KeyboardEvent();
        release.setKey(KeyboardEvent.KEY_SPACE);
        release.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

        myKeyboard.addEventListener(release);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_UP){
            player.moveUP();
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN) {
            player.moveDOWN();
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_Q) {
            System.exit(0);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_S){

        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            player.shoot();
        }
    }
}


/*
import org.academiadecodigo.simplegraphics.keyboard.*;

public class Game implements KeyboardHandler {

    private boolean started = false;

    public static void main(String[] args) {
        Game game = new Game();
        game.initKeys();
    }

    private void initKeys() {
        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent startEvent = new KeyboardEvent();
        startEvent.setKey(KeyboardEvent.KEY_SPACE);
        startEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(startEvent);
    }

    @Override
    public void keyPressed(KeyboardEvent e) {
        if (!started && e.getKey() == KeyboardEvent.KEY_SPACE) {
            started = true;
            startGame();
        }
    }

    @Override
    public void keyReleased(KeyboardEvent e) {
        // Not needed for this example
    }

    private void startGame() {
        System.out.println("Game started!");
        // Your game logic here
    }
}

 */
