package aureo.anaerosync;

import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private int moneyResource;
    private int timeResource;
    private int trustResource;
    private int position;

    private ArrayList<Task> ownedTasks;

    public Player(int id, String name, int timeResource, int moneyResource, int trustResource) {
        this.id = id;
        this.name = name;
        this.timeResource = timeResource;
        this.moneyResource = moneyResource;
        this.trustResource = trustResource;
        this.position = 0;
        this.ownedTasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setMoneyResource(int moneyResource) {
        this.moneyResource = moneyResource;
    }

    public int getMoneyResource() {
        return moneyResource;
    }

    public void setTimeResource(int timeResource) {
        this.timeResource = timeResource;
    }

    public int getTimeResource() {
        return timeResource;
    }

    public void setTrustResource(int trustResource) {
        this.trustResource = trustResource;
    }
    public int getTrustResource() {
        return trustResource;
    }

    public ArrayList<Task> getOwnedTasks() {
        return ownedTasks;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        if (task != null && !ownedTasks.contains(task)) {
            ownedTasks.add(task);
        }
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
                ", tasks=" + ownedTasks.size() +
                '}';
    }

    // Setters and other methods can be added below accordingly as becomes necessary, with proper validation
    // What we have above will do for now, as Player's full functionality is not fully implemented

    // Helper methods for adding and removing resources
    public void addMoney(int amount) {
        this.moneyResource += amount;
    }

    public void removeMoney(int amount) {
        this.moneyResource -= amount;
        if (this.moneyResource < 0) {
            this.moneyResource = 0;
        }
    }

    public void addTime(int amount) {
        this.timeResource += amount;
    }

    public void removeTime(int amount) {
        this.timeResource -= amount;
        if (this.timeResource < 0) {
            this.timeResource = 0;
        }
    }
}
