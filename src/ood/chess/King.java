package ood.chess;

public class King extends Piece {
	
	private boolean castlingDone;

	public King(boolean white) {
		super(white);
	}
	
	public boolean isCastlingDone() {
		return this.castlingDone;
	}
	public void setCastlingDone(boolean castlingDone) {
		this.castlingDone = castlingDone;
	}

	@Override
	public boolean canMove(Board board, Box start, Box end) {

		// if the end/destination box is not empty and has white piece
		if(this.isWhite() && end.getPiece().isWhite()) {
			return false;
		}
		
		int x = Math.abs(start.getX() - end.getX());
		int y = Math.abs(start.getY() - end.getY());
		
		/* This method can be used for Pawn as well with some changes
		 * 
		 * move: Up or Down or Left or Right
		 * 
		 * x+y == 1 and box is unoccupied
		 */
		if(x+y == 1 && (end.getPiece() == null || !end.getPiece().isWhite()) ) {
			return true;
		}
		// Diagonal : In case of pawn we only move diagonal and forward if we have an opponent 
		if(x+y == 2 && (end.getPiece() == null || !end.getPiece().isWhite()) ) {
			return true; 
		}
		
		return false;
	}
	
	private boolean isValidCastling(Board board, Box start, Box end) {
		if(this.isCastlingDone()) {
			return false;
		}
		// check for the white king castling
		if(this.isWhite() && start.getX() == 0 && start.getY() == 4 && end.getY() == 0) {
			// confirm if white king moved to the correct ending box
			if (Math.abs(end.getY() - start.getY()) == 2) {
				// check if there the Rook is in the correct position
				// check if there is no piece between Rook and the King
				// check if the King or the Rook has not moved before
				// check if this move will not result in king being attacked
				//...
				this.setCastlingDone(true);
				return true;
			}
		} else {
			// check for black castling
			this.setCastlingDone(true);
			return true;
		}
		return false;
	}
	
	public boolean isCastlingMove(Box start, Box end) {
		// check if the starting and ending position are correct
		return false; // just to avoid compilation error
	}
}
