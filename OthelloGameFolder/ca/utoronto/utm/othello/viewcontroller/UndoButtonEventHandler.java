package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * EventHandler for undo button. It detects the history of othello game
 * and updates the current game. When it is pressed, it undos one move.
 * @author minhyeok12
 *
 */

public class UndoButtonEventHandler implements EventHandler<ActionEvent>{
	private Othello othello;

	public UndoButtonEventHandler(Othello othello) {
		this.othello = othello;
	}
	@Override
	public void handle(ActionEvent event) {
		Button source = (Button) event.getSource();
		if(source.getText() == "Undo") {
			try {
				Othello curr = othello.arrOthello.get(othello.arrOthello.size()-1);
				othello.arrOthello.remove(othello.arrOthello.size()-1);
				othello.changeOthello(curr);
			}
			catch(IndexOutOfBoundsException error) {
			}
		}	
	}
}
	
