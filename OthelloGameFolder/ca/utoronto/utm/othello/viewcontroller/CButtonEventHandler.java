package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


/**
 * This is the button eventHandler for users to make a move.
 * This connects to the VMove(Button) to make a move in Othello model
 * with corresponding row and column on the gridpane. Also, it is connected
 * to the timer of the game and activates and deactivates timer according to
 * the player's turn in Othello.
 * 
 * @author minhyeok12
 *
 */


public class CButtonEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	public VTimer vTimer1;
	public VTimer vTimer2;
	
	public CButtonEventHandler(Othello othello, VTimer vTimerP1, VTimer vTimerP2) {
		this.othello = othello;
		this.vTimer1 = vTimerP1;
		this.vTimer2 = vTimerP2;
	}
	
	/**
	 * delay of movement to show AI's move after user's move when playing against AI,
	 * 
	 * @param othello
	 * @param row
	 * @param col
	 */
	public void delayedmove(Othello othello, int row, int col) {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		KeyFrame frame = new KeyFrame(Duration.seconds(0.75));
		timeline.getKeyFrames().add(frame);
		timeline.play();
		timeline.setOnFinished(e -> othello.move(row, col));
	}

	@Override
	public void handle(ActionEvent event) {
		VMove source = (VMove) event.getSource();
		int row = GridPane.getRowIndex(source);
		int col = GridPane.getColumnIndex(source);
		//stores the entire board in the list as a history for undo
		this.othello.storeOthello(this.othello);
		Player opponent = this.othello.getOpponent();
		if (othello.move(row, col) && this.othello.getWhosTurn() == opponent.getPlayer()) {
			if (opponent.getType() != "Human") {
				Move move = opponent.getMove();
				delayedmove(othello, move.getRow(), move.getCol());
			}
		}
		if (this.othello.isGameOver() || (this.vTimer1.getTime() == 0 || this.vTimer2.getTime() == 0)) {
			this.vTimer1.pause();
			this.vTimer2.pause();
		}
		if (this.othello.getWhosTurn() == 'O') {
			this.vTimer1.pause();
			this.vTimer2.start();
		} else if (this.othello.getWhosTurn() == 'X') {
			this.vTimer2.pause();
			this.vTimer1.start();
		}

	}
}
