package calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class WeekHeaderPane extends Pane {

	protected final CalendarView calendarView;
	protected final WeekBodyPane weekBodyPane;
	protected final List<DayHeaderPane> dayHeaderPanes;

	public WeekHeaderPane(CalendarView calendarView, WeekBodyPane weekBodyPane) {
		this.calendarView = calendarView;
		this.weekBodyPane = weekBodyPane;
		dayHeaderPanes = new ArrayList<DayHeaderPane>();

		// setStyle("-fx-border-color:PURPLE;-fx-border-width:1px;");

		this.prefHeightProperty().set(calendarView.textHeight);
		this.setTranslateX(1); // correct for the ScrollPane

		for (int i = 0; i < 7; i++) {
			DayHeaderPane dayHeader = new DayHeaderPane(
					weekBodyPane.dayPanes.get(i), this, i);
			dayHeader.layoutXProperty().bind(
					weekBodyPane.dayPanes.get(i).layoutXProperty());
			dayHeader.layoutYProperty().set(0);
			dayHeader.prefWidthProperty().bind(
					weekBodyPane.dayPanes.get(i).prefWidthProperty());
			dayHeader.prefHeightProperty().set(getPrefHeight());

			this.getChildren().add(dayHeader);
			dayHeaderPanes.add(dayHeader);
		}
	}

}
