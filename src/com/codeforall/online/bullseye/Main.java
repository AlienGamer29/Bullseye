package com.codeforall.online.bullseye;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.Target;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        Arena arena = new Arena();
        Target target = new Target(900, 300);
        System.out.println(arena.getBottom());
        System.out.println(arena.getTop());

        while (true) {

            Thread.sleep(150);
            target.update(arena);
        }
    }
}