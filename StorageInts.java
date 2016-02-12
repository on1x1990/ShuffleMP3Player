package ru.artemryzhenkov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
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

    // new tracks list
    static final EventHandler<ActionEvent> chooseFiles = (ActionEvent ae) -> {
        List<File> medFilesList = Main.fc.showOpenMultipleDialog(Main.stage);
        if (medFilesList == null) {
            Main.informLastAction.setText("NEW TRACKS BUTTON :: Nothing choosen! Try again!");
            return;
        }
        Main.filesList = new ArrayList<>(medFilesList);
        Main.informScrollPane.clear().addNewText(" New tracks list: \n \n");
        for (int i = 0; i <= Main.filesList.size()-1; i++) {
            if (i < 10) Main.informScrollPane.addNewText(" "); // if number of track < 10 to format string added one space
            Main.informScrollPane.addNewText(" " + i + " :: " + Main.filesList.get(i).getPath() + "\n");
        }
        Main.informLastAction.setText("NEW TRACKS BUTTON :: Tracks list is ready to play!");
    };

    // start tracks list
    static final EventHandler<ActionEvent> startChooserList = (ActionEvent ae) -> {
        if (Main.filesList.isEmpty()) {
            Main.informLastAction.setText("START BUTTON :: Tracks list is empty! Choose new track list...");
            return;
        }
        if (Main.mc.getMp() != null) Main.mc.destroy();
        try {
            File file = Main.filesList.get(0);
            if (Main.mc.init(new MediaPlayer(new Media(file.toURI().toURL().toString())))) {
                now = 0;
                Main.informLastAction.setText("now = " + now + " :: " + file.getPath());
            }
        }
        catch (MalformedURLException e) {
            Main.informLastAction.setText("START BUTTON :: Something is wrong... Try again...");
            System.out.println(e);
        }
    };

    // play next button
    static final EventHandler<ActionEvent> playNextClip = (ActionEvent ae) -> {
        if (Main.filesList.isEmpty()) {
            Main.informLastAction.setText("NEXT BUTTON :: Tracks list is empty! choose new track list...");
            return;
        }
        int next = (now+1 > Main.filesList.size()-1) ? 0 : now+1;
        File file = Main.filesList.get(next);
        Main.mc.destroy();
        try {
            if (Main.mc.init(new MediaPlayer(new Media(file.toURI().toURL().toString())))) {
                now = next;
                Main.informLastAction.setText("now = " + now + " :: " + file.getPath());
            }
        }
        catch (MalformedURLException e) {
            Main.informLastAction.setText("NEXT BUTTON :: Something is wrong... Try again...");
            System.out.println(e);
        }
    };

    // play prev button
    static final EventHandler<ActionEvent> playPrevClip = (ActionEvent ae) -> {
        if (Main.filesList.isEmpty()) {
            Main.informLastAction.setText("PREV BUTTON :: Tracks list is empty! choose new track list...");
            return;
        }
        int prev = (now-1 < 0) ? Main.filesList.size()-1 : now-1;
        File file = Main.filesList.get(prev);
        Main.mc.destroy();
        try {
            if (Main.mc.init(new MediaPlayer(new Media(file.toURI().toURL().toString())))) {
                now = prev;
                Main.informLastAction.setText("now = " + now + " :: " + file.getPath());
            }
        }
        catch (MalformedURLException e) {
            Main.informLastAction.setText("PREV BUTTON :: Something is wrong... Try again...");
            System.out.println(e);
        }
    };

    // shuffle button
    static final EventHandler<ActionEvent> shuffleClips = (ActionEvent ae) -> {
        if (Main.filesList.isEmpty()) {
            Main.informLastAction.setText("SHUFFLE BUTTON :: Tracks list is empty! choose new track list...");
            return;
        }
        if (Main.mc.getMp() != null) Main.mc.destroy();
        int size = Main.filesList.size();
        List<File> bufferList = new ArrayList<>(Main.filesList);
        List<File> finishList = new ArrayList<>();
        Random random = new Random();
        for (int i = size; i >= 1; i--) {
            int choice = random.nextInt(i);
            finishList.add(bufferList.get(choice));
            bufferList.remove(choice);
        }
        Main.filesList = finishList;
        Main.informScrollPane.clear().addNewText(" New shuffled tracks list: \n \n");
        for (int i = 0; i <= Main.filesList.size()-1; i++) {
            if (i < 10) Main.informScrollPane.addNewText(" ");
            Main.informScrollPane.addNewText(" " + i + " :: " + Main.filesList.get(i).getPath() + "\n");
        }
        try {
            File file = Main.filesList.get(0);
            now = 0;
            Main.mc.init(new MediaPlayer(new Media(file.toURI().toURL().toString())));
            Main.informLastAction.setText("now = " + now + " :: " + file.getPath());
        }
        catch (MalformedURLException e) {
            Main.informLastAction.setText("SHUFFLE BUTTON :: Something is wrong... Try again...");
            System.out.println(e);
        }
    };

    // add button
    static final EventHandler<ActionEvent> addClips = (ActionEvent ae) -> {
        List<File> medFilesList = Main.fc.showOpenMultipleDialog(Main.stage);
        if (medFilesList == null) {
            Main.informLastAction.setText("ADD BUTTON :: Nothing choosen to add! Try again...");
            return;
        }
        if (Main.filesList.isEmpty()) {
            Main.informLastAction.setText("ADD BUTTON :: At first use new tracks list button to choose tracks!");
            return;
        }
        boolean oneOrMore = false; // defines how match files added in filesList
        String outString = "\n";
        int nowPlus = Main.filesList.size()-1;
        for (File medFile : medFilesList) {
            boolean isExists = false;
            for (File file : Main.filesList) {
                if (file.getPath().equals(medFile.getPath())) {
                    System.out.println("ADD BUTTON :: This track is already added :: " + medFile.getPath());
                    isExists = true;
                    break;
                }
            }
            if (isExists == true) continue;
            Main.filesList.add(medFile);
            oneOrMore = true;
            nowPlus++;
            if (nowPlus < 10) outString += " ";
            outString += " " + nowPlus + " :: " + medFile.getPath() + "\n";
        }
        if (oneOrMore == false) return;
        outString = "\n Added tracks :: \n" + outString;
        Main.informScrollPane.addNewText(outString);
        Main.informLastAction.setText("ADD BUTTON :: Finished successful! Below you can see added tracks!");
    };

    // delete clip button
    static final EventHandler<ActionEvent> deleteThisClip = (ActionEvent ae) -> {
        if (Main.mc.getMp() == null) {
            Main.informLastAction.setText("DELETE BUTTON :: [Error 1] No track for delete!");
            return;
        }
        if (Main.filesList.isEmpty()) {
            Main.informLastAction.setText("DELETE BUTTON :: [Error 2] Tracks list is empty!");
            return;
        }
        Main.filesList.remove(now);
        Main.mc.destroy();
        if (Main.filesList.isEmpty()) {
            Main.informScrollPane.clear().addNewText(" No tracks for playing...");;
            Main.mainPane.getChildren().remove(Main.mc);
            Main.mc = new MediaControl();
            Main.mainPane.getChildren().add(Main.mc);
            return;
        }
        Main.informScrollPane.clear().addNewText(" New tracks list after deleted: \n \n");
        for (int i = 0; i <= Main.filesList.size()-1; i++) {
            if (i < 10) Main.informScrollPane.addNewText(" ");
            Main.informScrollPane.addNewText(" " + i + " :: " + Main.filesList.get(i) + "\n");
        }
        try {
            File file;
            if (now <= Main.filesList.size()-1) file = Main.filesList.get(now);
            else { file = Main.filesList.get(0); now = 0; }
            Main.mc.init(new MediaPlayer(new Media(file.toURI().toURL().toString())));
            Main.informLastAction.setText("now = " + now + " :: " + file.getPath());
        }
        catch (MalformedURLException e) {
            Main.informLastAction.setText("DELETE BUTTON :: Something is wrong... Try again...");
            System.out.println(e);
        }
    };

    // vol ><
    static final EventHandler<ActionEvent> medVolume = (ActionEvent ae) -> {
        Main.mc.getVolumeSlider().setValue(50.0);
        Main.mc.getMp().setVolume(0.5);
    };
}
