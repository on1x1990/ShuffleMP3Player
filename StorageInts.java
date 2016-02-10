package ru.artemryzhenkov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class StorageInts {
    static int now = 0;

    // URL decoder
    static final UnaryOperator<String> decode = (String string) -> {
        String outString = "...";
        try {outString = URLDecoder.decode(string, "UTF-8");}
        catch (UnsupportedEncodingException e) {outString = string;}
        return outString;
    };

    // get normal path
    static final Function<Media, String> getNormalPath = (Media media) -> {
        String filePath = media.getSource().replace('/','\\');
        filePath = filePath.substring(filePath.indexOf('\\')+1);
        return filePath;
    };

    // files choose dialog
    static final EventHandler<ActionEvent> chooseFiles = (ActionEvent ae) -> {
        Main.filesList = Main.fc.showOpenMultipleDialog(Main.stage);
        if (Main.filesList != null) {
            //Main.appState = AppState.READY_TO_PLAY;
            //Main.stateLabel.setText("State: " + Main.appState.toString());
            Main.mediaList.clear();
            for (File file : Main.filesList) {
                try {Main.mediaList.add(new Media(file.toURI().toURL().toString()));}
                catch (MalformedURLException e) {System.out.println("Error add " + file + "in mediaList :: " + e);}
                //System.out.println(file.getName());
            }
            if(!Main.mediaList.isEmpty()) {
                Main.choiseResponse.setText("now = " + now + " :: " + decode.apply(Main.mediaList.get(now).getSource()));
                Main.informScrollPane.clear().addNewText(" MediaList choosen :: \n \n");
                for (int i = 0; i <= Main.mediaList.size()-1; i++) {
                    if (i < 10) Main.informScrollPane.addNewText("  " + i + "  ::  " + decode.apply(Main.mediaList.get(i).getSource() + "\n"));
                    else Main.informScrollPane.addNewText(" " + i + "  ::  " + decode.apply(Main.mediaList.get(i).getSource() + "\n"));
                }
            }
        }
        else {Main.choiseResponse.setText("Choose mp3-clips please!"); return;}
        if(!Main.filesList.isEmpty()) Main.filesList = new ArrayList<>();
    };

    // start playList button
    static final EventHandler<ActionEvent> startChooserList = (ActionEvent ae) -> {
        if (Main.mediaList.isEmpty()) return;
        now = 0;
        if (Main.mc.getMp() != null) Main.mc.destroy();
        if( Main.mc.init(new MediaPlayer(Main.mediaList.get(0))) ) {
            Main.choiseResponse.setText("now = " + now + " :: " + decode.apply(Main.mediaList.get(now).getSource()));
        }
    };

    // play next button
    static final EventHandler<ActionEvent> playNextClip = (ActionEvent ae) -> {
        if (Main.mediaList.isEmpty()) return;
        int next = ( now+1 > Main.mediaList.size()-1 ) ? 0 : now+1;
        Main.mc.destroy();
        if ( Main.mc.init(new MediaPlayer(Main.mediaList.get(next))) ) {
            now = next;
            Main.choiseResponse.setText("now = " + now + " :: " + decode.apply(Main.mediaList.get(now).getSource()));
        }
    };

    // play prev button
    static final EventHandler<ActionEvent> playPrevClip = (ActionEvent ae) -> {
        if (Main.mediaList.isEmpty()) return;
        int prev = ( now-1 < 0 ) ? Main.mediaList.size()-1 : now-1;
        Main.mc.destroy();
        if ( Main.mc.init(new MediaPlayer(Main.mediaList.get(prev))) ) {
            now = prev;
            Main.choiseResponse.setText("now = " + now + " :: " + decode.apply(Main.mediaList.get(now).getSource()));
        }
    };

    // shuffle button
    static final EventHandler<ActionEvent> shuffleClips = (ActionEvent ae) -> {
        if (Main.mediaList.isEmpty()) {Main.choiseResponse.setText("Instances of clips is not founded! Choose them and try again..."); return;}

        if (Main.mc.getMp() != null) Main.mc.destroy();

        int size = Main.mediaList.size();
        List<Media> bufferList = new ArrayList<>(Main.mediaList);
        List<Media> finishList = new ArrayList<>();
        Random random = new Random();
        for (int i = size; i >= 1; i--) {
            int choise = random.nextInt(i);
            finishList.add(bufferList.get(choise));
            bufferList.remove(choise);
        }
        Main.mediaList = finishList;
        Main.informScrollPane.clear().addNewText(" New shuffled mediaList :: \n \n");
        for (int i = 0; i <= Main.mediaList.size()-1; i++) {
            if (i < 10) Main.informScrollPane.addNewText("  " + i + "  ::  " + decode.apply(Main.mediaList.get(i).getSource() + "\n"));
            else Main.informScrollPane.addNewText(" " + i + "  ::  " + decode.apply(Main.mediaList.get(i).getSource() + "\n"));
        }

        Main.mc.init(new MediaPlayer(Main.mediaList.get(0)));
    };



}
