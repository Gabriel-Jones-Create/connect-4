import java.util.Random;

/**
 * 
 * Represents an agent's movement strategy in the game Connect4 by providing the
 * 
 * best starting location, moves following the initial one, defensive movements,
 * 
 * when the agent will win, when the opponent will win, when a move is bad, and
 * 
 * random moves.
 * 
 * 
 * 
 * @author gabrieljones
 *
 * 
 * 
 */

public class MyAgent extends Agent {

	/**
	 * 
	 * A random number generator to randomly decide where to place a token.
	 * 
	 */

	private Random random;

	/**
	 * 
	 * Constructs a new agent, giving it the game and telling it whether it is Red
	 * 
	 * or Yellow.
	 *
	 * 
	 * 
	 * @param game   The game the agent will be playing.
	 * 
	 * @param iAmRed True if the agent is Red, False if the agent is Yellow.
	 * 
	 */

	public MyAgent(Connect4Game game, boolean iAmRed) {

		super(game, iAmRed);

		random = new Random();

	}

//CODE WASN'T GIVEN HERE SO COMMENT?

	/**
	 * 
	 * The move method is run every time it is this agent's turn in the game. You
	 * 
	 * may assume that when move() is called, the game has at least one open slot
	 * 
	 * for a token, and the game has not already been won.
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * By the end of the move method, the agent should have placed one token into
	 * 
	 * the game at some point.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * After the move() method is called, the game engine will check to make sure
	 * 
	 * the move was valid. A move might be invalid if: - No token was place into the
	 * 
	 * game. - More than one token was placed into the game. - A previous token was
	 * 
	 * removed from the game. - The color of a previous token was changed. - There
	 * 
	 * are empty spaces below where the token was placed.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * If an invalid move is made, the game engine will announce it and the game
	 * 
	 * will be ended.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 */

	public void move() {

		int iCanWinColumn = iCanWin();

		int theyCanWinColumn = theyCanWin();

		int randomMoveColumn = randomMove();

		int basicColumn = basicStrategy();

		int decentMoveColumn = decentMove();

		int twoRowColumn = twoInARow();

		if (iCanWinColumn != -1) {

			moveOnColumn(iCanWinColumn);

		} else if (theyCanWinColumn != -1) {

			moveOnColumn(theyCanWinColumn);

		}

		else if (twoRowColumn != -1) {

			moveOnColumn(twoRowColumn);

		} else if (basicColumn != -1) {

			moveOnColumn(basicColumn);

		} else if (decentMoveColumn != -1) {

			moveOnColumn(decentMoveColumn);

		} else {

			moveOnColumn(randomMoveColumn);

		}

	}

	/**
	 * 
	 * Drops a token into a particular column so that it will fall to the bottom of
	 * 
	 * the column. If the column is already full, nothing will change.
	 *
	 * 
	 * 
	 * @param columnNumber The column into which to drop the token.
	 * 
	 */

	public void moveOnColumn(int columnNumber) {

		// Find the top empty slot in the column

		// If the column is full, lowestEmptySlot will be -1

		int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));

		// if the column is not full

		if (lowestEmptySlotIndex > -1) {

			// get the slot in this column at this index

			Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);

			// If the current agent is the Red player...

			if (iAmRed) {

				lowestEmptySlot.addRed(); // Place a red token into the empty slot

			} else {

				lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot

			}

		}

	}

	/**
	 * 
	 * Returns the index of the top empty slot in a particular column.
	 *
	 * 
	 * 
	 * @param column The column to check.
	 * 
	 * @return the index of the top empty slot in a particular column; -1 if the
	 * 
	 *         column is already full.
	 * 
	 */

	public int getLowestEmptyIndex(Connect4Column column) {

		int lowestEmptySlot = -1;

		for (int i = 0; i < column.getRowCount(); i++) {

			if (!column.getSlot(i).getIsFilled()) {

				lowestEmptySlot = i;

			}

		}

		return lowestEmptySlot;

	}

	/**
	 * 
	 * Returns a random valid move. If your agent doesn't know what to do, making a
	 * 
	 * random move can allow the game to go on anyway.
	 *
	 * 
	 * 
	 * @return a random valid move.
	 * 
	 */

	public int randomMove() {

		int i = random.nextInt(myGame.getColumnCount());

		while (getLowestEmptyIndex(myGame.getColumn(i)) == -1) {

			i = random.nextInt(myGame.getColumnCount());

		}

		return i;

	}

