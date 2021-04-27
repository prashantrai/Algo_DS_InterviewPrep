package ood.chess;

/*
 * This class will implement the common behavior of all the pieces and
 * each kind of piece e.g. King, Queen, Knight, Rook, Pawn, Bishop
 * will extend this for their specific behavior
 * */
public abstract class Piece {
	private boolean white;
	private boolean killed;
	
	public Piece(boolean white) {
		this.white = white;
	}
	
	public boolean isWhite() {
		return white;
	}

	public void setWhite(boolean white) {
		this.white = white;
	}

	public boolean isKilled() {
		return killed;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}

	public abstract boolean canMove(Board board, Box start, Box end);
	
}
