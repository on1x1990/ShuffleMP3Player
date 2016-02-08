package ru.artemryzhenkov;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.List;

/**
 * Created by Artem2011 on 09.02.2016.
 */
public class MediaControlClip extends BorderPane {
    public AudioInputStream ais;
    public Clip clip;
    public List<File> clipsList;

    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private HBox mediaBar;

    private Pane mvPane;

    public MediaControlClip(){
        setStyle("-fx-background-color: #bfc2c7;");
        mvPane = new Pane() {};
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);

        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        Button playButton = new Button(">");


        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //MediaPlayer.Status status = mp.getStatus();
                //if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED){return;}
                //if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.STOPPED) {
                if (clip.isRunning() == false) {
                    if (atEndOfMedia) {
                        //mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    //mp.play();
                    clip.start();
                }
                //else {mp.pause();}
                else clip.stop();
            }
        });






        mediaBar.getChildren().add(playButton);
        setBottom(mediaBar);

        // add spacer
        Label spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);

        // add time label
        Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);

        // add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    //mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });

        mediaBar.getChildren().add(timeSlider);

        // add play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);

        // add the volume label
        Label volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);

        // add volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    //mp.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });

        mediaBar.getChildren().add(volumeSlider);
    }
}
