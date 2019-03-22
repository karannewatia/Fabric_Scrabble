package fabfx.scene;

import fabfx.Wrapper;
import fabric.lang.security.Label;

public class Scene implements Wrapper {
	final Label L;
	final Label M;

	private javafx.scene.Scene jscene;

	public Scene(Label L, Label M) {
		this.L = L;
		this.M = M;
	}

	public Scene(Label L, Label M, Label rL, Label rM, Parent root) {
		this(L, M);
		this.jscene = new javafx.scene.Scene(root._impl());
	}

	@Override
	public javafx.scene.Scene _impl() {
		return this.jscene;
	}

}
