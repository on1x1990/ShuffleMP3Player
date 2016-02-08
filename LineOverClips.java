package ru.artemryzhenkov;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class LineOverClips extends HBox {
    public LineOverClips(){
        //this.setPrefSize(Main.WIDTH * 0.8, Main.HEIGHT * 0.6);
        //this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10.0);
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.DARKKHAKI, new CornerRadii(10.0), null)));
    }
}
