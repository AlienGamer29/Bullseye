package com.codeforall.online.bullseye;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.Target;
import com.codeforall.online.bullseye.playables.TargetFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        List<Target> targets = new ArrayList<>();
        int numberOfTargets = 10;

        Arena arena = new Arena();


        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }



        //Target target = new Target(850, 594);
        //Target target1 = new Target(850, 250);
        //Target target2 = new Target(800, 250);


        while (true) {

            Thread.sleep(16);


            for (Target t : targets) {
                t.update(arena);
            }



            //target.update(arena);
            //target1.update(arena);
            //target2.update(arena);
        }
    }
}