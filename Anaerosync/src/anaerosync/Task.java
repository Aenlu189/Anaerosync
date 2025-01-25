public class Task {
    private String taskName;
    private String taskDescription;
    private int taskMoney;
    private int taskEnergy;
    private int taskWorkforce;
    private boolean isCompleted;
    private Player owner;
    private Objective objective;

    public Task(String taskName, String taskDescription, int taskMoney, int taskEnergy, int taskWorkforce) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskMoney = taskMoney;
        this.taskEnergy = taskEnergy;
        this.taskWorkforce = taskWorkforce;
    }

    public boolean checkResources(Player player) {
        return false;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTaskMoney() {
        return taskMoney;
    }

    public void setTaskMoney(int taskMoney) {
        this.taskMoney = taskMoney;
    }

    public int getTaskEnergy() {
        return taskEnergy;
    }

    public void setTaskEnergy(int taskEnergy) {
        this.taskEnergy = taskEnergy;
    }

    public int getTaskWorkforce() {
        return taskWorkforce;
    }

    public void setTaskWorkforce(int taskWorkforce) {
        this.taskWorkforce = taskWorkforce;
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

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskMoney=" + taskMoney +
                ", taskEnergy=" + taskEnergy +
                ", taskWorkforce=" + taskWorkforce +
                ", isCompleted=" + isCompleted +
                ", owner=" + owner +
                ", objective=" + objective +
                '}';
    }
}
