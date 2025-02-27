package aureo.anaerosync;

import java.util.ArrayList;
import aureo.anaerosync.Task;

public class Player {
    private int id;
    private String name;
    private int timeResource;
    private int moneyResource;
    private int position;
    private ArrayList<Task> ownedTasks;

    public Player(int id, String name, int timeResource, int moneyResource) {
        this.id = id;
        this.name = name;
        this.timeResource = timeResource;
        this.moneyResource = moneyResource;
        this.position = 0;
        this.ownedTasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeResource() {
        return timeResource;
    }

    public void setTimeResource(int timeResource) {
        this.timeResource = timeResource;
    }

    public int getMoneyResource() {
        return moneyResource;
    }

    public void setMoneyResource(int moneyResource) {
        this.moneyResource = moneyResource;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addTask(Task task) {
        if (task != null) {
            ownedTasks.add(task);
        }
    }

    public ArrayList<Task> getOwnedTasks() {
        return ownedTasks;
    }

    public boolean ownsTask(Task task) {
        if (task == null) return false;
        return ownedTasks.contains(task);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timeResource=" + timeResource +
                ", moneyResource=" + moneyResource +
                ", position=" + position +
                '}';
    }
}
