package aureo.anaerosync;

import aureo.anaerosync.squares.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.io.InputStream;

import java.util.Random;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Set;

public class boardGameController {
    @FXML public Button completeTaskButton, rollDiceButton, acceptTaskButton, declineTaskButton, endTurnButton;
    @FXML public Button offerTaskButton, confirmOfferButton, cancelOfferButton;
    @FXML public Button acceptOfferButton, declineOfferButton, confirmTradeButton, cancelTradeButton;
    @FXML public Button viewOwnedTasksButton, viewCompletedTasksButton, confirmCompleteButton;
    @FXML public Button tradeButton;

    @FXML private Label diceResult, showErrorDialog, offerMessage, tradePlayerLabel;
    @FXML private Label availableTimeLabel, viewTaskField, taskCountLabel, progressPercentage;

    @FXML private Circle currentPlayerIndicator;

    @FXML private VBox playerInfoContainer, cardInfoBox, offerModal, playerChoiceBox, offerResponseModal, offeredTaskInfo;
    @FXML private VBox tradePlayerModal, tradePlayerChoiceBox, completeTaskModal, viewTasksModal;
    @FXML private VBox objectivesPanel, objectivesContainer;

    @FXML private HBox tradeCardsModal, progressBarContainer;

    @FXML private FlowPane currentPlayerCards, selectedPlayerCards, completableTaskCards, taskList;
    @FXML private HBox exit, thankyou;

    @FXML private Circle B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28;
    @FXML private Circle G1, G2, G3, G4, G5, G6, G7, G8, G9, G10, G11, G12, G13, G14, G15, G16, G17, G18, G19, G20, G21, G22, G23, G24, G25, G26, G27, G28;
    @FXML private Circle O1, O2, O3, O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, O14, O15, O16, O17, O18, O19, O20, O21, O22, O23, O24, O25, O26, O27, O28;
    @FXML private Circle R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27, R28;

    @FXML private Label taskNameLabel, taskDescLabel, taskBonusLabel, taskCostLabel, taskOwnerLabel;
    @FXML private ImageView taskCardImage;

    @FXML private Label luckNameLabel, luckDescLabel, luckBonusLabel, luckCostLabel, luckOwnerLabel;
    @FXML private ImageView luckCardImage;
    @FXML private Button Ok;
    @FXML private VBox luckInfoBox;

    @FXML private Label esNameLabel, esDescLabel, esBonusLabel, esCostLabel, esOwnerLabel;
    @FXML private ImageView esCardImage;
    @FXML private Button Ok1;
    @FXML private VBox esInfoBox;

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

    private static final int STARTING_MONEY = 100000;
    private static final int STARTING_TIME = 10000;
    private static int SHARED_TRUST = 10000;
    private static final int TOTAL_TASKS = 20;

    /**
     * Initialize players with proper resources
     */
    private Player[] players = {
        new Player(1, "Player 1", STARTING_TIME, STARTING_MONEY, SHARED_TRUST),
        new Player(2, "Player 2", STARTING_TIME, STARTING_MONEY, SHARED_TRUST),
        new Player(3, "Player 3", STARTING_TIME, STARTING_MONEY, SHARED_TRUST),
        new Player(4, "Player 4", STARTING_TIME, STARTING_MONEY, SHARED_TRUST)
    };

    // ArrayList containing all tasks in the game
    private static final ArrayList<Task> tasks = new ArrayList<Task>();
    private static final ArrayList<Objective> objectives = new ArrayList<Objective>();
    private static final ArrayList<Corner> corners = new ArrayList<>();
    private HashMap<Player, ArrayList<Task>> completedTasks = new HashMap<>();
    private static final ArrayList<Luck> lucks = new ArrayList<Luck>();

    private boolean hasMoved = false;

    private Task currentOfferedTask;
    private Player offeringPlayer;
    private Player selectedPlayer;

    private Task selectedCardToTrade;
    private Task selectedCardToReceive;
    private Player tradePlayer;

    // Add these fields after the existing fields
    private List<Task> selectedCardsToGive = new ArrayList<>();
    private List<Task> selectedCardsToReceive = new ArrayList<>();

    private Task selectedTaskToComplete;

