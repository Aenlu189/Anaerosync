import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private int currentPlayerID;
    private int boardSize;
    private Square[] board;
    private ArrayList<Objective> objectives;

    public Game(ArrayList<Player> players) {
        this.players = players;
    }

    public void rollDice() {

    }

    public void getCurrentPlayer(int id) {

    }

    public void objectiveCheck() {

    }

    public Square movePlayer() {
        return null;
    }

    public boolean completeTask(Task task) {
        return false;
    }

    public void updateResources(Player player, int money, int energy, int workforce) {

    }

    public void trade() {

    }

    public void endTurn() {

    }

    public void endGame() {

    }

    public void gameWin() {

    }
}
