package aureo.anaerosync;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
import java.util.List;

import java.util.Random;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

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
    private VBox cardInfoBox;

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

    @FXML
    private Button tradeButton;
    
    @FXML
    private VBox tradePlayerModal;
    
    @FXML
    private VBox tradePlayerChoiceBox;
    
    @FXML
    private HBox tradeCardsModal;
    
    @FXML
    private FlowPane currentPlayerCards;
    
    @FXML
    private FlowPane selectedPlayerCards;
    
    @FXML
    private Label tradePlayerLabel;

    @FXML
    private Button confirmTradeButton;
    
    @FXML
    private Button cancelTradeButton;

    @FXML
    private VBox currentPlayerResources;
    
    @FXML
    private VBox selectedPlayerResources;
    
    private int moneyToGive = 0;
    private int timeToGive = 0;
    private int moneyToReceive = 0;
    private int timeToReceive = 0;

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

    private Task selectedCardToTrade;
    private Task selectedCardToReceive;
    private Player tradePlayer;

    // Add these fields after the existing fields
    private List<Task> selectedCardsToGive = new ArrayList<>();
    private List<Task> selectedCardsToReceive = new ArrayList<>();

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
        if (PositionManager.isTaskPosition(position)) {
            showTaskDialog(PositionManager.getTaskAtPosition(position));
        } else if (PositionManager.isOtherPosition(position)) {
            handleSpecialPosition(position);
        }
    }

    private void showTaskDialog(Task task) {
        cardInfoBox.getChildren().clear();
        
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
        Text taskCost = new Text(String.format("Cost: Money %d, Trust %d",
            task.getTaskMoney(),
            task.getTaskTrustNeeded()));
        
        taskName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        cardInfoBox.getChildren().addAll(
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
            cardInfoBox.getChildren().add(ownerText);
            
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
        
        cardInfoBox.setVisible(true);
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
        cardInfoBox.setVisible(false);
        acceptTaskButton.setVisible(false);
        declineTaskButton.setVisible(false);
        offerTaskButton.setVisible(false);
    }

    private void handleSpecialPosition(int position) {
        String specialType = PositionManager.getOthersAtPosition(position);
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

    @FXML
    public void showTradeModal() {
        tradePlayerChoiceBox.getChildren().clear();
        
        // Add radio buttons for each player except current player
        ToggleGroup group = new ToggleGroup();
        for (int i = 0; i < numPlayers; i++) {
            if (i != currentPlayer) {
                RadioButton rb = new RadioButton(players[i].getName());
                rb.setToggleGroup(group);
                rb.setUserData(players[i]);
                tradePlayerChoiceBox.getChildren().add(rb);
            }
        }
        
        // Only show trade modal if there are players to trade with
        if (!tradePlayerChoiceBox.getChildren().isEmpty()) {
            tradePlayerModal.setVisible(true);
        } else {
            showErrorDialog.setText("No players available to trade with!");
        }
    }

    private void showTradeCardsModal(Player player) {
        tradePlayer = player;
        tradePlayerLabel.setText(player.getName() + "'s Cards");
        
        // Reset all trade values
        selectedCardToTrade = null;
        selectedCardToReceive = null;
        moneyToGive = 0;
        timeToGive = 0;
        moneyToReceive = 0;
        timeToReceive = 0;
        selectedCardsToGive.clear();
        selectedCardsToReceive.clear();

        // Create main container with horizontal layout
        HBox mainContainer = new HBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        
        // Current player's section
        VBox currentPlayerSection = new VBox(5);
        currentPlayerSection.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1;");
        Text currentPlayerTitle = new Text("Your Offer");
        currentPlayerTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Resource inputs for current player
        HBox currentResourceInputs = new HBox(10);
        VBox moneyInputBox = createResourceInput("Money to give:", 
            players[currentPlayer].getMoneyResource());
        VBox timeInputBox = createResourceInput("Time to give:", 
            players[currentPlayer].getTimeResource());
        currentResourceInputs.getChildren().addAll(moneyInputBox, timeInputBox);
        
        // Cards section for current player
        Text currentPlayerCardsText = new Text("Your Cards to Trade:");
        currentPlayerCardsText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        // Container for current player's cards - Ignacio can change this according to what he wants
        currentPlayerCards = new FlowPane();
        currentPlayerCards.setHgap(10);
        currentPlayerCards.setVgap(10);
        currentPlayerCards.setPrefWrapLength(330);
        currentPlayerCards.setStyle("-fx-padding: 5;");
        
        currentPlayerSection.getChildren().addAll(
            currentPlayerTitle,
            currentResourceInputs,
            currentPlayerCardsText,
            currentPlayerCards
        );
        
        // Add current player's cards
        for (Task task: players[currentPlayer].getOwnedTasks()){
            VBox cardBox = createTaskCardForTrade(task, true);
            currentPlayerCards.getChildren().add(cardBox);
        }

        // Center section with trade controls
        VBox centerControls = new VBox(10);
        centerControls.setAlignment(Pos.CENTER);
        
        // Trade direction indicator
        Text tradeArrow = new Text("⇄");
        tradeArrow.setStyle("-fx-font-size: 24px;");
        
        // Trade buttons
        confirmTradeButton = new Button("Confirm Trade");
        cancelTradeButton = new Button("Cancel");
        
        confirmTradeButton.setOnAction(e -> confirmTrade());
        cancelTradeButton.setOnAction(e -> cancelTrade());
        
        centerControls.getChildren().addAll(tradeArrow, confirmTradeButton, cancelTradeButton);

        // Selected player's section
        VBox selectedPlayerSection = new VBox(5);
        selectedPlayerSection.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1;");
        Text selectedPlayerTitle = new Text(player.getName() + "'s Offer");
        selectedPlayerTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Resource inputs for selected player
        HBox selectedResourceInputs = new HBox(10);
        VBox moneyReceiveBox = createResourceInput("Money to receive:", 
            player.getMoneyResource());
        VBox timeReceiveBox = createResourceInput("Time to receive:", 
            player.getTimeResource());
        selectedResourceInputs.getChildren().addAll(moneyReceiveBox, timeReceiveBox);
        
        // Cards section for selected player
        Text selectedPlayerCardsText = new Text("Their Cards to Trade:");
        selectedPlayerCardsText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        // Container for selected player's cards
        selectedPlayerCards = new FlowPane();
        selectedPlayerCards.setHgap(10);
        selectedPlayerCards.setVgap(10);
        selectedPlayerCards.setPrefWrapLength(330); // Width for 3 cards (100px) + gaps
        selectedPlayerCards.setStyle("-fx-padding: 5;");
        
        selectedPlayerSection.getChildren().addAll(
            selectedPlayerTitle,
            selectedResourceInputs,
            selectedPlayerCardsText,
            selectedPlayerCards
        );
        
        // Add selected player's cards
        for (Task task: player.getOwnedTasks()) {
            VBox cardBox = createTaskCardForTrade(task, false);
            selectedPlayerCards.getChildren().add(cardBox);
        }

        // Add all sections to main container
        mainContainer.getChildren().addAll(currentPlayerSection, centerControls, selectedPlayerSection);

        // Update trade modal
        tradeCardsModal.getChildren().clear();
        tradeCardsModal.getChildren().add(mainContainer);
        tradeCardsModal.setVisible(true);
    }

    private VBox createResourceInput(String label, int maxValue) {
        VBox container = new VBox(5);
        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size: 12px;");
        
        // Create text field for input
        TextField inputField = new TextField("0");
        inputField.setPrefWidth(80);
        
        // This one I got it from ChatGPT no idea how to implement in JavaFX
        inputField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        
        // Add listener for value changes
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    // Ensure value doesn't exceed max
                    if (value > maxValue) {
                        inputField.setText(String.valueOf(maxValue));
                        value = maxValue;
                    }
                    
                    // Update the appropriate resource value based on the label
                    if (label.contains("Money to give")) {
                        moneyToGive = value;
                    } else if (label.contains("Time to give")) {
                        timeToGive = value;
                    } else if (label.contains("Money to receive")) {
                        moneyToReceive = value;
                    } else if (label.contains("Time to receive")) {
                        timeToReceive = value;
                    }
                    
                    // Enable trade button if any resources are selected
                    boolean hasResources = moneyToGive > 0 || timeToGive > 0 || 
                                        moneyToReceive > 0 || timeToReceive > 0;
                    boolean hasCards = !selectedCardsToGive.isEmpty() || !selectedCardsToReceive.isEmpty();
                    confirmTradeButton.setDisable(!(hasResources || hasCards));
                    
                } catch (NumberFormatException e) {
                    inputField.setText("0");
                }
            } else {
                inputField.setText("0");
            }
        });
        
        // Add max value indicator
        Text maxValueText = new Text("(Max: " + maxValue + ")");
        maxValueText.setStyle("-fx-font-size: 10px; -fx-fill: #666666;");
        
        container.getChildren().addAll(labelText, inputField, maxValueText);
        return container;
    }

    @FXML
    private void confirmTrade() {
        // Validate if any cards or resources are selected not that efficient but couldn't think of a better way
        if (selectedCardsToGive.isEmpty() && selectedCardsToReceive.isEmpty() && moneyToGive == 0 && timeToGive == 0 && moneyToReceive == 0 && timeToReceive == 0) {
            showErrorDialog.setText("Please select at least one card or resource to trade");
            return;
        }

        // Process resources first
        if (moneyToGive > 0 || timeToGive > 0) {
            if (players[currentPlayer].getMoneyResource() >= moneyToGive &&
                players[currentPlayer].getTimeResource() >= timeToGive) {
                players[currentPlayer].setMoneyResource(players[currentPlayer].getMoneyResource() - moneyToGive);
                players[currentPlayer].setTimeResource(players[currentPlayer].getTimeResource() - timeToGive);
                tradePlayer.setMoneyResource(tradePlayer.getMoneyResource() + moneyToGive);
                tradePlayer.setTimeResource(tradePlayer.getTimeResource() + timeToGive);
            } else {
                showErrorDialog.setText("Not enough resources to give!");
                return;
            }
        }

        if (moneyToReceive > 0 || timeToReceive > 0) {
            if (tradePlayer.getMoneyResource() >= moneyToReceive &&
                tradePlayer.getTimeResource() >= timeToReceive) {
                tradePlayer.setMoneyResource(tradePlayer.getMoneyResource() - moneyToReceive);
                tradePlayer.setTimeResource(tradePlayer.getTimeResource() - timeToReceive);
                players[currentPlayer].setMoneyResource(players[currentPlayer].getMoneyResource() + moneyToReceive);
                players[currentPlayer].setTimeResource(players[currentPlayer].getTimeResource() + timeToReceive);
            } else {
                showErrorDialog.setText("Your team mate does not have enough resources!");
                return;
            }
        }

        // Process cards to give
        for (Task cardToGive : selectedCardsToGive) {
            players[currentPlayer].getOwnedTasks().remove(cardToGive);
            tradePlayer.getOwnedTasks().add(cardToGive);
            cardToGive.setOwner(tradePlayer);
        }

        // Process cards to receive
        for (Task cardToReceive : selectedCardsToReceive) {
            tradePlayer.getOwnedTasks().remove(cardToReceive);
            players[currentPlayer].getOwnedTasks().add(cardToReceive);
            cardToReceive.setOwner(players[currentPlayer]);
        }

        // Trade Summary
        StringBuilder message = new StringBuilder("Trade completed! ");
        if (!selectedCardsToGive.isEmpty()) {
            message.append("You gave ").append(selectedCardsToGive.size()).append(" cards. ");
        }
        if (!selectedCardsToReceive.isEmpty()) {
            message.append("You received ").append(selectedCardsToReceive.size()).append(" cards. ");
        }
        if (moneyToGive > 0 || timeToGive > 0) {
            message.append("You gave $").append(moneyToGive).append(" and ").append(timeToGive).append(" time. ");
        }
        if (moneyToReceive > 0 || timeToReceive > 0) {
            message.append("You received $").append(moneyToReceive).append(" and ").append(timeToReceive).append(" time.");
        }
        showErrorDialog.setText(message.toString());

        // Reset trade state
        selectedCardsToGive.clear();
        selectedCardsToReceive.clear();
        tradeCardsModal.setVisible(false);
        setupPlayerInfo(); // Update the player info display
    }

    @FXML
    public void confirmTradePlayer() {
        Player player = null;

        for (Node node: tradePlayerChoiceBox.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) node;
                if (radioButton.isSelected()) {
                    player = (Player) radioButton.getUserData();
                    break;
                }
            }
        }

        if (player != null) {
            tradePlayerModal.setVisible(false);
            showTradeCardsModal(player);
        }
    }

    @FXML
    public void cancelTradePlayer() {
        tradePlayerModal.setVisible(false);
    }

    private VBox createTaskCardForTrade(Task task, boolean isCurrentPlayer) {
        VBox cardBox = new VBox(10);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(10));
        cardBox.setStyle("-fx-border-color: transparent; -fx-border-width: 2;");

        String imagePath = task.getTaskCard();
        if (imagePath != null) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView taskImage = new ImageView(image);
            taskImage.setFitWidth(100);
            taskImage.setPreserveRatio(true);
            taskImage.setSmooth(true);
            cardBox.getChildren().add(taskImage);
        }

        // Ignacio could format this more to be better
        Label taskName = new Label(task.getTaskName());
        taskName.setWrapText(true);
        taskName.setMaxWidth(90);
        taskName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        taskName.setStyle("-fx-font-size: 11px;");
        cardBox.getChildren().add(taskName);

        // Update selection logic to handle multiple cards
        cardBox.setOnMouseClicked(event -> {
            if (isCurrentPlayer) {
                if (selectedCardsToGive.contains(task)) {
                    selectedCardsToGive.remove(task);
                    cardBox.setStyle("-fx-border-color: transparent; -fx-border-width: 2;");
                } else {
                    selectedCardsToGive.add(task);
                    cardBox.setStyle("-fx-border-color: blue; -fx-border-width: 2; -fx-border-radius: 5;");
                }
            } else {
                if (selectedCardsToReceive.contains(task)) {
                    selectedCardsToReceive.remove(task);
                    cardBox.setStyle("-fx-border-color: transparent; -fx-border-width: 2;");
                } else {
                    selectedCardsToReceive.add(task);
                    cardBox.setStyle("-fx-border-color: green; -fx-border-width: 2; -fx-border-radius: 5;");
                }
            }
        });

        // Add hover effect for better interactivity
        cardBox.setOnMouseEntered(e -> {
            if (!selectedCardsToGive.contains(task) && !selectedCardsToReceive.contains(task)) {
                cardBox.setStyle("-fx-border-color: #dddddd; -fx-border-width: 2; -fx-border-radius: 5;");
            }
        });
        
        cardBox.setOnMouseExited(e -> {
            if (!selectedCardsToGive.contains(task) && !selectedCardsToReceive.contains(task)) {
                cardBox.setStyle("-fx-border-color: transparent; -fx-border-width: 2;");
            }
        });

        return cardBox;
    }

    @FXML
    private void cancelTrade() {
        selectedCardsToGive.clear();
        tradeCardsModal.setVisible(false);
        selectedCardsToReceive.clear();
        tradePlayerModal.setVisible(false);
    }
}
