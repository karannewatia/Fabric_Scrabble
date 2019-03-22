package calendar;

import java.util.Calendar;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class DayPane extends Pane {

	/**
	 * the parent
	 */
	protected final WeekBodyPane weekBodyPane;

	/**
	 * ranges over 0~6
	 */
	protected final int dayOfWeek;

	/**
	 * a convenient reference to the {@code CalendarModel}.
	 */
	protected final CalendarModel model;

	protected final double minApptHeight;

	protected Rectangle ghostRect;
	protected boolean dragged;

	public DayPane(WeekBodyPane weekBodyPane, int dayOfWeek) {
		this.weekBodyPane = weekBodyPane;
		this.dayOfWeek = dayOfWeek;
		this.model = weekBodyPane.calendarView.model;
		this.minApptHeight = 10;

		getStylesheets().add(
				this.getClass().getResource("CalendarView.css")
						.toExternalForm());
		getStyleClass().add("Day");

		// the appointments layout is too complex to cleanly calculate using
		// binding, so we'll listen to size changes
		widthProperty().addListener((observable) -> {
			relayout();
		});
		heightProperty().addListener((observable) -> {
			relayout();
		});

		// ---------

		// start new appointment
		setOnMousePressed((mouseEvent) -> {
			// show the rectangle
			DayPane.this.setCursor(Cursor.V_RESIZE);
			ghostRect = new Rectangle(0, mouseEvent.getY(),
					weekBodyPane.calendarView.dayWidthProperty.get(),
					minApptHeight);
			StringBuilder style = new StringBuilder();
			style.append("-fx-fill: transparent;");
			style.append("-fx-stroke: #22222280;");
			style.append("-fx-stroke-width: 1;");
			style.append("-fx-stroke-dash-array: 4 4 4 4;");
			ghostRect.setStyle(style.toString());
			DayPane.this.getChildren().add(ghostRect);

			mouseEvent.consume();
			dragged = false;
			// getSkinnable().selectedAppointments().clear();
		});

		// visualize the resizing of ghostRect
		setOnMouseDragged((mouseEvent) -> {
			double lHeight = mouseEvent.getY() - ghostRect.getY();
			ghostRect.setHeight(lHeight < minApptHeight ? minApptHeight
					: lHeight);

			mouseEvent.consume();
			dragged = true;
		});

		// end resizing
		setOnMouseReleased((mouseEvent) -> {
			mouseEvent.consume();

			// reset ui
			DayPane.this.setCursor(Cursor.HAND);
			DayPane.this.getChildren().remove(ghostRect);

			// calculate the start time
			Calendar startTime = (Calendar) model.calendarObjectProperties
					.get(this.dayOfWeek).getValue().clone();
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			startTime.set(Calendar.MILLISECOND, 0);
			startTime
					.add(Calendar.MINUTE,
							(int) (ghostRect.getY() * weekBodyPane.calendarView.durationInMinPerPixel));
			startTime.set(Calendar.MINUTE,
					Math.round(startTime.get(Calendar.MINUTE) / 15) * 15);

			// calculate the end time
			Calendar endTime = (Calendar) startTime.clone();
			endTime.add(
					Calendar.MINUTE,
					(int) (ghostRect.getHeight() * weekBodyPane.calendarView.durationInMinPerPixel));
			endTime.set(Calendar.MINUTE,
					Math.round(startTime.get(Calendar.MINUTE) / 15) * 15);
			
			System.out.println(startTime.getTime()+" ~ "+endTime.getTime());

			// clean up
			ghostRect = null;

//			// ask the control to create a new appointment (null may be
//			// returned)
//			Agenda.Appointment lAppointment = getSkinnable()
//					.createAppointmentCallbackProperty()
//					.get()
//					.call(new Agenda.CalendarRange(lStartCalendar, lEndCalendar));
//			if (lAppointment != null) {
//				getSkinnable().appointments().add(lAppointment); // the
//				// appointments
//				// collection
//				// is
//				// listened
//				// to, so
//				// they will
//				// automatically
//				// be
//				// refreshed
//			}
		});
	}

	// Rectangle resizeRectangle = null;
	// boolean dragged = false;

	// /**
	// *
	// * @return
	// */
	// public List<AbstractAppointmentPane> allAbstractAppointmentPanes() {
	// List<AbstractAppointmentPane> lPanes = new
	// ArrayList<AgendaWeekSkin.AbstractAppointmentPane>(
	// clusteredAppointmentPanes);
	// lPanes.addAll(wholedayAppointmentPanes);
	// lPanes.addAll(dayHeaderPane.appointmentHeaderPanes);
	// return lPanes;
	// }

	/**
	 * 
	 */
	private void relayout() {
		// // first add all the whole day appointments
		// int lWholedayCnt = 0;
		// for (WholedayAppointmentPane lAppointmentPane :
		// wholedayAppointmentPanes) {
		// lAppointmentPane.setLayoutX(NodeUtil.snapXY(lWholedayCnt
		// * wholedayAppointmentWidth));
		// lAppointmentPane.setLayoutY(0);
		// lAppointmentPane.setPrefSize(NodeUtil.snapWH(
		// lAppointmentPane.getLayoutX(), wholedayAppointmentWidth),
		// NodeUtil.snapWH(lAppointmentPane.getLayoutY(),
		// dayHeightProperty.get()));
		// lWholedayCnt++;
		// }
		//
		// // then add all regular appointments to the day
		// // calculate how much room is remaining for the regular appointments
		// double lRemainingWidthForRegularAppointments =
		// dayContentWidthProperty
		// .get() - (lWholedayCnt * wholedayAppointmentWidth);
		// for (AbstractClusteredDayAppointmentPane
		// lClusteredDayAppointmentPanePane : clusteredAppointmentPanes) {
		// // the X is determine by offsetting the wholeday appointments and
		// // then calculate the X of the track the appointment is placed in
		// // (available width / number of tracks)
		// lClusteredDayAppointmentPanePane
		// .setLayoutX(NodeUtil
		// .snapXY((lWholedayCnt * wholedayAppointmentWidth)
		// + (lRemainingWidthForRegularAppointments
		// / lClusteredDayAppointmentPanePane.clusterOwner.clusterTracks
		// .size() * lClusteredDayAppointmentPanePane.clusterTrackIdx)));
		//
		// // the Y is determined by the start time in minutes projected onto
		// // the total day height (being 24 hours)
		// int lTimeFactor = (lClusteredDayAppointmentPanePane.start
		// .get(Calendar.HOUR_OF_DAY) * 60)
		// + lClusteredDayAppointmentPanePane.start
		// .get(Calendar.MINUTE);
		// lClusteredDayAppointmentPanePane.setLayoutY(NodeUtil
		// .snapXY(dayHeightProperty.get() / (24 * 60) * lTimeFactor));
		//
		// // the width is the remaining width (subtracting the wholeday
		// // appointments) divided by the number of tracks in the cluster
		// double lW = (dayContentWidthProperty.get() -
		// (wholedayAppointmentPanes
		// .size() * wholedayAppointmentWidth))
		// * (1.0 / (((double)
		// lClusteredDayAppointmentPanePane.clusterOwner.clusterTracks
		// .size())));
		// // all but the most right appointment get 50% extra width, so they
		// // underlap the next track
		// if (lClusteredDayAppointmentPanePane.clusterTrackIdx <
		// lClusteredDayAppointmentPanePane.clusterOwner.clusterTracks
		// .size() - 1)
		// lW *= 1.75;
		// lClusteredDayAppointmentPanePane.setPrefWidth(NodeUtil.snapWH(
		// lClusteredDayAppointmentPanePane.getLayoutX(), lW));
		//
		// // the height is determing by the duration projected against the
		// // total dayHeight (being 24 hours)
		// double lH = (dayHeightProperty.get() / (24 * 60) *
		// (lClusteredDayAppointmentPanePane.durationInMS / 1000 / 60));
		// // the height has a minimum size, in order to be able to render
		// // sensibly
		// // if (lH < textHeight1MProperty.get() + padding) lH =
		// // textHeight1MProperty.get() + padding;
		// if (lH < 2 * padding)
		// lH = 2 * padding;
		// lClusteredDayAppointmentPanePane.setPrefHeight(NodeUtil.snapWH(
		// lClusteredDayAppointmentPanePane.getLayoutY(), lH));
		// }
	}

	/**
	 * This method prepares a day for being drawn. The appointments within one
	 * day might overlap, this method will create a data structure so it is
	 * clear how these overlapping appointments should be drawn. All
	 * appointments in one day are process based on their start time; earliest
	 * first, and if there are more with the same start time, longest duration
	 * first. The appointments are then place onto (parallel) tracks; an
	 * appointment initially is placed in track 0. But if there is already an
	 * (partially overlapping) appointment there, then the appointment is moved
	 * to track 1. Unless there also is an appointment already in that track 1,
	 * then the next track is tried, and so forth, until a free track is found.
	 * For example (the letters are not the sequence in which the appointments
	 * are processed, they're just for identifying them):
	 * 
	 * tracks 0 1 2 3 ------- . . . . . . . . A . . . A B C . A B C D A B . D A
	 * . . D A E . D A . . D . . . D . . . D F . . D F H . D . . . . G . . . . .
	 * . .
	 * 
	 * Appointment A was rendered first and put into track 0 and its start time.
	 * Then appointment B was added, initially it was put in track 0, but
	 * appointment A already uses the that slot, so B was moved into track 1. C
	 * moved from track 0, conflicting with A, to track 1, conflicting with B,
	 * and ended up in track 2. And so forth. F and H show that even though D
	 * overlaps them, they could perfectly be placed in lower tracks.
	 * 
	 * A cluster of appointments always starts with a free standing appointment
	 * in track 0, for example A or G, such appointment is called the cluster
	 * owner. When the next appointment is added to the tracks, and finds that
	 * it cannot be put in track 0, it will be added as a member to the cluster
	 * represented by the appointment in track 0. Special attention must be paid
	 * to an appointment that is placed in track 0, but is linked to a cluster
	 * by a earlier appointment in a higher track; such an appointment is not
	 * the cluster owner. In the example above, F is linked through D to the
	 * cluster owned by A. So F is not a cluster owner, but a member of the
	 * cluster owned by A. And appointment H through F is also part of the
	 * cluster owned by A. G finally starts a new cluster. The cluster owner
	 * knows all members and how many tracks there are, each member knows in
	 * what track it is and has a direct link to the cluster owner.
	 * 
	 * When rendering the appointments above, parallel appointments are rendered
	 * narrower & indented, so appointments partially overlap and the left side
	 * of an appointment is always visible to the user. In the example above the
	 * single appointment G is rendered full width, while for example A, B, C
	 * and D are overlapping. F and H are drawn in the same dimensions as A and
	 * B in order to allow D to overlap then. The size and amount of indentation
	 * depends on the number of appointments that are rendered next to each
	 * other. In order to compute its location and size, each appointment needs
	 * to know: - its start and ending time, - its track number, - its total
	 * number of tracks, - and naturally the total width and height available to
	 * draw the day.
	 * 
	 */
	public void setupAppointments() {

		// // remember for animation
		// final List<AbstractClusteredDayAppointmentPane>
		// lOldClusteredDayAppointmentPanes = new
		// ArrayList<AbstractClusteredDayAppointmentPane>(
		// clusteredAppointmentPanes);
		// final List<WholedayAppointmentPane> lOldWholedayAppointmentPanes =
		// new ArrayList<WholedayAppointmentPane>(
		// wholedayAppointmentPanes);
		//
		// // clear
		// clusteredAppointmentPanes.clear();
		// wholedayAppointmentPanes.clear();
		// if (calendarObjectProperty.get() == null) {
		// return;
		// }
		//
		// // scan all appointments and filter the ones for this day
		// for (Agenda.Appointment lAppointment : getSkinnable().appointments())
		// {
		//
		// // different panes depending on the appointment time
		// if (lAppointment.isWholeDay()) {
		//
		// // if appointment falls on the same date as this day pane
		// if (isSameDay(calendarObjectProperty.get(),
		// lAppointment.getStartTime())) {
		// WholedayAppointmentPane lAppointmentPane = new
		// WholedayAppointmentPane(
		// lAppointment, this);
		// wholedayAppointmentPanes.add(lAppointmentPane);
		// lAppointmentPane.setId(lAppointmentPane.getClass()
		// .getSimpleName() + wholedayAppointmentPanes.size());
		// }
		// } else if (lAppointment.getEndTime() == null) {
		//
		// // an not-wholeday appointment without an enddate is a task
		// if (isSameDay(calendarObjectProperty.get(),
		// lAppointment.getStartTime())) {
		// TaskAppointmentPane lAppointmentPane = new TaskAppointmentPane(
		// lAppointment, this);
		// clusteredAppointmentPanes.add(lAppointmentPane);
		// lAppointmentPane
		// .setId(lAppointmentPane.getClass().getSimpleName()
		// + clusteredAppointmentPanes.size());
		// }
		// } else {
		// // appointments may span multiple days, but the appointment pane
		// // will clamp the start and end date
		// RegularAppointmentPane lAppointmentPane = new RegularAppointmentPane(
		// lAppointment, this);
		//
		// // check if the appointment falls in the same day as this day
		// // pane
		// if (isSameDay(calendarObjectProperty.get(),
		// lAppointmentPane.start)
		// && isSameDay(calendarObjectProperty.get(),
		// lAppointmentPane.end)) {
		// clusteredAppointmentPanes.add(lAppointmentPane);
		// lAppointmentPane
		// .setId(lAppointmentPane.getClass().getSimpleName()
		// + clusteredAppointmentPanes.size());
		// }
		// }
		// }
		//
		// // sort on start time and then decreasing duration
		// Collections.sort(clusteredAppointmentPanes,
		// new Comparator<AbstractDayAppointmentPane>() {
		// @Override
		// public int compare(AbstractDayAppointmentPane o1,
		// AbstractDayAppointmentPane o2) {
		// if (o1.startAsString.equals(o2.startAsString) == false) {
		// return o1.startAsString.compareTo(o2.startAsString);
		// }
		// return o1.durationInMS > o2.durationInMS ? -1 : 1;
		// }
		// });
		//
		// // start placing appointments in the tracks
		// AbstractClusteredDayAppointmentPane lClusterOwner = null;
		// for (AbstractClusteredDayAppointmentPane lAppointmentPane :
		// clusteredAppointmentPanes) {
		// // if there is no cluster owner
		// if (lClusterOwner == null) {
		//
		// // than the current becomes an owner
		// // only create a minimal cluster, because it will be setup fully
		// // in the code below
		// lClusterOwner = lAppointmentPane;
		// lClusterOwner.clusterTracks = new
		// ArrayList<List<AbstractClusteredDayAppointmentPane>>();
		// }
		//
		// // in which track should it be added
		// int lTrackNr = determineTrackWhereAppointmentCanBeAdded(
		// lClusterOwner.clusterTracks, lAppointmentPane);
		// // if it can be added to track 0, then we have a "situation". Track
		// // 0 could mean
		// // - we must start a new cluster
		// // - the appointment is still linked to the running cluster by means
		// // of a linking appointment in the higher tracks
		// if (lTrackNr == 0) {
		//
		// // So let's see if there is a linking appointment higher up
		// boolean lOverlaps = false;
		// for (int i = 1; i < lClusterOwner.clusterTracks.size()
		// && lOverlaps == false; i++) {
		// lOverlaps =
		// checkIfTheAppointmentOverlapsAnAppointmentAlreadyInThisTrack(
		// lClusterOwner.clusterTracks, i, lAppointmentPane);
		// }
		//
		// // if it does not overlap, we start a new cluster
		// if (lOverlaps == false) {
		// lClusterOwner = lAppointmentPane;
		// lClusterOwner.clusterMembers = new
		// ArrayList<AbstractClusteredDayAppointmentPane>();
		// lClusterOwner.clusterTracks = new
		// ArrayList<List<AbstractClusteredDayAppointmentPane>>();
		// lClusterOwner.clusterTracks
		// .add(new ArrayList<AbstractClusteredDayAppointmentPane>());
		// }
		// }
		//
		// // add it to the track (and setup all other cluster data)
		// lClusterOwner.clusterMembers.add(lAppointmentPane);
		// lClusterOwner.clusterTracks.get(lTrackNr).add(lAppointmentPane);
		// lAppointmentPane.clusterOwner = lClusterOwner;
		// lAppointmentPane.clusterTrackIdx = lTrackNr;
		// // for debug System.out.println("----"); for (int i = 0; i <
		// // lClusterOwner.clusterTracks.size(); i++) { System.out.println(i +
		// // ": " + lClusterOwner.clusterTracks.get(i) ); }
		// // System.out.println("----");
		// }
		//
		// // laying out the appointments is fairly complex, so we use listeners
		// // and a relayout method instead of binding
		// relayout();
		//
		// // and swap the appointments; old ones out, new ones in
		// // TODO: animation? we could move the old appointments to the
		// equivalent
		// // positions on the drag pane, then animate them to their new
		// positions,
		// // remove the old, and insert the new ones.
		// // however, this needs to be cross-days, so it cannot be done here
		// (this
		// // is only one day), but after the complete setupAppointments()
		// getChildren().removeAll(lOldClusteredDayAppointmentPanes);
		// getChildren().removeAll(lOldWholedayAppointmentPanes);
		// getChildren().addAll(wholedayAppointmentPanes);
		// getChildren().addAll(clusteredAppointmentPanes);
		//
		// // we're done, now have the header updated
		// dayHeaderPane.setupAppointments();
	}

	// final List<AbstractClusteredDayAppointmentPane> clusteredAppointmentPanes
	// = new ArrayList<AbstractClusteredDayAppointmentPane>();
	// final List<WholedayAppointmentPane> wholedayAppointmentPanes = new
	// ArrayList<WholedayAppointmentPane>();
}