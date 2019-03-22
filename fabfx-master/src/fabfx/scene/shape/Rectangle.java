package fabfx.scene.shape;

import fabric.lang.security.Label;

public class Rectangle extends Shape {

	public Rectangle(Label L, Label M) {
		super(L, M);
	}

	@Override
	protected void makeImpl() {
		this._impl = new javafx.scene.shape.Rectangle();
	}

	@Override
	public javafx.scene.shape.Rectangle _impl() {
		return (javafx.scene.shape.Rectangle) super._impl();
	}

	public final void setHeight(double value) {
		_impl().setHeight(value);
	}

	public final void setWidth(double value) {
		_impl().setWidth(value);
	}

	public final void setArcHeight(double value) {
		_impl().setArcHeight(value);
	}

	public final void setArcWidth(double value) {
		_impl().setArcWidth(value);
	}

}
