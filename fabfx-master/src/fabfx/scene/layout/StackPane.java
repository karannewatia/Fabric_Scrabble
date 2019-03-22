package fabfx.scene.layout;

import fabric.lang.security.Label;

public class StackPane extends Pane {

	public StackPane(Label L, Label M) {
		super(L, M);
	}

	@Override
	public javafx.scene.layout.StackPane _impl() {
		return (javafx.scene.layout.StackPane) super._impl();
	}

	@Override
	protected void makeImpl() {
		this._impl = new javafx.scene.layout.StackPane();
	}

	public static boolean jif$Instanceof(Label l, Label e, Object o) {
		return (o instanceof StackPane) && Pane.jif$Instanceof(l, e, o);
	}

}