//NO CODE HERE ORIGINALLY COMMENT?

	/**
	 * 
	 * Returns the column that would allow the agent to win.
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * You might want your agent to check to see if it has a winning move available
	 * 
	 * to it so that it can go ahead and make that move. Implement this method to
	 * 
	 * return what column would allow the agent to win.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the column that would allow the agent to win.
	 * 
	 */

	public int iCanWin() {

		// check to see if I can win in the next move

		for (int i = 0; i < myGame.getColumnCount(); i++) {

			// create a copy of the game

			Connect4Game copyGame = new Connect4Game(myGame);

			MyAgent copyAgent = new MyAgent(copyGame, iAmRed);

			if (!copyGame.getColumn(i).getIsFull()) {

				copyAgent.moveOnColumn(i);

				if ((copyGame.gameWon() == 'R' && iAmRed) || (copyGame.gameWon() == 'Y' && !iAmRed)) {

					return i;

				}

			}

		}

		// if there is no situation where I can win

		return -1;

	}

	// NO CODE HERE ORIGINALLY COMMENT?

	/**
	 * 
	 * Returns the column that would allow the opponent to win.
	 *
	 * 
	 * 
	 * <p>
	 * 
	 * You might want your agent to check to see if the opponent would have any
	 * 
	 * winning moves available so your agent can block them. Implement this method
	 * 
	 * to return what column should be blocked to prevent the opponent from winning.
	 * 
	 * </p>
	 *
	 * 
	 * 
	 * @return the column that would allow the opponent to win.
	 * 
	 */

	public int theyCanWin() {

		for (int i = 0; i < myGame.getColumnCount(); i++) {

			// create a copy of the game

			Connect4Game copyGame = new Connect4Game(myGame);

			MyAgent copyAgent = new MyAgent(copyGame, !iAmRed);

			if (!copyGame.getColumn(i).getIsFull()) {

				copyAgent.moveOnColumn(i);

				if ((copyGame.gameWon() == 'R' && !iAmRed) || (copyGame.gameWon() == 'Y' && iAmRed)) {

					return i;

				}

			}

		}

		// if there is no situation where they can win

		return -1;

	}

	/**
	 * 
	 * Returns the name of this agent.
	 *
	 * 
	 * 
	 * @return the agent's name
	 * 
	 */

	public String getName() {

		return "My Agent";

	}

	/**
	 * 
	 * Returns <true> the index of the column where the opponent would win on the
	 * 
	 * next move if the agent moved on the specified column else return <false>
	 * 
	 * index values
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return <true> if the opponent would win on the next move if the agent moved
	 * 
	 *         on the specified column else return <false> index values
	 * 
	 */

	public boolean[] isBadMove() {

		boolean[] columnNotToMoveOn = new boolean[7];

		for (int i = 0; i < myGame.getColumnCount() - 1; i++) {

			Connect4Game copyGame = new Connect4Game(myGame);

			MyAgent copyAgent = new MyAgent(copyGame, iAmRed);

			MyAgent randAgent = new MyAgent(copyGame, !iAmRed);

			if (getLowestEmptyIndex(copyGame.getColumn(i)) <= 3 || !copyGame.getColumn(i).getIsFull()) {

				copyAgent.moveOnColumn(i);

				randAgent.moveOnColumn(i);

				if ((copyGame.gameWon() == 'R' && randAgent.iAmRed == true)

						|| (copyGame.gameWon() == 'Y' && randAgent.iAmRed == false)) {

					columnNotToMoveOn[i] = true;

				}

			}

		}

		return columnNotToMoveOn;

	}

	/**
	 * 
	 * Returns the column the agent should move on starting at the center column and
	 * 
	 * working outwards unless it is a bad move
	 * 
	 * 
	 * 
	 * @return what column the agent should move on starting in the middle and
	 * 
	 *         working outwards
	 * 
	 */

	public int basicStrategy() {

		Connect4Game copyGame = new Connect4Game(myGame);

		boolean[] badMoveColumns = isBadMove();

		if (getLowestEmptyIndex(copyGame.getColumn(3)) != -1 && !badMoveColumns[3]) {

			return 3;

		} else if (getLowestEmptyIndex(copyGame.getColumn(4)) != -1 && !badMoveColumns[4]) {

			return 4;

		} else if (getLowestEmptyIndex(copyGame.getColumn(2)) != -1 && !badMoveColumns[2]) {

			return 2;

		} else if (getLowestEmptyIndex(copyGame.getColumn(1)) != -1 && !badMoveColumns[1]) {

			return 1;

		} else if (getLowestEmptyIndex(copyGame.getColumn(5)) != -1 && !badMoveColumns[5]) {

			return 5;

		} else if (getLowestEmptyIndex(copyGame.getColumn(6)) != -1 && !badMoveColumns[6]) {

			return 6;

		} else if (getLowestEmptyIndex(copyGame.getColumn(0)) != -1 && !badMoveColumns[0]) {

			return 0;

		} else {

			return -1;

		}

	}

	/**
	 * 
	 * Returns the column, starting at index 0 and incrementing by 1, the agent
	 * 
	 * should move on if it is not a bad move and the column is not full
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return what column starting at index 0 and incrementing by 1, the agent
	 * 
	 *         should move on if it is not a bad move and the column is not full
	 * 
	 */

	public int decentMove() {

		boolean[] badMoveColumns = isBadMove();

		for (int i = 0; i < myGame.getColumnCount() - 1; i++) {

			if (!badMoveColumns[i] && !myGame.getColumn(i).getIsFull()) {

				return i;

			}

		}

		return -1;

	}

	/**
	 * 
	 * Returns the column, closest to index 0, the agent should move on if the
	 * 
	 * opponent has moved two spots in a row
	 * 
	 * 
	 * 
	 * @return what column, closest to index 0, the agent should move on if the
	 * 
	 *         opponent has moved two spots in a row
	 * 
	 */
	public int twoInARow() {
		char[][] board = myGame.getBoardMatrix();
		boolean[] badMoveColumn = isBadMove();
		if (iAmRed) {
			for (int i = board.length - 1; i >= 0; i--) {
				for (int j = 1; j < board[i].length - 3; j++) {
					if ((board[i][j - 1] == 'B' && board[i][j] == 'Y' && board[i][j + 1] == 'Y'
							&& board[i][j + 2] == 'B') && !badMoveColumn[j - 1]) {
						return j - 1;
					}

				}
			}
		} else if (!iAmRed) {
			for (int i = board.length - 1; i >= 0; i--) {
				for (int j = 1; j < board[i].length - 3; j++) {
					if ((board[i][j - 1] == 'B' && board[i][j] == 'R' && board[i][j + 1] == 'R'
							&& board[i][j + 2] == 'B') && !badMoveColumn[j - 1]) {
						return j - 1;
					}

				}
			}
		}
		return -1;
	}
}
