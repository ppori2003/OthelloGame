package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OthelloApplication extends Application {
	// REMEMBER: To run this in the lab put 
	// --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
	// in the run configuration under VM arguments.
	// You can import the JavaFX.prototype launch configuration and use it as well.
	String menuStyle = "-fx-background-color: #ffebcd;\n" + 
			"-fx-border-color:  #555555;\n" + 
			"-fx-border-width: 1px;\n" + 
			"-fx-border-style: solid;\n" +
			"-fx-border-radius: 12px;";
	String background = "-fx-base: #deb887;";
    
	@Override
	public void start(Stage stage) throws Exception {
		// Create and hook up the Model, View and the controller

		// MODEL
		Othello othello=new Othello();
		
		//Images
		Image blacktoken = new Image(getClass().getResourceAsStream("blacktoken.png"),55,55,false,false);
		Image whitetoken = new Image(getClass().getResourceAsStream("whitetoken.png"),55,55,false,false);
		
		// VIEW COMPONENTS
		//GUI component for Turn, Winner, and TokenCount of the game Othello (TextField)
		VTurn vTurn = new VTurn();
		vTurn.setPrefColumnCount(10);
		vTurn.setEditable(false);
		vTurn.setStyle(background);
		VWinner vWinner = new VWinner();
		vWinner.setPrefColumnCount(10);
		vWinner.setEditable(false);
		vWinner.setStyle(background);
		VTokenCount vToken = new VTokenCount();
		vToken.setPrefColumnCount(10);
		vToken.setEditable(false);
		vToken.setStyle(background);

		//GUI component for Game Timer in TextField
		VTimer vTimer_p1 = new VTimer(othello, Character.toString(OthelloBoard.P1));
		vTimer_p1.setStyle(background);
		vTimer_p1.setPrefColumnCount(10);
		vTimer_p1.setEditable(false);
		VTimer vTimer_p2 = new VTimer(othello, Character.toString(OthelloBoard.P2));
		vTimer_p2.setStyle(background);
		vTimer_p2.setPrefColumnCount(10);
		vTimer_p2.setEditable(false);

		//attachment of view to the model
		othello.attach(vTurn);
		othello.attach(vWinner);
		othello.attach(vToken);
		othello.attach(vTimer_p1);
		othello.attach(vTimer_p2);

		// GUI component for Opponent Chooser and Current Player
		Button vHuman = new Button("Human");
		vHuman.setStyle(background);
		Button vRandom = new Button("Random");
		vRandom.setStyle(background);
		Button vGreedy = new Button("Greedy");
		vGreedy.setStyle(background);
		Button vBetter = new Button("Better");
		vBetter.setStyle(background);
		TextField player1 = new TextField("P1(X:Black): Human");
		player1.setStyle(background);
		player1.setEditable(false);
		TextField player2 = new TextField("P2(O:White): Human");
		player2.setStyle(background);
		player2.setEditable(false);

		//GUI Component for Timer
		TextField setTimer = new TextField();
		setTimer.setPrefColumnCount(6);
	    setTimer.setEditable(true);
	    setTimer.setPromptText("Insert Time");
	    setTimer.setStyle(background);
	    Button setTimebtn = new Button();
	    setTimebtn.setText("Set Time");
	    setTimebtn.setStyle(background);
	    setTimebtn.setOnAction(new CTimeButtonEventHandler(setTimer, vTimer_p1, vTimer_p2));

	    //Button for undo move
		Button undo_btn = new Button("Undo");
		undo_btn.setStyle(background);

		//Undo ButtonEventHandler
		UndoButtonEventHandler undoEvent = new UndoButtonEventHandler(othello);
		undo_btn.setOnAction(undoEvent);

		// GUI component for greedy hint
		CheckBox greedyHint = new CheckBox("GreedyHint");
		CheckBox randomHint = new CheckBox("RandomHint");
		CheckBox availableMove = new CheckBox("AvailableMove");
		CheckBox betterHint = new CheckBox("BetterHint");
		greedyHint.setStyle(background);
		randomHint.setStyle(background);
		availableMove.setStyle(background);
		betterHint.setStyle(background);

		//GUI component for restart option button
		Button restart_btn = new Button("Restart");
		restart_btn.setStyle(background);
		restart_btn.setOnAction(c -> {

			try {
				start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		
		// ButtonEventHandler for Othello game play
		CButtonEventHandler click = new CButtonEventHandler(othello, vTimer_p1, vTimer_p2);
		CPlayerButtonEventHandler bClick = new CPlayerButtonEventHandler(player1, player2, othello, click);
		vHuman.setOnAction(bClick);
		vRandom.setOnAction(bClick);
		vGreedy.setOnAction(bClick);
		vBetter.setOnAction(bClick);
	    
	    // VIEW of OthelloBoard
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setGridLinesVisible(true);
		grid.setStyle(menuStyle);
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				// Button on OthelloBoard to play the game			
				String text = Character.toString(othello.getToken(row, col));
				VMove btn = new VMove(othello, greedyHint, randomHint, availableMove, betterHint);
				if (text.equals("X")) {
					btn.setGraphic(new ImageView(blacktoken));
				}
				else if (text.equals("O")) {
					btn.setGraphic(new ImageView(whitetoken));
				}
				btn.setStyle("-fx-background-color: #ffebcd;" +
						"-fx-border-color:  #000000;\n" + 
						"-fx-border-width: 1px;\n" + 
						"-fx-border-style: solid;\n");
				btn.setPrefSize(75, 75);
				grid.add(btn, col,row);
				btn.setOnAction(click);
				othello.attach(btn);
			}
		}
		
		//ButtonEventHandler for hint check boxes
		CHintButtonEventHandler hint = new CHintButtonEventHandler(othello, grid);
		greedyHint.setOnAction(hint);
		randomHint.setOnAction(hint);
		availableMove.setOnAction(hint);
		betterHint.setOnAction(hint);

	    // VIEW LAYOUT
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #ffebcd;");

		// GUI for Timer display and timer setting option
		HBox timerBox = new HBox(10);
		timerBox.getChildren().addAll(setTimer, setTimebtn);
		timerBox.setAlignment(Pos.CENTER_RIGHT);
		VBox timerBox2 = new VBox(10);
		timerBox2.setPadding(new Insets(10,10,10,10));
		timerBox2.getChildren().addAll(vTimer_p1, vTimer_p2, timerBox);
		timerBox2.setStyle(menuStyle);
	
		// GUI Collection of Turn, TokenCount, and Winner
		HBox infoBox = new HBox(10);
		infoBox.setPadding(new Insets(10,10,10,10));
		infoBox.getChildren().addAll(vTurn, vToken, vWinner);
		infoBox.setAlignment(Pos.CENTER);
		
		// GUI of who is playing
		HBox currentPlayerBox = new HBox(10);
		currentPlayerBox.setPadding(new Insets(10,10,10,10));
		currentPlayerBox.getChildren().addAll(player1, player2);
		currentPlayerBox.setAlignment(Pos.CENTER);
		
		HBox infoBox2 = new HBox(currentPlayerBox, infoBox);
		pane.setBottom(infoBox2);
		
		// GUI for Hint Button
		Label hintLabel = new Label("HINT");
		hintLabel.setStyle("-fx-font-size: 15px");
		VBox hintBox = new VBox(2, hintLabel, greedyHint,randomHint,availableMove, betterHint);
		hintBox.setPadding(new Insets(10,10,10,10));
		hintBox.setAlignment(Pos.CENTER_LEFT);
		hintBox.setStyle(menuStyle);
		
		// GUI of OpponentChooser
		VBox opponentChooser = new VBox(10);
		opponentChooser.setPadding(new Insets(10,10,10,10));
		Label opponentLabel = new Label("Choose Your Opponent");
		opponentLabel.setStyle("-fx-font-size: 15px");
		opponentChooser.getChildren().addAll(opponentLabel, vHuman, vRandom, vGreedy, vBetter);
		opponentChooser.setAlignment(Pos.CENTER);
		opponentChooser.setStyle(menuStyle);
		
		// GUI for restart and undo button
		HBox menuBox = new HBox(10);
		menuBox.getChildren().addAll(restart_btn, undo_btn);
		menuBox.setStyle(menuStyle);
		menuBox.setPadding(new Insets(10,10,10,10));
		menuBox.setAlignment(Pos.CENTER);
		
		// GUI of all the information set to the right side of the pane.
		VBox vbox = new VBox(15);
		vbox.setPadding(new Insets(10,10,10,10));
		vbox.getChildren().addAll(opponentChooser, hintBox, timerBox2, menuBox);
		vbox.setAlignment(Pos.CENTER);
		pane.setRight(vbox);
		pane.setCenter(grid);
		
		// SCENE
		Scene scene = new Scene(pane); 
		stage.setTitle("Othello");
		stage.setScene(scene);
				
		// LAUNCH THE GUI
		stage.show();
	}
	
	public static void main(String[] args) {
		OthelloApplication view = new OthelloApplication();
		launch(args);
	}

	
}