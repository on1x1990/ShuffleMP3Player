package ru.artemryzhenkov;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class TopPane extends VBox {
    public TopPane(){
        this.setPrefSize(Main.WIDTH * 0.8, Main.HEIGHT * 0.15);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, new CornerRadii(10.0), null)));
    }
}
