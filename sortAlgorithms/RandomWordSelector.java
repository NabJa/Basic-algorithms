package sortAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomWordSelector {
	Random rand = new Random();

	public String giveWord(String[] words) {
		int idx = rand.nextInt(words.length - 1);
		return words[idx];
	}

	public String[] generateRandomList(int length, int minWordLength, int maxWordLength) {
		String[] randomWords = new String[length];

		for (int i = 0; i < length; i++) {
			char[] word = new char[rand.nextInt(maxWordLength) + minWordLength];

			for (int j = 0; j < word.length; j++) {
				word[j] = (char) ('a' + rand.nextInt(26));
			}
			randomWords[i] = new String(word);
		}
		return randomWords;
	}

	public HashMap<String, ArrayList<Integer>> rdmListDict(String[] list) {
		HashMap<String, ArrayList<Integer>> dictionary = new HashMap<String, ArrayList<Integer>>();
		for (int i = 0; i < list.length; i++) {
			if (dictionary.containsKey(list[i])) {
				ArrayList<Integer> indexList = dictionary.get(list[i]);
				indexList.add(i);
				dictionary.put(list[i], indexList);
			} else {
				ArrayList<Integer> indexList = new ArrayList<Integer>();
				indexList.add(i);
				dictionary.put(list[i], indexList);
			}
		}
		return dictionary;
	}
}
