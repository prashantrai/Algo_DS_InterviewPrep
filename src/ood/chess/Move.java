package ood.chess;


/*
 * Represents a game move, containing the starting and ending box. 
 * The Move class will also keep track of the player who made the move, 
 * if it is a castling move, or if the move resulted in the capture of a piece.
 * */
public class Move {
	private Player player;
	private Box start;
	private Box end;
	private Piece pieceMoved;
	private Piece pieceKilled;
	private boolean castlingMove;
	
	public Move (Player player, Box start, Box end) {
		this.player = player;
		this.start = start;
		this.end = end;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Box getStart() {
		return start;
	}
	public void setStart(Box start) {
		this.start = start;
	}
	public Box getEnd() {
		return end;
	}
	public void setEnd(Box end) {
		this.end = end;
	}
	public Piece getPieceMoved() {
		return pieceMoved;
	}
	public void setPieceMoved(Piece pieceMoved) {
		this.pieceMoved = pieceMoved;
	}
	public Piece getPieceKilled() {
		return pieceKilled;
	}
	public void setPieceKilled(Piece pieceKilled) {
		this.pieceKilled = pieceKilled;
	}
	public boolean isCastlingMove() {
		return castlingMove;
	}
	public void setCastlingMove(boolean castlingMove) {
		this.castlingMove = castlingMove;
	}
}
