package org.typ.view;

import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CurrentWord extends TextFlow {

    public CurrentWord(Text correctPart, Text remainingPart) {
        super();
        String colorStr = "#051e35";
        correctPart.setFont(Font.font ("Verdana", FontWeight.NORMAL, 20));
        remainingPart.setFont(Font.font ("Verdana", FontWeight.NORMAL, 20));
        TextFlow wholeWord = new TextFlow();
        wholeWord.getChildren().add(correctPart);
        wholeWord.getChildren().add(remainingPart);
        wholeWord.setStyle("-fx-background-color: #051e35;");
        this.getChildren().add(wholeWord);
        Text space = new Text(" ");
        space.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        this.getChildren().add(space);
    }
}

