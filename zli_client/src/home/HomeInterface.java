package home;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public interface HomeInterface {
    @FXML
    void exitHomeScreen(MouseEvent event);

    @FXML
    void logoutFromUser(MouseEvent event);

}
