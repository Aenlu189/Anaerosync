package aureo.anaerosync;

public class Task {
    private int id;
    private String taskName;
    private String taskObjective;
    private int taskMoney;
    private int taskTime;
    private int taskTrustNeeded;
    private int taskWorkforce;
    private boolean isCompleted;
    private Player owner;
    private String taskCard;

    public Task(int id, String taskName, int taskMoney, int taskTime, int taskTrustNeeded, 
                int taskWorkforce, int taskEnergy, int taskBonus, String taskObjective, String taskCard) {
        this.id = id;
        this.taskName = taskName;
        this.taskMoney = taskMoney;
        this.taskTime = taskTime;
        this.taskTrustNeeded = taskTrustNeeded;
        this.taskWorkforce = taskWorkforce;
        this.taskObjective = taskObjective;
        this.taskCard = taskCard;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskObjective() {
        return taskObjective;
    }

    public int getTaskMoney() {
        return taskMoney;
    }

    public int getTaskTime() {
        return taskTime;
    }

    public int getTaskTrustNeeded() {
        return taskTrustNeeded;
    }

    public int getTaskWorkforce() {
        return taskWorkforce;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getTaskCard() {
        return taskCard;
    }

    public void setTaskCard(String taskCard) {
        this.taskCard = taskCard;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskObjective='" + taskObjective + '\'' +
                ", taskMoney=" + taskMoney +
                ", taskTime=" + taskTime +
                ", taskTrustNeeded=" + taskTrustNeeded +
                ", taskWorkforce=" + taskWorkforce +
                ", isCompleted=" + isCompleted +
                ", owner=" + (owner != null ? owner.getName() : "none") +
                '}';
    }
}
