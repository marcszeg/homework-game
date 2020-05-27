package homeworkGame.javafx.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import homeworkGame.results.GameResult;
import homeworkGame.results.GameResultDao;
import homeworkGame.state.HomeworkGameState;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private GameResultDao gameResultDao;

    private String bluePlayerName;
    private String redPlayerName;
    private String winnerName;
    private String opponentName;
    private boolean isGonnaSelect;
    private int fromRow;
    private int fromCol;
    private int currentPlayer;
    private HomeworkGameState gameState;
    private Instant startTime;
    private List<Image> discImages;

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button giveUpButton;

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    public void setBluePlayerName(String bluePlayerName) {
        this.bluePlayerName = bluePlayerName;
    }

    public void setRedPlayerName(String redPlayerName) {
        this.redPlayerName = redPlayerName;
    }

    @FXML
    public void initialize() {
        discImages = List.of(
                new Image(getClass().getResource("/images/empty.png").toExternalForm()),
                new Image(getClass().getResource("/images/blue.png").toExternalForm()),
                new Image(getClass().getResource("/images/red.png").toExternalForm())
        );
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
                stopWatchTimeline.stop();
            }

        });
        resetGame();
    }

    private void resetGame() {
        gameState = new HomeworkGameState();
        currentPlayer = 1;
        isGonnaSelect = true;
        startTime = Instant.now();
        gameOver.setValue(false);
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText("*" + bluePlayerName + "* VS " + redPlayerName));
    }

    private void displayGameState() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 5 + j);
                if (view.getImage() != null) {
                    log.trace("Image({}, {}) = {}", i, j, view.getImage().getUrl());
                }
                view.setImage(discImages.get(gameState.getBoard()[i][j].getValue()));
            }
        }
        if (currentPlayer == 1) {
            messageLabel.setText("*" + bluePlayerName + "* VS " + redPlayerName);
        }
        else
            messageLabel.setText(bluePlayerName + " VS *" + redPlayerName + "*");

        if (gameState.isBlueWon()) {
            messageLabel.setText(bluePlayerName + " won the Game!");
        } else if (gameState.isRedWon()) {
            messageLabel.setText(redPlayerName + " won the Game!");
        }
    }

    public void handleClick (MouseEvent mouseEvent) {
        if (isGonnaSelect)
        {
            fromRow = GridPane.getRowIndex((Node) mouseEvent.getSource());
            fromCol = GridPane.getColumnIndex((Node) mouseEvent.getSource());
            log.info("Disc selected at ({}, {})", fromRow, fromCol);
            isGonnaSelect = false;
            displayGameState();
        }
        else if (!isGonnaSelect) {
            int toRow = GridPane.getRowIndex((Node) mouseEvent.getSource());
            int toCol = GridPane.getColumnIndex((Node) mouseEvent.getSource());
            if (!gameState.isBlueWon() && !gameState.isRedWon() && gameState.canMove(fromRow, fromCol, toRow, toCol, currentPlayer)) {
                gameState.moveDisc(fromRow, fromCol, toRow, toCol, currentPlayer);
                log.info("Disc moved to ({}, {})", toRow, toCol);
                if (gameState.isBlueWon()) {
                    gameOver.setValue(true);
                    log.info("Player {} has won the game", bluePlayerName);
                    //messageLabel.setText(bluePlayerName + "won the Game!");
                    winnerName = bluePlayerName;
                    opponentName = redPlayerName;
                    giveUpButton.setText("Finish");
                    gameResultDao.persist(createGameResult());
                } else if (gameState.isRedWon()) {
                    gameOver.setValue(true);
                    log.info("Player {} has won the game", redPlayerName);
                    //messageLabel.setText(redPlayerName + "won the Game!");
                    winnerName = redPlayerName;
                    opponentName = bluePlayerName;
                    giveUpButton.setText("Finish");
                    gameResultDao.persist(createGameResult());
                }

                if (currentPlayer == 1)
                    currentPlayer = 2;
                else
                    currentPlayer = 1;
            }
            isGonnaSelect = true;
            displayGameState();
        }

    }

    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
        }
        gameOver.setValue(true);
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/scores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private GameResult createGameResult() {
        GameResult result = GameResult.builder()
                .winPlayer(winnerName)
                .opPlayer(opponentName)
                .duration(Duration.between(startTime, Instant.now()))
                .build();
        return result;
    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }

}