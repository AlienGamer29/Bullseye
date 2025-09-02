package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.Player;
import com.codeforall.simplegraphics.keyboard.Keyboard;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.simplegraphics.keyboard.KeyboardHandler;

public class MyKeyboard implements KeyboardHandler {
    private Keyboard myKeyboard;
    private Player player;
    private Arena arena;
    private Game game;
    private GameState gameState;

    private boolean spaceHeld = false;

    public MyKeyboard(Player player, Arena arena, Game game) { // associar o Player dentro do MyKeyboard
        myKeyboard = new Keyboard(this);

        this.player = player;
        this.arena = arena;
        this.game = game;
        initKeys();

    }

    /*
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

     */

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

        KeyboardEvent spacePress = new KeyboardEvent();
        spacePress.setKey(KeyboardEvent.KEY_SPACE);
        spacePress.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(spacePress);

        KeyboardEvent spaceRelease = new KeyboardEvent();
        spaceRelease.setKey(KeyboardEvent.KEY_SPACE);
        spaceRelease.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

        myKeyboard.addEventListener(spaceRelease);

        KeyboardEvent reset = new KeyboardEvent();
        reset.setKey(KeyboardEvent.KEY_R);
        reset.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(reset);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int key = keyboardEvent.getKey();
        if(keyboardEvent.getKey() == KeyboardEvent.KEY_UP){
            player.moveUp(arena);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN) {
            player.moveDown(arena);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_Q) {
            System.exit(0);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_S){
            game.initGame();
        } else if(keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            if (!spaceHeld) {
                spaceHeld = true;
                game.playerShoot();
            }
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_R) {
            System.out.println("R key pressed");
            if (game.isGameOver()) {
                System.out.println("Game over - let's restart");
                    game.reset();
                    try {
                        game.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
               }
            }
        }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE){
            spaceHeld = false;
        }

    }
}


