module org.typ {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.typ to javafx.fxml;
    opens org.typ.view to javafx.fxml;
    opens org.typ.view.menu to javafx.fxml;

    requires opencsv;
    requires junit;
    exports org.typ;
    exports org.typ.tests.modelTests;
    exports org.typ.menu;
    opens org.typ.menu to javafx.fxml;
}