    @FXML
    public void initialize() {
        System.out.println("Initializing controller...");
        exit.setVisible(false);
        thankyou.setVisible(false);

        // Initialize task and square objects
        initializeTasks();
        
        // Initialize luck cards
        initializeLucks();
        
        // Initialize event squares
        initializeEventSquares();
        
        // Initialize the board with both tasks and luck cards
        PositionManager.initializeSquares(tasks, lucks);
        
        // Initialize objectives
        initializeObjectives();

        // Initialize game state
        hideAllCirclesExceptStart();
        updateCurrentPlayerDisplay();
        setupPlayerInfo();
        
        // Initialize progress bar
        initializeProgressBar();

        // Initialize objectives panel
        setupObjectivesPanel();

        // Initialize buttons
        if (rollDiceButton != null) {
            rollDiceButton.setDisable(false);
            System.out.println("Roll button ready");
        }
        
        if (endTurnButton != null) {
            endTurnButton.setDisable(true);
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
        // Initialize tasks, objectives, luck cards, and event squares
        initializeTasks();
        initializeObjectives();
        initializeLucks();
        initializeEventSquares();
        
        // Initialize the board with tasks and luck cards
        PositionManager.initializeSquares(tasks, lucks);
        
        // Hide all player pieces initially
        hideAllPlayers();
        
        // Set up player information display
        setupPlayerInfo();
        
        // Initialize the progress bar
        initializeProgressBar();
        
        // Set up objectives panel
        setupObjectivesPanel();
        
        // Hide exit and thank you screens
        exit.setVisible(false);
        thankyou.setVisible(false);
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

    // Changed the way how the objectives are counted instead of shown each by each for space
    private void setupPlayerInfo() {
        playerInfoContainer.getChildren().clear();

        // Shared-trust display at the top
        Text sharedTrustText = new Text(String.format("Trust: %d", SHARED_TRUST));
        sharedTrustText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold");
        playerInfoContainer.getChildren().add(sharedTrustText);

        for (int i=0; i<numPlayers; i++) {
            Player player = players[i];
            VBox playerBox = new VBox(5);
            playerBox.setStyle("-fx-padding:10; -fx-background-color: "+ toRGBCode(playerColors[i]) + "20;");

            Text nameText = new Text(player.getName());
            nameText.setStyle("-fx-font-weight: bold");
            Text moneyText = new Text("Money: "+player.getMoneyResource());
            Text timeText = new Text("Time: "+player.getTimeResource());
            Text tasksText = new Text("Tasks: "+player.getOwnedTasks().size());

            // Add completed tasks count
            List<Task> playerCompletedTasks = completedTasks.getOrDefault(player, new ArrayList<>());
            Text completedTasksText = new Text("Completed Tasks: " + playerCompletedTasks.size());
            Button viewTasks = new Button();
            viewTasks.setText("View tasks");

            // Store the player reference for the lambda
            final Player currentPlayerForButton = player;

            // Add event handler to view this specific player's owned tasks
            viewTasks.setOnAction(event -> {
                showPlayerOwnedTasks(currentPlayerForButton);
            });

            playerBox.getChildren().addAll(nameText, moneyText, timeText, tasksText, completedTasksText, viewTasks);
            playerInfoContainer.getChildren().addAll(playerBox);
        }
    }

    // Method to help show owned tasks according to the player view task button
    private void showPlayerOwnedTasks(Player player) {
        // Clear previous tasks
        taskList.getChildren().clear();

        // Set the title
        viewTaskField.setText(player.getName() + "'s Owned Tasks");

        // Get owned tasks for this player
        List<Task> playerOwnedTasks = player.getOwnedTasks();

        // Update task count
        taskCountLabel.setText(String.valueOf(playerOwnedTasks.size()));

        // Add each owned task to the display
        for (Task task : playerOwnedTasks) {
            VBox taskCard = createTaskCardsForView(task, false);
            taskList.getChildren().add(taskCard);
        }

        final Player currentPlayerForButton = player;

        viewOwnedTasksButton.setOnAction(event -> {
            showPlayerOwnedTasks(currentPlayerForButton);
        });

        viewCompletedTasksButton.setOnAction(event -> {
            showViewCompletedTasksModal(currentPlayerForButton);
        });

        // Show the modal
        viewTasksModal.setVisible(true);
    }

    // Method to show completed tasks for the current player
    @FXML
    public void showViewCompletedTasksModal(Player player) {
        // Clear previous tasks
        taskList.getChildren().clear();

        // Title
        viewTaskField.setText(player.getName() + "'s Completed Tasks");

        // Get completed tasks for players
        ArrayList<Task> playerCompletedTasks = completedTasks.getOrDefault(player, new ArrayList<>());

        // Update the task count according to how many tasks the player have
        taskCountLabel.setText(String.valueOf(playerCompletedTasks.size()));

        // Add each completed task to display on the modal
        for (Task task : playerCompletedTasks) {
            VBox taskCard = createTaskCardsForView(task, true);
            taskList.getChildren().add(taskCard);
        }

        // Show the modal
        viewTasksModal.setVisible(true);
    }

    // Create task cards to put in view tasks modal
    private VBox createTaskCardsForView(Task task, boolean isCompleted) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(200);
        container.setStyle("-fx-padding: 10;");

        String imagePath = task.getTaskCard();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);

        Image image = new Image(imageStream);
        ImageView taskImage = new ImageView(image);
        taskImage.setFitWidth(180);
        taskImage.setPreserveRatio(true);
        taskImage.setSmooth(true);
        container.getChildren().add(taskImage);

        // Add task details
        VBox detailsBox = new VBox(3);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setPadding(new Insets(5, 0, 0, 0));

        Text taskNameText = new Text(task.getTaskName());
        taskNameText.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        taskNameText.setWrappingWidth(180);

        Text taskTypeText = new Text("Type: " + task.getTaskName().split(":")[0]); // Use first part of name as type
        taskTypeText.setStyle("-fx-font-size: 11px;");

        Text taskTimeText = new Text("Time Required: " + task.getTaskTime());
        taskTimeText.setStyle("-fx-font-size: 11px;");

        Text taskMoneyText = new Text("Money Cost: " + task.getTaskMoney());
        taskMoneyText.setStyle("-fx-font-size: 11px;");

        detailsBox.getChildren().addAll(taskNameText, taskTypeText, taskTimeText, taskMoneyText);
        container.getChildren().add(detailsBox);

        return container;
    }

    // Just to close the view task modal
    @FXML
    private void OkViewTask() {
        viewTasksModal.setVisible(false);
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
        hasMoved = true;
    }

