package fabfx.scene.input;

import fabfx.event.Event;
import fabric.lang.security.Label;

public class InputEvent extends Event {

	public InputEvent(Label m, javafx.scene.input.InputEvent e) {
		super(m, e);
	}

	@Override
	public javafx.scene.input.InputEvent _impl() {
		return (javafx.scene.input.InputEvent) super._impl();
	}

	public static boolean jif$Instanceof(Label m, Object o) {
		return Event.jif$Instanceof(m, o)
				&& ((Event) o)._impl() instanceof javafx.scene.input.InputEvent;
	}

	public static InputEvent jif$cast$fabfx_scene_input_InputEvent(Label M,
			Object o) {
		if (o == null)
			return null;
		if (jif$Instanceof(M, o))
			return new InputEvent(M,
					(javafx.scene.input.InputEvent) ((Event) o)._impl());
		throw new ClassCastException();
	}

}
