package fabfx.scene.input;

import javafx.event.EventType;

import fabfx.event.Event;
import fabric.lang.security.Label;

public class MouseEvent extends InputEvent {

	public MouseEvent(Label M, javafx.scene.input.MouseEvent e) {
		super(M, e);
	}

	@Override
	public javafx.scene.input.MouseEvent _impl() {
		return (javafx.scene.input.MouseEvent) super._impl();
	}

	public static boolean jif$Instanceof(Label M, Object o) {
		return InputEvent.jif$Instanceof(M, o)
				&& ((Event) o)._impl() instanceof javafx.scene.input.MouseEvent;
	}

	public static MouseEvent jif$cast$fabfx_scene_input_MouseEvent(Label M,
			Object o) {
		if (o == null)
			return null;
		if (jif$Instanceof(M, o))
			return new MouseEvent(M,
					(javafx.scene.input.MouseEvent) ((Event) o)._impl());
		throw new ClassCastException();
	}

	public static final EventType MOUSE_CLICKED = javafx.scene.input.MouseEvent.MOUSE_CLICKED;

	public final double getSceneX() {
		return _impl().getSceneX();
	}

	public final double getSceneY() {
		return _impl().getSceneY();
	}

}
