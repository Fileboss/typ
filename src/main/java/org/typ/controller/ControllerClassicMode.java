package org.typ.controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.typ.model.ClassicCorrector;
import org.typ.model.GameOverException;

public class ControllerClassicMode extends VBox {


    private ClassicCorrector model;
    private HBox hBoxQuitterRejouer;
    private TextField textInput;
    private Button exitButton;
    private Button replayButton;

    /**
     * Constructeur de la classe ControllerClassicMode
     * @param model : modele
     */
    public ControllerClassicMode(ClassicCorrector model) {
        super(20);
        this.model = model;
        this.model.start();
        /* Redéfinition du textfield pour refuser les espaces */
        textInput = new TextField() {
            @Override public void replaceText(int start, int end, String text) {
                if (!text.matches("\\s")) {
                    super.replaceText(start, end, text);
                }
            }
            @Override public void replaceSelection(String text) {
                if (!text.matches("\\s")) {
                    super.replaceSelection(text);
                }
            }
        };

        /* Définition du comportement lorsque la barre espace est enfoncée. (validation du mot) */
        textInput.setOnKeyPressed(e -> {
            //System.out.println("released : "+e.getCode());
            KeyCode keyPressed = e.getCode();
            if (keyPressed == KeyCode.SPACE && !textInput.getText().isEmpty() && !model.isGameOver()) {
                try {
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
        });

        // Définition du comportement lors d'un changement sur le textInput. (validation par caractère)
        textInput.textProperty().addListener(this::validateCharacters);

        exitButton = new Button("Exit");
        replayButton = new Button("Replay");
        replayButton.setOnAction(this::onClickReplayButton);
        exitButton.setOnAction(this::onClickExitButton);
        hBoxQuitterRejouer = new HBox(500, exitButton, replayButton);
        this.getChildren().addAll(textInput, hBoxQuitterRejouer);

    }

    private void validateCharacters(ObservableValue<? extends String> observable,
                                    String oldValue, String newValue) {
        model.evaluateCharacters(newValue);
    }

    /**
     * Méthode invoquée lors du click sur le bouton "quitter". Ferme l'application.
     * @param e
     */
    private void onClickExitButton(ActionEvent e) {
        System.exit(1);
    }

    /**
     * Méthode invoquée lors du click sur le bouton rejouer.
     * Réinitialise la partie.
     * @param e
     */
    private void onClickReplayButton(ActionEvent e) {

        model.initialize();
        model.start();
        textInput.setText("");
        textInput.setEditable(true);

    }

}
