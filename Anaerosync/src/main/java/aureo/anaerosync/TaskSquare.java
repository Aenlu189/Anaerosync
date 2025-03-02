package aureo.anaerosync;

public class TaskSquare extends Square {

    private Task task;
    
    TaskSquare(int position, Task task) {
        super(position, SquareType.TASK_SQUARE);
        this.task = task;
    }
}
