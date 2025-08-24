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




/*
If the Player manages their own collection of arrows and can choose which arrow to shoot, the best design is a method that lets you specify which arrow to shoot, but still ensures that only arrows owned by the player can be shot.

1. How to Design This?
You’ll likely want:

The Player class to have an internal collection of arrows (like an ArrayList<Arrow> or Arrow[]).
A method that allows you to specify which arrow (by reference or index), and removes it from the player's collection when shot.
Method Signature
The most flexible and clear approach is:

public Arrow shoot(Arrow arrow);
Copy
The parameter is the specific Arrow object you want to shoot.
The method checks if the player actually owns that arrow, removes it from their inventory, and returns it (so it can be launched in the game world).
2. Example Implementation
import java.util.ArrayList;

public class Player {
    private ArrayList<Arrow> arrows = new ArrayList<>();

    // Add arrow to player's inventory
    public void addArrow(Arrow arrow) {
        arrows.add(arrow);
    }

    // Shoot a specific arrow
    public Arrow shoot(Arrow arrow) {
        if (arrows.contains(arrow)) {
            arrows.remove(arrow); // Remove from player's hand
            return arrow;         // Return for launching in game world
        }
        return null; // Or throw exception if preferred
    }
}
Copy
Usage:

Player player = new Player();
Arrow fireArrow = new Arrow("fire");
Arrow iceArrow = new Arrow("ice");
player.addArrow(fireArrow);
player.addArrow(iceArrow);

Arrow shot = player.shoot(fireArrow); // Shoots fire arrow
Copy
3. Alternative: Select by Index
If you want to select by index:

public Arrow shoot(int index) {
    if (index >= 0 && index < arrows.size()) {
        return arrows.remove(index);
    }
    return null;
}
Copy
4. Summary Table
Signature	Allows choice?	Checks ownership?	Removes from inventory?	Returns shot arrow?
public Arrow shoot()	❌	N/A	✔️	✔️
public void shoot(Arrow)	✔️	❌*	❌*	❌/✔️
public Arrow shoot(Arrow)	✔️	✔️	✔️	✔️
* Only if you add ownership check and removal logic.

5. Best Practice
Use:

public Arrow shoot(Arrow arrow)
Copy
with an internal check for ownership and removal.

Diagram
Player's Arrows: [A1, A2, A3]
call player.shoot(A2)
Result:
- Returns A2
- Player's Arrows: [A1, A3]
Let me know if you'd like a more advanced version (e.g., by type, by name, etc.)!
 */
