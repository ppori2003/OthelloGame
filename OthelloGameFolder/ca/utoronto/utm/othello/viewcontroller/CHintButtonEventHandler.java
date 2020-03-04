
package ca.utoronto.utm.othello.viewcontroller;

import java.util.ArrayList;

import ca.utoronto.utm.othello.model.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

/**
 * Button Event Handler for check boxes that displays different types of 
 * hint. When it is pressed, it detects the text associated with the check box
 * and updates the model to generate hint. It displays the hint that user wants
 * 
 * @author minhyeok12
 *
 */
public class CHintButtonEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	private GridPane gridpane;
	
	public CHintButtonEventHandler(Othello othello, GridPane grid) {
		this.othello = othello;
		this.gridpane = grid;
	}

	@Override
	public void handle(ActionEvent event) {
		ObservableList<Node> childrens = this.gridpane.getChildren();
		CheckBox source = (CheckBox) event.getSource();
		Move randomHint = this.othello.getRandomHint();
		Move greedyHint = this.othello.getGreedyHint();
		Move betterHint = this.othello.getBetterHint();
		this.othello.availableMoves();
		ArrayList<Move> moves = this.othello.getAvailableMoves();
		if (source.isSelected()) {
			if (source.getText() == "GreedyHint") {
				this.othello.setHint("Greedy");
				for (int i = 1; i < childrens.size(); i ++) {
					VMove btn = (VMove) childrens.get(i);
					btn.showGreedyHint();
				}
			}
			if (source.getText() == "RandomHint") {
				this.othello.setHint("Random");
				for (int i = 1; i < childrens.size(); i ++) {
					VMove btn = (VMove) childrens.get(i);
					btn.showRandomHint();
				}
			}
			if (source.getText() == "BetterHint") {
				this.othello.setHint("Better");
				for (int i = 1; i < childrens.size(); i ++) {
					VMove btn = (VMove) childrens.get(i);
					btn.showBetterHint();
				} 
			}
			if (source.getText() == "AvailableMove") {
				for (int i = 0; i < moves.size(); i ++) {
					int row = moves.get(i).getRow();
					int col = moves.get(i).getCol();
					for (int j = 1; j < childrens.size(); j ++) {
						VMove btn = (VMove) childrens.get(j);
						if (GridPane.getRowIndex(btn) == row && GridPane.getColumnIndex(btn) == col) {
							btn.setStyle("-fx-background-color: #deb887;\n" + 
									"-fx-border-color:  #555555;\n" + 
									"-fx-border-width: 1px;\n" + 
									"-fx-border-style: solid;\n" +
									"-fx-border-radius: 4px;");
		
						}
					}
				}
			}
		}
		
		else {
			if (source.getText() == "GreedyHint") {
				this.othello.setHint("Greedy");
				for (int i = 1; i < childrens.size(); i ++) {
					VMove btn = (VMove) childrens.get(i);
					if (GridPane.getRowIndex(btn) == greedyHint.getRow() && GridPane.getColumnIndex(btn) == greedyHint.getCol()) {
						btn.setEffect(null);
						if (btn.randomHint.isSelected()) {
							btn.showRandomHint();
						}
						if (btn.betterHint.isSelected()) {
							btn.showBetterHint();
						}
					}
				}
			}
			if (source.getText() == "RandomHint") {
				this.othello.setHint("Random");
				for (int i = 1; i < childrens.size(); i ++) {
					VMove btn = (VMove) childrens.get(i);
					if (GridPane.getRowIndex(btn) == randomHint.getRow() && GridPane.getColumnIndex(btn) == randomHint.getCol()) {
						btn.setEffect(null);
						if (btn.greedyHint.isSelected()) {
							btn.showGreedyHint();
						}
						if (btn.betterHint.isSelected()) {
							btn.showBetterHint();
						}
					}
				}
			}
			if (source.getText() == "BetterHint") {
				this.othello.setHint("Better");
				for (int i = 1; i < childrens.size(); i ++) {
					VMove btn = (VMove) childrens.get(i);
					if (GridPane.getRowIndex(btn) == betterHint.getRow() && GridPane.getColumnIndex(btn) == betterHint.getCol()) {
						btn.setEffect(null);
						if (btn.greedyHint.isSelected()) {
							btn.showGreedyHint();
						}
						if (btn.randomHint.isSelected()) {
							btn.showRandomHint();
						}
					}
				} 
			}
			if (source.getText() == "AvailableMove") {
				for (int i = 0; i < moves.size(); i ++) {
					int row = moves.get(i).getRow();
					int col = moves.get(i).getCol();
					for (int j = 1; j < childrens.size(); j ++) {
						VMove btn = (VMove) childrens.get(j);
						if (GridPane.getRowIndex(btn) == row && GridPane.getColumnIndex(btn) == col) {
							btn.setStyle("-fx-background-color: #ffebcd;" +
									"-fx-border-color:  #000000;\n" + 
									"-fx-border-width: 1px;\n" + 
									"-fx-border-style: solid;\n");
							if (btn.greedyHint.isSelected()) {
								btn.showGreedyHint();
							}
							if (btn.randomHint.isSelected()) {
								btn.showRandomHint();
							}
							if (btn.betterHint.isSelected()) {
								btn.showBetterHint();
							}
						}
					}
				}
			}
		}
	}
}

			