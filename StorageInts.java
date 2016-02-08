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

    // Обработка нажатия кнопки выбора файлов
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

    // Обработка нажатия кнопки старта нового плейлиста
    static final EventHandler<ActionEvent> startChooserList = (ActionEvent ae) -> {
        if (Main.filesList.isEmpty()) return;
        Main.mc.init(new MediaPlayer(Main.mediaList.get(0)));
        Main.mainPane.getChildren().add(Main.mc);


    };

    // Обработка нажатия кнопки для следующей песни
    static final EventHandler<ActionEvent> playNextClip = (ActionEvent ae) -> {
        int next = ( now+1 > Main.mediaList.size()-1 ) ? 0 : now+1;
        //MediaControl mcIn = new MediaControl(new MediaPlayer(Main.mediaList.get(next)));
        //Main.mc.setMp(new MediaPlayer(Main.mediaList.get(next)));
        //Main.mainPane.getChildren().remove(Main.mc);
        //Main.mc.close();
        //Main.mc.setMv(null);
        //Main.mc.setMp(null);
        //Main.mc = null;
        //System.gc();
        Main.mc.destroy();
        Main.mc.init(new MediaPlayer(Main.mediaList.get(next)));
        System.out.println("next = " + next);
        now = next;
        //Main.mc = mcIn;
    };
}
