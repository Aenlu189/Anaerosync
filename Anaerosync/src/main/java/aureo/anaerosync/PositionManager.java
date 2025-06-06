package aureo.anaerosync;

import aureo.anaerosync.squares.*;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class PositionManager {

    // ArrayList with all squares in the game's board
    private static final ArrayList<Square> positions = new ArrayList<Square>();

    public static void initializeSquares(ArrayList<Task> tasks){
        // Initialize squares
        positions.clear();
        positions.add(new CornerSquare(0, "Home")); // Starting position
        positions.add(new TaskSquare(1, tasks.get(0)));
        positions.add(new TaskSquare(2, tasks.get(1)));
        positions.add(new LuckSquare(3));
        positions.add(new TaskSquare(4, tasks.get(2)));
        positions.add(new TaskSquare(5, tasks.get(3)));
        positions.add(new TaskSquare(6, tasks.get(4)));
        positions.add(new CornerSquare(7, "DDOS Attack"));
        positions.add(new TaskSquare(8, tasks.get(5)));
        positions.add(new TaskSquare(9, tasks.get(6)));
        positions.add(new TaskSquare(10, tasks.get(7)));
        positions.add(new LuckSquare(11));
        positions.add(new TaskSquare(12, tasks.get(8)));
        positions.add(new TaskSquare(13, tasks.get(9)));
        positions.add(new CornerSquare(14, "Receive Funds"));
        positions.add(new TaskSquare(15, tasks.get(10)));
        positions.add(new TaskSquare(16, tasks.get(11)));
        positions.add(new LuckSquare(17));
        positions.add(new TaskSquare(18, tasks.get(12)));
        positions.add(new TaskSquare(19, tasks.get(13)));
        positions.add(new TaskSquare(20, tasks.get(14)));
        positions.add(new CornerSquare(21, "Donate Money")); // Donate Money corner
        positions.add(new TaskSquare(22, tasks.get(15)));
        positions.add(new TaskSquare(23, tasks.get(16)));
        positions.add(new TaskSquare(24, tasks.get(17)));
        positions.add(new LuckSquare(25));
        positions.add(new TaskSquare(26, tasks.get(18)));
        positions.add(new TaskSquare(27, tasks.get(19)));

        System.out.println("Board initialized with " + positions.size() + " squares");
        for (int i = 0; i < positions.size(); i++) {
            System.out.println("Position " + i + ": " + positions.get(i).getType());
        }
    }

    public static Square getSquareAtPosition(int position) {
        if (position >= 0 && position < positions.size()) {
            return positions.get(position);
        }
        return null;
    }

} 