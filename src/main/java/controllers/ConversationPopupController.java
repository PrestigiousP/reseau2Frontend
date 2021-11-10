package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.reseau.App;

public class ConversationPopupController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void toPerson(ActionEvent actionEvent) throws IOException {
        App.setRoot("create_conv_person");
    }

    public void toGroup(ActionEvent actionEvent) {
    }

    public void toEveryone(ActionEvent actionEvent) {
    }
}
