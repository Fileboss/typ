package org.typ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.typ.controller.menu.*;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        MenuController controller = new MenuController();
        MenuController playController = new MenuController();
        MenuController settingsController = new MenuController();
        MenuController statisticsController = new MenuController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));
        FXMLLoader playLoader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));
        FXMLLoader statisticsLoader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));

        loader.setController(controller);
        playLoader.setController(playController);
        settingsLoader.setController(settingsController);
        statisticsLoader.setController(statisticsController);

        Parent mainRoot = loader.load();
        Parent playRoot = playLoader.load();
        Parent settingsRoot = settingsLoader.load();
        Parent statisticsRoot = statisticsLoader.load();

        Scene mainScene = new Scene(mainRoot);

        controller.setTitle("Menu");
        controller.addCommandButton("play", new ChangeRootSceneCommand(mainScene, playRoot));
        controller.addCommandButton("statistics", new ChangeRootSceneCommand(mainScene, statisticsRoot));
        controller.addCommandButton("settings", new ChangeRootSceneCommand(mainScene, settingsRoot));
        controller.addCommandButton("exit", new Command() {
            @Override
            public void execute() {
                System.exit(0);
            }
        });

        settingsController.setTitle("Settings");
        settingsController.addCommandButton("back", new ChangeRootSceneCommand(mainScene, mainRoot));

        playController.setTitle("Play");
        playController.addCommandButton("Classic Mode", new StartClassicModeCommand(mainScene));
        //playController.addCommandButton("Classic Mode", null);
        playController.addCommandButton("back", new ChangeRootSceneCommand(mainScene, mainRoot));

        statisticsController.setTitle("Statistics");
        statisticsController.addCommandButton("back",new ChangeRootSceneCommand(mainScene, mainRoot));

        stage.setTitle("Typ");
        stage.setScene(mainScene);

        stage.setMinHeight(475);
        stage.setMinWidth(900);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}