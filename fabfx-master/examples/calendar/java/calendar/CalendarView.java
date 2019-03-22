package calendar;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CalendarView extends Control {

	protected final CalendarModel model;

	/**
	 * This constructor defines how the Control widget is constructed
	 */
	public CalendarView(CalendarModel m) {
		this.model = m;

		setPrefSize(1000, 800);
		setSkin(new CalendarViewSkin(this));
		calculateSizes();

		BorderPane wholePane = new BorderPane();
		this.getChildren().add(wholePane);

		MenuBar menuBar = new MenuBar();
		wholePane.setTop(menuBar);
		menuBar.prefHeightProperty().set(textHeight);

		Menu viewMenu = new Menu("View");
		menuBar.getMenus().addAll(viewMenu);

		MenuItem showCalendarItem = new MenuItem(
				"Show another user's calendar...");
		MenuItem showMyCalendar = new MenuItem("Show my calendar");
		viewMenu.getItems().addAll(showCalendarItem, showMyCalendar);

		// Create a transparent pane where dragging an appointment is visualized
		Pane dragPane = new Pane();
		wholePane.setCenter(dragPane);
		dragPane.prefWidthProperty().bind(widthProperty());
		dragPane.prefHeightProperty().bind(
				heightProperty().subtract(menuBar.prefWidthProperty()));

		// A BorderPane is placed in the drag pane and sized to match
		BorderPane calendarPane = new BorderPane();
		dragPane.getChildren().add(calendarPane);
		calendarPane.prefWidthProperty().bind(dragPane.widthProperty());
		calendarPane.prefHeightProperty().bind(dragPane.heightProperty());

		// The borderPane's center
		ScrollPane weekScrollPane = new ScrollPane();
		calendarPane.setCenter(weekScrollPane);
		weekScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		weekScrollPane.setFitToWidth(true);
		weekScrollPane.setPannable(false);
		// panning would conflict with creating a new appointment

		WeekBodyPane weekBodyPane = new WeekBodyPane(this);
		weekScrollPane.setContent(weekBodyPane);

		// The borderPane's top
		WeekHeaderPane weekHeaderPane = new WeekHeaderPane(this, weekBodyPane);
		calendarPane.setTop(weekHeaderPane);

	}

	protected double textHeight;
	protected double hourHeight;
	protected double timeWhitespace;
	protected double timeWidth;
	protected DoubleProperty dayWidthProperty = new SimpleDoubleProperty(0);
	protected double dayHeight;
	protected double headerHeight;
	protected double durationInMinPerPixel;

	/**
	 * Initializes the calculation of widget sizes. The method should called
	 * before children nodes are constructed because the construction of
	 * children nodes depend on knowing these sizes.
	 */
	protected void calculateSizes() {
		textHeight = new Text("X").getBoundsInParent().getHeight();
		hourHeight = textHeight * 2 + 10; // 10 is for padding
		timeWhitespace = 10;
		timeWidth = new Text("88:88").getBoundsInParent().getWidth()
				+ timeWhitespace;
		dayWidthProperty.bind(widthProperty().subtract(timeWidth).divide(7));
		dayHeight = hourHeight * 24;
		durationInMinPerPixel = 24 * 60 / dayHeight;
	}

}
