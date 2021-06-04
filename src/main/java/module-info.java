module org.typ {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires junit;
    requires java.desktop;
    exports org.typ;
    exports org.typ.tests.modelTests;

    opens org.typ to javafx.fxml;
    opens org.typ.view to javafx.fxml;
    opens org.typ.controller to javafx.fxml;
}