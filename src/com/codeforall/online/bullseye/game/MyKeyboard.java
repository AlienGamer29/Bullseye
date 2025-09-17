package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.Player;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.simplegraphics.keyboard.Keyboard;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.simplegraphics.keyboard.KeyboardHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles keyboard input for the game.
 *
 * Maps keys to player actions and game controls.
 */
public class MyKeyboard implements KeyboardHandler {

    /** SimpleGraphics keyboard wrapper. */
    private Keyboard myKeyboard;

    /** The player controlled by the keyboard. */
    private Player player;

    /** The game arena (needed for movement limits). */
    private Arena arena;

    /** Reference to the main game. */
    private Game game;

    /** Placeholder for arrows to apply cheats (if needed). */
    private List<Arrows> arrows = new ArrayList<>();

    /** True while SPACE is being held (prevents autofire). */
    private boolean spaceHeld = false;

    /**
     * Creates the keyboard handler and registers the keys.
     *
     * @param game the game instance to control
     */
    public MyKeyboard(Game game) {
        myKeyboard = new Keyboard(this);
        this.game = game;
        initKeys();
    }

    /**
     * Sets the arena and player so movement can be applied.
     *
     * @param arena the arena
     * @param player the player
     */
    public void setArenaAndPlayer(Arena arena, Player player) {
        this.arena = arena;
        this.player = player;
    }

    /**
     * Registers all keyboard events used by the game.
     *
     * UP/DOWN  -> move player
     * S        -> start game
     * R        -> reset game
     * Q        -> quit
     * SPACE    -> shoot (with cooldown)
     * W        -> cheat (woosh)
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

        KeyboardEvent restart = new KeyboardEvent();
        restart.setKey(KeyboardEvent.KEY_R);
        restart.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(restart);

        KeyboardEvent cheatcode = new KeyboardEvent();
        cheatcode.setKey(KeyboardEvent.KEY_W);
        cheatcode.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        myKeyboard.addEventListener(cheatcode);
    }

    /**
     * Handles key press events and triggers actions.
     *
     * @param keyboardEvent the event with the pressed key
     */
    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int key = keyboardEvent.getKey();
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_UP) {
            //System.out.println("Up pressed");
            player.moveUp(arena);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN) {
            //System.out.println("Down pressed");
            player.moveDown(arena);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_Q) {
            //System.out.println("Q pressed");
            System.exit(0);
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_S) {
            //System.out.println("S pressed");
            game.initGame();
            game.start();
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_R) {
            //System.out.println("R pressed");
            game.resetGame();
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE) {
            //System.out.println("Space pressed");
            if (!spaceHeld) {
                spaceHeld = true;
                game.playerShoot();
            }
        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_W) {
            //System.out.println("W pressed");
             player.woosh(arena, 5000);
                for (Arrows a : arrows) {
                    a.woosh(arena, 0);
                }
            }
        }

    /**
     * Handles key release events.
     *
     * @param keyboardEvent the event with the released key
     */
    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_SPACE){
            //System.out.println("Space released");
            spaceHeld = false;
        }

    }
}


