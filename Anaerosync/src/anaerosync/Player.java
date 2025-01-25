import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private ArrayList<Task> ownedTasks;
    private int energy;
    private int workforce;
    private int money;
    private int currentSquare;

    public Player(int id, String name, int energy, int workforce, int money) {
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.workforce = workforce;
        this.money = money;
    }

    public void addTask(Task task) {
        ownedTasks.add(task);
    }

    public Task removeTask(Task task) {
        ownedTasks.remove(task);
        return task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getWorkforce() {
        return workforce;
    }

    public void setWorkforce(int workforce) {
        this.workforce = workforce;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Task> getOwnedTasks() {
        return ownedTasks;
    }

    public int getCurrentSquare() {
        return currentSquare;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownedTasks=" + ownedTasks +
                ", energy=" + energy +
                ", workforce=" + workforce +
                ", money=" + money +
                ", currentSquare=" + currentSquare +
                '}';
    }
}
