package aureo.anaerosync;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;

import java.util.Random;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;

public class boardGameController {
    // Initialization
    @FXML
    private Label diceResult;

    @FXML
    private Circle currentPlayerIndicator;

    @FXML
    private Circle B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28;

    @FXML
    private Circle G1, G2, G3, G4, G5, G6, G7, G8, G9, G10, G11, G12, G13, G14, G15, G16, G17, G18, G19, G20, G21, G22, G23, G24, G25, G26, G27, G28;

    @FXML
    private Circle O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20, O21, O22, O23, O24, O25, O26, O27, O28;

    @FXML
    private Circle R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27, R28;

    @FXML
    private VBox playerInfoContainer;

    @FXML
    private Button acceptTaskButton;

    @FXML
    private Button declineTaskButton;

    @FXML
    private VBox taskInfoBox;

    @FXML
    private Label showErrorDialog;

    @FXML
    private Button rollDiceButton;

    @FXML
    private Button endTurnButton;

    @FXML
    private Button offerTaskButton;
    
    @FXML
    private VBox offerModal;
    
    @FXML
    private VBox playerChoiceBox;
    
    @FXML
    private Button confirmOfferButton;
    
    @FXML
    private Button cancelOfferButton;
    
    @FXML
    private VBox offerResponseModal;
    
    @FXML
    private Label offerMessage;
    
    @FXML
    private VBox offeredTaskInfo;
    
    @FXML
    private Button acceptOfferButton;
    
    @FXML
    private Button declineOfferButton;

    private int numPlayers = 4;
    private int currentPlayer = 0;
    private final Color[] playerColors = {
            Color.DODGERBLUE,
            Color.GREEN,
            Color.ORANGE,
            Color.RED
    };

    private final Random random = new Random();

    private static final int STARTING_MONEY = 500;
    private static final int STARTING_TIME = 500;
    private static int SHARED_TRUST = 500;

    /**
     * Initialize players with proper resources
     */
    private Player[] players = {
        new Player(1, "Player 1", STARTING_TIME, STARTING_MONEY, SHARED_TRUST),
        new Player(2, "Player 2", STARTING_TIME, STARTING_MONEY, SHARED_TRUST),
        new Player(3, "Player 3", STARTING_TIME, STARTING_MONEY, SHARED_TRUST),
        new Player(4, "Player 4", STARTING_TIME, STARTING_MONEY, SHARED_TRUST)
    };

    private boolean hasMoved = false;  // Track if current player has moved

    private Task currentOfferedTask;
    private Player offeringPlayer;
    private Player selectedPlayer;

    @FXML
    public void initialize() {
        System.out.println("Initializing controller...");
        
        // Initialize game state
        hideAllCirclesExceptStart();
        updateCurrentPlayerDisplay();
        setupPlayerInfo();
        
        // Initialize buttons
        if (rollDiceButton != null) {
            rollDiceButton.setDisable(false);
            System.out.println("Roll button ready");
        }
        
        if (endTurnButton != null) {
            endTurnButton.setVisible(false);
            System.out.println("End turn button ready");
        }
    }

    // Update setGameData to initialize players with resources
    public void setGameData(String[] playerNames) {
        this.numPlayers = playerNames.length;
        for (int i = 0; i < numPlayers; i++) {
            players[i].setName(playerNames[i]);
            players[i].setPosition(0); // to reset the position but not sure if it is required but its working
        }

        // Initialize the game with the correct number of players
        initializeGame();
        setupPlayerInfo();
    }

    private void initializeGame() {
        hideAllPlayers();
        // Show only the circles for active players
        for (int i = 0; i < numPlayers; i++) {
            Circle[] playerCircles = getPlayerCircles(i);
            playerCircles[0].setVisible(true);
        }
        updateCurrentPlayerDisplay();
    }

    // helper method to hide all the players at the start
    private void hideAllPlayers() {
        for (int i = 0; i < 4; i++) {
            Circle[] circles = getPlayerCircles(i);
            for (Circle circle : circles) {
                circle.setVisible(false);
            }
        }
    }

