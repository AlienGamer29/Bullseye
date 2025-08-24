package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.game.MyKeyboard;
import com.codeforall.simplegraphics.pictures.Picture;

public class Player extends Entity {

    private static final int TOP_MARGIN = 80;
    private static final int BOTTOM_MARGIN = 80;
    private static final int ARENA_HEIGHT = 1024;

    public Player(int x, int y) {
        super(x, y);
        this.speed = 5;
        picture = new Picture(50,80, "resources/player_resized.png");
        this.picture.draw();
        this.x = 50;
        this.y = 80;

    }

    public void moveUp(Arena  arena) {
        int oldY = y;
        int top = TOP_MARGIN; //impede o player de passar o arbusto, vou mudar quando tiver os getters
        int bottom = ARENA_HEIGHT - BOTTOM_MARGIN - picture.getHeight();
        int newY = y - speed; //igual ao top margin mas para baixo

        y = Math.max(top, Math.min(newY, bottom));
        picture.translate(0, y - oldY);
    }
    public void moveDown(Arena arena) {
        int oldY = y;
        int top = TOP_MARGIN;
        int bottom = ARENA_HEIGHT - BOTTOM_MARGIN - picture.getHeight();

        int newY = y + speed;
        y = Math.max(top, Math.min(newY, bottom));

        picture.translate(0, y - oldY);
    }

    public Arrows shoot() {
        int arrowX = x + picture.getWidth();// spawn ao lado direito do player
        int arrowY = y + picture.getHeight() / 2; // spawn ao centro da imagem do player

        Arrows arrows = new Arrows(arrowX, arrowY);
        return arrows;

    }


}
