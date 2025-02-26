package aureo.anaerosync;

import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private int moneyResource;
    private int timeResource;
    private int position;
    /**
     * WARNING!!!!! Currently implemented as String[] but should be Task[]. Update once we add the Task class to this project
     * */
    private ArrayList<String> ownedTasks;

    public Player(int id, String name, int moneyResource, int timeResource) {
        this.id = id;
        this.name = name;
        this.moneyResource = moneyResource;
        this.timeResource = timeResource;
        this.position = 0;
        this.ownedTasks = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getMoneyResource() {
        return moneyResource;
    }
    public int getTimeResource() {
        return timeResource;
    }
    public ArrayList<String> getOwnedTasks() {
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

    /**
     * WARNING!!!! Will change once we have class implementation of Tasks
     * @param task: (currently a String but should change to Task object)
     */
    public void addOwnedTasks(String task) {
        this.ownedTasks.add(task);
    }

    // Setters and other methods can be added below accordingly as becomes necessary, with proper validation
    // What we have above will do for now, as Player's full functionality is not fully implemented


}
