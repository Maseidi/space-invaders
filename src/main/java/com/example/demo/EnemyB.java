package com.example.demo;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class EnemyB extends Sprite {

    public EnemyB(int positionX, int positionY, int width, int height, String type, int health, double speed) {
        super(positionX, positionY, width, height, type, health, speed);
        setFill(new ImagePattern(Images.enemyB));
    }

    @Override
    public void shoot(Pane root) {
        Sprite shot = new Sprite((int)getTranslateX()+30, (int)getTranslateY()+20, 5, 5, "EnemyB bullet", 1, 3);
        shot.setFill(Color.RED);
        root.getChildren().add(shot);
    }

}
