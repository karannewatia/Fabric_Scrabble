package fabfx.scene.layout;

import fabric.lang.security.Label;

public class GridPane extends Pane {

	public GridPane(Label L, Label M) {
		super(L, M);
	}

	@Override
	public javafx.scene.layout.GridPane _impl() {
		return (javafx.scene.layout.GridPane) super._impl();
	}

	@Override
	protected void makeImpl() {
		this._impl = new javafx.scene.layout.GridPane();
	}

	public static boolean jif$Instanceof(Label l, Label m, Object o) {
		return (o instanceof GridPane) && Pane.jif$Instanceof(l, m, o);
	}

}
