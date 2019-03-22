package calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The pane above a DayPane, showing the date of the day.
 * 
 * TODO Add support for all-day appointments.
 */
public class DayHeaderPane extends Pane {

	/**
	 * the correspondent DayPane
	 */
	protected final DayPane dayPane;
	/**
	 * the parent node
	 */
	protected final WeekHeaderPane weekHeaderPane;

	/**
	 * a convenient reference to the underlying model
	 */
	protected final CalendarModel model;

	/**
	 * ranges over 0~6, representing Sunday~Saturday or Monday~Sunday depending
	 * on locale
	 */
	protected final int dayOfWeek;

	public DayHeaderPane(DayPane dayPane, WeekHeaderPane weekHeader,
			int dayOfWeek) {
		this.dayPane = dayPane;
		this.weekHeaderPane = weekHeader;
		this.model = dayPane.weekBodyPane.calendarView.model;
		this.dayOfWeek = dayOfWeek;

		// setStyle("-fx-border-color:PINK;-fx-border-width:2px;");
		getStyleClass().add("DayHeader");

		Text text = new Text();
		double padding = 20;
		text.setX(padding); // align left
		text.setY(text.prefHeight(0));
		Rectangle lClip = new Rectangle(0, 0, 0, 0);
		lClip.widthProperty().bind(widthProperty().subtract(padding));
		lClip.heightProperty().bind(heightProperty());
		text.setClip(lClip);
		getChildren().add(text);
		text.textProperty().bind(
				new DateFormatStringBinding(model.calendarObjectProperties
						.get(this.dayOfWeek)));
	}

}
