package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.TextField;

/**
 * View of the Othello game to display the winner when the game is over.
 * @author minhyeok12
 *
 */
public class VWinner extends TextField implements Observer {
	
	public char winner;

	public VWinner() {
		this.setText("Game In Progress");
	}
	
	/**
	 * displays the winner depending on the winner of the game
	 * @param winner
	 */
	public void displayWinner(char winner) {	
		if (winner == OthelloBoard.EMPTY)
			this.setText("DRAW!!");
		else
			this.setText("Winner is " + winner + "!!");	
	}

	@Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		char win = othello.getWinner();
		if (othello.isGameOver()) {
			displayWinner(win);
		}
	}
	
}

