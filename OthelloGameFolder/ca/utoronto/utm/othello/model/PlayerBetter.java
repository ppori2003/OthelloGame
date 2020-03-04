package ca.utoronto.utm.othello.model;

import java.util.ArrayList;

/**
 * PlayerBetter makes a move by considering the strategy below.
 * Strategy: Choose a corner if available [(0,0) (0,7) (7,0) (7,7)], 
 * otherwise choose a side if available [(0,1~7), (1,1 or 7), (2,1 or 7),..., (6,1 or7) ,(7,1~7)]
 * otherwise choose a space that maximizes the players presence in the middle 4 by 4 square of the board,
 * [(2,2~5)(3,2~5)(4,2~5)(5,2~5)]
 * otherwise choose the location maximizing the movers presence on the board (Greedy Move)
 * @author minhyeok12
 *
 */
public class PlayerBetter extends Player {

	public PlayerBetter(Othello othello, char player) {
		super(othello, player);
		this.type = "Better";
	}
	
	public String getType() {
		return this.type;
	}
	
	//Condition1
	public ArrayList<Move> getCornerMoves() {
		ArrayList<Move> corner = new ArrayList<Move>();
		corner.add(new Move(0,0));
		corner.add(new Move(7,0));
		corner.add(new Move(0,7));
		corner.add(new Move(7,7));
		return corner;
		
	}
	
	// Condition2
	public ArrayList<Move> getSideMoves() {
		ArrayList<Move> side = new ArrayList<Move>();
		for (int row = 0; row < Othello.DIMENSION; row ++) {
			for (int col = 0; col < Othello.DIMENSION; col ++) {
				if (row == 0 || row == 7) {
					side.add(new Move(row,col));
				}
				else if (row > 0 && row < 7) {
					if (col == 0 || col == 7) {
						side.add(new Move(row, col));
					}
				}
			}
		}
		return side;
	}
	
	// Condition3
	public ArrayList<Move> getMidMoves() {
		ArrayList<Move> mid = new ArrayList<Move>();
		for (int row = 0; row < Othello.DIMENSION; row ++) {
			for (int col = 0; col < Othello.DIMENSION; col ++) {
				if (row == 2 || row == 5) {
					if (col >= 2 && col <= 5) {
						mid.add(new Move(row, col));
					}
				}
				else if (row == 3 || row == 4) {
					if (col == 2 || col == 5) {
						mid.add(new Move(row, col));
					}
				}
			}
		}
		return mid;
	}
	
	
	// Condition 4
	public Move getGreedyMoves() {
		Othello othelloCopy = othello.copy();
		PlayerGreedy pg = new PlayerGreedy(othelloCopy, othelloCopy.getWhosTurn());
		return pg.getMove();
	}
	
	// Checks if the Move is in the list of Moves.
	public boolean checkMoves(ArrayList<Move> list, Move move) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRow() == move.getRow() && list.get(i).getCol() == move.getCol()) {
				return true;
			}
		}
		return false;
		
	}
	// returns the bestgreedyMove in the list;
	public Move CheckCondition(ArrayList<Move> list, ArrayList<Move> available) {
		Othello othelloCopy = othello.copy();
		Move bestMove = null;
		int bestMoveCount = othelloCopy.getCount(this.player);
		for (int i = 0; i < available.size(); i++) {
			if (this.checkMoves(list, available.get(i))) {
				Move move = new Move(available.get(i).getRow(), available.get(i).getCol());
				if (othelloCopy.move(move.getRow(), move.getCol()) && othelloCopy.getCount(this.player) > bestMoveCount) {
					bestMoveCount = othelloCopy.getCount(this.player);
					bestMove = move;
				}
			}
		}
		return bestMove;
	}

	@Override
	public Move getMove() {
		Othello othelloCopy = othello.copy();
		othelloCopy.availableMoves();
		ArrayList<Move> available = othelloCopy.getAvailableMoves();
		ArrayList<Move> corner = this.getCornerMoves();
		ArrayList<Move> side = this.getSideMoves();
		ArrayList<Move> mid = this.getMidMoves();
		Move greedyMove = this.getGreedyMoves();
		Move bestMove = null;
		Move moveInCond1 = this.CheckCondition(corner, available);
		Move moveInCond2 = this.CheckCondition(side, available);
		Move moveInCond3 = this.CheckCondition(mid, available);
		if (moveInCond1 != null) {
			bestMove = moveInCond1;
		}
		else if (moveInCond2 != null) {
			bestMove = moveInCond2;
		}
		else if (moveInCond3 != null) {
			bestMove = moveInCond3;
		}
		else {
			bestMove = greedyMove;
		}
		return bestMove;
	}

}
