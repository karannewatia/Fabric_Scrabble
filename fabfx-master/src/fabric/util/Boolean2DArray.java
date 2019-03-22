package fabric.util;

import fabric.lang.security.Label;

public class Boolean2DArray {

	protected final Label L;
	protected final boolean[][] _impl;

	public Boolean2DArray(Label L, int dim1, int dim2)
			throws NegativeArraySizeException {
		this.L = L;
		this._impl = new boolean[dim1][dim2];
	}

	public boolean get(int idx1, int idx2) {
		return _impl[idx1][idx2];
	}

	public void set(int idx1, int idx2, boolean value) {
		_impl[idx1][idx2] = value;
	}
}
