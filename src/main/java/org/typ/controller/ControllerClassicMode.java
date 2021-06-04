package org.typ.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.typ.model.ClassicCorrector;
import org.typ.model.GameOverException;

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

        // Définition du comportement lors d'un changement sur le textInput. (validation par caractère)

        // TODO
        //textInput.textProperty().addListener(this::validateCharacters);

    }

    private void validateCharacters(ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
        model.evaluateCharacters(newValue);
    }

    /**
     * Méthode invoquée lors du click sur le bouton "quitter". Ferme l'application.
     * @param e
     */
    @FXML
    private void onClickExitButton(ActionEvent e) {
        System.exit(1);
    }

    /**
     * Méthode invoquée lors du click sur le bouton rejouer.
     * Réinitialise la partie.
     * @param e
     */
    @FXML
    private void onClickReplayButton(ActionEvent e) {

        model.initialize();
        model.start();
        textInput.setText("");
        textInput.setEditable(true);

    }

    @FXML
    public void onSpacePressed(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        if (keyPressed == KeyCode.SPACE && !textInput.getText().isEmpty() && !model.isGameOver()) {
            try {
                System.out.println(textInput.getText());
                model.evaluateWord(textInput.getText());
                textInput.setText("");
                model.nextWord();
            } catch (GameOverException gameOverException) {
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
            textInput.setEditable(false);

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

    public void start() {
        textInput.textProperty().addListener(this::validateCharacters);
    }
}
