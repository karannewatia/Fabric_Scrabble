package fabric.util.java;

import fabric.lang.security.Label;

public class Object2DArray {

	protected final Label L;
	protected final Object[][] _impl;

	public Object2DArray(Label L, int dim1, int dim2)
			throws NegativeArraySizeException {
		this.L = L;
		this._impl = new Object[dim1][dim2];
	}

	public Object get(int idx1, int idx2) {
		return _impl[idx1][idx2];
	}

	public void set(int idx1, int idx2, Object value) {
		_impl[idx1][idx2] = value;
	}
}
