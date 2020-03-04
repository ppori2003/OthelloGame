package ca.utoronto.utm.othello.viewcontroller;

import java.util.ArrayList;

import ca.utoronto.utm.othello.model.*;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Actual view of the OthelloBoard consist of VMove(button) for each grid
 * on the pane.
 * 
 * @author minhyeok12
 *
 */
public class VMove extends Button implements Observer {
	private Othello othello;
	protected CheckBox greedyHint, randomHint, betterHint, availableMoves;
	SepiaTone greedyEffect = new SepiaTone();
	Bloom randomEffect = new Bloom();
	Lighting betterEffect = new Lighting();
	String available = "-fx-background-color: #deb887;\n" + 
			"-fx-border-color:  #555555;\n" + 
			"-fx-border-width: 1px;\n" + 
			"-fx-border-style: solid;\n" +
			"-fx-border-radius: 4px;";
	String menuStyle = "-fx-background-color: #ffebcd;\n" + 
			"-fx-border-color:  #555555;\n" + 
			"-fx-border-width: 1px;\n" + 
			"-fx-border-style: solid;\n";
	
	public VMove(Othello othello, CheckBox gh, CheckBox rh, CheckBox am, CheckBox bh) {
		this.othello = othello;
		this.greedyHint = gh;
		this.randomHint = rh;
		this.availableMoves = am;
		this.betterHint = bh;
	}
	
	/**
	 * displays the hint how PlayerBetter will move and sets the corresponding effect.
	 */
	public void showBetterHint() {
		Move bHint = this.othello.getBetterHint();
		if (GridPane.getRowIndex(this) == bHint.getRow()
				&& GridPane.getColumnIndex(this) == bHint.getCol()) {
			betterEffect.setContentInput(new InnerShadow());
			this.setEffect(betterEffect);
		}
	}
	
	/**
	 * displays the hint how PlayerGreedy will move and sets the corresponding effect.
	 */
	public void showGreedyHint() {
		Move gHint = this.othello.getGreedyHint();
		if (GridPane.getRowIndex(this) == gHint.getRow()
				&& GridPane.getColumnIndex(this) == gHint.getCol()) {
			greedyEffect.setInput(new InnerShadow());
			this.setEffect(greedyEffect);
		}
	}
	
	/**
	 * displays the hint how PlayerRandom will move and sets the corresponding effect.
	 */
	public void showRandomHint() {
		Move rHint = this.othello.getRandomHint();
		if (GridPane.getRowIndex(this) == rHint.getRow()
				&& GridPane.getColumnIndex(this) == rHint.getCol()) {
			randomEffect.setInput(new InnerShadow());
			this.setEffect(randomEffect);
		}
	}
	
	/**
	 * displays all the available move of current player
	 */
	public void showAvailableMoves() {
		othello.availableMoves();
		ArrayList<Move> moves = this.othello.getAvailableMoves();
		for (int i = 0; i < moves.size(); i ++) {
			int row = moves.get(i).getRow();
			int col = moves.get(i).getCol();
			if (GridPane.getRowIndex(this) == row
					&& GridPane.getColumnIndex(this) == col) {
				this.setStyle(available);
			}	
		}
	}
	
	/**
	 * To show what tokens are flipped when player makes move, 
	 * fade animation applied to the image when it appears on the view.
	 * @param image
	 */
	public void FadeAnimation(ImageView image) {

		FadeTransition fade = new FadeTransition(Duration.millis(800), image);
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		fade.setOnFinished(e -> this.setGraphic(image));
		fade.play();
	}
	
	/**
	 * Sets the Graphic for the button. Checks if text and prevtext is the same or if text ord prevtext is EMPTY, if so sets the
	 * graphic for the button as image. Otherwise calls for a FadeAnimation on the button.
	 * @param text
	 * @param prevtext
	 * @param image
	 */
	public void setimageview(String text, String prevtext,ImageView image) {
		if (prevtext != null) {
			if (text.equals(prevtext)) {
				this.setGraphic(image);
			}
			if ((text.equals("O") && prevtext.equals("X"))
					|| (text.equals("X") && prevtext.equals("O"))) {
				this.FadeAnimation(image);
			}
			else {
				this.setGraphic(image);
			}
		}
	}
	
	/**
	 * popup window to show the winner of the game
	 * @param othello
	 */
	public void start(Othello othello){
		String winner = Character.toString(othello.getWinner());
		Label message = null;
		if (winner.equals("X")) {
			message = new Label("Winner is Player1!!");
		}
		else if (winner.equals("O")) {
			message = new Label("Winner is Player2!!");
					
		}
		else if (winner.equals(" ")) {
			message = new Label("DRAW!");
		}
		message.setStyle("-fx-font-size: 25px");
		HBox box = new HBox(message);
		box.setPadding(new Insets(10,10,10,10));
		box.setStyle("-fx-background-color: #ffebcd;\n" + 
			"-fx-border-color:  #555555;\n" + 
			"-fx-border-width: 1px;\n" + 
			"-fx-border-style: solid;\n");
		Stage stage = new Stage();
		Scene scene = new Scene(box);
		stage.setScene(scene);
		stage.show();
	}

	
	@Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		String text = "" + othello.getToken(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));
		this.setEffect(null);
		this.setStyle("-fx-background-color: #ffebcd;" +
				"-fx-border-color:  #000000;\n" + 
				"-fx-border-width: 1px;\n" + 
				"-fx-border-style: solid;\n");
		this.setGraphic(null);
		if (GridPane.getRowIndex(this) == 0 &&
				GridPane.getColumnIndex(this) == 0 && !othello.isGameOver()) {
			this.othello.setHint("Random");
			this.othello.setHint("Greedy");
			this.othello.setHint("Better");
			this.othello.availableMoves();
		}
		Othello prev = null;
		String prevtext = null;
		ImageView whitetoken = new ImageView(new Image(getClass().getResourceAsStream("whitetoken.png"),55,55,false,false));
		ImageView blacktoken = new ImageView(new Image(getClass().getResourceAsStream("blacktoken.png"),55,55,false,false));
		if (othello.arrOthello.isEmpty()) {
			if (text.equals("O")) {
				this.setGraphic(whitetoken);
			}
			else if (text.equals("X")) {
				this.setGraphic(blacktoken);
			}
		}
		if (!othello.arrOthello.isEmpty()) {
			int size = othello.arrOthello.size() - 1;
			prev = othello.arrOthello.get(size);
			prevtext = "" + prev.getToken(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));
			if (text.equals("O")) {
				this.setimageview(text, prevtext, whitetoken);
			}
			else if (text.equals("X")) {
				this.setimageview(text, prevtext, blacktoken);
			}
		}
		if (this.greedyHint.isSelected()) {
			this.showGreedyHint();
		}
		if (this.randomHint.isSelected()) {
			this.showRandomHint();
		}
		if (this.availableMoves.isSelected()) {
			this.showAvailableMoves();
		}
		if (this.betterHint.isSelected()) {
			this.showBetterHint();
		}
		if (othello.isGameOver()) {
			this.setEffect(null);
			this.setStyle(null);
			this.setStyle(menuStyle);
			if (GridPane.getRowIndex(this) == 0 && GridPane.getColumnIndex(this) == 0) {
				this.start(this.othello);
			}
		}
	}
}	
