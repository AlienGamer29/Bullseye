package com.codeforall.online.bullseye;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.game.MyKeyboard;
import com.codeforall.simplegraphics.graphics.Canvas;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Canvas.setMaxX(760);
        Canvas.setMaxY(512);

        Arena arena = new Arena();

        MyKeyboard myKeyboard = new MyKeyboard();

    }
}