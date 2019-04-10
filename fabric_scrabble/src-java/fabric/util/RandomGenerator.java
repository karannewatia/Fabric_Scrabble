package fabric.util;

import fabric.lang.security.Label;

public class RandomGenerator {

	private java.util.Random _impl;

	protected final Label L;

	public RandomGenerator(Label L) {
		this.L = L;
		makeImpl();
	}

	protected void makeImpl() {
		this._impl = new java.util.Random();
	}

	public int nextInt(int bound) {
		return _impl.nextInt(bound);
	}

}
