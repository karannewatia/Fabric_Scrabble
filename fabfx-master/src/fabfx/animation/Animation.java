package fabfx.animation;

import fabric.lang.security.Label;

public abstract class Animation {

	protected final Label L, M;

	protected javafx.animation.Animation _impl;

	protected Animation(Label L, Label M) {
		this.L = L;
		this.M = M;
	}

	public javafx.animation.Animation _impl() {
		return this._impl;
	}

	public void play() {
		this._impl().play();
	}
}