import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.fail;

public class ConsoleUITest {
    private ConsoleUI testConsole;


    @BeforeEach
    void setUp(){
        testConsole = new ConsoleUI();
        /*
        testConsole.printMessage(message)
        int notSure = testConsole.promptMode()
        testConsole.printBoard()
        int notSure2 testConsole.promptMove()
        */
    }

    @AfterEach
    void tearDown(){
        testConsole = null;
    }

    @Test
    void playGame(){
        // TODO
        // Author Prof Cheon
        // converting a string to an InputStream
        String testInput = "7 8\n";
        InputStream in = new ByteArrayInputStream(testInput.getBytes());

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(result, true); // true for auto flushing

        ConsoleUI ui = new ConsoleUI(in, out);
        //ui.printMessage("Welcome to Omok!"); // print to result

        // use result.toString() to find out what is printed.
        int index = ui.promptMove(player); // read the string "7 8\n" from testInput
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