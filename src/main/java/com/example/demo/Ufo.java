package com.example.demo;

import javafx.scene.paint.ImagePattern;

public class Ufo extends Sprite {

    public Ufo(int positionX, int positionY, int width, int height, String type, int health, double speed) {
        super(positionX, positionY, width, height, type, health, speed);
        setFill(new ImagePattern(Images.ufo));
    }

}
