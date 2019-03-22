package calendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalendarApp extends Application {

	@Override
	public void start(Stage loginStage) {
		constructLoginForm(loginStage);
		loginStage.show();
	}

	private void constructLoginForm(final Stage primaryStage) {
		primaryStage.setTitle("JavaFX Welcome");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		final PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 6);
		actiontarget.setText("Sign in button to be pressed");
		actiontarget.setFill(Color.LIGHTGREEN);

		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				actiontarget.setFill(Color.FIREBRICK);
				actiontarget.setText("Sign in button pressed");
				System.out.println(pwBox.getText());
				Stage calendarStage = new Stage();
				constructCalendar(calendarStage);
				calendarStage.setTitle("Calendar");
				calendarStage.show();
				primaryStage.close();
			}
		};
		btn.setOnAction(handler);

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
	}

	private void constructCalendar(Stage calendarStage) {
		// Agenda agenda = new Agenda();
		// agenda.createAppointmentCallbackProperty().set(
		// new Callback<Agenda.CalendarRange, Agenda.Appointment>() {
		// @Override
		// public Agenda.Appointment call(
		// Agenda.CalendarRange calendarRange) {
		// return new Agenda.AppointmentImpl()
		// .withStartTime(calendarRange.getStartCalendar())
		// .withEndTime(calendarRange.getEndCalendar())
		// .withAppointmentGroup(
		// new Agenda.AppointmentGroupImpl()
		// .withStyleClass("group1"));
		// // it is better to have a map of appointment groups to
		// // get from
		// }
		// });

		CalendarModel calendarModel = new CalendarModel();
		CalendarView calendarView = new CalendarView(calendarModel);
		Scene scene = new Scene(calendarView);
		scene.getStylesheets().add(
				this.getClass().getResource("CalendarView" + ".css")
						.toExternalForm());

		calendarStage.setScene(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
