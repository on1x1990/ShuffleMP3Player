package ru.artemryzhenkov;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Created by Artem2011 on 08.02.2016.
 */
public class Main extends Application {
    final int WIDTH = 500;
    final int HEIGHT = 700;

    public static void main (String args[]){
        launch(args);
    }

    @Override
    public void start (Stage primaryStage){
        primaryStage.setTitle("ShuffleMP3Player");

        // root node
        GridPane rootNode = new GridPane();
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setPadding(new Insets(5, 5, 5, 5));
        rootNode.setGridLinesVisible(false);


        // embedded grid panes
            // topPane
            GridPane topPane = new GridPane();
            topPane.setMinSize(0.7*WIDTH, 0.15*HEIGHT);
            Background topPaneBG = new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10.0), null));
            topPane.setBackground(topPaneBG);
            rootNode.add(topPane, 0, 0);
            // mainPane
            GridPane mainPane = new GridPane();
            mainPane.setMinSize(0.7*WIDTH, 0.4*HEIGHT);
            Background mainPaneBG = new Background(new BackgroundFill(Color.LEMONCHIFFON, new CornerRadii(10.0), null));
            mainPane.setBackground(mainPaneBG);
            rootNode.add(mainPane, 0, 1);
            // bottomPane
            GridPane bottomPane = new GridPane();
            bottomPane.setMinSize(0.7*WIDTH, 0.15*HEIGHT);
            Background bottomPaneBG = new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(10.0), null));
            bottomPane.setBackground(bottomPaneBG);
            rootNode.add(bottomPane, 0, 2);

        Scene primaryScene = new Scene(rootNode, WIDTH, HEIGHT);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
