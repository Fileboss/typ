package org.typ.view.menu;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/*
Controller of the menu.fxml
 */
public class MenuController {
    @FXML
    private Text menuTitle; // Title of the menu

    @FXML
    private VBox commandList; // All commands of the menu

    /*
    Set the title of the menu
     */
    public void setTitle(String title) {
        this.menuTitle.setText(title);
    }

    /*
    Add a command to the menu
     */
    public void addCommandButton(String name, Command command) {
        this.commandList.getChildren().add(new MenuButton(name, command));
    }
}
