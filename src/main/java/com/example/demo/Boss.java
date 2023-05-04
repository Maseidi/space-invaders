package com.example.demo;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Boss extends Sprite {

    public Boss(int positionX, int positionY, int width, int height, String type, int health, double speed) {
        super(positionX, positionY, width, height, type, health, speed);
        setFill(new ImagePattern(Images.boss));
    }

    public void attackA(Pane root) {
        Sprite sprite1 = new Sprite((int)getTranslateX()+37, (int)getTranslateY()+330, 10, 10, "Attack1", 1, 3);
        sprite1.setFill(Color.YELLOW);
        Sprite sprite2 = new Sprite((int)getTranslateX()+350, (int)getTranslateY()+330, 10, 10, "Attack1", 1, 3);
        sprite2.setFill(Color.YELLOW);
        Sprite sprite3 = new Sprite((int)getTranslateX()+193, (int)getTranslateY()+286, 10, 10, "Attack1", 1, 3);
        sprite3.setFill(Color.YELLOW);
        root.getChildren().addAll(sprite1, sprite2, sprite3);
    }

    public void attackB(Pane root) {
        Sprite shot = new Sprite((int)getTranslateX()+193, (int)getTranslateY()+286, 15, 15, "Attack2", 1, 1);
        shot.setFill(Color.RED);
        root.getChildren().add(shot);
    }

}
