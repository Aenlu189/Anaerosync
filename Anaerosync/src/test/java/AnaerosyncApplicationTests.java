import org.junit.jupiter.api.Test;

public class AnaerosyncApplicationTests {

    @Test
    void contextLoads() {
        PlayerTests t1 = new PlayerTests();
        PositionManagerTests t2 = new PositionManagerTests();

        t1.allTests();
        t2.allTests();
    }

}
