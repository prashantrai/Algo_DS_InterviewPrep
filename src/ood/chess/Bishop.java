package ood.chess;

public class Bishop extends Piece {

	public Bishop(boolean white) {
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
		
		// if x == y and no pieces are available in between start and end
		if(x == y && !hasPieceInBetween()) {
			return true;
		}
		
		return false;
	}

	private boolean hasPieceInBetween() {
		// TODO Auto-generated method stub
		return false;
	}
}
