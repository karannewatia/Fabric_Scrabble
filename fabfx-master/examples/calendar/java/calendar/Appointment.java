package calendar;

import java.util.Calendar;
import java.util.List;

import javafx.beans.property.*;

public abstract class Appointment {

	protected ObjectProperty<Calendar> startTime;
	protected ObjectProperty<Calendar> endTime;

	protected List<Person> person;

	public java.util.Calendar getStartTime() {
		return startTime.getValue();
	}

	public void setStartTime(java.util.Calendar value) {
		startTime.setValue(value);
	}

	public java.util.Calendar getEndTime() {
		return startTime.get();
	}

	public void setEndTime(java.util.Calendar value) {
		endTime.setValue(value);
	}

}
