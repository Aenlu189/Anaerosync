package aureo.anaerosync;

import aureo.anaerosync.squares.*;
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
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

import java.util.Random;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.TextAlignment;

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

    // ArrayList containing all tasks in the game
    private static final ArrayList<Task> tasks = new ArrayList<Task>();
    private static final ArrayList<Objective> objectives = new ArrayList<>();

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
    private VBox completeTaskModal;
    
    @FXML
    private FlowPane completableTaskCards;
    
    @FXML
    private Label availableTimeLabel;
    
    @FXML
    private Button confirmCompleteButton;
    
    private Task selectedTaskToComplete;

    // Ahmed you can use this for the progress bar objectives because each player's list of tasks that they finished is stored here
    private HashMap<Player, List<Task>> completedTasks = new HashMap<>();

    @FXML
    public void initialize() {
        System.out.println("Initializing controller...");

        // Initialize task and square objects
        initializeTasks();
        PositionManager.initializeSquares(tasks);

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

            playerBox.getChildren().addAll(nameText, moneyText, timeText, tasksText, completedTasksText);
            playerInfoContainer.getChildren().addAll(playerBox);
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
        tasks.get(1).setClaimMessage("Understanding the local environment is key for AnaeroSync’s success. Investigate the waste management situation in Makers Valley and potential opportunities. Invest 60 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 25 Community Trust units as a reward for claiming this task.");
        tasks.get(2).setClaimMessage("Before development starts, it’s essential to define how users will interact with AnaeroSync. Create detailed use cases. Invest 80 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 50 Community Trust units as a reward for claiming this task.");
        tasks.get(3).setClaimMessage("Structuring the system is crucial for development. Create a UML diagram to map out the project’s structure. Invest 85 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 50 Community Trust units as a reward for claiming this task.");
        tasks.get(4).setClaimMessage("A user-friendly design is key! Start working on AnaeroSync’s UI/UX using Figma. Invest 105 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 75 Community Trust units as a reward for claiming this task.");
        tasks.get(5).setClaimMessage("The coding phase begins! Lay the groundwork for AnaeroSync by starting JavaScript development. Invest 120 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 75 Community Trust units as a reward for claiming this task.");
        tasks.get(6).setClaimMessage("Create reusable and well-structured classes for the project. Invest 135 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 100 Community Trust units as a reward for claiming this task.");
        tasks.get(7).setClaimMessage("Write essential functions and methods to ensure AnaeroSync’s core operations work smoothly. Invest 150 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 100 Community Trust units as a reward for claiming this task.");
        tasks.get(8).setClaimMessage("Testing is crucial! Define test cases for AnaeroSync’s functionalities. Invest 190 units of Money to claim this Task.You are not required any Community Trust units for this task, however, receive 125 Community Trust units as a reward for claiming this task.");
        tasks.get(9).setClaimMessage("Ensure smooth integration of all system components by developing an integration acceptance plan. Invest 230 units of Money to claim this Task. You are not required any Community Trust units for this task, however, receive 125 Community Trust units as a reward for claiming this task.");
        tasks.get(10).setClaimMessage("Secure the AnaeroSync website with an SSL certificate. Invest 235 units of Money to claim this Task. You are required 250 Community Trust units to claim this task. Receive 150 Community Trust units as a reward for claiming this task.");
        tasks.get(11).setClaimMessage("Set up a secure and functional cookies system for AnaeroSync. Invest 245 units of Money to claim this Task. You are required 300 Community Trust units to claim this task. Receive 150 Community Trust units as a reward for claiming this task.");
        tasks.get(12).setClaimMessage("Keep AnaeroSync up to date with improvements and bug fixes. Invest 270 units of Money to claim this Task. You are required 400 Community Trust units to claim this task. Receive 175 Community Trust units as a reward for claiming this task.");
        tasks.get(13).setClaimMessage("Ensure that AnaeroSync is running smoothly and without errors. Invest 290 units of Money to claim this Task. You are required 600 Community Trust units to claim this task. Receive 175 Community Trust units as a reward for claiming this task.");
        tasks.get(14).setClaimMessage("Create engaging and informative content for AnaeroSync. Invest 300 units of Money to claim this Task. You are required 800 Community Trust units to claim this task. Receive 200 Community Trust units as a reward for claiming this task.");
        tasks.get(15).setClaimMessage("Promote AnaeroSync through social media and marketing strategies. Invest 315 units of Money to claim this Task. You are required 1000 Community Trust units to claim this task. Receive 200 Community Trust units as a reward for claiming this task.");
        tasks.get(16).setClaimMessage("Establish partnerships to enhance AnaeroSync’s impact. Invest 350 units of Money to claim this Task. You are required 1500 Community Trust units to claim this task. Receive 225 Community Trust units as a reward for claiming this task.");
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
    // Initialization of objectives
    private static void initializeObjectives(){
        objectives.add(new Objective(1, "RESEARCH", 50, 50, 25, tasks.getFirst(), tasks.get(1)));
        objectives.add(new Objective(2, "SKETCHING", 100, 100, 50, tasks.get(2), tasks.get(3)));
        objectives.add(new Objective(3, "FRONT-END", 150, 150, 75, tasks.get(4), tasks.get(5)));
        objectives.add(new Objective(4, "BACK-END", 200, 200, 100, tasks.get(6), tasks.get(7)));
        objectives.add(new Objective(5, "TESTING", 250, 250, 125, tasks.get(8), tasks.get(9)));
        objectives.add(new Objective(6, "CYBERSECURITY", 300, 300, 150, tasks.get(10), tasks.get(11)));
        objectives.add(new Objective(7, "DEPLOYMENT", 350, 350, 175, tasks.get(12), tasks.get(13)));
        objectives.add(new Objective(8, "PARTNERSHIPS", 400, 400, 200, tasks.get(14), tasks.get(15)));
        objectives.add(new Objective(9, "MARKETING", 450, 450, 225, tasks.get(16), tasks.get(17)));
        objectives.add(new Objective(10, "SUSTAINABILITY", 500, 500, 250, tasks.get(18), tasks.get(19)));
        for (int i = 1; i < objectives.size() + 1; ++i) {
            objectives.get(i).setCompleteObjectiveMessage("Congratulations on completing both tasks of the" + objectives.get(i).getObjectiveName() + "OBJECTIVE. You can’t step on these squares anymore! Please receive, as a thank you for your commitment, " + 50*i + " units of Money, " + 50*i + " units of Time and " + 50*i/2 + " units of Community Trust.");
        }

    }


    // Initialization using Ignacio's lucks
    private static void initializeLucks() {

    }


    // Check if the player is on Task Square or Other squares like corners and luck card
    private void checkPosition(int position) {
        Square currentSquare = PositionManager.getSquareAtPosition(position);

        if (currentSquare.getType() == SquareType.TASK_SQUARE) {
            Task task = ((TaskSquare) currentSquare).getTask();
            showTaskDialog(task);

        } else if (currentSquare.getType() == SquareType.LUCK_SQUARE) {
            // TODO: Handle LUCK position

        } else {
            String specialType = ((CornerSquare) currentSquare).getSquareName();
            switch (specialType) {
                case "Home":
                    // TODO: Call method that handles home position
                    break;
                case "DDOS":
                    // TODO: Call method that handles DDOS position
                    break;
                case "Receive Funds":
                    // TODO: Call method that handles Receive Funds position
                    break;
                case "Donate Money":
                    // TODO: Call method that handles Donate Money position
                    break;
            }
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
        Text taskBonus = new Text(Integer.toString(task.getTaskBonus()));
        Text taskCost = new Text(String.format("Cost: Money %d, Trust %d, Task Bonus Trust: %d",
            task.getTaskMoney(), task.getTaskTrustNeeded(), task.getTaskBonus()));
        
        taskName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        cardInfoBox.getChildren().addAll(
            taskCardView,
            taskName,
            taskDesc,
            taskBonus,
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

    private void declineTask() {
        hideTaskDialog();
    }

    private void hideTaskDialog() {
        cardInfoBox.setVisible(false);
        acceptTaskButton.setVisible(false);
        declineTaskButton.setVisible(false);
        offerTaskButton.setVisible(false);
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
        // Initialize completed tasks list for current player if not exist
        completedTasks.putIfAbsent(players[currentPlayer], new ArrayList<>());

        // Clear previous selections
        selectedTaskToComplete = null;
        confirmCompleteButton.setDisable(true);

        // Show available time
        availableTimeLabel.setText(String.valueOf(players[currentPlayer].getTimeResource()));

        // Clear and dynamically output all the task cards which the player's gonna complete
        completableTaskCards.getChildren().clear();
        for (Task task: players[currentPlayer].getOwnedTasks()){
            VBox taskCard = createTaskCardForCompletion(task);
            completableTaskCards.getChildren().add(taskCard);
        }
        completeTaskModal.setVisible(true);
    }

    private VBox createTaskCardForCompletion(Task task){
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(200);
        container.setStyle("-fx-padding: 10; -fx-border-color: transparent; -fx-border-width: 2;");

        String imagePath = task.getTaskCard();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        Image image = new Image(imageStream);
        ImageView taskImage = new ImageView(image);
        taskImage.setFitWidth(180);
        taskImage.setPreserveRatio(true);
        container.getChildren().add(taskImage);

        Text timeRequired = new Text("Time Required: " + task.getTaskTime());
        timeRequired.setStyle("-fx-font-size: 12px");
        container.getChildren().add(timeRequired);

        // Add click handler
        container.setOnMouseClicked(event -> {
            // Deselect previously selected card
            completableTaskCards.getChildren().forEach(node ->
                node.setStyle("-fx-padding: 10; -fx-border-color: transparent; -fx-border-width: 2px;"));

            selectedTaskToComplete = task;
            container.setStyle("-fx-padding: 10; -fx-border-color: #4CAF50; -fx-border-width: 2;");

            // Enable confirm only if the player has enough time resource
            confirmCompleteButton.setDisable(players[currentPlayer].getTimeResource() < task.getTaskTime());
        });
        return container;
    }

    @FXML
    private void confirmCompleteTask() {
        if (selectedTaskToComplete != null) {
            Player player = players[currentPlayer];

            // Check if player has enough time
            if (player.getTimeResource() >= selectedTaskToComplete.getTaskTime()) {
                // Deduct time
                player.setTimeResource(player.getTimeResource() - selectedTaskToComplete.getTaskTime());

                // Remove task from owned
                player.getOwnedTasks().remove(selectedTaskToComplete);

                // Add to completed tasks
                completedTasks.get(player).add(selectedTaskToComplete);

                // Update player display
                setupPlayerInfo();

                // Show message on top which I basically need to change it later on but right now temporary solution
                showErrorDialog.setText("Task completed successfully!");
                showErrorDialog.setStyle("-fx-text-fill: green;");

                completeTaskModal.setVisible(false);
            } else {
                showErrorDialog.setText("Not enough time resource to complete this task");
                showErrorDialog.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void cancelCompleteTask() {
        completeTaskModal.setVisible(false);
    }

    // Got from ChatGPT to convert Hex to RGB Code for transparency essentially for the background!
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
