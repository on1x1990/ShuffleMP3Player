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
    static final int HEIGHT = 500;

    static Stage stage;
    static RootNode rootNode = new RootNode();
    static Scene primaryScene = new Scene(rootNode, WIDTH, HEIGHT);

    static TopPane topPane = new TopPane();
    static MainPane mainPane = new MainPane();
    static BottomPane bottomPane = new BottomPane();

    static Button choiseButton; // = new Button("Choose mp3 file...");
    static LineOverClips lineOverClips = new LineOverClips();
        static Button shuffleClips;
        static Button startButton; // = new Button("Start playing...");
        static Button nextClip;
        static Button prevClip;
    static Label choiseResponseButton; // = new Label("While nothing chosen...");
    static ScrollPane informScrollPane;

    static FileChooser fc = new FileChooser();
    static AppState appState = AppState.UNKNOWN;
    static List<File> filesList = new ArrayList<>();
    static List<Media> mediaList = new ArrayList<>();
    static MediaView mv = new MediaView();
    static MediaControl mc;

    // launch app
    public static void main (String args[]){
        launch(args);
    }

    @Override
    public void start (Stage primaryStage){
        stage = primaryStage;

        choiseButton = new Button("Choose mp3 file...");
            shuffleClips = new Button("Shuffle");
            startButton = new Button("Start playing...");
            nextClip = new Button(" >> ");
            prevClip = new Button(" << ");
        choiseResponseButton = new Label("While nothing chosen...");
        informScrollPane = new InformScrollPane();

        primaryStage.setTitle("ShuffleMP3Player");
        primaryStage.setScene(primaryScene);

        rootNode.getChildren().add(topPane);
        rootNode.getChildren().add(mainPane);
        rootNode.getChildren().add(bottomPane);

        choiseButton.setOnAction(StorageInts.chooseFiles);
            shuffleClips.setOnAction(StorageInts.shuffleClips);
            startButton.setOnAction(StorageInts.startChooserList);
            nextClip.setOnAction(StorageInts.playNextClip);
            prevClip.setOnAction(StorageInts.playPrevClip);


        mainPane.getChildren().add(choiseButton);
        mainPane.getChildren().add(lineOverClips);
            lineOverClips.getChildren().add(shuffleClips);
            lineOverClips.getChildren().add(startButton);
            lineOverClips.getChildren().add(nextClip);
            lineOverClips.getChildren().add(prevClip);
        mainPane.getChildren().add(choiseResponseButton);
        mainPane.getChildren().add(informScrollPane);

        mc = new MediaControl();
        mainPane.getChildren().add(mc);

        primaryStage.show();
    }
}
