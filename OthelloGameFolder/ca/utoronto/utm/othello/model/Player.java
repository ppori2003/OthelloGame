package ca.utoronto.utm.othello.model;

public abstract class Player {
	protected Othello othello;
	protected char player;
	protected String type; // type of the player ("Human,Greedy,Random")

	public Player(Othello othello, char player) {
		this.othello=othello;
		this.player=player;
		this.type = null;
	}
	public char getPlayer() {
		return this.player;
	}
	
	public String getType() {
		return this.type;
	}
	
	public abstract Move getMove();
}
