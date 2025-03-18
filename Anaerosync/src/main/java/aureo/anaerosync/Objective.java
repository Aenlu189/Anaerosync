package aureo.anaerosync;

public class Objective {


    private int objectiveID;
    private String objectiveName;
    private int objectiveMoney;
    private int objectiveTime;
    private int objectiveTrust;
    private Task task1;
    private Task task2;
    private boolean isCompleted;
    private String completeObjectiveMessage;

    public Objective(int objectiveID, String objectiveName, int objectiveMoney, int objectiveTime, int objectiveTrust, Task task1, Task task2) {
        this.objectiveID = objectiveID;
        this.objectiveName = objectiveName;
        this.objectiveMoney = objectiveMoney;
        this.objectiveTime = objectiveTime;
        this.objectiveTrust = objectiveTrust;
        this.task1 = task1;
        this.task2 = task2;
        this.isCompleted = false;
    }

    public int getObjectiveID() {
        return objectiveID;
    }

    public void setObjectiveID(int objectiveID) {
        this.objectiveID = objectiveID;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public int getObjectiveMoney() {
        return objectiveMoney;
    }

    public void setObjectiveMoney(int objectiveMoney) {
        this.objectiveMoney = objectiveMoney;
    }

    public int getObjectiveTime() {
        return objectiveTime;
    }

    public void setObjectiveTime(int objectiveTime) {
        this.objectiveTime = objectiveTime;
    }

    public int getObjectiveTrust() {
        return objectiveTrust;
    }

    public void setObjectiveTrust(int objectiveTrust) {
        this.objectiveTrust = objectiveTrust;
    }
    
    public Task getTask1() {
    	return task1;
    }
    
    public void setTask1(Task task1) {
    	this.task1 = task1;
    }
    
    public Task getTask2() {
    	return task2;
    }
    
    public void setTask2(Task task2) {
    	this.task2 = task2;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    
    public String getCompleteObjectiveMessage() {
        return completeObjectiveMessage;
    }

    public void setCompleteObjectiveMessage(String completeObjectiveMessage) {
        this.completeObjectiveMessage = completeObjectiveMessage;
    }
    
    /*
     * public void checkIsCompleted() {
    	boolean task1Complete = task1.isCompleted();
    	boolean task2Complete = task2.isCompleted();
    	
    	if (task1Complete == true & task2Complete == true) {
    		player.money += objectiveMoney;
    		player.Time += objectiveTime;
    		communityTrust += objectiveTrust;
    		setCompleted(true);
    	}
    }
     */
    
}
