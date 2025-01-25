public class TaskSquare {
    private int id;
    private SquareType squareType;
    private Task task;

    public TaskSquare(int id, SquareType squareType, Task task) {
        this.id = id;
        this.squareType = squareType;
        this.task = task;
    }

    public void landOnSquare(Player player) {

    }

    private boolean claimTask(Player player) {
        return false;
    }

    private boolean payResources(Player player) {
        return false;
    }

    private boolean offerTask() {
        return false;
    }
}
