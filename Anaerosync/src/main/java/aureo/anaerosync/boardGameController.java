package aureo.anaerosync;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import java.util.Random;

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

    private int numPlayers = 4;
    private int currentPlayer = 0;
    private final Color[] playerColors = {
            Color.DODGERBLUE,
            Color.GREEN,
            Color.ORANGE,
            Color.RED
    };

    private final Random random = new Random();

    /**
     * We no longer use "playerNames" and "position" arrays.
     * That functionality has been replaced with Player objects stored in this array.
     */
    private Player[] players = {
            new Player(1, "Error: no name", 500, 500),
            new Player(2, "Error: no name", 500, 500),
            new Player(3, "Error: no name", 500, 500),
            new Player(4, "Error: no name", 500, 500)
    };

    @FXML
    public void initialize() {
        hideAllCirclesExceptStart();
        updateCurrentPlayerDisplay();
    }

    // to carry the data from index
    public void setGameData(String[] playerNames) {
        this.numPlayers = playerNames.length;
        for (int i = 0; i < numPlayers; i++) {
            this.players[i].setName(playerNames[i]);
        }
        
        // Initialize the game with the correct number of players
        initializeGame();
        setupPlayerInfo();
    }

    private void initializeGame() {
        hideAllCircles();
        // Show only the circles for active players
        for (int i = 0; i < numPlayers; i++) {
            Circle[] playerCircles = getPlayerCircles(i);
            playerCircles[0].setVisible(true);
        }
        updateCurrentPlayerDisplay();
    }

    private void hideAllCircles() {
        for (int i = 0; i < 4; i++) {
            Circle[] circles = getPlayerCircles(i);
            for (Circle circle : circles) {
                circle.setVisible(false);
            }
        }
    }

    private void setupPlayerInfo() {
        playerInfoContainer.getChildren().clear();

        for (int i=0; i<numPlayers; i++) {
            HBox playerInfo = new HBox(10);
            playerInfo.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Circle playerIndicator = new Circle(8);
            playerIndicator.setFill(playerColors[i]);
            playerIndicator.setStroke(Color.BLACK);

            Text playerName = new Text(players[i].getName()); // Uses player object getter now
            playerName.setStyle("-fx-font-family: 'Chalkboard SE Regular'; -fx-font-size: 16px;");

            playerInfo.getChildren().addAll(playerIndicator, playerName);
            playerInfoContainer.getChildren().add(playerInfo);
        }
    }

    // to initialize the start of the game and the player positions
    private void hideAllCirclesExceptStart() {
        Circle[] bluePlayer = getPlayerCircles(0);
        Circle[] greenPlayer = getPlayerCircles(1);
        Circle[] orangePlayer = getPlayerCircles(2);
        Circle[] redPlayer = getPlayerCircles(3);

        hideCircles(bluePlayer);
        hideCircles(greenPlayer);
        hideCircles(orangePlayer);
        hideCircles(redPlayer);

        bluePlayer[0].setVisible(true);
        greenPlayer[0].setVisible(true);
        orangePlayer[0].setVisible(true);
        redPlayer[0].setVisible(true);

        // Set the position of every player to zero
        for (int i=0; i<numPlayers; i++) {
            players[i].setPosition(0);  // Sets position of player to 0 through setter
        }
    }

    // helper method to hide all the circles except the start
    private void hideCircles(Circle[] circles) {
        for (int i=0; i<28; i++) {
            circles[i].setVisible(false);
        }
    }

    @FXML
    public void rollDice() {
        // 1 to 6 for dice rolls
        int roll = random.nextInt(6) + 1;
        diceResult.setText("Dice: " + roll);
        movePlayer(currentPlayer, roll);
        currentPlayer = (currentPlayer + 1) % numPlayers;
        updateCurrentPlayerDisplay();
    }

    // to move the player according to the roll dice
    private void movePlayer(int playerIndex, int spaces) {
        int currentPosition = players[playerIndex].getPosition();
        int newPosition = currentPosition + spaces;
        newPosition = newPosition % 28;

        Circle[] playerCircle = getPlayerCircles(playerIndex);
        playerCircle[currentPosition].setVisible(false);
        playerCircle[newPosition].setVisible(true);
        players[playerIndex].setPosition(newPosition);
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
}
