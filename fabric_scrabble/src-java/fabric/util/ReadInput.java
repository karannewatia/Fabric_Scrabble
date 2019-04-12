package fabric.util;

import fabric.lang.security.Label;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadInput {

	protected final Label L;

	public ReadInput(Label L) {
		this.L = L;
	}

	public String read() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String result = reader.readLine();
		return result;
	}

}
