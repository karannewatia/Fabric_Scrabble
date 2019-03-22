package fabfx.application;

import com.sun.javafx.application.LauncherImpl;

abstract public class Application extends javafx.application.Application {

	/**************************************************
	 *** These are needed for the parameterized version
	 *
	 * public final fabric.lang.security.Principal p;
	 * 
	 * public Application(fabric.lang.security.Principal p) { this.p = p; }
	 * 
	 *************************************************/

	abstract public void run(fabfx.stage.Stage primaryStage);

	@Override
	public void start(javafx.stage.Stage jstage) {
		fabfx.stage.Stage fstage = new fabfx.stage.Stage(jstage);
		this.run(fstage);
	}

	public void launchApp() {
		LauncherImpl.launchApplication(this.getClass(), new String[0]);
	}

}
