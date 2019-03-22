package fabfx.scene;

import fabfx.collections.ObservableList;
import fabric.lang.security.Label;

public class Group extends Parent {

	public Group(Label L, Label M) {
		super(L, M);
	}

	public Group fetch() {
		return this;
	}

	@Override
	public javafx.scene.Group _impl() {
		return (javafx.scene.Group) this._impl;
	}

	@Override
	protected void makeImpl() {
		this._impl = new javafx.scene.Group();
	}

	public ObservableList getChildren() {
		ObservableList l = new ObservableList(L, M);
		l.makeImpl(_impl().getChildren());
		return l;
	}
}
