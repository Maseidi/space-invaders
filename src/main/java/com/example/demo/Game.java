package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Application {
    
    Random random = new Random();

    private Difficulty difficulty;

    public Game(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private boolean ufoSpawned = false;

    private Pane root = new Pane();

    private double enemyATimer = 0;

    private double enemyBTimer = 0;

    private double bossWaiting = 0;

    private double bossTimer = 0;

    private int enemyMovementDecision = 1;

    private int bossMovementDecision = random.nextInt(8)+1;

    private int totalEnemies = 0;

    private int round = 0;

    private double points = 0;

    private boolean gameOver = false;

    private boolean gamePaused = false;

    private boolean gameWon = false;

    public static boolean survival = false;

    public static boolean laserGun = false;

    private int laserGunTimer = 0;

    private boolean immunity = false;

    private int immunityTimer = 0;

    public Text getStageText() {
        Text stageText;
        if ( round % 10 != 0 ) {
            stageText = new Text("Round " + round);
        } else {
            stageText = new Text("Boss Round");
        }
        stageText.setTranslateX(640);
        stageText.setTranslateY(30);
        stageText.setScaleX(3);
        stageText.setScaleY(3);
        stageText.setFill(Color.RED);
        return stageText;
    }

    public Text getPlayerLives() {
        Text playerLives = new Text("Lives : " + player.health);
        playerLives.setTranslateX(60);
        playerLives.setTranslateY(20);
        playerLives.setScaleX(3);
        playerLives.setScaleY(3);
        playerLives.setFill(Color.GREENYELLOW);
        return playerLives;
    }

    public Text getPoints() {
        Text playerPoints = new Text("Score : "+(int)points);
        playerPoints.setTranslateX(1100);
        playerPoints.setTranslateY(30);
        playerPoints.setScaleX(3);
        playerPoints.setScaleY(3);
        playerPoints.setFill(Color.YELLOW);
        return playerPoints;
    }

    public Text gameOver() {
        Text gameOver;
        if (survival) {
            gameOver = new Text("        Game Over\n   Your score : " + (int) points + "\nYou survived "+round+" rounds\nPress enter to continue...");
        } else {
            gameOver = new Text("        Game Over\n   Your score : " + (int) points + "\nPress enter to continue...");
        }
        gameOver.setTranslateX(600);
        gameOver.setTranslateY(340);
        gameOver.setScaleX(5);
        gameOver.setScaleY(5);
        gameOver.setFill(Color.WHEAT);
        return gameOver;
    }

    public Text getGamePausedText() {
        Text pause = new Text("                 G A M E  P A U S E D\n\n" +
                "S : S a v e  a n d  Q u i t  t o  M a i n  M e n u\n\n" +
                "         E S C : C o n t i n u e  P l a y i n g");
        pause.setTranslateX(550);
        pause.setTranslateY(320);
        pause.setScaleX(3);
        pause.setScaleY(3);
        pause.setFill(Color.YELLOW);
        return pause;
    }

    public Text gameWonText() {
        Text gameWon = new Text("         CONGRATULATIONS!\n        You defeated the boss!");
        gameWon.setTranslateX(580);
        gameWon.setTranslateY(320);
        gameWon.setScaleX(3);
        gameWon.setScaleY(3);
        gameWon.setFill(Color.PINK);
        return gameWon;
    }

    public Sprite bossHp() {
        int variable;
        if ( this.difficulty.equals(Difficulty.EASY) || survival ) {
            variable = 550;
        } else if ( this.difficulty.equals(Difficulty.NORMAL) ) {
            variable = 500;
        } else {
            variable = 440;
        }
        if (!gameWon) {
            Sprite bossHp = new Sprite(variable, 690, getBoss().health, 15, "Boss Hp", 1, 1);
            bossHp.setScaleX(3);
            if ( this.difficulty.equals(Difficulty.EASY) || survival ) {
                if (getBoss().health > 150) {
                    bossHp.setFill(Color.GREEN);
                } else if ( getBoss().health > 100 ) {
                    bossHp.setFill(Color.YELLOW);
                } else if ( getBoss().health > 50 ) {
                    bossHp.setFill(Color.ORANGE);
                } else {
                    bossHp.setFill(Color.RED);
                }
            } else if ( this.difficulty.equals(Difficulty.NORMAL) ) {
                if (getBoss().health > 225) {
                    bossHp.setFill(Color.GREEN);
                } else if ( getBoss().health > 150 ) {
                    bossHp.setFill(Color.YELLOW);
                } else if ( getBoss().health > 75 ) {
                    bossHp.setFill(Color.ORANGE);
                } else {
                    bossHp.setFill(Color.RED);
                }
            } else {
                if (getBoss().health > 300) {
                    bossHp.setFill(Color.GREEN);
                } else if ( getBoss().health > 200 ) {
                    bossHp.setFill(Color.YELLOW);
                } else if ( getBoss().health > 100 ) {
                    bossHp.setFill(Color.ORANGE);
                } else {
                    bossHp.setFill(Color.RED);
                }
            }
            return bossHp;
        } else {
            return null;
        }
    }

    Player player = new Player(640, 620, 65, 65, "Player", 3, 10);

    private ArrayList<Sprite> sprites() {
        ArrayList<Sprite> sprites = new ArrayList<>();
        root.getChildren().forEach(sprite -> {
            String type = sprite.getClass().getSimpleName();
            if ( type.equals("Player") || type.equals("EnemyA") || type.equals("EnemyB")|| type.equals("Obstacle")||
                    type.equals("Ufo") || type.equals("Sprite") || type.equals("Boss") || type.equals("Heart") ||
                    type.equals("LaserGun")||type.equals("Shield")) {
                sprites.add((Sprite)sprite);
            }
        });
        return sprites;
    }

    public double getMultiplier() {
        if ( this.difficulty.equals(Difficulty.EASY) ) {
            return 1;
        } else if ( this.difficulty.equals(Difficulty.NORMAL) ) {
            return 1.5;
        } else {
            return 2;
        }
    }

    public double getTime() {
        if ( this.difficulty.equals(Difficulty.EASY) ) {
            return 5;
        } else if ( this.difficulty.equals(Difficulty.NORMAL) ) {
            return 4;
        } else {
            return 3;
        }
    }

    public double getPossibility() {
        if ( this.difficulty.equals(Difficulty.EASY) ) {
            return 0.2;
        } else if ( this.difficulty.equals(Difficulty.NORMAL) ) {
            return 0.3;
        } else {
            return 0.4;
        }
    }

    public void spawnUfo() {
        Ufo ufo = new Ufo(1200, 50, 40, 20, "Ufo", 1, 2*getMultiplier());
        ufoSpawned = true;
        root.getChildren().add(ufo);
    }

    public boolean reachedMinX() {
        for ( Sprite sprite : sprites() ) {
            if ( sprite.type.equals("EnemyA") || sprite.type.equals("EnemyB") ) {
                if ( sprite.getTranslateX() < 32 ) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveEnemiesLeft() {
        for ( Sprite sprite : sprites() ) {
            if ( sprite.type.equals("EnemyA") || sprite.type.equals("EnemyB") ) {
                sprite.moveLeft(1);
            }
            if ( reachedMinX() ) {
                enemyMovementDecision++;
                break;
            }
        }
    }

    public void moveEnemiesDown() {
        for ( Sprite sprite : sprites() ) {
            if ( sprite.type.equals("EnemyA") || sprite.type.equals("EnemyB") ) {
                sprite.moveOneBlockDown();
            }
        }
        enemyMovementDecision++;
    }

    public boolean reachedMaxX() {
        for ( Sprite sprite : sprites() ) {
            if ( sprite.type.equals("EnemyA") || sprite.type.equals("EnemyB") ) {
                if ( sprite.getTranslateX() > 1178 ) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveEnemiesRight() {
        for ( Sprite sprite : sprites() ) {
            if ( sprite.type.equals("EnemyA") || sprite.type.equals("EnemyB") ) {
                sprite.moveRight(1);
            }
            if ( reachedMaxX() ) {
                enemyMovementDecision++;
                break;
            }
        }
    }

    public void moveEnemies() {
        if ( enemyMovementDecision % 4 == 1 ) {
            moveEnemiesRight();
        } else if ( enemyMovementDecision % 4 == 2 || enemyMovementDecision % 4 == 0 ) {
            moveEnemiesDown();
        } else if (enemyMovementDecision % 4 == 3 ) {
            moveEnemiesLeft();
        }
    }

    public void spawnBoss() {
        double multiplier = getMultiplier();
        if (survival) {
            multiplier = 1;
        }
        Boss boss = new Boss(470, 60, 400, 400, "Boss", (int)(200*multiplier), 5);
        root.getChildren().add(boss);
    }

    public Boss getBoss() {
        for ( Sprite sprite : sprites() ) {
            if ( sprite.type.equals("Boss") ) {
                return (Boss)sprite;
            }
        }
        return null;
    }

    public void moveBossUp() {
        getBoss().moveUp();
        if ( getBoss().getTranslateY() <= 60 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossRight() {
        getBoss().moveRight(1);
        if ( getBoss().getTranslateX() >= 850 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossLeft() {
        getBoss().moveLeft(1);
        if ( getBoss().getTranslateX() <= 30 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossDown() {
        getBoss().moveDown();
        if ( getBoss().getTranslateY() >= 230 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossUpRight() {
        getBoss().moveUp();
        getBoss().moveRight(1);
        if ( getBoss().getTranslateY() <= 60 || getBoss().getTranslateX() >= 850 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossDownRight() {
        getBoss().moveDown();
        getBoss().moveRight(1);
        if ( getBoss().getTranslateY() >= 230 || getBoss().getTranslateX() >= 850 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossDownLeft() {
        getBoss().moveDown();
        getBoss().moveLeft(1);
        if ( getBoss().getTranslateY() >= 230 || getBoss().getTranslateX() <= 30 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBossUpLeft() {
        getBoss().moveUp();
        getBoss().moveLeft(1);
        if ( getBoss().getTranslateY() <= 60 || getBoss().getTranslateX() <= 30 ) {
            bossMovementDecision = random.nextInt(8) + 1;
        }
    }

    public void moveBoss() {
        if ( bossMovementDecision == 1 ) {
            moveBossUp();
        } else if ( bossMovementDecision == 2 ) {
            moveBossUpRight();
        } else if ( bossMovementDecision == 3 ) {
            moveBossRight();
        } else if ( bossMovementDecision == 4 ) {
            moveBossDownRight();
        } else if ( bossMovementDecision == 5 ) {
            moveBossDown();
        } else if ( bossMovementDecision == 6 ) {
            moveBossDownLeft();
        } else if ( bossMovementDecision == 7 ) {
            moveBossLeft();
        } else if ( bossMovementDecision == 8 ) {
            moveBossUpLeft();
        }

    }

    private double positionX = 640;

    private double enemyPositionX;

    private double enemyPositionY;

    private boolean shotEnemyB = false;

    private int shootTimer = 0;

    public void update() {
        shootTimer+=1;
        enemyATimer += 0.016;
        enemyBTimer += 0.016;
        bossWaiting += 1;
        bossTimer += 1;
        if ( shootTimer % 15 == 0 ) {
            player.shoot(root);
        }
        if ( player.health == 0 ) {
            gameOver = true;
        }
        for ( Sprite sprite : sprites() ) {
            if ( (sprite.type.equals("EnemyA") || sprite.type.equals("EnemyB")) && sprite.getTranslateY() >= 620) {
                gameOver = true;
            }
        }
        if (gameWon) {
            root.getChildren().clear();
            ImageView background = new ImageView(Images.background);
            background.setFitWidth(1280);
            background.setFitHeight(720);
            root.getChildren().addAll(background, player);
            if (bossTimer > 300) {
                player.setSpeed(5);
                player.moveUp();
            }
            if ( player.getTranslateY() < 0 ) {
                root.getChildren().add(gameWonText());
            }
            if (bossTimer == 660) {
                gameOver = true;
            }
        }
        if (gameOver) {
            root.getChildren().clear();
            ImageView background = new ImageView(Images.background);
            background.setFitWidth(1280);
            background.setFitHeight(720);
            laserGun = false;
            immunity = false;
            root.getChildren().addAll(background, gameOver());
            if (survival) {
                survival = false;
            }
        }
        if ( totalEnemies == 0 && !gameWon) {
            shootTimer = 0;
            round++;
            root.getChildren().removeIf(node -> !node.getClass().getSimpleName().equals("Player")&&!node.getClass().getSimpleName().equals("ImageView")
                    &&!node.getClass().getSimpleName().equals("Heart")&&!node.getClass().getSimpleName().equals("Shield")&&!node.getClass().getSimpleName().equals("LaserGun"));
            if ( round % 10 != 0) {
                spawnObstacles();
                spawnEnemies();
                ufoSpawned = false;
                totalEnemies = 50;
                enemyATimer = 0;
                enemyBTimer = 0;
                shotEnemyB = false;
            } else {
                bossWaiting = 0;
                bossTimer = 0;
                spawnBoss();
                root.getChildren().add(bossHp());
                totalEnemies = 1;
            }
            root.getChildren().addAll(getStageText(), getPlayerLives(), getPoints());
        }
        moveEnemies();
        if ( bossWaiting > 300 && !gameOver && round % 10 == 0 && !gameWon ) {
            moveBoss();
        }
        sprites().forEach(sprite -> {
            if ( sprite.getTranslateY() > 1000 || sprite.getTranslateY() < 0) {
                root.getChildren().remove(sprite);
            }
            switch (sprite.type) {
                case "Attack1":
                case "EnemyA bullet":
                    sprite.moveDown();
                    sprites().forEach(sprite1 -> {
                       if (sprite1.type.equals("Player")||sprite1.type.equals("Obstacle")) {
                           if ( sprite.getBoundsInParent().intersects(sprite1.getBoundsInParent()) ) {
                               if ( !immunity && sprite1.type.equals("Player") ) {
                                   sprite1.health--;
                               } else if ( sprite1.type.equals("Obstacle") ) {
                                   sprite1.health--;
                               }
                               root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("Text"));
                               if (sprite1.health == 0) {
                                   root.getChildren().remove(sprite1);
                               }
                               root.getChildren().remove(sprite);
                               root.getChildren().addAll(getStageText(), getPlayerLives(), getPoints());
                           }
                       }
                    });
                    break;
                case "Attack2":
                case "EnemyB bullet":
                    sprite.goToDestination(enemyPositionX, enemyPositionY, positionX, 620);
                    sprites().forEach(sprite1 -> {
                        if (sprite1.type.equals("Player")||sprite1.type.equals("Obstacle")) {
                            if ( sprite.getBoundsInParent().intersects(sprite1.getBoundsInParent()) ) {
                                if ( !immunity && sprite1.type.equals("Player")) {
                                    sprite1.health--;
                                } else if ( sprite1.type.equals("Obstacle") ) {
                                    sprite1.health--;
                                }
                                if (sprite1.health == 0) {
                                    root.getChildren().remove(sprite1);
                                }
                                root.getChildren().remove(sprite);
                                shotEnemyB = false;
                            }
                        }
                    });
                    if (sprite.getTranslateY() > 720) {
                        shotEnemyB = false;
                    }
                    break;
                case "Player bullet":
                    sprite.moveUp();
                    sprites().forEach(sprite1 -> {
                        if ( sprite1.type.equals("EnemyA") || sprite1.type.equals("EnemyB")) {
                            if ( sprite1.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
                                if (sprite1.type.equals("EnemyA")) {
                                    points += 100*getMultiplier();
                                } else {
                                    points += 150*getMultiplier();
                                }
                                sprite1.health--;
                                sprite.health--;
                                totalEnemies--;
                                root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("Text"));
                                root.getChildren().remove(sprite1);
                                if ( sprite.health == 0 ) {
                                    root.getChildren().remove(sprite);
                                }
                                root.getChildren().addAll(getPlayerLives(), getStageText(), getPoints());
                            }
                        }
                        if (sprite1.type.equals("Obstacle")) {
                            if ( sprite1.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
                                sprite1.health--;
                                if ( sprite1.health == 0 ) {
                                    root.getChildren().remove(sprite1);
                                }
                                root.getChildren().remove(sprite);
                            }
                        }
                        if ( sprite1.type.equals("Ufo") ) {
                            if (sprite1.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
                                points += 1000*getMultiplier();
                                root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("Text"));
                                root.getChildren().removeAll(sprite, sprite1);
                                root.getChildren().addAll(getPlayerLives(), getStageText(), getPoints());
                                ufoSpawned = false;
                            }
                        }
                        if ( sprite1.type.equals("Boss") ) {
                            if (sprite1.getBoundsInParent().intersects(sprite.getBoundsInParent())) {
                                points += 10*getMultiplier();
                                root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("Text"));
                                for ( Sprite sprite2 : sprites() ) {
                                    if ( sprite2.type.equals("Boss Hp") ) {
                                        root.getChildren().removeAll(sprite, sprite2);
                                    }
                                }
                                sprite1.health--;
                                if ( sprite1.health == 0 ) {
                                    root.getChildren().remove(getBoss());
                                    points += 50000*getMultiplier();
                                    bossTimer = 0;
                                    totalEnemies = 0;
                                    if (!survival) {
                                        gameWon = true;
                                    }
                                } else {
                                    root.getChildren().addAll(getPlayerLives(), getStageText(), getPoints(), bossHp());
                                }
                            }
                        }
                    });

                    break;
                case "EnemyA":
                    if ( Math.random() < getPossibility()*0.004 ) {
                        sprite.shoot(root);
                    }
                    break;
                case "EnemyB":
                    if (!shotEnemyB) {
                        if (enemyBTimer == 0.096) {
                            if (Math.random() < 0.5) {
                                sprite.shoot(root);
                                positionX = player.getTranslateX();
                                enemyPositionX = sprite.getTranslateX()+30;
                                enemyPositionY = sprite.getTranslateY()+20;
                                shotEnemyB = true;
                            }
                        }
                    }
                    break;
                case "Ufo":
                    sprite.moveLeft(1);
                    if (sprite.getTranslateX() < 31) {
                        root.getChildren().remove(sprite);
                        ufoSpawned = false;
                    }
                    break;
                case "Heart":
                    sprite.moveDown();
                    if ( sprite.getBoundsInParent().intersects(player.getBoundsInParent()) ) {
                        player.health++;
                        root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("Text"));
                        root.getChildren().remove(sprite);
                        root.getChildren().addAll(getStageText(), getPlayerLives(), getPoints());
                    }
                    break;
                case "LaserGun":
                    sprite.moveDown();
                    if ( sprite.getBoundsInParent().intersects(player.getBoundsInParent()) ) {
                        laserGun = true;
                        laserGunTimer = 0;
                        root.getChildren().remove(sprite);
                    }
                    break;
                case "Shield":
                    sprite.moveDown();
                    if ( sprite.getBoundsInParent().intersects(player.getBoundsInParent()) ) {
                        immunity = true;
                        immunityTimer = 0;
                        root.getChildren().remove(sprite);
                    }
                    break;
            }
        });

        if ( enemyATimer < 2 && !ufoSpawned && round % 10 != 0 ) {
            if ( Math.random() < 0.0005 ) {
                spawnUfo();
            }
        }

        if ( enemyATimer > getTime() ) {
            enemyATimer = 0;
        }

        if ( enemyBTimer > getTime()-0.1) {
            enemyBTimer = 0;
        }

        if ( bossTimer == 300 && round % 10 == 0 && !gameWon ) {
            getBoss().attackA(root);
        }

        if ( bossTimer == 420 && round % 10 == 0 && !gameWon ) {
            positionX = player.getTranslateX();
            enemyPositionX = getBoss().getTranslateX()+193;
            enemyPositionY = getBoss().getTranslateY()+286;
            getBoss().attackB(root);
            bossTimer = 0;
        }

        if ( Math.random() < 0.00075 ) {
            PowerUp powerUp = new PowerUp(root);
            powerUp.spawnPowerUp();
        }

        if ( laserGun ) {
            laserGunTimer += 1;
            ImageView laserGunImage = new ImageView(Images.laserGun);
            laserGunImage.setFitWidth(30);
            laserGunImage.setFitHeight(30);
            laserGunImage.setTranslateX(20);
            laserGunImage.setTranslateY(340);
            root.getChildren().add(laserGunImage);
            if ( laserGunTimer == 900 ) {
                laserGun = false;
                root.getChildren().remove(laserGunImage);
            }
        }

        if ( immunity ) {
            immunityTimer += 1;
            ImageView shieldImage = new ImageView(Images.shield);
            shieldImage.setFitWidth(30);
            shieldImage.setFitHeight(30);
            shieldImage.setTranslateX(20);
            shieldImage.setTranslateY(380);
            root.getChildren().add(shieldImage);
            if ( immunityTimer == 900 ) {
                immunity = false;
                root.getChildren().remove(shieldImage);
            }
        }

    }

    public void spawnEnemies() {
        for ( int i = 0; i < 10; i++ ) {
            EnemyB enemyB = new EnemyB(90+i*60, 90, 50, 50, "EnemyB", 1, getMultiplier());
            root.getChildren().add(enemyB);
            for ( int j = 0; j < 4; j++ ) {
                EnemyA enemyA = new EnemyA(90+i*60, 150+j*60, 50, 50, "EnemyA", 1, getMultiplier());
                root.getChildren().add(enemyA);
            }
        }
    }

    public void spawnObstacles() {
        for ( int i = 0; i < 5; i++ ) {
            Obstacle obstacle = new Obstacle(100+i*250, 500, 90, 90, "Obstacle", 5, 0);
            root.getChildren().add(obstacle);
        }
    }

    private Parent createContent() {
        root.setPrefSize(1280, 720);

        ImageView background = new ImageView(Images.background);
        background.setFitHeight(720);
        background.setFitWidth(1280);

        root.getChildren().addAll(background, player);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ImageView background = new ImageView(Images.background);
                background.setFitWidth(1280);
                background.setFitHeight(720);
                if (!gamePaused && !gameOver ) {
                    root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("Text"));
                    root.getChildren().removeIf(node -> node.getClass().getSimpleName().equals("ImageView"));
                    update();
                    root.getChildren().add(0, background);
                    root.getChildren().addAll(getPoints(), getPlayerLives(), getStageText());
                } else if (gamePaused) {
                    root.getChildren().addAll(background, getGamePausedText());
                }
            }
        };

        animationTimer.start();

        return root;
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createContent());

        scene.setOnKeyPressed( e-> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                if (!gameOver) {
                    gamePaused = !gamePaused;
                }
            }
            if ( !gameOver && !gamePaused && !gameWon ) {
                switch (e.getCode()) {
                    case A -> player.moveLeft(1);
                    case D -> player.moveRight(1);
                }
            } else if (gameOver) {
                Main main = new Main();
                switch (e.getCode()) {
                    case ENTER -> {
                        try {
                            main.start(stage);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else if ( gamePaused ) {
                if (e.getCode().equals(KeyCode.S)) {
                    root.getChildren().clear();
                    Main main = new Main();
                    try {
                        main.start(stage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

}