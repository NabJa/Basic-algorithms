package searchAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import sortAlgorithms.IndexedArray;

public class SearchAlgorithms {

	public ArrayList<Integer> linearSearch(String[] list, String word) {
		ArrayList<Integer> out = new ArrayList<Integer>();
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(word)) {
				out.add(i);
			}
		}
		return out;
	}

	public ArrayList<Integer> dictSearch(HashMap<String, ArrayList<Integer>> dictionary, String word) {
		ArrayList<Integer> out = dictionary.get(word);
		if(out != null) {
			return out;			
		} else {
			return new ArrayList<Integer>();
		}
	}

	
	public TreeSet<Integer> binarySearch(IndexedArray sorted, String word) {
		int start = 0;
		int end = sorted.length();
		TreeSet<Integer> idx = new TreeSet<Integer>();
		
		//Divide until last element
		while (start <= end) {
			int mid = (int) (start + end) / 2;

			if (sorted.giveValue(mid).toLowerCase().equals(word.toLowerCase())) {
				idx.add(sorted.giveIdx(mid));
				
				//Check reoccurrence of word to the right
				int i = mid +1;
				while(sorted.giveValue(i).toLowerCase().equals(word.toLowerCase())) {
					idx.add(sorted.giveIdx(i));
					i++;
				}
				
				//Check reoccurrence of word to the left
				int j = mid - 1;
				while(sorted.giveValue(j).toLowerCase().equals(word.toLowerCase())) {
					idx.add(sorted.giveIdx(j));
					j--;
				}
				break;
			} else if (sorted.giveValue(mid).toLowerCase().compareTo(word.toLowerCase()) > 0) {
				end = mid - 1;
			} else if (sorted.giveValue(mid).toLowerCase().compareTo(word.toLowerCase()) < 0) {
				start = mid +1;
			}
		}
		return idx;
	}
}
