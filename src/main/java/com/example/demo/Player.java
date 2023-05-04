package com.example.demo;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Player extends Sprite {

    public Player(int positionX, int positionY, int width, int height, String type, int health, double speed) {
        super(positionX, positionY, width, height, type, health, speed);
        setFill(new ImagePattern(Images.player));
    }

    @Override
    public void shoot(Pane root) {
        Sprite shot;
        if ( Game.laserGun ) {
            shot = new Sprite((int)getTranslateX()+30, (int)getTranslateY()+20, 5, 100, "Player bullet", 5, 100);
            shot.setFill(Color.ORANGE);
        } else {
            shot = new Sprite((int)getTranslateX()+30, (int)getTranslateY()+20, 5, 5, "Player bullet", 1, 3);
            shot.setFill(Color.GREEN);
        }
        root.getChildren().add(shot);
    }

}
