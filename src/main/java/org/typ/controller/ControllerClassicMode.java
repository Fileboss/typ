package org.typ.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.typ.model.ModelTest;

public class ControllerClassicMode extends VBox {


    private ModelTest model;
    private HBox hBoxQuitterRejouer;
    private TextField textInput;
    private Button exitButton;
    private Button replayButton;

    /**
     * Constructeur de la classe ControllerClassicMode
     * @param model : modele
     */
    public ControllerClassicMode(ModelTest model) {
        super(20);
        this.model = model;
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
            if (keyPressed == KeyCode.SPACE && !textInput.getText().isEmpty()) {
                model.isValidWord(textInput.getText());
                textInput.setText("");
                model.incrementIndice();
            }
        });

        exitButton = new Button("Exit");
        replayButton = new Button("Replay");
        replayButton.setOnAction(this::onClickReplayButton);
        exitButton.setOnAction(this::onClickExitButton);
        hBoxQuitterRejouer = new HBox(500, exitButton, replayButton);
        this.getChildren().addAll(textInput, hBoxQuitterRejouer);

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
        System.out.println("Replay ??");
        model.reset();
        textInput.setText("");
    }




}
