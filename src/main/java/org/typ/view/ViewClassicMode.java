package org.typ.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.typ.model.Struct;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Observable;

public class ViewClassicMode extends BorderPane implements PropertyChangeListener {


    @FXML
    private TextFlow viewTextFlow;

    @FXML
    private Label correctsWordCount;

    @FXML
    private Label FalseWordsCount;

    /** Le text complet qui vient du model **/
    private List<String> fullText;

    /**
     * Contructeur de la classe VueClassicMode. Cette classe affiche le titre, le chrono, le texte à recopier, le
     * compteur de mots corrects et faux
     */
    /*public ViewClassicMode() {
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
    }*/


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        viewTextFlow.getChildren().clear();

        Struct struct = (Struct) evt.getNewValue();

        List<String> wholeText = struct.getText();

        /* Ajouter le texte au viewTextFlow.  */
        for(int i = 0; i < wholeText.size(); i++) {
            // Cas du mot courant
            if (i == struct.getPosition()) {
                String wholeWord = wholeText.get(i);
                String correctString = "";
                String remainingString = "";
                Text remainingPart;
                TextTrue correctPart;

                if (struct.getPositionLastCorrectCharacter() == -1 && struct.getPositionFirstTypo() == -1) {
                    // Cas où il y a rien d'input
                    correctString = "";
                    remainingString = wholeWord;
                    correctPart = new TextTrue("");
                    remainingPart = new TextRemaining(remainingString);
                } else if ((struct.getPositionFirstTypo() != -1 && struct.getPositionLastCorrectCharacter() == -1) || (struct.getPositionFirstTypo() >= wholeWord.length())) {
                    // Cas où il y a que des erreur dans le mot
                    remainingString = wholeWord;
                    correctPart = new TextTrue("");
                    remainingPart = new TextFalse(remainingString);
                } else {
                    // Cas où il y a une partie correcte et une autre partie (fausse ou non)
                    // Dans ce cas, on utilise une substring pour découper le mot jusqu'au dernier caractère correct
                    // La deuxième partie du mot sera ecrite soit en rouge (si il y a une erreur), soit dans la police de base
                    // On utilise la classe CurrentWord pour concatener les deux Text
                    correctString = wholeWord.substring(0, struct.getPositionLastCorrectCharacter() + 1);
                    remainingString = wholeWord.substring(struct.getPositionLastCorrectCharacter() + 1, wholeWord.length());
                    correctPart = new TextTrue(correctString);
                    remainingPart = new TextRemaining(remainingString);
                    if (struct.getPositionFirstTypo() != -1) {
                        remainingPart = new TextFalse(remainingString);
                    }

                }
                CurrentWord currentWordPart = new CurrentWord(correctPart, remainingPart);
                viewTextFlow.getChildren().add(currentWordPart);
            // Cas des mots corrects
            } else if (struct.getCorrectList().contains(i)) {
                TextTrue tt = new TextTrue(wholeText.get(i)+" ");
                tt.setFont(Font.font ("Verdana", 20));
                viewTextFlow.getChildren().add(tt);
            // Cas des mots faux
            } else if (struct.getFalseList().contains(i)) {
                TextFalse tf = new TextFalse(wholeText.get(i)+" ");
                tf.setFont(Font.font ("Verdana", 20));
                viewTextFlow.getChildren().add(tf);
            }else {
            // Cas des autres mots
                Text tx =new Text(wholeText.get(i) + " ");
                tx.setFont(Font.font ("Verdana", 20));
                tx.setFill(Color.rgb(197,176,40));
                viewTextFlow.getChildren().add(tx);
            }
        }

    }

    /**
     * Change la couleur du mot pour indiquer qu'il a été correctement écris.
     * @param index la position du mot
     */
    public void colorCorrectWord(int index){
        TextTrue tt = new TextTrue(this.fullText.get(index)+" ");
        tt.setFont(Font.font ("Verdana", 20));
        viewTextFlow.getChildren().add(index, tt);
    }

    public void colorIncorrectWord(int index){
        TextFalse tf = new TextFalse(this.fullText.get(index)+" ");
        tf.setFont(Font.font ("Verdana", 20));
        viewTextFlow.getChildren().add(index, tf);
    }

    public void updateText(List<String> text){
        this.fullText = text;
        viewTextFlow.getChildren().clear();
        for (String word : text) {
            // Cas des autres mots
            Text tx =new Text(word + " ");
            tx.setFont(Font.font ("Verdana", 20));
            tx.setFill(Color.rgb(197,176,40));
            viewTextFlow.getChildren().add(tx);
        }

    }

    /**
     * Met à jour le nombre de mots correctes validés.
     * @param count le nombre de mots correctes
     */
    public void setCorrectsWordCount(int count){
        correctsWordCount.setText(""+count);
    }

    public void displayChronometer(int time){
        this.FalseWordsCount.setText(time + "");
    }

    /**
     * Met à jour le nombre de mots incorrectes validés.
     * @param count le nombre de mots incorrectes
     */
    public void setFalseWordsCount(int count) {
        FalseWordsCount.setText(""+count);
    }


}
