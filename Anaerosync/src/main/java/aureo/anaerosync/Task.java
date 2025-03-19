package aureo.anaerosync;

public class Task {
    private int id;
    private String taskName;
    private String taskObjective; // TODO: So far only implemented as String. Should be Objective class
    private int taskMoney;
    private int taskTime;
    private int taskTrustNeeded;
    private int taskWorkforce;
    private int taskBonus;
    private boolean isCompleted;
    private Player owner;
    private String taskCard;
    private String claimMessage;
    private String feeMessage;
    private String completeTask;
    private int feeMoney;
    private int feeTime;


    public Task(int id, String taskName, int taskMoney, int taskTrustNeeded, int taskTime,
                 int taskEnergy, int taskBonus, String taskObjective, String taskCard, int feeMoney, int feeTime) {
        this.id = id;
        this.taskName = taskName;
        this.taskMoney = taskMoney;
        this.taskTime = taskTime;
        this.taskTrustNeeded = taskTrustNeeded;
        this.taskBonus = taskBonus;
        this.taskObjective = taskObjective;
        this.taskCard = taskCard;
        this.isCompleted = false;
        this.feeMoney = feeMoney;
        this.feeTime = feeTime;
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getTaskBonus(){
        return taskBonus;
    }

    public String getTaskCard() {
        return taskCard;
    }

    public void setTaskCard(String taskCard) {
        this.taskCard = taskCard;
    }

    public String getClaimMessage() { return claimMessage; }

    public void setClaimMessage(String claimMessage) {this.claimMessage = claimMessage; }

    public String getFeeMessage() { return feeMessage; }

    public void setFeeMessage(String feeMessage) {this.feeMessage = feeMessage; }

    public String getCompleteTask() { return completeTask; }

    public void setCompleteTask(String completeTask) {this.completeTask = completeTask; }

    public int getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(int feeMoney) {
        this.feeMoney = feeMoney;
    }

    public int getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(int feeTime) {
        this.feeTime = feeTime;
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
