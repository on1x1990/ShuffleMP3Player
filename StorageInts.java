package ru.artemryzhenkov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class StorageInts {
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
        if (Main.filesList == null) return;
        MediaPlayer mp = new MediaPlayer(Main.mediaList.get(0));
        MediaControl mc = new MediaControl(mp);
        Main.mainPane.getChildren().add(mc);
    };
}
