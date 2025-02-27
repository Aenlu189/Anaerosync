package aureo.anaerosync;

import java.util.HashMap;

public class PositionManager {
    private static final HashMap<Integer, Task> taskPositions = new HashMap<>();
    private static final HashMap<Integer, String> otherPositions = new HashMap<>();
    
    static {
        // Initialize special positions
        otherPositions.put(0, "HOME");
        otherPositions.put(3, "LUCK");
        otherPositions.put(7, "CORNER");
        otherPositions.put(11, "LUCK");
        otherPositions.put(14, "CORNER");
        otherPositions.put(17, "LUCK");
        otherPositions.put(21, "CORNER");
        otherPositions.put(24, "LUCK");
        
        // Initialize tasks with their positions
        initializeTasks();
    }

    // Initialization using Ignacio's tasks
    private static void initializeTasks() {
        taskPositions.put(1, new Task(1, "Research on Anaerobic Digesters", 50, 0, 50, 10, 10, 25, "RESEARCH", "/images/1.png"));
        taskPositions.put(2, new Task(2, "Research on Makers Valley", 60, 0, 65, 12, 13, 25, "RESEARCH", "/images/2.png"));
        taskPositions.put(4, new Task(3, "Design Use Cases", 80, 0, 80, 16, 16, 50, "SKETCHING", "/images/3.png"));
        taskPositions.put(5, new Task(4, "Design UML Diagram", 85, 0, 100, 17, 20, 50, "SKETCHING", "/images/4.png"));
        taskPositions.put(6, new Task(5, "Develop Visual Design", 105, 0, 120, 21, 24, 75, "FRONT-END", "/images/5.png"));
        taskPositions.put(8, new Task(6, "Start JavaScript Development", 120, 0, 135, 24, 27, 75, "FRONT-END", "/images/6.png"));
        taskPositions.put(9, new Task(7, "Develop Classes", 135, 0, 155, 27, 31, 100, "BACK-END", "/images/7.png"));
        taskPositions.put(10, new Task(8, "Implement Functions", 150, 0, 180, 30, 36, 100, "BACK-END", "/images/8.png"));
        taskPositions.put(12, new Task(9, "Design Test Cases", 190, 0, 185, 38, 37, 125, "TESTING", "/images/9.png"));
        taskPositions.put(13, new Task(10, "Testing Plan", 230, 0, 210, 46, 42, 125, "TESTING", "/images/10.png"));
        taskPositions.put(15, new Task(11, "SSL Certificate", 235, 250, 235, 47, 47, 150, "SECURITY", "/images/11.png"));
        taskPositions.put(16, new Task(12, "Cookies System", 245, 300, 240, 49, 48, 150, "SECURITY", "/images/12.png"));
        taskPositions.put(18, new Task(13, "Sell Subscriptions", 270, 400, 265, 54, 53, 175, "DEPLOYMENT", "/images/13.png"));
        taskPositions.put(19, new Task(14, "Add Media", 290, 600, 285, 58, 57, 175, "DEPLOYMENT", "/images/14.png"));
        taskPositions.put(20, new Task(15, "Partner with Lock", 300, 800, 300, 60, 60, 200, "PARTNERS", "/images/15.png"));
        taskPositions.put(22, new Task(16, "Hardware Store Partners", 315, 1000, 320, 63, 64, 200, "PARTNERS", "/images/16.png"));
        taskPositions.put(23, new Task(17, "Market Research", 330, 1500, 340, 70, 68, 225, "MARKETING", "/images/17.png"));
        taskPositions.put(25, new Task(18, "Content Development", 375, 2000, 355, 75, 71, 225, "MARKETING", "/images/18.png"));
        taskPositions.put(26, new Task(19, "Maintain System", 395, 3000, 375, 79, 75, 250, "MAINTAIN", "/images/19.png"));
        taskPositions.put(27, new Task(20, "Update System", 400, 4000, 400, 80, 80, 250, "MAINTAIN", "/images/20.png"));
    }

    // to get the player task position
    public static Task getTaskAtPosition(int position) {
        return taskPositions.get(position);
    }

    // to get other positions
    public static String getOthersAtPosition(int position) {
        return otherPositions.get(position);
    }

    // key will basically be the array of Task that Sebastian suggested
    public static boolean isTaskPosition(int position) {
        return taskPositions.containsKey(position);
    }

    // same logic for other positions
    public static boolean isOtherPosition(int position) {
        return otherPositions.containsKey(position);
    }
} 