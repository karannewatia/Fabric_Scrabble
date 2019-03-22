package fabfx.scene.shape;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import fabfx.scene.Node;
import fabric.lang.security.Label;

public abstract class Shape extends Node {

	public Shape(Label L, Label M) {
		super(L, M);
	}

	@Override
	public javafx.scene.shape.Shape _impl() {
		return (javafx.scene.shape.Shape) super._impl();
	}

	public final void setFill(Paint value) {
		_impl().setFill(value);
	}

	public final void setStrokeWidth(double value) {
		_impl().setStrokeWidth(value);
	}

	public final void setStroke(Paint value) {
		_impl().setStroke(value);
	}
	
	public final Paint getFill() {
		return _impl().getFill();
	}
	
	public final ObjectProperty fillProperty() {
		return _impl().fillProperty();
	}

}
