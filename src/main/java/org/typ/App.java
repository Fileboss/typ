package org.typ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.typ.view.menu.*;
//import org.typ.menu.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        MenuController controller = new MenuController();
        MenuController playController = new MenuController();
        MenuController settingsController = new MenuController();
        MenuController statisticsController = new MenuController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        FXMLLoader playLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        FXMLLoader statisticsLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));

        loader.setController(controller);
        playLoader.setController(playController);
        settingsLoader.setController(settingsController);
        statisticsLoader.setController(statisticsController);

        Scene scene = new Scene(loader.load());
        Scene playScene = new Scene(playLoader.load());
        Scene settingsScene = new Scene(settingsLoader.load());
        Scene statisticsScene = new Scene(statisticsLoader.load());

        controller.setTitle("Menu");
        controller.addCommandButton("play", new changeSceneCommand(scene, playScene));
        controller.addCommandButton("statistics", new changeSceneCommand(scene, statisticsScene));
        controller.addCommandButton("settings", new changeSceneCommand(scene, settingsScene));
        controller.addCommandButton("exit", new Command() {
            @Override
            public void execute() {
                System.exit(0);
            }
        });

        settingsController.setTitle("Settings");
        settingsController.addCommandButton("back", new changeSceneCommand(settingsScene, scene));

        playController.setTitle("Play");
        playController.addCommandButton("back", new changeSceneCommand(playScene, scene));

        statisticsController.setTitle("Statistics");
        statisticsController.addCommandButton("back", new changeSceneCommand(statisticsScene, scene));

        stage.setTitle("Typ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}