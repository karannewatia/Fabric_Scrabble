package fabfx.animation;

import java.util.Collection;

import fabfx.event.EventHandler;
import fabric.lang.security.Label;

public class KeyFrame {

	final public Label L;
	final public Label M;

	public javafx.animation.KeyFrame _impl;

	public KeyFrame(Label L, Label M, javafx.util.Duration duration,
			String name, EventHandler onFinished, Collection keyValues) {
		this.L = L;
		this.M = M;
		this._impl = new javafx.animation.KeyFrame(duration, name,
				onFinished._impl(), keyValues);
	}

	public void makeImpl(javafx.animation.KeyFrame impl) {
		this._impl = impl;
	}

	public javafx.animation.KeyFrame _impl() {
		return this._impl;
	}

}
