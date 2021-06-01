package org.typ.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.typ.model.Struct;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ViewClassicMode extends BorderPane implements ViewMode {

    private final Label correctLabel;
    private final Label falseLabel;
    private HBox hBoxCorrectFalse;
    private Label falseValue;
    private Label correctValue;

    private Label labelTyp;
    private Label labelChrono;
    private TextFlow textFlow;

    /**
     * Contructeur de la classe VueClassicMode. Cette classe affiche le titre, le chrono, le texte à recopier, le
     * compteur de mots corrects et faux
     */
    public ViewClassicMode() {
        super();
        labelTyp = new Label("Typ");
        labelTyp.setTextFill(Color.GOLDENROD);
        labelChrono = new Label("03:42");
        var boxTypChrono = new VBox(12, labelTyp, labelChrono);

        textFlow = new TextFlow();
        correctLabel = new Label("Correct : ");
        correctValue = new Label("0");
        falseLabel = new Label("False : ");
        falseValue = new Label("0");
        hBoxCorrectFalse = new HBox(50, correctLabel, correctValue, falseLabel, falseValue);

        this.setTop(boxTypChrono);
        this.setCenter(textFlow);
        this.setBottom(hBoxCorrectFalse);
    }


    @Override
    public void update(Observable observable, Object o) {

        textFlow = new TextFlow();

        Struct struct = (Struct) o;

        List<String> wholeText = struct.getText();

        /* Ajouter le texte au textFlow.  */
        for(int i = 0; i < wholeText.size(); i++) {
            // Cas du mot courant
            if (i == struct.getPosition()) {
                TextHighlight th = new TextHighlight(wholeText.get(i)+" ");
                th.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
                textFlow.getChildren().add(th);
            // Cas des mots corrects
            } else if (struct.getCorrectList().contains(i)) {
                TextTrue tt = new TextTrue(wholeText.get(i)+" ");
                tt.setFont(Font.font ("Verdana", 20));
                textFlow.getChildren().add(tt);
            // Cas des mots faux
            } else if (struct.getFalseList().contains(i)) {
                TextFalse tf = new TextFalse(wholeText.get(i)+" ");
                tf.setFont(Font.font ("Verdana", 20));
                textFlow.getChildren().add(tf);
            }else {
            // Cas des autres mots
                Text tx =new Text(wholeText.get(i) + " ");
                tx.setFont(Font.font ("Verdana", 20));
                textFlow.getChildren().add(tx);
            }
        }


        this.setCenter(textFlow);

        correctValue.setText(""+struct.getNbCorrectTotal());
        falseValue.setText(""+struct.getNbFalseTotal());
    }
}
