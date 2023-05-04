package com.example.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Text text = new Text("S P A C E   I N V A D E R S");
        text.setScaleX(5);
        text.setScaleY(5);
        text.setTranslateX(600);
        text.setTranslateY(100);
        text.setFill(Color.YELLOW);

        Button button1 = new Button("Campaign");
        button1.setTranslateX(620);
        button1.setTranslateY(250);
        button1.setScaleX(2);
        button1.setScaleY(2);
        button1.setTextFill(Color.GREEN);
        StartGameMenu startGameMenu = new StartGameMenu();
        button1.setOnAction( event -> {
            try {
                startGameMenu.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        Button button2 = new Button("Survival");
        button2.setTranslateX(620);
        button2.setTranslateY(400);
        button2.setScaleX(2);
        button2.setScaleY(2);
        button2.setTextFill(Color.ORANGE);
        button2.setOnAction( event -> {
            try {
                Game.survival = true;
                Game game = new Game(Difficulty.HARD);
                game.start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        Button button3 = new Button("Exit game");
        button3.setTranslateX(620);
        button3.setTranslateY(550);
        button3.setScaleX(2);
        button3.setScaleY(2);
        button3.setTextFill(Color.RED);
        button3.setOnAction( event -> System.exit(1));

        ImageView imageView = new ImageView(Images.background);
        imageView.setFitWidth(1280);
        imageView.setFitHeight(720);

        Group group = new Group(imageView, text, button1, button2, button3);

        Scene scene = new Scene(group, 1280, 720);

        stage.setScene(scene);
        stage.setTitle("Space Invaders");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
