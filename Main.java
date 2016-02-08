package ru.artemryzhenkov;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;


/**
 * Created by Artem2011 on 08.02.2016.
 */

enum AppState {UNKNOWN;}

public class Main extends Application {
    // app var's
    final int WIDTH = 500;
    final int HEIGHT = 700;
    AppState appState = AppState.UNKNOWN;
    List<File> filesList;

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
        Background topPaneBG = new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10.0), null));
        topPane.setBackground(topPaneBG);
        rootNode.add(topPane, 0, 0);
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

        final Label choiseResponse = new Label("Nothing choised");
        mainPane.add(choiseResponse, 0, 1);

        choiseButton.setOnAction((ActionEvent ae) -> {
            filesList = fc.showOpenMultipleDialog(primaryStage);
            if (filesList != null) {
                for (File file : filesList) {
                }
                choiseResponse.setText("Your choise is: ");
            }
            else choiseResponse.setText("Choose please!");
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
