package aureo.anaerosync;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class IndexController {
    @FXML private AnchorPane optionsMenu;
    @FXML private AnchorPane credits;
    @FXML private AnchorPane exit;
    @FXML private AnchorPane thankyou;
    @FXML private AnchorPane numPlayersMenu;
    @FXML private AnchorPane nameInput;
    @FXML private AnchorPane Dialog;
    @FXML private AnchorPane Dialog2;
    @FXML private VBox playerNameInputs;
    @FXML private Text warningName;

    private void showPane(Pane showPane) {
        optionsMenu.setVisible(false);
        credits.setVisible(false);
        exit.setVisible(false);
        thankyou.setVisible(false);
        numPlayersMenu.setVisible(false);
        nameInput.setVisible(false);
        Dialog.setVisible(false);
        Dialog2.setVisible(false);

        showPane.setVisible(true);
    }

    private void setupPlayerNameInputs(int numPlayers) {
        playerNameInputs.getChildren().clear();
        
        for (int i = 1; i <= numPlayers; i++) {
            HBox playerRow = new HBox(20);
            playerRow.setAlignment(javafx.geometry.Pos.CENTER);
            
            Label playerLabel = new Label("Player " + i);
            playerLabel.getStyleClass().add("player-label");
            playerLabel.setPrefWidth(100);
            
            TextField nameField = new TextField();
            nameField.setPromptText("Enter name");
            nameField.setPrefWidth(300);
            nameField.getStyleClass().add("name-input");
            
            playerRow.getChildren().addAll(playerLabel, nameField);
            playerNameInputs.getChildren().add(playerRow);
        }
        
        if (warningName != null) {
            warningName.setVisible(false);
        }
        showPane(nameInput);
    }

    @FXML
    public void selectTwoPlayers() {
        setupPlayerNameInputs(2);
    }

    @FXML
    public void selectThreePlayers() {
        setupPlayerNameInputs(3);
    }

    @FXML
    public void selectFourPlayers() {
        setupPlayerNameInputs(4);
    }

    @FXML
    public void backToPlayerSelection() {
        showPane(numPlayersMenu);
    }

    @FXML
    public void startGame() {
        boolean allNamesEntered = true;
        boolean allNamesUnique = true;
        Set<String> nameSet = new HashSet<>();
        
        // Check for empty names and duplicates
        for (int i = 0; i < playerNameInputs.getChildren().size(); i++) {
            HBox playerRow = (HBox) playerNameInputs.getChildren().get(i);
            TextField nameField = (TextField) playerRow.getChildren().get(1);
            String name = nameField.getText().trim();
            
            // Check for empty name
            if (name.isEmpty()) {
                allNamesEntered = false;
                break;
            }
            
            // Check for duplicate name
            if (!nameSet.add(name)) {
                allNamesUnique = false;
                break;
            }
        }
        
        // Determine action based on validation results
        if (!allNamesEntered) {
            // Show warning for empty names
            if (warningName != null) {
                warningName.setText("Please enter all player names!");
                warningName.setVisible(true);
            }
        } else if (!allNamesUnique) {
            // Show warning for duplicate names
            if (warningName != null) {
                warningName.setText("Player names must be unique!");
                warningName.setVisible(true);
            }
        } else {
            // All validations passed, proceed to dialog
            if (warningName != null) {
                warningName.setVisible(false);
            }
            showPane(Dialog);
            System.out.println("Dialog 1 through");
        }
    }

    @FXML
    public void okDialogButtonClicked() {
        showPane(Dialog2);
        System.out.println("Dialog 2 through");
    }

    @FXML
    public void okDialog2ButtonClicked() {
        try {
            // Get player names from text fields
            String[] playerNames = getPlayerNames();
            
            // Load the board game FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aureo/anaerosync/board-game.fxml"));
            Scene boardGameScene = new Scene(loader.load());
            
            // Get the controller and set the player data
            boardGameController controller = loader.getController();
            controller.setGameData(playerNames);
            
            // Switch to the board game scene
            Stage stage = (Stage) Dialog2.getScene().getWindow();
            stage.setScene(boardGameScene);
            stage.setFullScreen(true);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getPlayerNames() {
        return playerNameInputs.getChildren().stream()
            .map(node -> (HBox) node)
            .map(hbox -> {
                TextField nameField = (TextField) hbox.getChildren().get(1);
                String name = nameField.getText().trim();
                return name;
            })
            .toArray(String[]::new);
    }

    @FXML
    public void startButtonOnAction() {
        showPane(numPlayersMenu);
    }

    @FXML
    public void creditsButtonOnAction() {
        showPane(credits);
    }

    @FXML
    public void backButtonOnAction() {
        showPane(optionsMenu);
    }

    @FXML
    public void quitButtonOnAction() {
        showPane(exit);
    }

    @FXML
    public void quitDOnAction() {
        showPane(thankyou);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            Platform.exit();
        });
        pause.play();
    }
}
