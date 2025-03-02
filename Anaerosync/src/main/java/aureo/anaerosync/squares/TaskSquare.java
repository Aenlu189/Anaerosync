package aureo.anaerosync.squares;

import aureo.anaerosync.Task;

public class TaskSquare extends Square {

    private Task task;
    
    TaskSquare(int position, Task task) {
        super(position, SquareType.TASK_SQUARE);
        this.task = task;
    }
}
