package org.typ.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.typ.modele.ModeleTest;
import org.typ.vue.VueClassicMode;

public class ControllerClassicMode extends VBox {


    private ModeleTest modele;
    private HBox hBoxQuitterRejouer;
    private TextField textInput;
    private Button exitButton;
    private Button replayButton;

    /**
     *
     * @param modele : modele
     */
    public ControllerClassicMode(ModeleTest modele) {
        super(20);
        this.modele = modele;
        textInput = new TextField();
        textInput.setOnKeyReleased(e -> {
            //System.out.println("released : "+e.getCode());
            KeyCode keyPressed = e.getCode();
            if (keyPressed == KeyCode.SPACE) {
                modele.isValidWord(textInput.getText());
                textInput.setText("");
                modele.incrementIndice();
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
        modele.reset();
        textInput.setText("");
    }




}