    @FXML
    public void endTurn() {
        System.out.println("End turn called");
        endTurnButton.setDisable(true);
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

            // Check if player passed or landed on home (position 0)
            // We check if currentPosition > newPosition because that means we crossed position 0
            if (currentPosition > newPosition) {
                // give player resources for passing home
                players[playerIndex].addMoney(100);
                players[playerIndex].addTime(100);
                showErrorDialog.setText("You passed home! Received 100 money and 100 time.");
            }

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

    // Initialization using Ignacio's tasks
    private static void initializeTasks() {
        tasks.add(new Task(1, "Research on Anaerobic Digesters", 50, 0, 50, 10, 10, 25, "RESEARCH", "/images/1.png", 10));
        tasks.add(new Task(2, "Research on Makers Valley", 60, 0, 65, 12, 13, 25, "RESEARCH", "/images/2.png", 10));
        tasks.add(new Task(3, "Design Use Cases", 80, 0, 80, 16, 16, 50, "SKETCHING", "/images/3.png", 20));
        tasks.add(new Task(4, "Design UML Diagram", 85, 0, 100, 17, 20, 50, "SKETCHING", "/images/4.png", 20));
        tasks.add(new Task(5, "Develop Visual Design", 105, 0, 120, 21, 24, 75, "FRONT-END", "/images/5.png", 30));
        tasks.add(new Task(6, "Start JavaScript Development", 120, 0, 135, 24, 27, 75, "FRONT-END", "/images/6.png", 30));
        tasks.add(new Task(7, "Develop Classes", 135, 0, 155, 27, 31, 100, "BACK-END", "/images/7.png", 40));
        tasks.add(new Task(8, "Implement Functions", 150, 0, 180, 30, 36, 100, "BACK-END", "/images/8.png", 40));
        tasks.add(new Task(9, "Design Test Cases", 190, 0, 185, 38, 37, 125, "TESTING", "/images/9.png", 50));
        tasks.add(new Task(10, "Testing Plan", 230, 0, 210, 46, 42, 125, "TESTING", "/images/10.png", 50));
        tasks.add(new Task(11, "SSL Certificate", 235, 250, 235, 47, 47, 150, "SECURITY", "/images/11.png", 60));
        tasks.add(new Task(12, "Cookies System", 245, 300, 240, 49, 48, 150, "SECURITY", "/images/12.png", 60));
        tasks.add(new Task(13, "Sell Subscriptions", 270, 400, 265, 54, 53, 175, "DEPLOYMENT", "/images/13.png", 70));
        tasks.add(new Task(14, "Add Media", 290, 600, 285, 58, 57, 175, "DEPLOYMENT", "/images/14.png", 70));
        tasks.add(new Task(15, "Partner with Lock", 300, 800, 300, 60, 60, 200, "PARTNERS", "/images/15.png", 80));
        tasks.add(new Task(16, "Hardware Store Partners", 315, 1000, 320, 63, 64, 200, "PARTNERS", "/images/16.png", 80));
        tasks.add(new Task(17, "Market Research", 330, 1500, 340, 70, 68, 225, "MARKETING", "/images/17.png", 90));
        tasks.add(new Task(18, "Content Development", 375, 2000, 355, 75, 71, 225, "MARKETING", "/images/18.png", 90));
        tasks.add(new Task(19, "Maintain System", 395, 3000, 375, 79, 75, 250, "MAINTAIN", "/images/19.png", 100));
        tasks.add(new Task(20, "Update System", 400, 4000, 400, 80, 80, 250, "MAINTAIN", "/images/20.png", 100));

        tasks.getFirst().setClaimMessage("Research is the foundation of AnaeroSync. Investigate the economic, environmental, and social factors of anaerobic digestion. Invest 50 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 25 Community Trust units as a reward for claiming this task.");
        tasks.get(1).setClaimMessage("Understanding the local environment is key for AnaeroSync's success. Investigate the waste management situation in Makers Valley and potential opportunities. Invest 60 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 25 Community Trust units as a reward for claiming this task.");
        tasks.get(2).setClaimMessage("Before development starts, it's essential to define how users will interact with AnaeroSync. Create detailed use cases. Invest 80 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 50 Community Trust units as a reward for claiming this task.");
        tasks.get(3).setClaimMessage("Structuring the system is crucial for development. Create a UML diagram to map out the project's structure. Invest 85 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 50 Community Trust units as a reward for claiming this task.");
        tasks.get(4).setClaimMessage("A user-friendly design is key! Start working on AnaeroSync's UI/UX using Figma. Invest 105 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 75 Community Trust units as a reward for claiming this task.");
        tasks.get(5).setClaimMessage("The coding phase begins! Lay the groundwork for AnaeroSync by starting JavaScript development. Invest 120 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 75 Community Trust units as a reward for claiming this task.");
        tasks.get(6).setClaimMessage("Create reusable and well-structured classes for the project. Invest 135 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 100 Community Trust units as a reward for claiming this task.");
        tasks.get(7).setClaimMessage("Write essential functions and methods to ensure AnaeroSync's core operations work smoothly. Invest 150 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 100 Community Trust units as a reward for claiming this task.");
        tasks.get(8).setClaimMessage("Testing is crucial! Define test cases for AnaeroSync's functionalities. Invest 190 units of Money to claim this Task.You are not required any Community Trust units for this task, however, receive 125 Community Trust units as a reward for claiming this task.");
        tasks.get(9).setClaimMessage("Ensure smooth integration of all system components by developing an integration acceptance plan. Invest 230 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 125 Community Trust units as a reward for claiming this task.");
        tasks.get(10).setClaimMessage("Secure the AnaeroSync website with an SSL certificate. Invest 235 units of Money to claim this Task. You are required 250 Community Trust units to claim this task. Receive 150 Community Trust units as a reward for claiming this task.");
        tasks.get(11).setClaimMessage("Set up a secure and functional cookies system for AnaeroSync. Invest 245 units of Money to claim this Task. You are required 300 Community Trust units to claim this task. Receive 150 Community Trust units as a reward for claiming this task.");
        tasks.get(12).setClaimMessage("Keep AnaeroSync up to date with improvements and bug fixes. Invest 270 units of Money to claim this Task. You are required 400 Community Trust units to claim this task. Receive 175 Community Trust units as a reward for claiming this task.");
        tasks.get(13).setClaimMessage("Ensure that AnaeroSync is running smoothly and without errors. Invest 290 units of Money to claim this Task. You are required 600 Community Trust units to claim this task. Receive 175 Community Trust units as a reward for claiming this task.");
        tasks.get(14).setClaimMessage("Create engaging and informative content for AnaeroSync. Invest 300 units of Money to claim this Task. You are required 800 Community Trust units to claim this task. Receive 200 Community Trust units as a reward for claiming this task.");
        tasks.get(15).setClaimMessage("Promote AnaeroSync through social media and marketing strategies. Invest 315 units of Money to claim this Task. You are required 1000 Community Trust units to claim this task. Receive 200 Community Trust units as a reward for claiming this task.");
        tasks.get(16).setClaimMessage("Establish partnerships to enhance AnaeroSync's impact. Invest 350 units of Money to claim this Task. You are required 1500 Community Trust units to claim this task. Receive 225 Community Trust units as a reward for claiming this task.");
        tasks.get(17).setClaimMessage("Develop a sustainability plan for long-term success. Invest 375 units of Money to claim this Task. You are required 2000 Community Trust units to claim this task. Receive 225 Community Trust units as a reward for claiming this task.");
        tasks.get(18).setClaimMessage("Optimize performance and security for AnaeroSync. Invest 395 units of Money to claim this Task. You are required 300 Community Trust units to claim this task. Receive 250 Community Trust units as a reward for claiming this task.");
        tasks.get(19).setClaimMessage("Finalize and deploy AnaeroSync for public access. Invest 400 units of Money to claim this Task. You are required 4000 Community Trust units to claim this task. Receive 250 Community Trust units as a reward for claiming this task.");

        tasks.getFirst().setFeeMessage("You have landed on an already claimed task! You can offer to help investigate the sustainability of anaerobic digesters by investing 10 units of Money and 10 units of Time. Receive 10 Community Trust units as a reward.");
        tasks.get(1).setFeeMessage("You have landed on an already claimed task! Support ongoing research by contributing 12 units of Money and 13 units of Time. Receive 10 Community Trust units as a reward.");
        tasks.get(2).setFeeMessage("You have landed on an already claimed task! Assist in structuring use case scenarios by investing 16 units of Money and 16 units of Time. Receive 20 Community Trust units as a reward.");
        tasks.get(3).setFeeMessage("You have landed on an already claimed task! Help refine the UML diagram by investing 17 units of Money and 20 units of Time. Receive 20 Community Trust units as a reward.");
        tasks.get(4).setFeeMessage("You have landed on an already claimed task! Support the design process by suggesting improvements. Invest 21 units of Money and 24 units of Time. Receive 30 Community Trust units as a reward.");
        tasks.get(5).setFeeMessage("You have landed on an already claimed task! Contribute to the coding process by assisting in JavaScript development. Invest 24 units of Money and 27 units of Time. Receive 30 Community Trust units as a reward.");
        tasks.get(6).setFeeMessage("You have landed on an already claimed task! Help refine and optimize class structures. Invest 27 units of Money and 31 units of Time. Receive 40 Community Trust units as a reward.");
        tasks.get(7).setFeeMessage("You have landed on an already claimed task! Assist by debugging and refining the implemented functions. Invest 30 units of Money and 36 units of Time.Receive 40 Community Trust units as a reward.");
        tasks.get(8).setFeeMessage("You have landed on an already claimed task! Contribute to test planning by reviewing and suggesting improvements. Invest 38 units of Money and 37 units of Time. Receive 50 Community Trust units as a reward.");
        tasks.get(9).setFeeMessage("You have landed on an already claimed task! Help refine and validate integration strategies. Invest 46 units of Money and 42 units of Time. Receive 50 Community Trust units as a reward.");
        tasks.get(10).setFeeMessage("You have landed on an already claimed task! Contribute to the security enhancement by reviewing options. Invest 47 units of Money and 47 units of Time. Receive 60 Community Trust units as a reward.");
        tasks.get(11).setFeeMessage("You have landed on an already claimed task! Assist in designing and testing the cookies system. Invest 49 units of Money and 48 units of Time.Receive 60 Community Trust units as a reward.");
        tasks.get(12).setFeeMessage("You have landed on an already claimed task! Contribute by suggesting or testing updates. Invest 54 units of Money and 53 units of Time. Receive 70 Community Trust units as a reward.");
        tasks.get(13).setFeeMessage("You have landed on an already claimed task! Assist in monitoring the system for issues. Invest 58 units of Money and 57 units of Time. Receive 70 Community Trust units as a reward.");
        tasks.get(14).setFeeMessage("You have landed on an already claimed task! Help by contributing articles, videos, or images. Invest 60 units of Money and 60 units of Time. Receive 80 Community Trust units as a reward.");
        tasks.get(15).setFeeMessage("You have landed on an already claimed task! Support marketing efforts with ideas or content creation. Invest 63 units of Money and 64 units of Time. Receive 80 Community Trust units as a reward.");
        tasks.get(16).setFeeMessage("You have landed on an already claimed task! Help establish connections by reaching out to potential partners. Invest 70 units of Money and 68 units of Time. Receive 90 Community Trust units as a reward.");
        tasks.get(17).setFeeMessage("You have landed on an already claimed task! Assist in developing a sustainability plan. Invest 75 units of Money and 71 units of Time. Receive 90 Community Trust units as a reward.");
        tasks.get(18).setFeeMessage("You have landed on an already claimed task! Contribute to performance optimization and security measures. Invest 79 units of Money and 75 units of Time. Receive 100 Community Trust units as a reward.");
        tasks.get(19).setFeeMessage("You have landed on an already claimed task! Support the deployment process by reviewing final steps. Invest 80 units of Money and 80 units of Time. Receive 100 Community Trust units as a reward.");

        tasks.getFirst().setCompleteTask("In order to complete this research stage, you must spend time reading news, scientific articles, and surfing the web. Invest 50 units of Time to complete this Task.");
        tasks.get(1).setCompleteTask("Conduct interviews, collect data, and explore case studies on waste management in Makers Valley. Invest 60 units of Time to complete this Task.");
        tasks.get(2).setCompleteTask("Develop user stories and diagrams to visualize user interactions. Invest 80 units of Time to complete this Task.");
        tasks.get(3).setCompleteTask("Finalize a well-documented UML diagram covering all system components. Invest 100 units of Time to complete this Task.");
        tasks.get(4).setCompleteTask("Finalize wireframes and prototypes for a seamless user experience. Invest 120 units of Time to complete this Task.");
        tasks.get(5).setCompleteTask("Write initial code for core functionalities and set up the development environment. Invest 135 units of Time to complete this Task.");
        tasks.get(6).setCompleteTask("Ensure object-oriented principles are correctly implemented in the development. Invest 155 units of Time to complete this Task.");
        tasks.get(7).setCompleteTask("Write, test, and document key functions for system operations. Invest 180 units of Time to complete this Task.");
        tasks.get(8).setCompleteTask("Develop and execute test cases to ensure system reliability. Invest 185 units of Time to complete this Task.");
        tasks.get(9).setCompleteTask("Finalize and document the integration acceptance plan. Invest 210 units of Time to complete this Task.");
        tasks.get(10).setCompleteTask("Purchase and configure the SSL certificate for the domain. Invest 235 units of Time to complete this Task.");
        tasks.get(11).setCompleteTask("Implement, test, and document the cookies system. Invest 240 units of Time to complete this Task.");
        tasks.get(12).setCompleteTask("Implement and document necessary updates. Invest 265 units of Time to complete this Task.");
        tasks.get(13).setCompleteTask("Analyze logs and optimize performance. Invest 285 units of Time to complete this Task.");
        tasks.get(14).setCompleteTask("Develop and publish high-quality content. Invest 300 units of Time to complete this Task.");
        tasks.get(15).setCompleteTask("Create marketing materials and outreach strategies. Invest 320 units of Time to complete this Task.");
        tasks.get(16).setCompleteTask("Establish and finalize partnerships with relevant organizations. Invest 340 units of Time to complete this Task.");
        tasks.get(17).setCompleteTask("Develop a long-term sustainability plan for AnaeroSync. Invest 355 units of Time to complete this Task.");
        tasks.get(18).setCompleteTask("Optimize system security and ensure compliance with best practices. Invest 375 units of Time to complete this Task.");
        tasks.get(19).setCompleteTask("Deploy the final version of AnaeroSync and ensure a successful launch. Invest 400 units of Time to complete this Task.");
    }

    // Initialization using Ignacio's Objectives
    private void initializeObjectives() {
        objectives.add(new Objective(1, "RESEARCH", 50, 50, 25, tasks.get(0), tasks.get(1)));
        objectives.add(new Objective(2, "SKETCHING", 100, 100, 50, tasks.get(2), tasks.get(3)));
        objectives.add(new Objective(3, "FRONT-END", 150, 150, 75, tasks.get(4), tasks.get(5)));
        objectives.add(new Objective(4, "BACK-END", 200, 200, 100, tasks.get(6), tasks.get(7)));
        objectives.add(new Objective(5, "TESTING", 250, 250, 125, tasks.get(8), tasks.get(9)));
        objectives.add(new Objective(6, "CYBER-SECURITY", 300, 300, 150, tasks.get(10), tasks.get(11)));
        objectives.add(new Objective(7, "DEPLOYMENT", 350, 350, 175, tasks.get(12), tasks.get(13)));
        objectives.add(new Objective(8, "PARTNERSHIPS", 400, 400, 200, tasks.get(14), tasks.get(15)));
        objectives.add(new Objective(9, "MARKETING", 450, 450, 225, tasks.get(16), tasks.get(17)));
        objectives.add(new Objective(10, "SUSTAINABILITY", 500, 500, 250, tasks.get(18), tasks.get(19)));
    }

    // Initialization using Ignacio's lucks
    private static void initializeLucks() {
        lucks.add(new Luck(1, "You deserve a day off!\nHave 100 units of Time!", 0, 100, 0, 0, "/images/LuckCard1.png", -1));
        lucks.add(new Luck(2, "You got a new hardware store subscription!\nHave 100 units of Money!", 100, 0, 0, 0, "/images/LuckCard2.png", -1));
        lucks.add(new Luck(3, "You deserve two days off!\nHave 200 units of Time!", 0, 200, 0, 0, "/images/LuckCard3.png", -1));
        lucks.add(new Luck(4, "You got two new hardware store subscriptions!\nHave 200 units of Money!", 200, 0, 0, 0, "/images/LuckCard4.png", -1));
        lucks.add(new Luck(5, "A new intern just arrived and wants to help!\nReceive 400 units of Time!", 0, 400, 0, 0, "/images/LuckCard5.png", -1));
        lucks.add(new Luck(6, "South Africa's Government appreciates you!\nThey want to help you.\nReceive 400 units of Money.", 400, 0, 0, 0, "/images/LuckCard6.png", -1));
        lucks.add(new Luck(7, "Congratulations!\nYou are doing everything very well.\nReceive 200 units of Money and Time as a reward.", 200, 200, 0, 0, "/images/LuckCard7.png", -1));
        lucks.add(new Luck(8, "Advance to\nUpdating AnaeroSync Task square", 0, 0, 0, 0, "/images/LuckCard8.png", 1));
        lucks.add(new Luck(9, "Advance to\nReceive funds Event Square", 0, 0, 0, 0, "/images/LuckCard9.png", 14));
        lucks.add(new Luck(10, "The other Software Engineer is sick!\nYou have to work twice as much.\nLose 100 units of time", 0, -100, 0, 0, "/images/LuckCard11.png", -1));
        lucks.add(new Luck(11, "Buy the necessary materials\nto build the DIY Anaerobic Digester\nfor the video instructions.\nLose 100 units of Money", -100, 0, 0, 0, "/images/LuckCard12.png", -1));
        lucks.add(new Luck(12, "Lock requires you to help them\norganize an event.\nLose 200 units of time", 0, -200, 0, 0, "/images/LuckCard13.png", -1));
        lucks.add(new Luck(13, "Buy the Apple Store and\nGoogle Play upload permits.\nLose 200 units of Money", -200, 0, 0, 0, "/images/LuckCard14.png", -1));
        lucks.add(new Luck(14, "Oh Oh! The app crashed.\nFix it now!\nLose 400 units of Time", 0, -400, 0, 0, "/images/LuckCard15.png", -1));
        lucks.add(new Luck(15, "Hire another software engineer\nto finish the programming of\nthe app sooner.\nLose 400 units of Money", -400, 0, 0, 0, "/images/LuckCard16.png", -1));
        lucks.add(new Luck(16, "There has been a lot of complaints\nabout the app's performance.\nPeople are mad.\nLose 200 units of Money and Time", -200, -200, 0, 0, "/images/LuckCard17.png", -1));
        lucks.add(new Luck(17, "Advance to the\nDenial of Service Attack Event Square", 0, 0, 0, 0, "/images/LuckCard18.png", 7));
        lucks.add(new Luck(18, "Advance to the\nonate Money Event Square", 0, 0, 0, 0, "/images/LuckCard19.png", 21));
    }

    // Initialize event squares
    private static void initializeEventSquares() {
        corners.clear();
        corners.add(new Corner(7, "DDOS Attack", 0, 0, -300, "/images/EventDDOS.png"));    // Position 7
        corners.add(new Corner(14, "Donate Money", 0, 0, 200, "/images/EventDM.png"));     // Position 14
        corners.add(new Corner(21, "Home", 100, 100, 0, "/images/EventHome.png"));         // Position 21
        corners.add(new Corner(28, "Receive Funds", 50, 50, 0, "/images/EventRF.png"));    // Position 28
    }

    // Create a box for an objective with tasks listed below
    private VBox createObjectiveBox(Objective objective) {
        VBox objectiveBox = new VBox(5);
        objectiveBox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");

        // Objective header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        // Objective name
        Label objectiveName = new Label(objective.getObjectiveName());
        objectiveName.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label completionStatus = new Label("");
        completionStatus.setStyle("-fx-font-size: 14px;");

        header.getChildren().addAll(objectiveName, completionStatus);

        // Task list
        VBox tasksList = new VBox(3);
        tasksList.setStyle("-fx-padding: 5 0 0 10;");

        // Add tasks
        HBox task1Box = createTaskBox(objective.getTask1());
        HBox task2Box = createTaskBox(objective.getTask2());

        tasksList.getChildren().addAll(task1Box, task2Box);

        objectiveBox.getChildren().addAll(header, tasksList);

        // Update completion status
        updateObjectiveStatus(objective, completionStatus, objectiveName);

        return objectiveBox;
    }

    // Create a box for a task with completion status
    private HBox createTaskBox(Task task) {
        HBox taskBox = new HBox(5);
        taskBox.setAlignment(Pos.CENTER_LEFT);

        Label taskName = new Label("• " + task.getTaskName());
        taskName.setWrapText(true);
        taskName.setPrefWidth(180);
        taskName.setStyle("-fx-font-size: 12px;");

        Label completionStatus = new Label("");
        completionStatus.setStyle("-fx-font-size: 12px;");

        taskBox.getChildren().addAll(taskName, completionStatus);

        // Update completion status accordingly
        updateTaskStatus(task, completionStatus, taskName);

        return taskBox;
    }

    // Update the completion status of an objective
    private void updateObjectiveStatus(Objective objective, Label statusLabel, Label nameLabel) {
        boolean task1Complete = isTaskCompleted(objective.getTask1());
        boolean task2Complete = isTaskCompleted(objective.getTask2());

        // Cheak if they are complete
        if (task1Complete && task2Complete) {
            statusLabel.setText("✓");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 12px;");
            nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: green; -fx-font-size: 14px;");

            // Change the background color of the completed objective to green
            ((VBox) nameLabel.getParent().getParent()).setStyle("-fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-color: #E8F5E9;");
        } else {
            statusLabel.setText("");
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        }
    }

    // Set up the objectives panel basically boxes will appear here
    private void setupObjectivesPanel() {
        objectivesContainer.getChildren().clear();

        // According to how many objectives you have, you will see the objective boxes
        for (Objective objective : objectives) {
            // Create a box for each objective
            VBox objectiveBox = createObjectiveBox(objective);
            // each and every box, put it inside objectives container
            objectivesContainer.getChildren().add(objectiveBox);
        }
    }

    // Update the completion status of a task
    private void updateTaskStatus(Task task, Label statusLabel, Label nameLabel) {
        if (isTaskCompleted(task)) {
            statusLabel.setText("✓");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 12px;");
            nameLabel.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
        } else {
            statusLabel.setText("");
            nameLabel.setStyle("-fx-font-size: 12px;");
        }
    }

    // Check if a task is completed by all the players
    private boolean isTaskCompleted(Task task) {
        for (ArrayList<Task> playerTasks : completedTasks.values()) {
            for (Task completedTask : playerTasks) {
                if (completedTask.getId() == task.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    // Toggle the objectives panel visibility
    @FXML
    private void toggleObjectivesPanel() {
        // Update objectives status before showing
        if (!objectivesPanel.isVisible()) {
            updateObjectivesStatus();
        }
        objectivesPanel.setVisible(!objectivesPanel.isVisible());
    }

    // Update all objectives status
    private void updateObjectivesStatus() {
        // Clear and rebuild from ground up the objectives panel
        setupObjectivesPanel();
    }

    // Check if the player is on Task Square or Other squares like corners and luck card
    private void checkPosition(int position) {
        Square square = PositionManager.getSquareAtPosition(position);
        System.out.println("Player landed on " + square.getType() + " at position " + position);

        if (square instanceof TaskSquare) {
            TaskSquare taskSquare = (TaskSquare) square;
            Task task = taskSquare.getTask();
            if (task != null) {
                showTaskDialog(task);
            }
        } else if (square instanceof LuckSquare) {
            // Show a random luck card when landing on a luck square
            if (!lucks.isEmpty()) {
                Luck luck = lucks.get(random.nextInt(lucks.size()));
                showLuckDialog(luck);
            }
        } else if (square instanceof CornerSquare) {
            // Corner squares are event squares
            System.out.println("Landed on corner square at position: " + position);
            
            // Find the corresponding corner for this position
            Corner corner = corners.stream().filter(c -> c.getId() == position).findFirst().orElse(null);

            if (corner != null) {
                System.out.println("Found corner: " + corner.getEventName());
                // Disable buttons until event is handled
                endTurnButton.setDisable(true);
                rollDiceButton.setDisable(true);
                
                // Show the event square dialog
                esNameLabel.setText(corner.getEventName());
                esDescLabel.setText("Effect: " + getEventDescription(corner));
                esBonusLabel.setText("Bonus: " + getEventBonus(corner));
                esCostLabel.setText("Cost: " + getEventCost(corner));
                
                // Set the image
                try {
                    Image image = new Image(getClass().getResourceAsStream(corner.getEventCard()));
                    esCardImage.setImage(image);
                } catch (Exception e) {
                    System.out.println("Error loading event card image: " + e.getMessage());
                }
                
                // Show the dialog
                esInfoBox.setVisible(true);
                
                // Set up the OK button action
                Ok1.setOnAction(event -> {
                    applyCornerEffect(corner);
                    esInfoBox.setVisible(false);
                    endTurnButton.setDisable(false);
                    rollDiceButton.setDisable(false);
                });
            } else {
                System.out.println("No corner found for position: " + position);
            }
        }
    }

    private String getEventDescription(Corner corner) {
        switch (corner.getEventName()) {
            case "DDOS Attack":
                return "Lose 300 Shared Trust";
            case "Donate Money":
                return "Gain 200 Shared Trust and roll dice for penalty";
            case "Home":
                return "Gain 100 money and 100 time";
            case "Receive Funds":
                return "Roll dice for bonus money";
            default:
                return "";
        }
    }

    private String getEventBonus(Corner corner) {
        if (corner.getEventMoney() > 0 || corner.getEventTime() > 0) {
            StringBuilder bonus = new StringBuilder();
            if (corner.getEventMoney() > 0) {
                bonus.append("+").append(corner.getEventMoney()).append(" Money");
            }
            if (corner.getEventTime() > 0) {
                if (bonus.length() > 0) bonus.append(", ");
                bonus.append("+").append(corner.getEventTime()).append(" Time");
            }
            return bonus.toString();
        }
        return "None";
    }

    private String getEventCost(Corner corner) {
        if (corner.getEventMoney() < 0 || corner.getEventTime() < 0 || corner.getEventTrust() < 0) {
            StringBuilder cost = new StringBuilder();
            if (corner.getEventMoney() < 0) {
                cost.append(corner.getEventMoney()).append(" Money");
            }
            if (corner.getEventTime() < 0) {
                if (cost.length() > 0) cost.append(", ");
                cost.append(corner.getEventTime()).append(" Time");
            }
            if (corner.getEventTrust() < 0) {
                if (cost.length() > 0) cost.append(", ");
                cost.append(corner.getEventTrust()).append(" Trust");
            }
            return cost.toString();
        }
        return "None";
    }

    private void applyCornerEffect(Corner corner) {
        Player player = players[currentPlayer];
        
        // Apply the effects
        if (corner.getEventMoney() != 0) {
            player.addMoney(corner.getEventMoney());
        }
        if (corner.getEventTime() != 0) {
            player.addTime(corner.getEventTime());
        }
        if (corner.getEventTrust() != 0) {
            SHARED_TRUST += corner.getEventTrust();
        }
        
        // Handle special cases
        switch (corner.getEventName()) {
            case "DDOS Attack":
                corner.setActive(true);
                corner.setActivatedBy(player);
                break;
            case "Donate Money":
            case "Receive Funds":
                int roll = random.nextInt(6) + 1;
                if (corner.getEventName().equals("Donate Money")) {
                    player.addMoney(-50 * roll);
                } else {
                    player.addMoney(50 * roll);
                }
                break;
        }
    }

    private void showLuckDialog(Luck luck) {
        // Set the luck card information
        luckNameLabel.setText("Luck Card");
        luckDescLabel.setText(luck.getLuckName());

        // Set task image
        String imagePath = luck.getLuckCard();
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        luckCardImage.setImage(image);
        
        // Create a description based on the luck card effects
        StringBuilder description = new StringBuilder();
        
        if (luck.getLuckMoney() > 0) {
            description.append("Gain ").append(luck.getLuckMoney()).append(" money. ");
        } else if (luck.getLuckMoney() < 0) {
            description.append("Lose ").append(Math.abs(luck.getLuckMoney())).append(" money. ");
        }
        
        if (luck.getLuckTime() > 0) {
            description.append("Gain ").append(luck.getLuckTime()).append(" time. ");
        } else if (luck.getLuckTime() < 0) {
            description.append("Lose ").append(Math.abs(luck.getLuckTime())).append(" time. ");
        }
        
        if (luck.getLuckTrustNeeded() > 0) {
            description.append("Requires ").append(luck.getLuckTrustNeeded()).append(" trust. ");
        }
        
        if (luck.getNewPosition() != -1) {
            description.append("Move to position ").append(luck.getNewPosition()).append(".");
        }
        
        luckDescLabel.setText(description.toString());
        
        // Set bonus information if applicable
        if (luck.getLuckMoney() != 0 || luck.getLuckTime() != 0) {
            StringBuilder bonusText = new StringBuilder("Effect: ");
            if (luck.getLuckMoney() != 0) {
                bonusText.append(luck.getLuckMoney() > 0 ? "+" : "")
                        .append(luck.getLuckMoney())
                        .append(" Money");
            }
            if (luck.getLuckTime() != 0) {
                if (luck.getLuckMoney() != 0) bonusText.append(", ");
                bonusText.append(luck.getLuckTime() > 0 ? "+" : "")
                        .append(luck.getLuckTime())
                        .append(" Time");
            }
            luckBonusLabel.setText(bonusText.toString());
        } else {
            luckBonusLabel.setText("");
        }
        
        // Set cost information if applicable
        if (luck.getLuckTrustNeeded() != 0) {
            StringBuilder costText = new StringBuilder("Required: ");
            if (luck.getLuckTrustNeeded() != 0) {
                costText.append(luck.getLuckTrustNeeded())
                        .append(" Trust");
            }
            luckCostLabel.setText(costText.toString());
        } else {
            luckCostLabel.setText("");
        }
        
        // Set owner information (not applicable for luck cards)
        luckOwnerLabel.setText("");
        
        // Set up the OK button action - Apply effects immediately when OK is clicked
        Ok.setOnAction(event -> {
            // First apply the effect immediately
            applyLuckEffect(luck);
            // Then hide the dialog
            hideLuckDialog();
            // Make sure the end turn button is enabled
            endTurnButton.setDisable(false);
        });
        
        // Show the luck info box
        luckInfoBox.setVisible(true);
    }

    // Applying the luck effect
    private void applyLuckEffect(Luck luck) {
        Player player = players[currentPlayer];
        StringBuilder effectMessage = new StringBuilder();
        
        // Apply money effects
        if (luck.getLuckMoney() > 0) {
            player.setMoneyResource(player.getMoneyResource() + luck.getLuckMoney());
            effectMessage.append("You gained ").append(luck.getLuckMoney()).append(" money! ");
        } else if (luck.getLuckMoney() < 0) {
            player.setMoneyResource(player.getMoneyResource() - Math.abs(luck.getLuckMoney()));
            effectMessage.append("You lost ").append(Math.abs(luck.getLuckMoney())).append(" money! ");
        }
        
        // Apply time effects
        if (luck.getLuckTime() > 0) {
            player.setTimeResource(player.getTimeResource() + luck.getLuckTime());
            effectMessage.append("You gained ").append(luck.getLuckTime()).append(" time! ");
        } else if (luck.getLuckTime() < 0) {
            player.setTimeResource(player.getTimeResource() - Math.abs(luck.getLuckTime()));
            effectMessage.append("You lost ").append(Math.abs(luck.getLuckTime())).append(" time! ");
        }
        
        // Apply position change if applicable
        if (luck.getNewPosition() != -1) {
            effectMessage.append("You moved to position ").append(luck.getNewPosition()).append("! ");
            
            // Move the player to the new position
            player.setPosition(luck.getNewPosition());
            movePlayer(currentPlayer, 0); // Update the visual position without adding spaces
            
            // Check the new position for any effects
            checkPosition(luck.getNewPosition());
        }
        
        // Display the effect message
        showErrorDialog.setText(effectMessage.toString());
        
        // Update the player display
        updateCurrentPlayerDisplay();
        endTurnButton.setDisable(false);
        setupPlayerInfo();
    }

    /**
     * Hides the luck card dialog
     */
    private void hideLuckDialog() {
        luckInfoBox.setVisible(false);
    }

    private void showTaskDialog(Task task) {
        // Check if task is already owned
        Player owner = null;
        for (Player player : players) {
            if (player.ownsTask(task)) {
                owner = player;
                break;
            }
        }
        
        // Set task details
        taskNameLabel.setText(task.getTaskName());
        taskDescLabel.setText("Description: " + task.getTaskObjective());
        taskBonusLabel.setText("Trust Bonus: " + task.getTaskBonus());
        taskCostLabel.setText(String.format("Cost: $%d, Trust %d", task.getTaskMoney(), task.getTaskTrustNeeded()));

        // Set task image
        String imagePath = task.getTaskCard();
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        taskCardImage.setImage(image);
        
        // Disable End Turn button until player makes a decision
        endTurnButton.setDisable(true);
        
        // Show or hide owner information
        if (owner != null) {
            taskOwnerLabel.setText("Owned by: " + owner.getName());
            taskOwnerLabel.setVisible(true);
            
            // Only show decline button
            acceptTaskButton.setVisible(false);
            offerTaskButton.setVisible(false);
            declineTaskButton.setVisible(true);
        } else {
            taskOwnerLabel.setVisible(false);

            // Show all buttons
            acceptTaskButton.setVisible(true);
            declineTaskButton.setVisible(true);
            offerTaskButton.setVisible(true);
        }
        
        // Set button actions
        acceptTaskButton.setOnAction(e -> {
            acceptTask(task);
            hideTaskDialog();
            // Enable End Turn button after decision
            endTurnButton.setDisable(false);
        });

        declineTaskButton.setOnAction(e -> {
            hideTaskDialog();
            // Enable End Turn button after decision
            endTurnButton.setDisable(false);
        });

        offerTaskButton.setOnAction(e -> {
            showOfferModal(task);
            hideTaskDialog();
            // Enable End Turn button after decision
            endTurnButton.setDisable(false);
        });

        // Show the task card box
        cardInfoBox.setVisible(true);
    }

    private void hideTaskDialog() {
        cardInfoBox.setVisible(false);
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
            SHARED_TRUST >= task.getTaskTrustNeeded()) {
            
            // Deduct resources
            currentPlayerObj.setMoneyResource(currentPlayerObj.getMoneyResource() - task.getTaskMoney());
            SHARED_TRUST -= task.getTaskTrustNeeded();
            
            // Add task to player's owned tasks
            currentPlayerObj.addTask(task);
            task.setOwner(currentPlayerObj);

            SHARED_TRUST += task.getTaskBonus();
            
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
            // Enable End Turn button after offer is declined
            endTurnButton.setDisable(false);
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

            // Enable End Turn button after offer is accepted
            endTurnButton.setDisable(false);
        } else {
            showErrorDialog.setText("Not enough resources to accept offer!");

            // Enable End Turn button even if offer fails
            endTurnButton.setDisable(false);
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

        if (moneyToReceive > 0 || timeToReceive > 0) {
            if (tradePlayer.getMoneyResource() >= moneyToReceive &&
                tradePlayer.getTimeResource() >= timeToReceive) {
                tradePlayer.setMoneyResource(tradePlayer.getMoneyResource() - moneyToReceive);
                tradePlayer.setTimeResource(tradePlayer.getTimeResource() - timeToReceive);
                players[currentPlayer].setMoneyResource(players[currentPlayer].getMoneyResource() + moneyToReceive);
                players[currentPlayer].setTimeResource(players[currentPlayer].getTimeResource() + timeToReceive);
            } else {
                showErrorDialog.setText("Your teammate does not have enough resources!");
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

    // Completing Task
    @FXML
    public void showCompleteTaskModal() {
        // Clear any previously selected task
        selectedTaskToComplete = null;
        
        // Clear the container before adding new cards
        completableTaskCards.getChildren().clear();
        
        Player currentPlayerObj = players[currentPlayer];
        int availableTime = currentPlayerObj.getTimeResource();
        availableTimeLabel.setText(String.valueOf(availableTime));
        
        // Get owned tasks that are not completed
        List<Task> ownedTasks = currentPlayerObj.getOwnedTasks();
        
        // Check if player has any tasks
        if (ownedTasks.isEmpty()) {
            showErrorDialog.setText("You don't have any tasks to complete!");
            return;
        }
        
        // Filter for completable tasks (those that require less time than available)
        boolean hasCompletableTasks = false;
        
        // Use a Set to track tasks we've already added to prevent duplicates
        Set<Integer> addedTaskIds = new HashSet<>();
        
        for (Task task : ownedTasks) {
            // Skip if we've already added this task
            if (addedTaskIds.contains(task.getId())) {
                continue;
            }
            
            // Only show tasks that can be completed with available time
            if (task.getTaskTime() <= availableTime && !task.isCompleted()) {
                VBox taskCard = createTaskCardForCompletion(task);
                completableTaskCards.getChildren().add(taskCard);
                hasCompletableTasks = true;
                
                // Mark this task as added
                addedTaskIds.add(task.getId());
            }
        }
        
        if (!hasCompletableTasks) {
            showErrorDialog.setText("You don't have enough time to complete any tasks!");
            return;
        }
        
        // Show the modal
        completeTaskModal.setVisible(true);
    }

    @FXML
    private void confirmCompleteTask() {
        if (selectedTaskToComplete != null) {
            Player currentPlayerObj = players[currentPlayer];

            // Check if player has enough time for the task
            int timeRequired = selectedTaskToComplete.getTaskTime();

            if (currentPlayerObj.getTimeResource() >= timeRequired) {
                // Deduct time for the task
                currentPlayerObj.setTimeResource(
                    currentPlayerObj.getTimeResource() - timeRequired);

                // Remove task from owned tasks
                currentPlayerObj.getOwnedTasks().remove(selectedTaskToComplete);

                // Add task to completed tasks
                completedTasks.putIfAbsent(currentPlayerObj, new ArrayList<>());
                completedTasks.get(currentPlayerObj).add(selectedTaskToComplete);

                // Show success message
                showErrorDialog.setText("Task completed successfully!");
                showErrorDialog.setStyle("-fx-text-fill: green;");

                // Check if this completes an objective
                rewardObjective(currentPlayerObj, selectedTaskToComplete);

                // Update progress bar
                updateProgressBar();

                // Update objectives status if panel is visible
                if (objectivesPanel.isVisible()) {
                    updateObjectivesStatus();
                }

                // Update player display
                setupPlayerInfo();

                // Hide modal
                completeTaskModal.setVisible(false);
            } else {
                showErrorDialog.setText("Not enough time to complete this task!");
                showErrorDialog.setStyle("-fx-text-fill: red;");
            }
        }
    }

    // Check if completing this task completes an objective, and award rewards if so
    private void rewardObjective(Player player, Task completedTask) {
        // Get the player's completed tasks
        ArrayList<Task> playerCompletedTasks = completedTasks.getOrDefault(player, new ArrayList<>());

        // Find which objective this task belongs to
        for (Objective objective : objectives) {
            Task task1 = objective.getTask1();
            Task task2 = objective.getTask2();

            // Check if the completed task is part of this objective
            if (completedTask.getId() == task1.getId() || completedTask.getId() == task2.getId()) {
                // Check if the player has completed both tasks of this objective
                boolean hasCompletedTask1 = false;
                boolean hasCompletedTask2 = false;

                for (Task task: playerCompletedTasks) {
                    if (task.getId() == task1.getId()) {
                        hasCompletedTask1 = true;
                        break;
                    }
                }

                for (Task task: playerCompletedTasks) {
                    if (task.getId() == task2.getId()) {
                        hasCompletedTask2 = true;
                        break;
                    }
                }

                // If both tasks are completed, award the objective rewards
                if (hasCompletedTask1 && hasCompletedTask2) {
                    // Award money
                    player.setMoneyResource(player.getMoneyResource() + objective.getObjectiveMoney());

                    // Award time
                    player.setTimeResource(player.getTimeResource() + objective.getObjectiveTime());

                    // Award trust
                    SHARED_TRUST += objective.getObjectiveTrust();

                    // Show a message about the rewards
                    showErrorDialog.setText("Objective completed! Received: $" +
                        objective.getObjectiveMoney() + ", " +
                        objective.getObjectiveTime() + " time, and " +
                        objective.getObjectiveTrust() + " trust.");
                    showErrorDialog.setStyle("-fx-text-fill: green;");

                    // No need to check other objectives since a task can only be part of one objective
                    break;
                }
            }
        }
    }

    @FXML
    private void cancelCompleteTask() {
        completeTaskModal.setVisible(false);
    }

    // Progress Bar
    // Logic here is, I wanna put blank bar and then dynamically fill up rectangles inside that are equally spaced accordingly
    private void initializeProgressBar() {
        // This is frontend initialized
        progressBarContainer.getChildren().clear();

        // Create the progress bar rectangle in the container itself before any bars being added
        Rectangle progressBackground = new Rectangle(220, 30);
        progressBackground.setFill(Color.LIGHTGRAY);

        // Create the progress bar fill, this will push the progress background because progressBarContainer is fixed
        Rectangle progressFill = new Rectangle(0, 30);
        progressFill.setFill(Color.GREEN);
        progressFill.setId("progressFill");

        // Add both to the container with the fill on top, this will be from the left
        StackPane progressStack = new StackPane();
        progressStack.getChildren().addAll(progressBackground, progressFill);
        progressStack.setAlignment(Pos.CENTER_LEFT);

        progressBarContainer.getChildren().add(progressStack);

        // Update the percentage display
        updateProgressPercentage();
    }

    // Update the progress bar based on completed tasks
    private void updateProgressBar() {
        // Count total completed tasks by all players
        int completedTaskCount = 0;

        // just for loop cause I love loops
        for (ArrayList<Task> playerTasks : completedTasks.values()) {
            completedTaskCount += playerTasks.size();
        }

        // Calculate percentage (5%)
        double percentage = 100 * completedTaskCount / TOTAL_TASKS;

        // Update the progress fill width
        Rectangle progressFill = (Rectangle) ((StackPane) progressBarContainer.getChildren().get(0)).getChildren().get(1);
        progressFill.setWidth((percentage / 100) * 220);

        // I set it red but using if else we can set different colors according to the percentage
        if (percentage < 100) {
            progressFill.setFill(Color.RED);
        }

        // Update the percentage text
        updateProgressPercentage();
    }

    // Update the percentage display sole purpose it to change the percentage (helper method)
    private void updateProgressPercentage() {
        // Count total completed tasks for all the players
        int completedTaskCount = 0;

        for (List<Task> playerTasks : completedTasks.values()) {
            completedTaskCount += playerTasks.size();
        }

        // Calculate percentage (5%)
        int percentage = 100 * completedTaskCount / TOTAL_TASKS;
        progressPercentage.setText(percentage + "%");
    }

    // Got from ChatGPT to convert Hex to RGB Code for transparency essentially for the background!
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // Fix the createTaskCardForCompletion method
    private VBox createTaskCardForCompletion(Task task) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(200);
        container.setStyle("-fx-padding: 10; -fx-border-color: transparent; -fx-border-width: 2;");

        // Find which objective this task belongs to (for display purposes only)
        Objective taskObjective = objectives.stream()
            .filter(objective -> objective.getTask1().getId() == task.getId() ||
                               objective.getTask2().getId() == task.getId())
            .findFirst().orElse(null);

        try {
            String imagePath = task.getTaskCard();
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                ImageView taskImage = new ImageView(image);
                taskImage.setFitWidth(180);
                taskImage.setPreserveRatio(true);
                taskImage.setSmooth(true);
                container.getChildren().add(taskImage);
            }
        } catch (Exception e) {
            // If image loading fails, show task name instead
            Text taskName = new Text(task.getTaskName());
            taskName.setWrappingWidth(180);
            taskName.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            container.getChildren().add(taskName);
        }

        // Add objective name (for information only)
        if (taskObjective != null) {
            Text objectiveText = new Text("Objective: " + taskObjective.getObjectiveName());
            objectiveText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #1976D2;");
            container.getChildren().add(objectiveText);
        }

        // Add required time
        Text timeRequired = new Text("Time Required: " + task.getTaskTime());
        timeRequired.setStyle("-fx-font-size: 12px;");
        container.getChildren().add(timeRequired);

        // Add click handler
        container.setOnMouseClicked(event -> {
            // Deselect previously selected card
            completableTaskCards.getChildren().forEach(node ->
                node.setStyle("-fx-padding: 10; -fx-border-color: transparent; -fx-border-width: 2;"));

            selectedTaskToComplete = task;
            container.setStyle("-fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-width: 2;");

            // Enable confirm button if player has enough time for the task
            confirmCompleteButton.setDisable(
                players[currentPlayer].getTimeResource() < task.getTaskTime());
        });
        return container;
    }

    @FXML
    public void quitGame() {
        exit.setVisible(true);
    }

    @FXML
    public void backButtonOnAction() {
        exit.setVisible(false);
    }

    @FXML
    public void quitDOnAction() {
        thankyou.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            Platform.exit();
        });
        pause.play();
    }

    private void hideEventSquareDialog() {
        esInfoBox.setVisible(false);
    }
}
