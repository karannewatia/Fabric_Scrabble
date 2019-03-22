package calendar;

import java.util.Calendar;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalendarOffsetBinding extends ObjectBinding<Calendar> {

	final int dayOfWeek;
	final ObjectProperty<Calendar> nowProperty;

	/**
	 * @param dayOfWeek
	 *            ranges over 0 ~ 6
	 * @param nowProperty
	 */
	public CalendarOffsetBinding(int dayOfWeek,
			ObjectProperty<Calendar> nowProperty) {
		this.dayOfWeek = dayOfWeek;
		this.nowProperty = nowProperty;
		super.bind(nowProperty);
	}

	@Override
	protected Calendar computeValue() {
		Calendar c = getFirstDayOfWeek();
		c.add(Calendar.DAY_OF_WEEK, dayOfWeek);
		return c;
	}

	@Override
	public ObservableList<?> getDependencies() {
		ObservableList<ObjectProperty<Calendar>> dep = FXCollections
				.<ObjectProperty<Calendar>> observableArrayList();
		dep.add(nowProperty);
		return dep;
	}

	/**
	 * @return a new instance of {@code Calendar} representing the first day of
	 *         the week
	 */
	private Calendar getFirstDayOfWeek() {
		int lFirstDayOfWeek = nowProperty.getValue().getFirstDayOfWeek();
		int lCurrentDayOfWeek = nowProperty.getValue()
				.get(Calendar.DAY_OF_WEEK);
		int lDelta = 0;
		if (lFirstDayOfWeek <= lCurrentDayOfWeek) {
			lDelta = -lCurrentDayOfWeek + lFirstDayOfWeek;
		} else {
			lDelta = -lCurrentDayOfWeek - (7 - lFirstDayOfWeek);
		}
		Calendar c = (Calendar) nowProperty.getValue().clone();
		c.add(Calendar.DATE, lDelta);
		return c;
	}

}
