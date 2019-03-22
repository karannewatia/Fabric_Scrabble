package fabric.util;

import fabric.lang.security.Label;

public class RandomGenerator {

	//private String _impl;

	protected final Label L;

	public PrintString(Label L) {
		this.L = L;
		//this.makeImpl = s;
	}

	protected void print(String s) {
		System.out.println(s);
	}

}
