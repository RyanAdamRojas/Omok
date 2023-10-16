import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.Assert.*;


public class ConsoleUITest {
    // FIXME
    private ConsoleUI ui;
    private InputStream inputStream;
    private OutputStream outputStream;
    ByteArrayOutputStream resultStream;

    @BeforeEach
    void setUp() throws IOException {
        ui = new ConsoleUI();
        InputStream in;
        OutputStream out;
        ui.printMessage("This is my message!");
        int notSure = ui.promptMode();
        ui.printBoard();
        int notSure2 = ui.promptMove(ui.getCurrentPlayer());

    }

    @AfterEach
    void tearDown(){
        ui = null;
    }

    @Test
    void playGame() throws IOException {
        // TODO
        // Converting a string to an InputStream
        // String testInput = "7 8\n";
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

        inputStream = new ByteArrayInputStream(testInput.getBytes());
        resultStream = new ByteArrayOutputStream();
        outputStream = new PrintStream(resultStream, true); // true for auto flushing
        ui = new ConsoleUI(inputStream, outputStream);
        ui.printMessage("I'm printing to the result stream!"); // print to resultStream

        // use result.toString() to find out what is printed.
        int index = ui.promptMove(ui.getPlayer1()); // read the string "7 8\n" from testInput
    }

    @Test
    void testConstructor(){
        // assertEquals( ,ConsoleUI);
    }
    @Test
    void testSetCurrentPlayer(){
        // assert();
    }


}