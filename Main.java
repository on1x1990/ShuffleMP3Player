package ru.artemryzhenkov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class Main extends Application {
    public static void main (String args[]){
        launch(args);
    }

    @Override
    public void start (Stage primaryStage){
        primaryStage.setTitle("ShuffleMP3Player");
        GridPane rootNode = new GridPane();
        Scene primaryScene = new Scene(rootNode, 500, 700);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
