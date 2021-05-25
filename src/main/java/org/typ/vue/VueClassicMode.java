package org.typ.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.typ.modele.Struct;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class VueClassicMode extends BorderPane implements Observer {

    private final Label correctLabel;
    private final Label falseLabel;
    private HBox hBoxCorrectFalse;
    private Label falseValue;
    private Label correctValue;

    private Label labelTyp;
    private Label labelChrono;
    private TextFlow textArea;

    /**
     * Contructeur de la classe VueClassicMode. Cette classe affiche le titre, le chrono, le texte à recopier, le
     * compteur de mots corrects et faux
     */
    public VueClassicMode() {
        super();
        labelTyp = new Label("Typ");
        labelTyp.setTextFill(Color.GOLDENROD);
        labelChrono = new Label("03:42");
        var boxTypChrono = new VBox(12, labelTyp, labelChrono);


        textArea = new TextFlow();
        //textArea.setWrapText(true);
        //textArea.setEditable(false);
        correctLabel = new Label("Correct : ");
        correctValue = new Label("0");
        falseLabel = new Label("False : ");
        falseValue = new Label("0");
        hBoxCorrectFalse = new HBox(50, correctLabel, correctValue, falseLabel, falseValue);



        this.setTop(boxTypChrono);
        this.setCenter(textArea);
        this.setBottom(hBoxCorrectFalse);



    }


    @Override
    public void update(Observable observable, Object o) {

        textArea = new TextFlow();

        Struct struct = (Struct) o;

        List<String> wholeText = struct.getText();


        for(int i = 0; i < wholeText.size(); i++) {
            if (i == struct.getPosition()) {
                TextHighlight th = new TextHighlight(wholeText.get(i)+" ");
                th.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
                textArea.getChildren().add(th);
            } else if (struct.getCorrectList().contains(i)) {
                TextTrue tt = new TextTrue(wholeText.get(i)+" ");
                tt.setFont(Font.font ("Verdana", 20));
                textArea.getChildren().add(tt);
            } else if (struct.getFalseList().contains(i)) {
                TextFalse tf = new TextFalse(wholeText.get(i)+" ");
                tf.setFont(Font.font ("Verdana", 20));
                textArea.getChildren().add(tf);
            }else {
                Text tx =new Text(wholeText.get(i) + " ");
                tx.setFont(Font.font ("Verdana", 20));
                textArea.getChildren().add(tx);
            }
        }


        this.setCenter(textArea);

        correctValue.setText(""+struct.getNbCorrectTotal());
        falseValue.setText(""+struct.getNbFalseTotal());


    }
}
