package calendar;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

class WeekBodyPane extends Pane {

	// parent node
	final CalendarView calendarView;
	// children nodes
	final List<DayPane> dayPanes;

	/**
	 * This constructor defines how the WeekPane widget is constructed.
	 */
	public WeekBodyPane(CalendarView calendarView) {
		this.calendarView = calendarView;
		this.dayPanes = new ArrayList<DayPane>(7);

		// draw hour rows
		for (int hour = 0; hour < 24; hour++) {
			// hour line
			drawHourLine(hour);
			// half hour line
			drawHalfHourLine(hour);
			// hour text
			drawHourText(hour);
		}

		// draw day columns
		for (int i = 0; i < 7; i++) {
			DayPane lDayPane = new DayPane(this, i);
			lDayPane.setId("dayPane" + i);
			lDayPane.layoutXProperty().bind(
					this.calendarView.dayWidthProperty.multiply(i).add(
							this.calendarView.timeWidth));
			lDayPane.layoutYProperty().set(0.0);
			lDayPane.prefWidthProperty().bind(
					this.calendarView.dayWidthProperty);
			lDayPane.prefHeightProperty().set(this.calendarView.dayHeight);
			this.getChildren().add(lDayPane);
			this.dayPanes.add(lDayPane);

			if (calendarView.model.isWeekend(i))
				lDayPane.setStyle("-fx-background-color: #e2e2e290;");
			else
				lDayPane.setStyle("-fx-background-color: #ffffff90;");
		}
	}

	private void drawHourLine(int hour) {
		Line l = new Line();
		l.setId("hourLine" + hour);
		l.getStyleClass().add("HourLine");
		l.startXProperty().set(0.0);
		l.startYProperty().set(Util.snapXY(calendarView.hourHeight * hour));
		l.endXProperty().bind(Util.snapXY(widthProperty()));
		l.endYProperty().set(Util.snapXY(l.getStartY()));
		getChildren().add(l);
	}

	private void drawHalfHourLine(int hour) {
		Line l = new Line(0, 10, 100, 10);
		l.setId("halfHourLine" + hour);
		l.getStyleClass().add("HalfHourLine");
		l.startXProperty().set(Util.snapXY(calendarView.timeWidth));
		l.endXProperty().bind(Util.snapXY(widthProperty()));
		l.startYProperty().set(
				Util.snapXY(calendarView.hourHeight * (hour + 0.5)));
		l.endYProperty().set(Util.snapXY(l.getStartY()));
		getChildren().add(l);
	}

	private void drawHourText(int hour) {
		Text t = new Text(hour + ":00");
		t.xProperty().set(
				calendarView.timeWidth - t.getBoundsInParent().getWidth()
						- calendarView.timeWhitespace / 2);
		t.yProperty().set(calendarView.hourHeight * hour);
		t.setTranslateY(t.getBoundsInParent().getHeight());
		// move it under the line
		t.getStyleClass().add("HourLabel");
		t.setFontSmoothingType(FontSmoothingType.LCD);
		getChildren().add(t);
	}

}
