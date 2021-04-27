package ood.chess;

public class Rook extends Piece {

	public Rook(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {
		return false;
	}
}
