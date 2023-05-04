package com.example.demo;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {

    protected final String type;

    protected int health;

    protected double speed;

    public Sprite(int positionX, int positionY, int width, int height, String type, int health, double speed) {
        super(width, height);
        this.type = type;
        setTranslateX(positionX);
        setTranslateY(positionY);
        setHealth(health);
        setSpeed(speed);
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void moveLeft(int multiplier) {
        if ( getTranslateX() > 30 ) {
            setTranslateX(getTranslateX() - multiplier*speed);
        }
    }

    public void moveRight(int multiplier) {
        if ( getTranslateX() < 1180 ) {
            setTranslateX(getTranslateX() + multiplier*speed);
        }
    }

    public void moveUp() {
        setTranslateY(getTranslateY()-speed);
    }

    public void moveDown() {
        setTranslateY(getTranslateY()+speed);
    }

    public void moveOneBlockDown() {
        setTranslateY(getTranslateY()+50);
    }

    public void goToDestination(double x1, double y1, double x2, double y2) {
        double differenceX = x2 - x1;
        double differenceY = y2 - y1;
        setTranslateX(getTranslateX()+(differenceX/differenceY)*speed);
        setTranslateY(getTranslateY()+speed);
    }

    public void shoot(Pane root) {}
}
