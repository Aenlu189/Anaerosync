import aureo.anaerosync.PositionManager;
import aureo.anaerosync.Task;
import aureo.anaerosync.squares.LuckSquare;
import aureo.anaerosync.squares.Square;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PositionManagerTests {

    void allTests(){
        lowerGetSquareAtPositionTest();
        upperGetSquareAtPositionTest();
        validGetSquareAtPositionTest();
    }

    @Test
    void lowerGetSquareAtPositionTest(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(1, "Research on Anaerobic Digestion", 50, 0, 50, 25, "RESEARCH", "/images/1.png", 10, 10, 10));
        tasks.add(new Task(2, "Research on Makers Valley", 60, 0, 65, 25, "RESEARCH", "/images/2.png", 12, 13, 10));
        tasks.add(new Task(3, "Design Use Cases", 80, 0, 80,  50, "SKETCHING", "/images/3.png", 16, 16, 20));
        tasks.add(new Task(4, "Design UML Diagram", 85, 0, 100, 50, "SKETCHING", "/images/4.png", 17, 20, 20));
        tasks.add(new Task(5, "Develop Visual Design on Figma", 105, 0, 21, 75, "FRONT-END", "/images/5.png", 21, 24, 30));
        tasks.add(new Task(6, "Start Developing in JavaScript", 120, 0, 135,  75, "FRONT-END", "/images/6.png", 24, 27, 30));
        tasks.add(new Task(7, "Develop Classes", 135, 0, 155,  100, "BACK-END", "/images/7.png", 27, 31, 40));
        tasks.add(new Task(8, "Implement Functions and Methods", 150, 0, 180,  100, "BACK-END", "/images/8.png", 30, 36, 40));
        tasks.add(new Task(9, "Design Test Cases", 190, 0, 185, 125, "TESTING", "/images/9.png", 38, 37,50));
        tasks.add(new Task(10, "Implement Testing Acceptance Plan", 230, 0, 210, 125, "TESTING", "/images/10.png", 46, 42, 50));
        tasks.add(new Task(11, "Implement Cookies System", 245, 300, 240, 150, "CYBER-SECURITY", "/images/11.png", 49, 48, 60));
        tasks.add(new Task(12, "Buy Domain SSL Certificate", 235, 250, 235, 150, "CYBER-SECURITY", "/images/12.png", 47, 47, 60));
        tasks.add(new Task(13, "Sell Subscriptions to Hardware Stores", 270, 400, 265, 175, "DEPLOYMENT", "/images/13.png", 54, 53, 70));
        tasks.add(new Task(14, "Add Videos and Images", 290, 600, 285,  175, "DEPLOYMENT", "/images/14.png", 58, 57, 70));
        tasks.add(new Task(15, "Partner with Lock", 300, 800, 300,  200, "PARTNERSHIPS", "/images/15.png", 60, 60, 80));
        tasks.add(new Task(16, "Partner with Hardware Stores", 315, 1000, 320,  200, "PARTNERSHIPS", "/images/16.png", 63, 64, 80));
        tasks.add(new Task(17, "Market Research", 350, 1500, 340,  225, "MARKETING", "/images/17.png", 70, 68, 90));
        tasks.add(new Task(18, "Content Development", 375, 2000, 355,  225, "MARKETING", "/images/18.png", 75, 71, 90));
        tasks.add(new Task(19, "Maintaining AnaeroSync", 395, 3000, 375,  250, "SUSTAINABILITY", "/images/19.png", 79, 75, 100));
        tasks.add(new Task(20, "Updating AnaeroSync", 400, 4000, 400,  250, "SUSTAINABILITY", "/images/20.png", 80, 80, 100));

        PositionManager.initializeSquares(tasks);

        Square s = PositionManager.getSquareAtPosition(-1);
        assertNull(s);
    }
    @Test
    void upperGetSquareAtPositionTest(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(1, "Research on Anaerobic Digestion", 50, 0, 50, 25, "RESEARCH", "/images/1.png", 10, 10, 10));
        tasks.add(new Task(2, "Research on Makers Valley", 60, 0, 65, 25, "RESEARCH", "/images/2.png", 12, 13, 10));
        tasks.add(new Task(3, "Design Use Cases", 80, 0, 80,  50, "SKETCHING", "/images/3.png", 16, 16, 20));
        tasks.add(new Task(4, "Design UML Diagram", 85, 0, 100, 50, "SKETCHING", "/images/4.png", 17, 20, 20));
        tasks.add(new Task(5, "Develop Visual Design on Figma", 105, 0, 21, 75, "FRONT-END", "/images/5.png", 21, 24, 30));
        tasks.add(new Task(6, "Start Developing in JavaScript", 120, 0, 135,  75, "FRONT-END", "/images/6.png", 24, 27, 30));
        tasks.add(new Task(7, "Develop Classes", 135, 0, 155,  100, "BACK-END", "/images/7.png", 27, 31, 40));
        tasks.add(new Task(8, "Implement Functions and Methods", 150, 0, 180,  100, "BACK-END", "/images/8.png", 30, 36, 40));
        tasks.add(new Task(9, "Design Test Cases", 190, 0, 185, 125, "TESTING", "/images/9.png", 38, 37,50));
        tasks.add(new Task(10, "Implement Testing Acceptance Plan", 230, 0, 210, 125, "TESTING", "/images/10.png", 46, 42, 50));
        tasks.add(new Task(11, "Implement Cookies System", 245, 300, 240, 150, "CYBER-SECURITY", "/images/11.png", 49, 48, 60));
        tasks.add(new Task(12, "Buy Domain SSL Certificate", 235, 250, 235, 150, "CYBER-SECURITY", "/images/12.png", 47, 47, 60));
        tasks.add(new Task(13, "Sell Subscriptions to Hardware Stores", 270, 400, 265, 175, "DEPLOYMENT", "/images/13.png", 54, 53, 70));
        tasks.add(new Task(14, "Add Videos and Images", 290, 600, 285,  175, "DEPLOYMENT", "/images/14.png", 58, 57, 70));
        tasks.add(new Task(15, "Partner with Lock", 300, 800, 300,  200, "PARTNERSHIPS", "/images/15.png", 60, 60, 80));
        tasks.add(new Task(16, "Partner with Hardware Stores", 315, 1000, 320,  200, "PARTNERSHIPS", "/images/16.png", 63, 64, 80));
        tasks.add(new Task(17, "Market Research", 350, 1500, 340,  225, "MARKETING", "/images/17.png", 70, 68, 90));
        tasks.add(new Task(18, "Content Development", 375, 2000, 355,  225, "MARKETING", "/images/18.png", 75, 71, 90));
        tasks.add(new Task(19, "Maintaining AnaeroSync", 395, 3000, 375,  250, "SUSTAINABILITY", "/images/19.png", 79, 75, 100));
        tasks.add(new Task(20, "Updating AnaeroSync", 400, 4000, 400,  250, "SUSTAINABILITY", "/images/20.png", 80, 80, 100));

        PositionManager.initializeSquares(tasks);

        Square s = PositionManager.getSquareAtPosition(28);
        assertNull(s);
    }

    @Test
    void validGetSquareAtPositionTest(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(1, "Research on Anaerobic Digestion", 50, 0, 50, 25, "RESEARCH", "/images/1.png", 10, 10, 10));
        tasks.add(new Task(2, "Research on Makers Valley", 60, 0, 65, 25, "RESEARCH", "/images/2.png", 12, 13, 10));
        tasks.add(new Task(3, "Design Use Cases", 80, 0, 80,  50, "SKETCHING", "/images/3.png", 16, 16, 20));
        tasks.add(new Task(4, "Design UML Diagram", 85, 0, 100, 50, "SKETCHING", "/images/4.png", 17, 20, 20));
        tasks.add(new Task(5, "Develop Visual Design on Figma", 105, 0, 21, 75, "FRONT-END", "/images/5.png", 21, 24, 30));
        tasks.add(new Task(6, "Start Developing in JavaScript", 120, 0, 135,  75, "FRONT-END", "/images/6.png", 24, 27, 30));
        tasks.add(new Task(7, "Develop Classes", 135, 0, 155,  100, "BACK-END", "/images/7.png", 27, 31, 40));
        tasks.add(new Task(8, "Implement Functions and Methods", 150, 0, 180,  100, "BACK-END", "/images/8.png", 30, 36, 40));
        tasks.add(new Task(9, "Design Test Cases", 190, 0, 185, 125, "TESTING", "/images/9.png", 38, 37,50));
        tasks.add(new Task(10, "Implement Testing Acceptance Plan", 230, 0, 210, 125, "TESTING", "/images/10.png", 46, 42, 50));
        tasks.add(new Task(11, "Implement Cookies System", 245, 300, 240, 150, "CYBER-SECURITY", "/images/11.png", 49, 48, 60));
        tasks.add(new Task(12, "Buy Domain SSL Certificate", 235, 250, 235, 150, "CYBER-SECURITY", "/images/12.png", 47, 47, 60));
        tasks.add(new Task(13, "Sell Subscriptions to Hardware Stores", 270, 400, 265, 175, "DEPLOYMENT", "/images/13.png", 54, 53, 70));
        tasks.add(new Task(14, "Add Videos and Images", 290, 600, 285,  175, "DEPLOYMENT", "/images/14.png", 58, 57, 70));
        tasks.add(new Task(15, "Partner with Lock", 300, 800, 300,  200, "PARTNERSHIPS", "/images/15.png", 60, 60, 80));
        tasks.add(new Task(16, "Partner with Hardware Stores", 315, 1000, 320,  200, "PARTNERSHIPS", "/images/16.png", 63, 64, 80));
        tasks.add(new Task(17, "Market Research", 350, 1500, 340,  225, "MARKETING", "/images/17.png", 70, 68, 90));
        tasks.add(new Task(18, "Content Development", 375, 2000, 355,  225, "MARKETING", "/images/18.png", 75, 71, 90));
        tasks.add(new Task(19, "Maintaining AnaeroSync", 395, 3000, 375,  250, "SUSTAINABILITY", "/images/19.png", 79, 75, 100));
        tasks.add(new Task(20, "Updating AnaeroSync", 400, 4000, 400,  250, "SUSTAINABILITY", "/images/20.png", 80, 80, 100));


        PositionManager.initializeSquares(tasks);

        Square expected = new LuckSquare(3);

        Square s = PositionManager.getSquareAtPosition(3);
        assertEquals(expected, s);
    }
}
