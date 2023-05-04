package com.example.demo;

import javafx.scene.layout.Pane;

import java.util.Random;

public class PowerUp {

    Random random = new Random();

    private Pane root;

    public PowerUp(Pane root) {
        this.root = root;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void spawnPowerUp() {
        double decision = Math.random();
        if ( decision < 0.33 ) {
            Heart heart = new Heart(random.nextInt(1000)+100, 80, 20, 20, "Heart", 1, 2);
            heart.setRoot(root);
            heart.spawn();
        } else if ( decision < 0.66 ) {
            LaserGun laserGun = new LaserGun(random.nextInt(1000)+100, 80, 20, 20, "LaserGun", 1, 2);
            laserGun.setRoot(root);
            laserGun.spawn();
        } else {
            Shield shield = new Shield(random.nextInt(1000)+100, 80, 20, 20, "Shield", 1, 2);
            shield.setRoot(root);
            shield.spawn();
        }
    }

}
