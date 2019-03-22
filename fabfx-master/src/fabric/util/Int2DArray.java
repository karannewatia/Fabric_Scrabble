package fabric.util;

import fabric.lang.security.Label;

public class Int2DArray {

	protected final Label L;
	protected final int[][] _impl;

	public Int2DArray(Label L, int dim1, int dim2)
			throws NegativeArraySizeException {
		this.L = L;
		this._impl = new int[dim1][dim2];
	}

	public int get(int idx1, int idx2) {
		return _impl[idx1][idx2];
	}

	public void set(int idx1, int idx2, int value) {
		_impl[idx1][idx2] = value;
	}
}
