module org.reseau {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;

    opens org.reseau to javafx.fxml;
    exports org.reseau;
    exports controllers;
    opens controllers to javafx.fxml;
    opens models to javafx.fxml, gson;
}
