package fabfx.event;

import fabric.lang.security.Label;

public abstract class EventHandler {

	protected final Label M;
	protected final javafx.event.EventHandler _impl;

	public EventHandler(Label M) {
		this.M = M;
		this._impl = new javafx.event.EventHandler() {

			@Override
			public void handle(javafx.event.Event event) {
				EventHandler.this.handle(new Event(EventHandler.this.M, event));
			}

		};
	}

	abstract public void handle(Event e);

	public javafx.event.EventHandler _impl() {
		return this._impl;
	}

}
