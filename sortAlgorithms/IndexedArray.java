package sortAlgorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class IndexedArray {

	String[] values;
	int[] indexe;

	public IndexedArray(String[] vals, int[] idx) {
		this.values = vals;
		this.indexe = idx;
	}
	
	public IndexedArray(int length) {
		this.values = new String[length];
		this.indexe = new int[length];
	}

	public IndexedArray(String[] array) {
		this.values = array;
		this.indexe = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			indexe[i] = i;
		}
	}

	public String giveValue(int i) {
		if(i < values.length && i >= 0) {
			return values[i];			
		} else {
			return "";
		}
	}

	public int giveIdx(int i) {
		return indexe[i];
	}

	public int length() {
		return values.length;
	}


	public IndexedArray cut(int from, int to, boolean cutFromStart) {
		String[] newVals = new String[to - from];
		int[] newIdx = new int[to - from];
		if (cutFromStart) {
			for (int i = from; i < to; i++) {
				newVals[i] = values[i];
				newIdx[i] = indexe[i];
			}
		} else {
			for (int i = from; i < to; i++) {
				newVals[i - from] = values[i];
				newIdx[i - from] = indexe[i];
			}
		}
		return new IndexedArray(newVals, newIdx);
	}

	@Override
	public String toString() {
		return Arrays.toString(values);
	}
}
