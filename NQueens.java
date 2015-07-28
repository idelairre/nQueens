import java.util.Stack;
import java.util.Iterator;

class NQueens {
	private int boardSize;
	private boolean[][] board;
	private Stack<Position> positions;
	
	public class Position<Row, Column> {
		public Row row;
		public Column column;
		
		public Position(Row row, Column column) {
			this.row = row;
			this.column = column;
		}
		
		public Row getRow() {
			return this.row;
		}
		
		public Column getCol() {
			return this.column;
		}
		
		public void setRow(Row row) {
			this.row = row;
		}

		public void setCol(Column column) {
			this.column = column;
		}
	}
	
	public NQueens(int n) {
		this.positions = new Stack<Position>();
		this.board = new boolean[n][n];
		this.boardSize = n;
		board[0][0] = true;
	}
	
	public boolean validate(Position queryPos) {
		Position queen;
		int deltaRow;
		int deltaCol;
		Iterator<Position> iterator = positions.iterator();
		System.out.println("validating row " + queryPos.getRow() + ", column " + queryPos.getCol());
		// check each position in the stack against the query position
		while (iterator.hasNext()) {
			queen = iterator.next();
//			System.out.println("checking against queen in  row " + queen.getRow() + ", column " + queen.getCol());
			// check the differences between the rows and columns of the query position 
			// and all the queens in the stack
			deltaRow = ((Integer) queryPos.getRow()).intValue() - ((Integer) queen.getRow()).intValue();
			deltaCol = ((Integer) queryPos.getCol()).intValue() - ((Integer) queen.getCol()).intValue();
			if (queen.getCol() == queryPos.getCol() || Math.abs(deltaRow) == Math.abs(deltaCol)) {
				System.out.println("query position at row " + queryPos.getRow() + ", column " + queryPos.getCol() + " conflicts with queen at row " + queen.getRow() + ", column " + queen.getCol());
				return false;
			}
		}
		return true;
	}
	
	// takes a valid position and places it on the board
	public void place(Position queen) {
		int row = ((Integer) queen.getRow()).intValue();
		int col = ((Integer) queen.getCol()).intValue();
		board[row][col] = true;
	}
	
	// takes a position makes sure it isn't out of bounds
	// if the position is not valid, increments the column by 1
	// if an out of bounds column position does get through, resets it to zero and cycles through positions
	// places the valid position on the board and increments the column
	public void nQueen(Position position) {
		printBoard();
			if ((((Integer) position.getCol())) >= boardSize - 1 && positions.size() != boardSize && !validate(position)) {
				nQueen(backtrack());
			} else if ((((Integer) position.getRow())) == boardSize - 1 && positions.size() != boardSize && !validate(position)) {
				System.out.println("reached last row and the problem isn't solved");
				nQueen(backtrack());
			} else if (boardSize == positions.size()) {
				System.out.println("solved. printing final queen positions");
				printBoard();
				System.exit(0); // can't think of a smoother way to terminate at the moment, might have to parse nQueens into seperate methods to set appropriate breakpoints
			}
			while (!validate(position) && ((Integer) position.getCol()) < boardSize) {
				try {
					position = new Position(position.getRow(), ((Integer) position.getCol()) + 1);
					nQueen(position);			
				} catch (StackOverflowError e) {
					continue;
				}
			}

			// if it goes beyond the board size, catch it and start from 0
			if (((Integer) position.getCol()) >= boardSize - 1) {
				System.out.println("went out of bounds, cycling positions");
				while (!validate(position) && ((Integer) position.getCol()) < boardSize) {
					position = new Position(position.getRow(), 0);
					nQueen(position);			
				}
			}

			System.out.println("final test to make sure position is valid...");
			// final gate to make sure the position is valid
			if (validate(position)) {
				System.out.println("valid");
				position = position;
				positions.push(position);
				place(position);
				System.out.println("placing queen on row " + position.getRow() + ", column " + position.getCol());
				nQueen(new Position(((Integer) position.getRow()).intValue() + 1, 0)); // increment the row by 1
			} else {
				System.out.println("something went wrong...");
			}
	}
	
	// find and remove the queen in the query row in the stack
	public void removeQueen() {
		Position queen;
		if (!positions.empty()) {
			queen = positions.peek();
			positions.pop();
			System.out.println("popping queen at row " + queen.getRow() + ", column " + queen.getCol());
			board[((Integer) queen.getRow()).intValue()][((Integer) queen.getCol()).intValue()] = false;
		}
	}
	
	// takes the queen and move it to the next valid column position
	// if it can't, backtrack again
	public Position shiftQueen(Position oldPos) {
		removeQueen();
		System.out.println("shifting queen on row " + oldPos.getRow() + ", column " + oldPos.getCol());
		Position newPos;
		newPos = new Position(oldPos.getRow(), ((Integer) oldPos.getCol()) + 1);
		if ((!validate(newPos))) {
			newPos = new Position(newPos.getRow(), ((Integer) newPos.getCol()) + 1);
		}
		
		if (((Integer) newPos.getCol()) > boardSize - 1) {
			System.out.println("query position is beyond the board size");
			newPos = shiftQueen(positions.peek());
		}
		return newPos;
	}

	// pop the queen in the previous row
	// and shift it over by one
	public Position backtrack() {
		System.out.println("backtracking...");
		Position oldQueen;
		Position newQueen;
		oldQueen = positions.peek();
		newQueen = shiftQueen(oldQueen);
		System.out.println("queen moved to row " + newQueen.getRow() + ", column " + newQueen.getCol());
		return newQueen;
	}
	
	public void printBoard() {
		for (int row = 0; board[0].length > row; row++) {
			for (int col = 0; board[1].length > col; col++) {
				if (board[row][col] == true) {
					System.out.print(" Q ");
				} else {
					System.out.print(" - ");
				}
			}
			System.out.println();
		}
	}
	
	public void printPositions() {
		System.out.println("printing positions...");
		Iterator<Position> iterator = positions.iterator();
		while (iterator.hasNext()) {
			Position queen = iterator.next();
			System.out.println("row " + queen.getRow() + ", column " + queen.getCol());
		}
	}
	
	public void run() {
		Position queen;
		queen = new Position(0, 0);
		nQueen(queen);
	}
	
	public static void main(String[] args) {
//		NQueens test = new NQueens(8);
		NQueens test = new NQueens(12);
//		NQueens test = new NQueens(16);
		test.run();
	}
}