package fabric.util;

import fabric.lang.security.Label;

public class PrintString {

	protected final Label L;

	public PrintString(Label L) {
		this.L = L;
	}

	public void print(String s) {
		System.out.println(s);
	}

}
