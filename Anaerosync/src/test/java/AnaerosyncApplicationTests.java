import org.junit.jupiter.api.Test;

public class AnaerosyncApplicationTests {
    @Test
    void allTests() {
        boardGameControllerTests tests1 = new boardGameControllerTests();
        PlayerTests tests2 = new PlayerTests();
        PositionManagerTests tests3 = new PositionManagerTests();

        tests1.allTests();
        tests2.allTests();
        tests3.allTests();
    }
}