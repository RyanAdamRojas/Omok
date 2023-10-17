import org.junit.jupiter.api.*;
import java.io.*;
import org.junit.Assert.*;


public class ConsoleUITest {
    private ConsoleUI testUI;

    @BeforeEach
    void setUp() throws IOException {
        // TODO?
        String testInput =
                """
                C
                15
                """;

        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        OutputStream outputStream = new PrintStream(resultStream, true); // true for auto flushing
        testUI = new ConsoleUI(inputStream, outputStream);
        testUI.promptToSetMode();
        testUI.promptToSetBoard();
    }

    @AfterEach
    void tearDown(){
        // TODO?
        testUI = null;
    }

    @Test
    void testPlayGame1() throws IOException {
        String testInput =
                """
                C
                15
                1 1
                1 2
                1 3
                1 4
                1 5
                """;

        // Converting test input into the Input Stream
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        OutputStream outputStream = new PrintStream(resultStream, true); // true for auto flushing

        // Init Game
        ConsoleUI ui = new ConsoleUI(inputStream, outputStream);
        ui.promptToSetMode();
        ui.promptToSetBoard();
        ui.playGame();

        System.out.println("Results of TestPlay1:\n" + resultStream.toString());
    }

    @Test
    void testConstructor() {
        Assertions.assertEquals(15, testUI.getBoard().getSize());
    }
    @Test
    void testPlayer1Symbol(){
        Assertions.assertEquals("●", testUI.getPlayer1().getSymbol());
    }

    @Test
    void tesGetPlayer1(){
        Assertions.assertEquals(testUI.getPlayer1(), testUI.getPlayer1());
    }

    @Test
    void tesGetPlayer2(){
        Assertions.assertEquals(testUI.getPlayer2(), testUI.getPlayer2());
    }
}