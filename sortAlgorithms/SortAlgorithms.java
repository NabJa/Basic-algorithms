package sortAlgorithms;

public class SortAlgorithms {

	public int[] mergeSort(int[] a) {
		if (a.length > 1) {
			int leftEnd = (int) Math.floor(a.length / 2);
			int[] left = new int[leftEnd];

			int rightEnd = a.length;
			int[] right = new int[rightEnd - leftEnd];

			for (int i = 0; i < left.length; i++)
				left[i] = a[i];

			for (int i = leftEnd; i < a.length; i++)
				right[i - leftEnd] = a[i];

			left = mergeSort(left);
			right = mergeSort(right);
			return merge(left, right);
		} else {
			return (a);
		}
	}

	private int[] merge(int[] left, int[] right) {
		int[] merged = new int[left.length + right.length];
		int i = 0;
		int leftidx = 0;
		int rightidx = 0;

		while (leftidx < left.length && rightidx < right.length) {
			if (left[leftidx] > right[rightidx]) {
				merged[i] = right[rightidx];
				rightidx++;
			} else {
				merged[i] = left[leftidx];
				leftidx++;
			}
			i++;
		}

		while (leftidx < left.length) {
			merged[i] = left[leftidx];
			leftidx++;
			i++;
		}

		while (rightidx < right.length) {
			merged[i] = right[rightidx];
			rightidx++;
			i++;
		}

		return merged;
	}

	public IndexedArray lexMergeSort(IndexedArray a) {
		if (a.length() > 1) {
			int leftEnd = (int) Math.floor(a.length() / 2);
			IndexedArray left = a.cut(0, leftEnd, true);
			IndexedArray right = a.cut(leftEnd, a.length(), false);

			left = lexMergeSort(left);
			right = lexMergeSort(right);
			return lexMerge(left, right);
		} else {
			return (a);
		}
	}

	private IndexedArray lexMerge(IndexedArray left, IndexedArray right) {
		IndexedArray merged = new IndexedArray(left.length() + right.length());
		int i = 0;
		int leftidx = 0;
		int rightidx = 0;

		while (leftidx < left.length() && rightidx < right.length()) {
			if (left.giveValue(leftidx).toLowerCase().compareTo(right.giveValue(rightidx).toLowerCase()) > 0) {
				merged.values[i] = right.giveValue(rightidx);
				merged.indexe[i] = right.giveIdx(rightidx);
				rightidx++;
			} else {
				merged.values[i] = left.giveValue(leftidx);
				merged.indexe[i] = left.giveIdx(leftidx);
				leftidx++;
			}
			i++;
		}

		while (leftidx < left.length()) {
			merged.values[i] = left.giveValue(leftidx);
			merged.indexe[i] = left.giveIdx(leftidx);
			leftidx++;
			i++;
		}

		while (rightidx < right.length()) {
			merged.values[i] = right.giveValue(rightidx);
			merged.indexe[i] = right.giveIdx(rightidx);
			rightidx++;
			i++;
		}
		return merged;
	}

	public int[] insertionSort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			int value = a[i];
			int j = i;
			while (j > 1 & a[j - 1] > value) {
				a[j] = a[j - 1];
				j = j - 1;
			}
			if (j == 1 & a[0] > value) {
				a[j] = a[j - 1];
				a[0] = value;
			} else {
				a[j] = value;
			}
		}
		return (a);
	}
}
