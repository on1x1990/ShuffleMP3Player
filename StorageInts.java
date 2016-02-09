package ru.artemryzhenkov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.io.*;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class StorageInts {
    static int now = 0;

    //  нопка выбора файлов
    static final EventHandler<ActionEvent> chooseFiles = (ActionEvent ae) -> {
        Main.filesList = Main.fc.showOpenMultipleDialog(Main.stage);
        if (Main.filesList != null) {
            Main.appState = AppState.READY_TO_PLAY;
            //Main.stateLabel.setText("State: " + Main.appState.toString());
            Main.mediaList.clear();
            for (File file : Main.filesList) {
                try {Main.mediaList.add(new Media(file.toURI().toURL().toString()));}
                catch (MalformedURLException e) {System.out.println("Error add " + file + "in mediaList :: " + e);}
            }
        }
        else Main.choiseResponseButton.setText("Choose please!");
    };

    //  нопка новый плейлист
    static final EventHandler<ActionEvent> startChooserList = (ActionEvent ae) -> {
        if (Main.filesList.isEmpty()) return;
        Main.mc.init(new MediaPlayer(Main.mediaList.get(0)));
        Main.mainPane.getChildren().add(Main.mc);


    };

    //  нопка следующа€ песн€
    static final EventHandler<ActionEvent> playNextClip = (ActionEvent ae) -> {
        int next = ( now+1 > Main.mediaList.size()-1 ) ? 0 : now+1;
        Main.mc.destroy();
        Main.mc.init(new MediaPlayer(Main.mediaList.get(next)));
        now = next;
        System.out.println("now = " + now);
    };

    //  нопка предыдуща€ песн€
    static final EventHandler<ActionEvent> playPrevClip = (ActionEvent ae) -> {
        int prev = ( now-1 < 0 ) ? Main.mediaList.size()-1 : now-1;
        Main.mc.destroy();
        Main.mc.init(new MediaPlayer(Main.mediaList.get(prev)));
        now = prev;
        System.out.println("now = " + now);
    };
}
