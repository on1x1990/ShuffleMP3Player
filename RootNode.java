package ru.artemryzhenkov;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Created by Artem2011 on 08.02.2016.
 */
public class RootNode extends VBox {
    public RootNode() {
        this.setPrefSize(Main.WIDTH * 0.8, Main.HEIGHT * 0.9);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setAlignment(Pos.CENTER);
    }
}
