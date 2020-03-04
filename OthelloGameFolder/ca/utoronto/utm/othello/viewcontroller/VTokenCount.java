package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.scene.control.TextField;

/**
 * View of the Othello game to show the current number of each player's
 * token on the board.
 * @author minhyeok12
 *
 */
public class VTokenCount extends TextField implements Observer {

	
	public VTokenCount() {
		this.setText(" X: 2" + " O: 2");
	}

	@Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		String p1 = String.valueOf(othello.getCount(OthelloBoard.P1));
		String p2 = String.valueOf(othello.getCount(OthelloBoard.P2));
		this.setText(" X: " + p1 + " O: " + p2);
	}
}