    // updated so that now it shows the resources
    private void setupPlayerInfo() {
        playerInfoContainer.getChildren().clear();

        // Add shared trust display at the top
        Text sharedTrustText = new Text(String.format("Trust: %d", SHARED_TRUST));
        sharedTrustText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        playerInfoContainer.getChildren().add(sharedTrustText);

        for (int i = 0; i < numPlayers; i++) {
            VBox playerBox = new VBox(5);
            playerBox.setAlignment(Pos.CENTER_LEFT);

            HBox nameRow = new HBox(10);
            nameRow.setAlignment(Pos.CENTER_LEFT);

            Circle playerIndicator = new Circle(8);
            playerIndicator.setFill(playerColors[i]);
            playerIndicator.setStroke(Color.BLACK);

            Text playerInfo = new Text(String.format("Player %d: %s", 
                players[i].getId(), 
                players[i].getName()));
            playerInfo.setStyle("-fx-font-family: 'Chalkboard SE Regular'; -fx-font-size: 16px;");

            nameRow.getChildren().addAll(playerIndicator, playerInfo);

            // Add resource information
            Text moneyText = new Text(String.format("Money: $%d", players[i].getMoneyResource()));
            Text timeText = new Text(String.format("Time: %d", players[i].getTimeResource()));
            
            // Add owned tasks
            Text tasksText = new Text("Owned Tasks:");
            // all the tasks that the player owned will show here
            VBox tasksBox = new VBox(2);
            ArrayList<Task> playerTasks = players[i].getOwnedTasks();
            // checking how many and if the player really has task
            if (playerTasks != null && !playerTasks.isEmpty()) {
                for (Task task : playerTasks) {
                    if (task != null) {
                        Text taskText = new Text("- " + task.getTaskName());
                        taskText.setStyle("-fx-font-size: 10px;");
                        tasksBox.getChildren().add(taskText);
                    }
                }
            }

            moneyText.setStyle("-fx-font-size: 12px;");
            timeText.setStyle("-fx-font-size: 12px;");
            tasksText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

            playerBox.getChildren().addAll(
                nameRow,
                moneyText,
                timeText,
                tasksText,
                tasksBox
            );

            playerInfoContainer.getChildren().add(playerBox);
        }
    }

    // to initialize the start of the game and the player positions
    private void hideAllCirclesExceptStart() {
        for (int i = 0; i < 4; i++) {
            Circle[] circles = getPlayerCircles(i);
            // deleted hider methods since this is more efficient
            if (circles != null) {
                for (int j = 1; j < circles.length; j++) {
                    if (circles[j] != null) {
                        circles[j].setVisible(false);
                    }
                }
                // Make start position visible
                if (circles[0] != null) {
                    circles[0].setVisible(true);
                }
            }
        }
    }

    @FXML
    public void rollDice() {

        // Roll the dice
        int roll = random.nextInt(6) + 1;
        System.out.println("Player " + currentPlayer + " rolled: " + roll);
        
        // Update display
        diceResult.setText("Dice: " + roll);
        
        // Move player
        movePlayer(currentPlayer, roll);
        setupPlayerInfo();

        // Update button states
        rollDiceButton.setDisable(true);
        endTurnButton.setVisible(true);
        hasMoved = true;
    }

    @FXML
    public void endTurn() {
        System.out.println("End turn called");
        endTurnButton.setVisible(false);
        rollDiceButton.setDisable(false);
        hasMoved = false;

        showErrorDialog.setText("");
        currentPlayer = (currentPlayer + 1) % numPlayers;
        updateCurrentPlayerDisplay();
        setupPlayerInfo();
    }

