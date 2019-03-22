package fabfx.collections;

import fabfx.Wrapper;
import fabfx.animation.KeyFrame;
import fabfx.scene.Node;
import fabric.lang.security.Label;

public class ObservableList implements Wrapper {
	final Label L;
	final Label M;

	private javafx.collections.ObservableList jlist;

	// TODO
	public ObservableList(Label L, Label M) {
		this.L = L;
		this.M = M;
	}

	public void makeImpl(javafx.collections.ObservableList jlist) {
		this.jlist = jlist;
	}

	public boolean add(Label cL, Label cM, Node node) {
		return jlist.add(node._impl());
	}

	public boolean add(Label cL, Label cM, KeyFrame keyValue) {
		return jlist.add(keyValue._impl());
	}

	@Override
	public javafx.collections.ObservableList _impl() {
		return this.jlist;
	}

}
