package fabfx.animation;

import fabfx.collections.ObservableList;
import fabric.lang.security.Label;

public class Timeline extends Animation {

	public Timeline(Label L, Label M, double targetFramerate) {
		super(L, M);
		this._impl = new javafx.animation.Timeline(targetFramerate);
	}

	@Override
	public javafx.animation.Timeline _impl() {
		return (javafx.animation.Timeline) super._impl();
	}

	public ObservableList getKeyFrames() {
		ObservableList l = new ObservableList(L, M);
		l.makeImpl(_impl().getKeyFrames());
		return l;
	}
}
