package fabfx.scene;

import javafx.beans.property.DoubleProperty;
import fabfx.Wrapper;
import fabfx.event.EventHandler;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;

public abstract class Node implements Wrapper {

	final public Label L;
	final public Label M;

	public javafx.scene.Node _impl;

	@Override
	public javafx.scene.Node _impl() {
		return this._impl;
	}

	public Node(Label L, Label M) {
		this.L = L;
		this.M = M;
		this.makeImpl();
	}

	/**
	 * This method is supposed to be selectively implemented by subclasses.
	 */
	protected void makeImpl() {
		throw new UnsupportedOperationException();
	}

	public static boolean jif$Instanceof(Label l, Label e, Object o) {
		if (o instanceof Node) {
			Node that = (Node) o;
			return LabelUtil._Impl.equivalentTo(that.L, l)
					&& LabelUtil._Impl.equivalentTo(that.M, e);
		}
		return false;
	}

	public final void setStyle(String value) {
		this._impl().setStyle(value);
	}

	public final void setLayoutX(double value) {
		this._impl().setLayoutX(value);
	}

	public final void setLayoutY(double value) {
		this._impl().setLayoutY(value);
	}

	public final double getLayoutX() {
		return this._impl().getLayoutX();
	}

	public final double getLayoutY() {
		return this._impl().getLayoutY();
	}

	public final DoubleProperty layoutXProperty() {
		return _impl().layoutXProperty();
	}

	public final DoubleProperty layoutYProperty() {
		return _impl().layoutYProperty();
	}

	public final void toBack() {
		this._impl().toBack();
	}

	public final void toFront() {
		this._impl().toFront();
	}

	public void requestFocus() {
		this._impl().requestFocus();
	}

	public void setOnKeyPressed(EventHandler handler) {
		this._impl().setOnKeyPressed(handler._impl());
	}

}
