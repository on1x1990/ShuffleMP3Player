package ru.artemryzhenkov;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by Artem2011 on 10.02.2016.
 */
public class InformScrollPane extends ScrollPane {
    Text text = new Text();

    public InformScrollPane(){
        this.setPrefSize(Main.WIDTH * 0.7, Main.HEIGHT * 0.25);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null)));
        this.setBorder(new Border(new BorderStroke(Color.AQUA, BorderStrokeStyle.SOLID, new CornerRadii(0.0), new BorderWidths(3.0))));
        this.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        this.setContent(text);
    }

    public void addNewText(String string){
        String oldText = this.text.getText();
        this.text.setText(oldText + string);
    }
}
