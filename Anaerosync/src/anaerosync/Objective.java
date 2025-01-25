import java.lang.reflect.Array;

public class Objective {
    private int id;
    private Task[] taskList;
    private boolean isCompleted;

    public Objective(int id, Task[] taskList) {
        this.id = id;
        this.taskList = taskList;
        this.isCompleted = false;
    }

    public boolean checkCompleted() {
        return isCompleted;
    }
}
