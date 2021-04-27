package ood.chess;


// https://www.youtube.com/watch?v=yBsWza2039o
public class Board {
	private final int BOARD_SIZE = 8;
	private final int PAWNS = 8;
	
	private Box[][] boxes;
	
	public Board() {
		boxes = new Box[BOARD_SIZE][BOARD_SIZE];
		initializeBoard();
	}

	public Box getBox(int x, int y) throws Exception {
		if(x<0 || x>7 || y<0 || y>7) {
			throw new Exception("Index out of bound");
		}
		return boxes[x][y];
	}
	
	// reset or initialize
	private void initializeBoard() {
		
		// initialize WHITE pieces
		for(int col=0; col<PAWNS; col++){ // draw pawns
			boxes[1][col] = new Box(new Pawn(true), 1, col);
        }
		
		boxes[0][0] = new Box(new Rook(true), 0, 0);
		boxes[0][7] = new Box(new Rook(true), 0, 7);
		
		boxes[0][1] = new Box(new Knight(true), 0, 1);
		boxes[0][6] = new Box(new Knight(true), 0, 6);
		
		boxes[0][2] = new Box(new Bishop(true), 0, 2);
		boxes[0][5] = new Box(new Bishop(true), 0, 5);

		boxes[0][3] = new Box(new Queen(true), 0, 3);
		boxes[0][4] = new Box(new King(true), 0, 4);
		
		
		// initialize BLACK pieces
		for(int col=0; col<PAWNS; col++){ // draw pawns
			boxes[6][col] = new Box(new Pawn(true), 6, col);
        }
		
		boxes[7][0] = new Box(new Rook(true), 7, 0);
		boxes[7][7] = new Box(new Rook(true), 7, 7);
		
		boxes[7][1] = new Box(new Knight(true), 7, 1);
		boxes[7][6] = new Box(new Knight(true), 7, 6);
		
		boxes[7][2] = new Box(new Bishop(true), 7, 2);
		boxes[7][5] = new Box(new Bishop(true), 7, 5);

		boxes[7][3] = new Box(new Queen(true), 7, 3);
		boxes[7][4] = new Box(new King(true), 7, 4);
		
	}
}
