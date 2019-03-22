package fabric.util;

import fabric.lang.security.Label;

public class PrintString {

	protected final Label L;

	protected final String _impl;

	public PrintString(Label L, String s) {
		this.L = L;
		this._impl = s;
	}

	protected void print() {
		System.out.println(this._impl);
	}

}
