import aureo.anaerosync.Player;
import aureo.anaerosync.Task;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
    void allTests() {
        addNullTaskTest();
        addValidTaskTest();
        addOwnedTaskTest();
        nullOwnsTaskTest();
        inListOwnsTaskTest();
        notInListOwnsTaskTest();
    }

    void addNullTaskTest(){
        Player p = new Player(1, "name",0,0,0);
        ArrayList<Task> before = p.getOwnedTasks();
        p.addTask(null);
        ArrayList<Task> after = p.getOwnedTasks();
        assertEquals(before, after);
    }
    void addValidTaskTest(){
        Player p = new Player(1, "name",0,0,0);
        Task t = new Task(1, "",0,0,0,0,0,"","",0, 0);
        p.addTask(t);
        assertTrue(p.ownsTask(t));
    }
    void addOwnedTaskTest(){
        Player p = new Player(1, "name",0,0,0);
        Task t = new Task(1, "",0,0,0,0,0,"","",0, 0);
        p.addTask(t);
        ArrayList<Task> before = p.getOwnedTasks();
        p.addTask(t);
        ArrayList<Task> after = p.getOwnedTasks();
        assertEquals(before, after);
    }
    void nullOwnsTaskTest(){
        Player p = new Player(1, "name",0,0,0);
        assertFalse(p.ownsTask(null));
    }
    void inListOwnsTaskTest(){
        Player p = new Player(1, "name",0,0,0);
        Task t = new Task(1, "",0,0,0,0,0,"","",0, 0);
        p.addTask(t);
        assertTrue(p.ownsTask(t));
    }
    void notInListOwnsTaskTest(){
        Player p = new Player(1, "name",0,0,0);
        Task t = new Task(1, "",0,0,0,0,0,"","",0, 0);
        assertFalse(p.ownsTask(t));
    }
}
