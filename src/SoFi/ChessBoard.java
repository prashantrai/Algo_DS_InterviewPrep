package SoFi;

public class ChessBoard {

	public static void main(String[] args) {

	     Board board = new Board(8);

	     // Place a white knight at (0, 1)
	     Knight whiteKnight = new Knight(Color.WHITE);
	     board.placePiece(whiteKnight, new Position(0, 1));

	     // Place a black piece at (2, 2) to test capture
	     Knight blackKnight = new Knight(Color.BLACK);
	     board.placePiece(blackKnight, new Position(2, 2));

	     // Test 1: Valid Knight move
	     System.out.println("Test 1: " +
	             board.movePiece(new Position(0, 1), new Position(2, 2)));
	     // Expected: true (capture)

	     // Test 2: Invalid Knight move
	     System.out.println("Test 2: " +
	             board.movePiece(new Position(2, 2), new Position(3, 3)));
	     // Expected: false

	     // Test 3: Move outside board
	     System.out.println("Test 3: " +
	             board.movePiece(new Position(2, 2), new Position(10, 10)));
	     // Expected: false

	     // Test 4: Empty source square
	     System.out.println("Test 4: " +
	             board.movePiece(new Position(0, 0), new Position(1, 2)));
	     // Expected: false

	     // Test 5: Valid Knight move to empty square
	     System.out.println("Test 5: " +
	             board.movePiece(new Position(2, 2), new Position(4, 3)));
	     // Expected: true
	 }

}

//Enum for piece color
enum Color {
	WHITE, BLACK
}

//Represents a position on the board
class Position {
	int row;
	int col;

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
}

//Abstract base class for all chess pieces
abstract class Piece {
	Color color;

	public Piece(Color color) {
		this.color = color;
	}

	// Each piece will implement its own movement logic
	public abstract boolean isValidMove(Position from, Position to, Board board);

	public Color getColor() {
		return color;
	}
}

//Knight implementation
class Knight extends Piece {

	public Knight(Color color) {
		super(color);
	}

	@Override
	public boolean isValidMove(Position from, Position to, Board board) {
		int dx = Math.abs(from.row - to.row);
		int dy = Math.abs(from.col - to.col);

		// Knight moves in L-shape: (2,1) or (1,2)
		if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
			return false;
		}

		// Check destination square
		Piece destinationPiece = board.getPiece(to);

		// If destination is empty, move is valid
		if (destinationPiece == null) {
			return true;
		}

		// If destination has opponent piece, capture is valid
		return destinationPiece.getColor() != this.color;
	}
}

//Board class
class Board {
	private int size;
	private Piece[][] board;

	public Board(int size) {
		this.size = size;
		this.board = new Piece[size][size];
	}

	// Place a piece on the board
	public void placePiece(Piece piece, Position position) {
		if (!isInsideBoard(position)) {
			throw new IllegalArgumentException("Position outside board");
		}
		board[position.row][position.col] = piece;
	}

	// Get piece at a position
	public Piece getPiece(Position position) {
		if (!isInsideBoard(position)) {
			return null;
		}
		return board[position.row][position.col];
	}

	// Validate and perform a move
	public boolean movePiece(Position from, Position to) {
		if (!isInsideBoard(from) || !isInsideBoard(to)) {
			System.out.println("Move outside board boundaries");
			return false;
		}

		Piece piece = getPiece(from);

		if (piece == null) {
			System.out.println("No piece at source position");
			return false;
		}

		if (!piece.isValidMove(from, to, this)) {
			System.out.println("Invalid move for this piece");
			return false;
		}

		// Perform move
		board[to.row][to.col] = piece;
		board[from.row][from.col] = null;

		return true;
	}

	// Check if position is inside board
	private boolean isInsideBoard(Position position) {
		return position.row >= 0 && position.row < size && position.col >= 0 && position.col < size;
	}
}
