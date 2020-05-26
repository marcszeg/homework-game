package homeworkGame.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Slf4j
public class RulesController {

    @Inject
    private FXMLLoader fxmlLoader;

    @FXML
    private void initialize() {
        log.debug("Loading rules...");
    }

    public void handleMenuButton(ActionEvent actionEvent) throws IOException {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Loading launch scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}