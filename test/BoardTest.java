import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

public class BoardTest {
    private List<Board> boards = new ArrayList<Board>();
    private Board board_default = new Board(15);
    private Board board_inPlay = new Board(15);
    private Board board_almostWin = new Board(15);
    private Board board_almostFull = new Board(15);
    private Board board_isFull = new Board(15);

    private Board diagonalRight = new Board(15);
    private Board diagonalLeft = new Board(15);
    private Board columnStreak = new Board(15);
    private Board rowStreak = new Board(15);

    private final HumanPlayer player1 = new HumanPlayer(null,"X");
    private final HumanPlayer player2 = new HumanPlayer(null,"O");

    @BeforeEach
    void setUp() {
        // Sets 2 stone placements from each player
        board_inPlay.placeStone(player1, 2, 2);
        board_inPlay.placeStone(player1, 2, 3);
        board_inPlay.placeStone(player2, 5, 5);
        board_inPlay.placeStone(player2, 5, 6);

        // Sets 1 less than a winning streak
        board_almostWin.placeStone(player2, 2, 2);
        board_almostWin.placeStone(player2, 2, 3);
        board_almostWin.placeStone(player2, 2, 4);
        board_almostWin.placeStone(player2, 2, 5);

        // Almost fills this board with player1
        for (int x = 1; x <= board_almostFull.size(); x ++){
            for (int y = 1; y <= board_almostFull.size(); y ++) {
                // Cell (1,1) is saved for last, rest are player1
                if(!(x == 1 && y == 1))
                    board_almostFull.placeStone(player1, x, y);
            }
        }

        // Fills this board
        for (int x = 1; x <= board_isFull.size(); x++) {
            for (int y = 1; y <= board_isFull.size(); y++) {
                board_isFull.placeStone(player1, x, y);
            }
        }

        // Adding to the ArrayList
        boards.add(board_default);
        boards.add(board_inPlay);
        boards.add(board_almostWin);
        boards.add(board_almostFull);
        boards.add(board_isFull);

        // Building wining streak test boards
        diagonalRight.placeStone(player1,1,5);
        diagonalRight.placeStone(player1,2,4);
        diagonalRight.placeStone(player1,3,3);
        diagonalRight.placeStone(player1,4,2);
        diagonalRight.placeStone(player1,5,1);

        diagonalLeft.placeStone(player1, 1,1);
        diagonalLeft.placeStone(player1, 2,2);
        diagonalLeft.placeStone(player1, 3,3);
        diagonalLeft.placeStone(player1, 4,4);
        diagonalLeft.placeStone(player1, 5,5);

        columnStreak.placeStone(player1, 1,1);
        columnStreak.placeStone(player1, 1,2);
        columnStreak.placeStone(player1, 1,3);
        columnStreak.placeStone(player1, 1,4);
        columnStreak.placeStone(player1, 1,5);

        rowStreak.placeStone(player1,1,1);
        rowStreak.placeStone(player1,2,1);
        rowStreak.placeStone(player1,3,1);
        rowStreak.placeStone(player1,4,1);
        rowStreak.placeStone(player1,5,1);
    }

    @AfterEach
    void tearDown() {
        for (Board board: boards){
            board = null;
        }
        diagonalLeft = null;
        diagonalRight = null;
        rowStreak = null;
        columnStreak = null;
    }

    @Test
    void testConstructor() {
        // All boards must not be null
        for (Board board: boards){
            Assertions.assertNotNull(board, "Default Board() constructor is inadvertently setting to 'null'");
        }
    }

    @Test
    void testSize() {
        // All boards must be size 15
        for (Board board: boards) {
            Assertions.assertEquals(15, board.size(),
                    "Board.size() is not returning the correct size");
        }

        // All size less than 15 must default to 15
        for (int i = -1; i <= 14; i++) {
            Assertions.assertEquals(15, new Board(i).size(),
                    "Issue with board.size() when size is " + i);
        }

        // All sizes must be within 15-100
        for (int i = 15; i <= 100; i++) {
            Assertions.assertEquals(i, new Board(i).size(),
        "Issue with board.size() when size is " + i);
        }

        // All sized greater than 100 must default to 15
        for (int i = 101; i <= 115; i++) {
            Assertions.assertEquals(15, new Board(i).size(),
                    "Issue with board.size() when size is " + i);
        }
    }

    @Test
    void testClear() {
        // Clears all boards
        for (Board board: boards) {
            board.clear();
        }

        // Checks if all boards are clear
        for (Board board: boards) {
            for (Player[] row: board.getCells()) {
                for (Player player: row){
                    Assertions.assertNull(player,"Board.clear() is not setting all cells[][] to 'null' properly");
                }
            }
        }
    }

    @Test
    void testIsFull() {
        Assertions.assertFalse(board_default.isFull(),
                "Board.isFull() is 'true' when expected 'false'");
        Assertions.assertFalse(board_inPlay.isFull(),
                "Board.isFull() is 'true' when expected 'false'");
        Assertions.assertFalse(board_almostFull.isFull(),
                "Board.isFull() is 'true' when expected 'false'");
        Assertions.assertFalse(board_almostWin.isFull(),
                "Board.isFull() is 'true' when expected 'false'");
        Assertions.assertTrue(board_isFull.isFull(),
                "Board.isFull() is 'false' when expected 'true'");
    }

