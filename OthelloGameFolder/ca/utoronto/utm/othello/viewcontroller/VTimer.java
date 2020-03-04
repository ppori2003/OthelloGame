package ca.utoronto.utm.othello.viewcontroller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * View of Othello game. It displays the time left for each player.
 * Players are initially given 5 minutes each to play. This can be changed
 * by users by using set time.
 * @author minhyeok12
 *
 */
public class VTimer extends TextField implements Observer {
	public static final double STARTTIME = 300;
	private double time = STARTTIME;
	private String player;
	Timeline timeline = new Timeline();
	private Othello othello;

	public VTimer(Othello othello, String player) {
		this.othello = othello;
		this.player = player;
		
		if (time == STARTTIME) {
			displayTime(time);
		}
		
		timeline.setCycleCount(Timeline.INDEFINITE);
		EventHandler<ActionEvent> eventhandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e) {					
				time --;
				int min = (int) (time/60);
				int sec = (int) time - (min*60);
				
				if (time <= 0) {
					timeline.stop();
					displayTime(0);
				} else {
					if (sec == 0) {
						setText(player + " Time Left " + min + ":00");
					}
					else {
						setText(player + " Time Left " + min + ":" + sec);
				}
			}}
		};
		KeyFrame frame = new KeyFrame(Duration.seconds(1), eventhandler);
		timeline.getKeyFrames().add(frame);
	}
	
	/**
	 * Starts the timer
	 */
	public void start() {
		this.timeline.play();
	}
	
	/**
	 * Pauses the timer
	 */
	public void pause() {
		this.timeline.pause();
	}
	
	/**
	 * Sets the time that user wants.
	 * @param i
	 */
	public void setTime(double i) {
		this.time = i;
		displayTime(this.time);
	}
	/**
	 * getter method to return the current time.
	 * @return this.time
	 */
	public double getTime() {
		return this.time;
	}
	
	/**
	 * method to display time in on the text field
	 * @param i
	 */
	public void displayTime(double i) {
		if (i == 0) {
			this.pause();
			this.othello.setWhosTurn(' ');
			this.setText("TIME OVER!!!!");
		} else {
			int min = (int) (i/60);
			int sec = (int) i - (min*60);

			if (sec == 0) {
				setText(player + " Time Left " + min + ":00");
			} else {
				setText(player + " Time Left " + min + ":" + sec);
			}
		}
	}

	@Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		if (time > 0) {
			displayTime(time);
		}
		if (othello.isGameOver()) {
			this.setText("GAME OVER!");
		}
	}
}


