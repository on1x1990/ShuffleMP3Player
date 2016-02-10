package ru.artemryzhenkov;

import javafx.application.Platform;
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
import javafx.scene.media.MediaPlayer.*;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;


/**
 * Created by Artem2011 on 08.02.2016.
 */
public class MediaControl extends BorderPane {
    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private HBox mediaBar;

    private Pane mvPane;
    private Button playButton;
    private Label spacer;
    private Label timeLabel;
    private Label volumeLabel;

    public void setMp(MediaPlayer setMp) {this.mp = setMp;}
    public MediaPlayer getMp() {return this.mp;}
    public void setMv(MediaView setMv) {this.mediaView = setMv;}

    public MediaControl(){
        setStyle("-fx-background-color: #bfc2c7;");
        mvPane = new Pane() {};
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);

        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        playButton = new Button(">");

        mediaBar.getChildren().add(playButton);
        setBottom(mediaBar);

        // add spacer
        spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);

        // add time label
        timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);

        // add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);

        mediaBar.getChildren().add(timeSlider);

        // add play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);

        // add the volume label
        volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);

        // add volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);

        mediaBar.getChildren().add(volumeSlider);
    }

    public boolean init(MediaPlayer mp) {
        // settings, if file is not exists (e.g. deleted or moved)
        if (!new File(StorageInts.getNormalPath.apply(mp.getMedia())).exists()) {
            // if prev file is last
            if (StorageInts.now == 0) {
                Main.choiseResponse.setText("No clips for play! Choose mp3 and start play...");
                return false;
            }
            Main.nextClip.fire();
            return false;
        }
        // settings, if file is exists - init continue...
        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        mvPane = new Pane() {};
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);

        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        playButton = new Button(">");


        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                MediaPlayer.Status status = mp.getStatus();
                if (status == Status.UNKNOWN || status == Status.HALTED){return;}
                if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                }
                else {mp.pause();}
            }
        });

        mp.currentTimeProperty().addListener(new InvalidationListener()
        {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });

        mp.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setText(">");
            }
        });

        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });

        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                    Main.nextClip.fire();
                }
            }
        });


        mediaBar.getChildren().add(playButton);
        setBottom(mediaBar);

        // add spacer
        spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);

        // add time label
        timeLabel = new Label("Time: ");
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
                    mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
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
        volumeLabel = new Label("Vol: ");
        mediaBar.getChildren().add(volumeLabel);

        // add volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mp.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });

        mediaBar.getChildren().add(volumeSlider);

        mp.setAutoPlay(true);
        return true;
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mp.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mp.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int)Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                    durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

    public void destroy(){
        if (volumeSlider != null) {
            mediaBar.getChildren().remove(volumeSlider);
            volumeSlider = null;}
        if (volumeLabel != null) {
            mediaBar.getChildren().remove(volumeLabel);
            volumeLabel = null;}
        if (playTime != null) {
            mediaBar.getChildren().remove(playTime);
            playTime = null;}
        if (timeSlider != null) {
            mediaBar.getChildren().remove(timeSlider);
            timeSlider = null;}
        if (timeLabel != null) {
            mediaBar.getChildren().remove(timeLabel);
            timeLabel = null;}
        if (spacer != null) {
            mediaBar.getChildren().remove(spacer);
            spacer = null;}
        if (playButton != null) {
            mediaBar.getChildren().remove(playButton);
            playButton = null;}
        if (mediaBar != null) mediaBar = null;
        if (mediaView != null) mvPane.getChildren().remove(mediaView);
        if (mvPane != null) mvPane = null;
        if(mediaView != null) mediaView = null;
        if (mp != null) {
            mp.dispose();
            this.mp = null;}
        stopRequested = false;
        atEndOfMedia = false;
    }
}
