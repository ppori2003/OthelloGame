package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Button Event Handler for buttons that changes the opponent
 * that users want to play against. Users can select to play against
 * AI(Random, Greedy, Better) or another player(Human). 
 * 
 * @author minhyeok12
 *
 */
public class CPlayerButtonEventHandler implements EventHandler<ActionEvent> {
	
	private TextField txt;
	private TextField txt2;
	private Othello othello;
	
	public CPlayerButtonEventHandler(TextField txt, TextField txt2, Othello othello, CButtonEventHandler cbeh) {
		this.txt = txt;
		this.txt2 = txt2;
		this.othello = othello;
	}

	@Override
	public void handle(ActionEvent event) {
		Button source = (Button) event.getSource();
		String turn = Character.toString(othello.getWhosTurn());
		this.othello.changePlayer(source.getText());
		if (turn.equals("X")) {
			String msg = ("P2(O:White): " + source.getText());
			this.txt2.setText(msg);
		}
		else {
			String msg = ("P1(X:Black): " + source.getText());
			this.txt.setText(msg);
		}
	}

}