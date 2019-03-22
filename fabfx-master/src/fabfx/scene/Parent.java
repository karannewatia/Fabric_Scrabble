package fabfx.scene;

import fabric.lang.security.Label;

public abstract class Parent extends Node {

	public Parent(Label L, Label M) {
		super(L, M);
	}

	@Override
	public javafx.scene.Parent _impl() {
		return (javafx.scene.Parent) super._impl();
	}

	public static boolean jif$Instanceof(Label l, Label e, Object o) {
		return (o instanceof Parent) && Node.jif$Instanceof(l, e, o);
	}

}
