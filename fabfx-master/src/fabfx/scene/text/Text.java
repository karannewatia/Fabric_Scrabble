package fabfx.scene.text;

import javafx.scene.text.Font;
import fabfx.scene.shape.Shape;
import fabric.lang.security.Label;

public class Text extends Shape {

	public Text(Label L, Label M) {
		super(L, M);
	}

	@Override
	public javafx.scene.text.Text _impl() {
		return (javafx.scene.text.Text) super._impl();
	}

	@Override
	protected void makeImpl() {
		this._impl = new javafx.scene.text.Text();
	}

	public final void setFont(Font value) {
		_impl().setFont(value);
	}

	public final void setText(String value) {
		_impl().setText(value);
	}

	public final String getText() {
		return _impl().getText();
	}

	public final void setX(int xpos) {
		_impl().setX(xpos);
	}

	public final void setY(int ypos) {
		_impl().setY(ypos);
	}
}
