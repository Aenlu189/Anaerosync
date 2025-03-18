import aureo.anaerosync.PositionManager;
import aureo.anaerosync.boardGameController;
import aureo.anaerosync.squares.Square;

import static org.junit.jupiter.api.Assertions.*;

public class PositionManagerTests {
    void allTests(){
        lowerBoundGetSquareAtPositionTest();
        upperBoundGetSquareAtPositionTest();
        validGetSquareAtPositionTest();
    }

    void lowerBoundGetSquareAtPositionTest(){
        boardGameController game = new boardGameController();
        PositionManager manager = new PositionManager();

        game.initialize();
        manager.initializeSquares(game.getTasks());

        Square s = manager.getSquareAtPosition(-1);

        assertNull(s);
    }

    void upperBoundGetSquareAtPositionTest(){
        boardGameController game = new boardGameController();
        PositionManager manager = new PositionManager();

        game.initialize();
        manager.initializeSquares(game.getTasks());

        Square s = manager.getSquareAtPosition(28);

        assertNull(s);
    }

    void validGetSquareAtPositionTest(){
        boardGameController game = new boardGameController();
        PositionManager manager = new PositionManager();

        game.initialize();
        manager.initializeSquares(game.getTasks());

        Square s = manager.getSquareAtPosition(3);

        assertEquals(s, manager.getPositions().get(3));
    }
}
