package ru.artemryzhenkov;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Artem2011 on 08.02.2016.
 */

enum AppState {READY_TO_PLAY, UNKNOWN;}

public class Main extends Application {
    // app var's
    static final int WIDTH = 800;
    static final int HEIGHT = 300;

    static Stage stage;
    static RootNode rootNode = new RootNode();
    static Scene primaryScene = new Scene(rootNode, WIDTH, HEIGHT);

    static TopPane topPane = new TopPane();
    static MainPane mainPane = new MainPane();
    static BottomPane bottomPane = new BottomPane();

    static Button choiseButton; // = new Button("Choose mp3 file...");
    static Button startButton; // = new Button("Start playing...");
    static Label choiseResponseButton; // = new Label("While nothing chosen...");

    static FileChooser fc = new FileChooser();
    static AppState appState = AppState.UNKNOWN;
    static List<File> filesList = new ArrayList<>();
    static List<Media> mediaList = new ArrayList<>();
    static MediaView mv = new MediaView();

    // launch app
    public static void main (String args[]){
        launch(args);
    }

    @Override
    public void start (Stage primaryStage){
        stage = primaryStage;

        choiseButton = new Button("Choose mp3 file...");
        startButton = new Button("Start playing...");
        choiseResponseButton = new Label("While nothing chosen...");

        primaryStage.setTitle("ShuffleMP3Player");
        primaryStage.setScene(primaryScene);

        rootNode.getChildren().add(topPane);
        rootNode.getChildren().add(mainPane);
        rootNode.getChildren().add(bottomPane);

        choiseButton.setOnAction(StorageInts.chooseFiles);
        startButton.setOnAction(StorageInts.startChooserList);

        mainPane.getChildren().add(choiseButton);
        mainPane.getChildren().add(startButton);
        mainPane.getChildren().add(choiseResponseButton);

        primaryStage.show();
    }
}
