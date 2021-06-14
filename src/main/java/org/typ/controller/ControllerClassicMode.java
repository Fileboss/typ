package org.typ.controller;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.typ.controller.menu.Command;

import org.typ.model.ClassicCorrector;
import org.typ.model.ClassicStatisticsReport;
import org.typ.model.GameOverException;
import org.typ.model.SimpleTimedStatisticsProxy;
import org.typ.model.TimedStatistics;
import org.typ.view.ViewClassicMode;

import java.util.List;

import java.io.IOException;

public class ControllerClassicMode extends VBox {


    private ClassicCorrector model;

    @FXML
    private TextField textInput;

    @FXML
    private Button exitButton;

    @FXML
    private Button replayButton;

    /**
     * Constructeur de la classe ControllerClassicMode
     * @param model : modele
     */
    public ControllerClassicMode(ClassicCorrector model) {
        super(20);
        this.model = model;
    }

    // Définition du comportement lors d'un changement sur le textInput. (validation par caractère)
    private void validateCharacters(ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
        model.evaluateCharacters(newValue);
    }

    private void test(Observable observable) {
    }

    /**
     * Méthode invoquée lors du click sur le bouton "quitter". Ferme l'application.
     * @param e
     */
    @FXML
    private void onClickExitButton(ActionEvent e) {
        System.exit(0);
    }

    /**
     * Méthode invoquée lors du click sur le bouton rejouer.
     * Réinitialise la partie.
     * @param e
     */
    @FXML
    private void onClickReplayButton(ActionEvent e) {

        model.stop();
        model.initialize();
        textInput.setText("");
        textInput.setEditable(true);

    }

    @FXML
    public void onSpacePressed(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if (keyPressed == KeyCode.SPACE && !textInput.getText().isEmpty() && !model.isGameOver()) {
            try {
                String word = textInput.getText();
                // Notifie evaluate character
                textInput.setText("");
                model.evaluateWord(word);
                model.nextWord();
            } catch (GameOverException gameOverException) {
                model.stop();
                textInput.setEditable(false);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exception");
                alert.setHeaderText(null);
                alert.setContentText(gameOverException.getMessage());

                EventHandler<DialogEvent> event =
                        e1 -> onClickReplayButton(null);

                alert.setOnCloseRequest(event);
                alert.showAndWait();
            }
        }
        if (model.isGameOver()){
            model.stop();
            textInput.setEditable(false);

            try {
                ClassicStatisticsReport.generateFile(model.getStats());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Fin de la partie");

            EventHandler<DialogEvent> event =
                    e1 -> onClickReplayButton(null);
            alert.setOnCloseRequest(event);
            alert.showAndWait();
        }
    }

    //TODO moi je veux renommer bind peut être à faire dans une autre fonction
    public void start(ViewClassicMode view) {
        textInput.textProperty().addListener(this::validateCharacters);
        SimpleTimedStatisticsProxy stats = (SimpleTimedStatisticsProxy) model.getStats();

        stats.nbCorrectWordsProperty().addListener((observable, oldval, newval) ->
                view.setCorrectsWordCount((Integer) newval)
        );

        stats.nbIncorrectWordsProperty().addListener((observable, oldval, newval) ->
                view.setFalseWordsCount((Integer) newval)
        );

        model.getText().addListener((ListChangeListener<? super String>) (c) -> {
            c.next();
            view.updateText(((List<String>) c.getList()));
        });

        ((TimedStatistics)stats).timeProperty().addListener((observable, oldval, newval) ->
                view.displayChronometer((Integer) newval)
        );
    }

    /**
     * Définit la commande à exécuter lors de l'activation du bouton `exit`
     * @param commande
     */
    public void setActionExitButton(Command commande) {
        this.exitButton.setOnAction(event -> {
            commande.execute();
            model.stop();
        });
    }

}
