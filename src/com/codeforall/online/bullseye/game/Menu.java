package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.simplegraphics.pictures.Picture;

public class Menu {
    private MyKeyboard myKeyboard;
    private Text menuText;
    private Picture picture;

    public Menu(MyKeyboard keyboard) {

        this.myKeyboard = keyboard;

        picture = new Picture(0, 0, "resources/background.jpeg");
        picture.grow(-256, -128); // 1534-1024= 512/2 = 256, 1024-768 = 256/2= 128
        picture.translate(-256, -128); // volta a encostar ao (0,0)

        width = picture.getWidth();
        height = picture.getHeight();

        //Ajusta o canvas
        Canvas.setMaxX(width - 10);
        Canvas.setMaxY(height - 10);

        picture.draw();

        menuText = new Text(picture.getMaxY()/2, picture.getY()/2, "Press S to start"); // operacionalizar S to start
        menuText.grow(100, 200);
        menuText.setColor(Color.BLACK);
        menuText.draw();

        myKeyboard.keyPressed(KeyboardEvent.KEY_S);
    }
    public void init(){
    }
}
