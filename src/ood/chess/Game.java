package ood.chess;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private Player[] players;
	private Board board;
	private Player currentTurn;
	private GameStatus gameStatus;
	List<Move> movesPlayed;			// keeps log of all the moves played so far
	
	
	public Game(Player p1, Player p2) {
		initialize(p1, p2);
	}
	
	private void initialize(Player p1, Player p2) {
		players[0] = p1;
		players[1] = p2;
		
		board = new Board(); // initialize the board with pieces
		
		if(p1.isWhiteSide()) {
			this.currentTurn = p1;
		} else {
			this.currentTurn = p2;
		}
		
		//movesPlayed.clear(); // this will be O(N) operation, so it's better to create a new list
		movesPlayed = new ArrayList<>();
	}

	public boolean isEnd() {
		return getGameStatus() == GameStatus.ACTIVE;
	}
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public boolean playerMove(Player player, int startX, int startY, int endX, int endY) throws Exception {
		Box startBox = board.getBox(startX, startY);
		Box endBox = board.getBox(endX, endY);
		
		Move move = new Move(player, startBox, endBox);
		
		return this.makeMove(move, player);
	}

	private boolean makeMove(Move move, Player player) {
		// get source Piece
		Piece sourcePiece = move.getStart().getPiece();
		if(sourcePiece == null) return false;
		
		// valid player
		if(player != currentTurn) return false;
		
		// check if color of piece is match player colorSide
		if(sourcePiece.isWhite() != player.isWhiteSide()) return false;
		
		// is valid move?
		if(!sourcePiece.canMove(board, move.getStart(), move.getEnd())) {
			return false;
		}
		
		// kill? If end/destination box has piece and of different color side
		Piece destinationPiece = move.getEnd().getPiece();
		if(destinationPiece != null && hasDiffentColorSide(player, destinationPiece)) {
			destinationPiece.setKilled(true);
			move.setPieceKilled(destinationPiece);
		}
		
		// castling
		if(sourcePiece instanceof King 
				&& ((King)sourcePiece).isCastlingMove(move.getStart(), move.getEnd())) {
			move.setCastlingMove(true);
		}
		
		// move piece from start to end box
		move.getEnd().setPiece(move.getStart().getPiece());
		move.getStart().setPiece(null);

		// If destination piece is King and of opposite color then set WIN status for source piece
		if(destinationPiece != null && destinationPiece instanceof King) {
			if(player.isWhiteSide()) {
				this.setGameStatus(GameStatus.WHITE_WIN);
			} else {
				this.setGameStatus(GameStatus.BLACK_WIN);
			}
		}
		
		//set the current turn to the other player
		if(this.currentTurn == players[0]) {
			this.currentTurn = players[1];
		} else {
			this.currentTurn = players[0];
		}
		
		movesPlayed.add(move); // logging played move 
		return true;
	}
	
	private boolean hasDiffentColorSide(Player player, Piece destinationPiece) {
		
		return (player.isWhiteSide() && !destinationPiece.isWhite())
				|| (!player.isWhiteSide() && destinationPiece.isWhite());
	}
	
}
