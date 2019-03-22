package fabric.util;

import fabric.lang.security.Label;

public class RandomGenerator {

	private String _impl;

	protected final Label L;

	public PrintString(Label L, String s) {
		this.L = L;
		this.makeImpl = s;
	}

	protected void print() {
		System.out.println(this.makeImpl);
	}

}
