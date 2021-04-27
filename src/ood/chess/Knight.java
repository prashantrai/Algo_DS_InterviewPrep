package ood.chess;

public class Knight extends Piece {

	public Knight(boolean white) {
		super(white);
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {
		// if the end/destination box is not empty and has same color of piece
		if(this.isWhite() && end.getPiece().isWhite()) {
			return false;
		}
		
		int x = Math.abs(start.getX() - end.getX());
		int y = Math.abs(start.getY() - end.getY());
		
		return x*y == 2;
	}

}
