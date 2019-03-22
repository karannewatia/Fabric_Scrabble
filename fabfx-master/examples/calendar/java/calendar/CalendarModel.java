package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fabric.lang.security.Principal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class CalendarModel {

	protected List<ObjectProperty<Calendar>> calendarObjectProperties;

	/**
	 * Represents the time when the application is started.
	 * 
	 * TODO Add support for updating the current time.
	 */
	protected ObjectProperty<Calendar> nowProperty;

	protected ObservableList<Appointment> appointments;
	
	protected ObjectProperty<Principal> owner;

	public CalendarModel() {
		nowProperty = new SimpleObjectProperty<>(this, "calendar",
				Calendar.getInstance());

		calendarObjectProperties = new ArrayList<>(7);
		for (int i = 0; i < 7; ++i) {
			ObjectProperty<Calendar> day = new SimpleObjectProperty<>();
			day.bind(new CalendarOffsetBinding(i, nowProperty));
			calendarObjectProperties.add(i, day);
		}

		// Create the collection that contains all appointments.
		// The appointments can be constructed from persistent storage.
		appointments = javafx.collections.FXCollections.observableArrayList();
	}

	public boolean isWeekend(int dayOfWeek) {
		int dayInWeek = calendarObjectProperties.get(dayOfWeek).getValue()
				.get(Calendar.DAY_OF_WEEK);
		return dayInWeek == Calendar.SATURDAY || dayInWeek == Calendar.SUNDAY;
	}
}
