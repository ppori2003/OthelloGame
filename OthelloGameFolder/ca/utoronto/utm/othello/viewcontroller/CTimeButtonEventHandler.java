package ca.utoronto.utm.othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;


/**
 * EventHandler for button to set time limit for both player.
 * Updates the text field associated with the controller and display the time.
 * @author minhyeok12
 *
 */
public class CTimeButtonEventHandler implements EventHandler<ActionEvent> {

   private TextField txt;
   private VTimer timer_p1;
   private VTimer timer_p2;
   
   public CTimeButtonEventHandler(TextField txt, VTimer timer_p1, VTimer timer_p2) {
      this.txt = txt;
      this.timer_p1 = timer_p1;
      this.timer_p2 = timer_p2;
   }
   
   public void handle(ActionEvent event) {
	   try {
		   double time = (new Double(60)) * Double.valueOf(txt.getText());
		   this.timer_p1.setTime(time);
		   this.timer_p2.setTime(time);
	   } catch (NumberFormatException e) {
		   this.txt.setText("");
		   this.txt.setPromptText("Invalid Input!");
	   }
   }
   
}