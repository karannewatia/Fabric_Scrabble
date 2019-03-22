package fabfx.stage;

import fabric.lang.security.Label;
import fabfx.scene.Scene;

public class Stage {

	private final javafx.stage.Stage jstage;

	public Stage(javafx.stage.Stage jstage) {
		this.jstage = jstage;
	}

	public void show() {
		jstage.show();
	}

	public void setScene(Label sL, Label sM, Scene s) {
		jstage.setScene((javafx.scene.Scene) s._impl());
	}

	public void setTitle(String title) {
		jstage.setTitle(title);
	}

}
