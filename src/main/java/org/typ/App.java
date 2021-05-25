package org.typ;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.typ.controller.ControllerClassicMode;
import org.typ.modele.ModeleTest;
import org.typ.vue.VueClassicMode;
import org.typ.vue.VueClassicModeCanvas;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Typ: classic mode");

        VueClassicMode vue = new VueClassicMode();

        ModeleTest modele = new ModeleTest(vue);

        ControllerClassicMode controller = new ControllerClassicMode(modele);

        VBox layout = new VBox(20, vue, controller);


        layout.setPadding(new Insets(20,20,20,20));
        var scene = new Scene(layout, 800, 600);

        stage.setMinHeight(300);
        stage.setMinWidth(700);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}