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
    final int WIDTH = 800;
    final int HEIGHT = 300;
    AppState appState = AppState.UNKNOWN;
    List<File> filesList = new ArrayList<>();
    List<Media> mediaList = new ArrayList<>();
    MediaView mv = new MediaView();

    // launch app
    public static void main (String args[]){
        launch(args);
    }

    @Override
    public void start (Stage primaryStage){
        // title for stage
        primaryStage.setTitle("ShuffleMP3Player");

        // root node
        final GridPane rootNode = new GridPane();
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setPadding(new Insets(5, 5, 5, 5));
        rootNode.setGridLinesVisible(false);

            // embedded grid panes
            // --------

        // topPane
        final GridPane topPane = new GridPane();
        topPane.setMinSize(0.7*WIDTH, 0.15*HEIGHT);
        topPane.setAlignment(Pos.CENTER);
        topPane.setHgap(5);
        topPane.setVgap(5);
        topPane.setPadding(new Insets(5, 5, 5, 5));
        Background topPaneBG = new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10.0), null));
        topPane.setBackground(topPaneBG);
        rootNode.add(topPane, 0, 0);

        final Label stateLabel = new Label("State: " + appState.toString());
        topPane.add(stateLabel, 0, 0);

        // mainPane
        final GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(5);
        mainPane.setVgap(5);
        mainPane.setPadding(new Insets(5, 5, 5, 5));
        mainPane.setMinSize(0.7 * WIDTH, 0.4 * HEIGHT);
        Background mainPaneBG = new Background(new BackgroundFill(Color.LEMONCHIFFON, new CornerRadii(10.0), null));
        mainPane.setBackground(mainPaneBG);
        rootNode.add(mainPane, 0, 1);

        final FileChooser fc = new FileChooser();
        fc.setTitle("Choose your file!");

        final Button choiseButton = new Button("Choose mp3 file...");
        mainPane.add(choiseButton, 0, 0);
        final Button startButton = new Button("Start playing...");
        mainPane.add(startButton, 1, 0);

        final Label choiseResponse = new Label("Nothing choised");
        mainPane.add(choiseResponse, 0, 1);

        //mainPane.add(mv, 0, 3);
        /*
        final Button playButton = new Button(">");
        HBox mediaBar = new HBox();
        mediaBar.getChildren().add(playButton);
        */


        choiseButton.setOnAction((ActionEvent ae) -> {
            filesList = fc.showOpenMultipleDialog(primaryStage);
            if (filesList != null) {
                appState = AppState.READY_TO_PLAY;
                stateLabel.setText("State: " + appState.toString());
                // clear old mediaList
                mediaList.clear();
                // set new mediaList
                for (File file : filesList) {
                    try {mediaList.add(new Media(file.toURI().toURL().toString()));}
                    catch (MalformedURLException e) {System.out.println("Error add " + file + "in mediaList :: " + e);}
                    /*
                    try {
                        MediaPlayer mp = new MediaPlayer(new Media(file.toURI().toURL().toString()));
                        mp.setAutoPlay(true);
                        mv = new MediaView(mp);
                        String fileName = file.getName().toString();
                        fileName = (fileName.length() > 35) ? fileName.substring(0, 34) : fileName;
                        choiseResponse.setText("Playing " + fileName + "...");
                    }
                    catch (MalformedURLException e) {System.out.println(e);}
                    */
                }
                //choiseResponse.setText("Your choise is: ");
            }
            else choiseResponse.setText("Choose please!");
        });

        startButton.setOnAction((ActionEvent ae) -> {
            if (filesList == null) return;
            MediaPlayer mp = new MediaPlayer(mediaList.get(0));
            MediaControl mc = new MediaControl(mp);
            mainPane.add(mc, 0, 3);
        });


        // bottomPane
        final GridPane bottomPane = new GridPane();
        bottomPane.setMinSize(0.7*WIDTH, 0.15*HEIGHT);
        Background bottomPaneBG = new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(10.0), null));
        bottomPane.setBackground(bottomPaneBG);
        rootNode.add(bottomPane, 0, 2);

        // show stage
        final Scene primaryScene = new Scene(rootNode, WIDTH, HEIGHT);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
