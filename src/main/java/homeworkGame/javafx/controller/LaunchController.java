package homeworkGame.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
public class LaunchController {

    @Inject
    private FXMLLoader fxmlLoader;

    @FXML
    private TextField bluePlayerName;

    @FXML
    private TextField redPlayerName;

    public void showRules(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/rules.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading rules...");
    }

    public void showHighscores(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/scores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Loading highscores...");
    }

    public void startAction(ActionEvent actionEvent) throws IOException {
        fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<GameController>getController().setBluePlayerName(bluePlayerName.getText());
        fxmlLoader.<GameController>getController().setRedPlayerName(redPlayerName.getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        log.info("Blue Players name  is set to {}, and Red Players name is set to {}", bluePlayerName.getText(), redPlayerName.getText());
    }

}