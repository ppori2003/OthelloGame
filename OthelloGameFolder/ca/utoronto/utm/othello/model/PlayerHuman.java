package ca.utoronto.utm.othello.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PlayerHuman makes a move by user input of row and col
 * Each input must be valid (number between 0 to 7) and if it is invalid
 * it raises an error and does not make a move.
 * 
 * @author arnold
 *
 */
public class PlayerHuman extends Player {
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin= new BufferedReader(new InputStreamReader(System.in));
	private String type; // type of the player ("Human,Greedy,Random")

	public PlayerHuman(Othello othello, char player) {
		super(othello, player);
		this.type = "Human";
	}
	
	public String getType() {
		return this.type;
	}
	
	public Move getMove() {
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}

	private int getMove(String message) {
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}


