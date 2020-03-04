package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello extends Observable {
	public static final int DIMENSION=8; // This is an 8x8 game
	protected OthelloBoard board=new OthelloBoard(Othello.DIMENSION);
	private char whosTurn = OthelloBoard.P1;
	private int numMoves = 0;
	private Move randomHint = null, greedyHint = null, betterHint = null;
	private ArrayList<Move> avMoves = null;
	public Othello copyOthello;
	public ArrayList<Othello> arrOthello = new ArrayList<Othello>();
	private Player opponent = new PlayerHuman(this, OthelloBoard.otherPlayer(this.whosTurn));
	
	/**
	 * Changes the opponent of the current player
	 * @param type
	 */
	public void changePlayer(String type) {
		if (type == "Greedy") {
			this.opponent = new PlayerGreedy(this, OthelloBoard.otherPlayer(this.whosTurn));
		}
		else if (type == "Random") {
			this.opponent = new PlayerRandom(this, OthelloBoard.otherPlayer(this.whosTurn));
		}
		else if (type == "Better") {
			this.opponent = new PlayerBetter(this, OthelloBoard.otherPlayer(this.whosTurn));
		}
		else if (type == "Human") {
			this.opponent = new PlayerHuman(this, OthelloBoard.otherPlayer(this.whosTurn));
		}
	}
	
	/**
	 * 
	 * @return other player
	 */
	public char getOtherPlayer() {
		if(this.whosTurn == OthelloBoard.P1) {
			return OthelloBoard.P2;
		}else {
			return OthelloBoard.P1;
		}
	}
	
	/**
	 * updates required variables using param
	 * and notifies the Observer.
	 * @param othello
	 */
	public void changeOthello(Othello othello) {
		this.board = othello.board;
		this.whosTurn = othello.getWhosTurn();
		this.randomHint = othello.getRandomHint();
		this.greedyHint = othello.getGreedyHint();
		this.betterHint = othello.getBetterHint();
		this.numMoves = othello.numMoves;
		this.notifyObservers();
	}
	
	/**
	 * Stores the current state of the othello in the array
	 * @param othello
	 */
	public void storeOthello(Othello othello) {
		this.copyOthello = othello.copy();
		this.arrOthello.add(this.copyOthello);
	}
	
	/**
	 * 
	 * @return the opponent of the current player
	 */
	public Player getOpponent() {
		return this.opponent;
	}
	
	/**
	 * Depending on the type of player generates the hint.
	 * Hint displays how would the player move in the current state of the game
	 * 
	 * @param type
	 */
	public void setHint(String type) {
		if (type == "Random") {
			PlayerRandom randomPlayer = new PlayerRandom(this, this.getWhosTurn());
			if (!this.isGameOver())
				this.randomHint = randomPlayer.getMove();
		}
		else if (type == "Greedy") {
			PlayerGreedy greedyPlayer = new PlayerGreedy(this, this.getWhosTurn());
			if (!this.isGameOver())
				this.greedyHint = greedyPlayer.getMove();
		}
		
		else if (type == "Better") {
			PlayerBetter betterPlayer = new PlayerBetter (this, this.getWhosTurn());
			if (!this.isGameOver())
				this.betterHint = betterPlayer.getMove();
		}
	}
	
	/**
	 * @return hint of playerRandom
	 */
	public Move getRandomHint() {
		return this.randomHint;
	}
	
	/**
	 * 
	 * @return  hint of playerGreedy
	 */
	public Move getGreedyHint() {
		return this.greedyHint;
	}
	
	/**
	 * 
	 * @return  hint of playerBetter
	 */
	public Move getBetterHint() {
		return this.betterHint;
	}
	
	/**
	 * stores all the available moves into the list of array
	 */
	public void availableMoves() {
		ArrayList<Move> aMoves = new ArrayList<Move>();
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				Othello copy = this.copy();
				
				if(copy.board.move(row, col, this.getWhosTurn())) {
					aMoves.add(new Move(row,col));
				}
			}
		}
		this.avMoves = aMoves;
	}
	
	/**
	 * 
	 * @return the list of all the moves available
	 */
	public ArrayList<Move> getAvailableMoves() {
		return this.avMoves;
	}

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}
	
	public void setWhosTurn(char c) {
		this.whosTurn = c;
		this.notifyObservers();
	}
	
	/**
	 * 
	 * @param row 
	 * @param col
	 * @return the token at position row, col.
	 */
	public char getToken(int row, int col) {
		return this.board.get(row, col);
	}
	
	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		if(this.board.move(row, col, this.whosTurn)) {
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			char allowedMove = board.hasMove();
			if(allowedMove!=OthelloBoard.BOTH)this.whosTurn=allowedMove;
			this.numMoves++;
			this.notifyObservers();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}


	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if(!this.isGameOver())return OthelloBoard.EMPTY;
		if(this.getCount(OthelloBoard.P1)> this.getCount(OthelloBoard.P2))return OthelloBoard.P1;
		if(this.getCount(OthelloBoard.P1)< this.getCount(OthelloBoard.P2))return OthelloBoard.P2;
		return OthelloBoard.EMPTY;
	}


	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return this.whosTurn==OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return a copy of this. The copy can be manipulated without impacting this.
	 */
	public Othello copy() {
		Othello o= new Othello();
		o.board=this.board.copy();
		o.numMoves = this.numMoves;
		o.whosTurn = this.whosTurn;
		return o;
	}
	/**
	 * 
	 * @return OthelloBoard's copied board
	 */
	public OthelloBoard copyBoard() {
		return this.board.copy();
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString()+"\n";
	}


	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		Random rand = new Random();
		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while(!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);
			if(o.move(row, col)) {
				System.out.println("makes move ("+row+","+col+")");
				System.out.println(o.getBoardString()+ o.getWhosTurn()+" moves next");
			}
		}

	}
}


