package com.example.demo;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class Shield extends Sprite  {

    private Pane root;

    public Shield(int positionX, int positionY, int width, int height, String type, int health, double speed) {
        super(positionX, positionY, width, height, type, health, speed);
        setFill(new ImagePattern(Images.shield));
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void spawn() {
        root.getChildren().add(this);
    }

}
