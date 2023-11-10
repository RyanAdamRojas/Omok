import org.junit.jupiter.api.*;
import java.io.*;


public class ConsoleUITest {
    private Main testUI;

    @BeforeEach
    void setUp() throws IOException {
        // Creates basic input to start a PVC game.
        String testInput =
                """
                PVP
                Human1
                Human2
                15
                """;

        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        OutputStream outputStream = new PrintStream(resultStream, true); // true for auto flushing
        testUI = new Main(inputStream, outputStream);
        testUI.init();
    }

    @AfterEach
    void tearDown(){
        testUI = null;
    }

    @Test
    void testPVPGame() throws IOException {
        // This input should result in Bonnie winning against Clyde
        String testInput =
                """
                PVP
                Bonnie
                Clyde
                15
                1 1
                2 2
                1 2
                2 3
                1 3
                2 4
                1 4
                2 6
                1 5
                """;

        // Converting test input into the Input Stream
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        OutputStream outputStream = new PrintStream(resultStream, true); // true for auto flushing

        // Passing testInput into an instance of a new game which  plays the game
        Main ui = new Main(inputStream, outputStream);
        ui.init();
        ui.playGame();
    }

    @Test
    void testConstructor() {
        // Tests default board size
        Assertions.assertNotNull(testUI.getCurrentPlayer());
    }

    @Test
    void testPromptToSetMode() {
        Assertions.assertEquals("PLAYER_VS_PLAYER",testUI.getGameMode());
    }

    @Test
    void testPromptToSetPlayers() {
        Assertions.assertEquals("Human1", testUI.getPlayer1().getName());
        Assertions.assertEquals("Human2", testUI.getPlayer2().getName());
    }

    @Test
    void testPromptToSetBoard() {
        Assertions.assertEquals(15, testUI.getBoard().size());
    }

    @Test
    void tesGetPlayer1(){
        Assertions.assertEquals(testUI.getPlayer1(), testUI.getPlayer1());
    }

    @Test
    void tesGetPlayer2(){
        Assertions.assertEquals(testUI.getPlayer2(), testUI.getPlayer2());
    }

    @Test
    void testGetCurrentPlayer() {
        Assertions.assertEquals(testUI.getCurrentPlayer(), testUI.getCurrentPlayer());
    }
}