    // to move the player according to the roll dice
    private void movePlayer(int playerIndex, int spaces) {
        try {
            System.out.println("Moving player " + playerIndex + " by " + spaces + " spaces");
            
            int currentPosition = players[playerIndex].getPosition() % 28;
            int newPosition = (currentPosition + spaces) % 28;

            // Debug added these
            System.out.println("Current position: " + currentPosition);
            System.out.println("New position: " + newPosition);

            Circle[] playerCircle = getPlayerCircles(playerIndex);
            
            playerCircle[currentPosition].setVisible(false);
            playerCircle[newPosition].setVisible(true);
            players[playerIndex].setPosition(newPosition);
                
            System.out.println("Player moved successfully");
            checkPosition(newPosition);
        } catch (Exception e) {
            System.out.println("Error in movePlayer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Check if the player is on Task Square or Other squares like corners and luck card
    private void checkPosition(int position) {
        if (TaskManager.isTaskPosition(position)) {
            showTaskDialog(TaskManager.getTaskAtPosition(position));
        } else if (TaskManager.isOtherPosition(position)) {
            handleSpecialPosition(position);
        }
    }

    private void showTaskDialog(Task task) {
        taskInfoBox.getChildren().clear();
        
        // Check if task is already owned
        Player owner = null;
        for (Player player : players) {
            if (player.ownsTask(task)) {
                owner = player;
                break;
            }
        }
        
        // Add task card image
        String imagePath = task.getTaskCard();
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView taskCardView = new ImageView(image);
        taskCardView.setFitWidth(200);
        taskCardView.setPreserveRatio(true);
        
        Text taskName = new Text(task.getTaskName());
        Text taskDesc = new Text(task.getTaskObjective());
        Text taskCost = new Text(String.format("Cost: Money %d, Time %d, Trust %d",
            task.getTaskMoney(), 
            task.getTaskTime(),
            task.getTaskTrustNeeded()));
        
        taskName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        taskInfoBox.getChildren().addAll(
            taskCardView,
            taskName, 
            taskDesc, 
            taskCost
        );

        // If task is owned, show owner and hide buttons
        if (owner != null) {
            // to show the owner and made it red to make it visible
            Text ownerText = new Text(String.format("Owned by: %s", owner.getName()));
            ownerText.setStyle("-fx-font-size: 14px; -fx-fill: #e74c3c;");
            taskInfoBox.getChildren().add(ownerText);
            
            acceptTaskButton.setVisible(false);
            declineTaskButton.setVisible(false);
            offerTaskButton.setVisible(false);
        } else {
            acceptTaskButton.setVisible(true);
            declineTaskButton.setVisible(true);
            offerTaskButton.setVisible(true);
            
            acceptTaskButton.setOnAction(e -> acceptTask(task));
            declineTaskButton.setOnAction(e -> declineTask());
            offerTaskButton.setOnAction(e -> showOfferModal(task));
        }
        
        taskInfoBox.setVisible(true);
    }

    // to accept the task for yourself
    private void acceptTask(Task task) {
        Player currentPlayerObj = players[currentPlayer];
        
        // Check if task is already owned
        for (Player player : players) {
            if (player.ownsTask(task)) {
                showErrorDialog.setText("Task already owned by " + player.getName());
                return;
            }
        }
        
        // Check if player has enough resources
        if (currentPlayerObj.getMoneyResource() >= task.getTaskMoney() && 
            currentPlayerObj.getTimeResource() >= task.getTaskTime() &&
            SHARED_TRUST >= task.getTaskTrustNeeded()) {
            
            // Deduct resources
            currentPlayerObj.setMoneyResource(currentPlayerObj.getMoneyResource() - task.getTaskMoney());
            currentPlayerObj.setTimeResource(currentPlayerObj.getTimeResource() - task.getTaskTime());
            SHARED_TRUST -= task.getTaskTrustNeeded();
            
            // Add task to player's owned tasks
            currentPlayerObj.addTask(task);
            task.setOwner(currentPlayerObj);
            
            // Update display
            setupPlayerInfo();
            hideTaskDialog();
        } else {
            if (SHARED_TRUST < task.getTaskTrustNeeded()) {
                showErrorDialog.setText("Not enough team trust!");
            } else {
                showErrorDialog.setText("Not enough personal resources!");
            }
        }
    }

    private void declineTask() {
        hideTaskDialog();
    }

    private void hideTaskDialog() {
        taskInfoBox.setVisible(false);
        acceptTaskButton.setVisible(false);
        declineTaskButton.setVisible(false);
        offerTaskButton.setVisible(false);
    }

    private void handleSpecialPosition(int position) {
        String specialType = TaskManager.getOthersAtPosition(position);
        switch (specialType) {
            case "HOME":
                // Handle home position
                break;
            case "LUCK":
                // Handle luck position
                break;
            case "CORNER":
                // Handle corner position
                break;
        }
    }

    // getting the circles which represent each player's positions
    private Circle[] getPlayerCircles(int playerIndex) {
        switch(playerIndex) {
            case 0:
                return new Circle[]{B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28};
            case 1:
                return new Circle[]{G1, G2, G3, G4, G5, G6, G7, G8, G9, G10, G11, G12, G13, G14, G15, G16, G17, G18, G19, G20, G21, G22, G23, G24, G25, G26, G27, G28};
            case 2:
                return new Circle[]{O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20, O21, O22, O23, O24, O25, O26, O27, O28};
            case 3:
                return new Circle[]{R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27, R28};
            default:
                return new Circle[0];
        }
    }

    // to update the player's indication
    private void updateCurrentPlayerDisplay() {
        currentPlayerIndicator.setFill(playerColors[currentPlayer]);
    }

    // for the player to choose which player he wants to offer to
    private void showOfferModal(Task task) {
        currentOfferedTask = task;
        offeringPlayer = players[currentPlayer];
        
        playerChoiceBox.getChildren().clear();
        
        // Add radio buttons for each player except current player
        ToggleGroup group = new ToggleGroup();
        for (int i = 0; i < numPlayers; i++) {
            if (i != currentPlayer) {
                RadioButton rb = new RadioButton(players[i].getName());
                rb.setToggleGroup(group);
                rb.setUserData(players[i]);
                playerChoiceBox.getChildren().add(rb);
            }
        }
        
        confirmOfferButton.setOnAction(e -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected != null) {
                selectedPlayer = (Player) selected.getUserData();
                showOfferToPlayer(task, selectedPlayer);
                offerModal.setVisible(false);
            }
        });
        
        cancelOfferButton.setOnAction(e -> offerModal.setVisible(false));
        
        offerModal.setVisible(true);
    }

    // to show the offer to the another player
    private void showOfferToPlayer(Task task, Player targetPlayer) {
        offerMessage.setText(String.format("%s is offering you: %s", 
            offeringPlayer.getName(), task.getTaskName()));
            
        offeredTaskInfo.getChildren().clear();
        offeredTaskInfo.getChildren().addAll(
            new Text(String.format("Money: %d", task.getTaskMoney())),
            new Text(String.format("Time: %d", task.getTaskTime())),
            new Text(String.format("Trust: %d", task.getTaskTrustNeeded()))
        );
        
        acceptOfferButton.setOnAction(e -> acceptOffer(task, targetPlayer));
        declineOfferButton.setOnAction(e -> {
            offerResponseModal.setVisible(false);
            showErrorDialog.setText("Offer declined");
        });
        
        offerResponseModal.setVisible(true);
    }

    // accept offer functionality
    private void acceptOffer(Task task, Player player) {
        if (player.getMoneyResource() >= task.getTaskMoney() && 
            player.getTimeResource() >= task.getTaskTime() &&
            SHARED_TRUST >= task.getTaskTrustNeeded()) {
            
            // Deduct resources
            player.setMoneyResource(player.getMoneyResource() - task.getTaskMoney());
            player.setTimeResource(player.getTimeResource() - task.getTaskTime());
            SHARED_TRUST -= task.getTaskTrustNeeded();
            
            // Add task to player's owned tasks
            player.addTask(task);
            task.setOwner(player);
            
            // Update display
            setupPlayerInfo();
            offerResponseModal.setVisible(false);
            hideTaskDialog();
            showErrorDialog.setText("Offer accepted!");
        } else {
            showErrorDialog.setText("Not enough resources to accept offer!");
        }
    }
}