    @Test
    void testPlaceStone() {
        // Places all "stones" as player1 and checks if that cell for proper occupancy
        for (int x = 1; x < board_default.size() + 1; x++) {
            for (int y = 1; y < board_default.size() + 1; y++) {
                board_default.placeStone(player1, x, y);
                Assertions.assertSame(board_default.getCells()[y - 1][x - 1], player1, "placeStone() is not assigning object 'Player' properly when x y is " + x + " " + y);
                Assertions.assertNotSame(board_default.getCells()[y - 1][x - 1], player2, "placeStone() is not assigning object 'Player' properly when x y is " + x + " " + y);
            }
        }
    }

    @Test
    void testIsCellEmpty() {
        // (1,1) were made to be empty
        Assertions.assertTrue(board_default.isCellEmpty(1,1),
                "Board.isCellEmpty() may not be functioning properly");
        Assertions.assertTrue(board_inPlay.isCellEmpty(1,1),
                "Board.isCellEmpty() may not be functioning properly");
        Assertions.assertTrue(board_almostWin.isCellEmpty(1,1),
                "Board.isCellEmpty() may not be functioning properly");
        Assertions.assertTrue(board_almostFull.isCellEmpty(1,1),
                "Board.isCellEmpty() may not be functioning properly");
    }

    @Test
    void testEvaluateMove() {
        Assertions.assertEquals("STONE_PLACED", board_default.evaluateMove(player1, 1,1),
                "Board.evaluateMove() doesn't return STONE_PLACED when expected");

        Assertions.assertEquals("CELL_UNAVAILABLE", board_inPlay.evaluateMove(player1, 2,2),
                "Board.evaluateMove() doesn't return CELL_UNAVAILABLE when expected");

        Assertions.assertEquals("PLAYER_WIN", board_almostWin.evaluateMove(player2, 2,6),
                "Board.evaluateMove() doesn't return PLAYER_WIN when expected");

        Assertions.assertEquals("BOARD_FULL", board_almostFull.evaluateMove(player2, 1,1),
                "Board.evaluateMove() doesn't return BOARD_FULL when expected");
    }

    @Test
    void testIsCellOccupied() {
        Assertions.assertFalse(board_default.isCellOccupied(1,1),
                "Board.isCellOccupied() may not be functioning properly");
        Assertions.assertFalse(board_inPlay.isCellOccupied(1,1),
                "Board.isCellOccupied() may not be functioning properly");
        Assertions.assertFalse(board_almostWin.isCellOccupied(1,1),
                "Board.isCellOccupied() may not be functioning properly");
        Assertions.assertFalse(board_almostFull.isCellOccupied(1,1),
                "Board.isCellOccupied() may not be functioning properly");
    }

    @Test
    void testIsCellOccupiedBy() {
        Assertions.assertFalse(board_default.isCellOccupiedBy(player1,2,2),
                "Board.isCellOccupiedBy() may not be functioning properly");
        Assertions.assertTrue(board_inPlay.isCellOccupiedBy(player1, 2,2),
                "Board.isCellOccupiedBy() may not be functioning properly");
        Assertions.assertTrue(board_almostWin.isCellOccupiedBy(player2,2,2),
                "Board.isCellOccupiedBy() may not be functioning properly");
        Assertions.assertTrue(board_almostFull.isCellOccupiedBy(null,1,1),
                "Board.isCellOccupiedBy() may not be functioning properly");
    }

    @Test
    void testPlayerAt() {
        Assertions.assertNull(board_default.playerAt(player1, player2, 1, 1),
                "Board.playerAt() not functioning properly");
        Assertions.assertEquals(player1, board_inPlay.playerAt(player1, player2, 2,2),
                "Board.playerAt() not functioning properly");
        Assertions.assertEquals(player2, board_almostWin.playerAt(player1, player2, 2, 2),
                "Board.playerAt() not functioning properly");
        Assertions.assertNull(board_almostFull.playerAt(player1, player2, 1, 1),
                "Board.playerAt() not functioning properly");
    }

    @Test
    void testIsWonBy() {
        Assertions.assertFalse(board_default.isWonBy(player1),
                "Board.isWonBy() not functioning properly");
        Assertions.assertFalse(board_inPlay.isWonBy(null),
                "Board.isWonBy() not functioning properly");
        board_almostWin.placeStone(player2, 2, 6);
        Assertions.assertTrue(board_almostWin.isWonBy(player2),
                "Board.isWonBy() not functioning properly");
        Assertions.assertTrue(board_almostFull.isWonBy(player1),
                "Board.isWonBy() not functioning properly");
    }

    @Test
    void testWinningRow() {

    }

    @Test
    void testStreakWinDiagonalRight() {
        Assertions.assertTrue(diagonalRight.streakDiagonalRight(player1));
        Assertions.assertFalse(diagonalRight.streakDiagonalRight(player2));
    }

    @Test
    void testStreakWinDiagonalLeft() {
        Assertions.assertTrue(diagonalLeft.streakDiagonalLeft(player1));
        Assertions.assertFalse(diagonalLeft.streakDiagonalLeft(player2));
    }

    @Test
    void testStreakRows() {
        Assertions.assertTrue(rowStreak.streakRows(player1));
        Assertions.assertFalse(rowStreak.streakRows(player2));
    }

    @Test
    void testStreakColumns() {
        Assertions.assertTrue(columnStreak.streakColumns(player1));
        Assertions.assertFalse(columnStreak.streakColumns(player2));
    }

    @Test
    void testGetCells() {
        Assertions.assertNotNull(board_default.getCells());
    }
}