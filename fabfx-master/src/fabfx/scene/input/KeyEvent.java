package fabfx.scene.input;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import fabfx.event.Event;
import fabric.lang.security.Label;

public class KeyEvent extends InputEvent {

	public KeyEvent(Label M, javafx.scene.input.KeyEvent e) {
		super(M, e);
	}

	@Override
	public javafx.scene.input.KeyEvent _impl() {
		return (javafx.scene.input.KeyEvent) super._impl();
	}

	public static boolean jif$Instanceof(Label M, Object o) {
		return InputEvent.jif$Instanceof(M, o)
				&& ((Event) o)._impl() instanceof javafx.scene.input.KeyEvent;
	}

	public static KeyEvent jif$cast$fabfx_scene_input_KeyEvent(Label M,
			Object o) {
		if (o == null)
			return null;
		if (jif$Instanceof(M, o))
			return new KeyEvent(M,
					(javafx.scene.input.KeyEvent) ((Event) o)._impl());
		throw new ClassCastException();
	}

	public static final EventType KEY_PRESSED = javafx.scene.input.KeyEvent.KEY_PRESSED;

	public final KeyCode getCode() {
		return _impl().getCode();
	}

}
