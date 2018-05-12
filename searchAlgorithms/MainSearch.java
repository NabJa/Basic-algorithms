package searchAlgorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import sortAlgorithms.IndexedArray;
import sortAlgorithms.RandomWordSelector;
import sortAlgorithms.SortAlgorithms;

public class MainSearch {
	static ArrayList<Integer> seq;
	static boolean writeOutput = false;
	static String outPath;
	static String path;
	static String word;
	static boolean measureTime = true;
	static SearchAlgorithms algr = new SearchAlgorithms();

	public static void main(String[] args) {

		if (measureTime) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-o"))
					outPath = args[i + 1];
				// outPath = "C:/Users/Anja/Desktop/S2/AlgoBio/Übung3";
			}
			RandomWordSelector randomList = new RandomWordSelector();
			String[] rdmL = randomList.generateRandomList(1000000, 3, 5);

			stopTime(rdmL, "-l", null, null);

			HashMap<String, ArrayList<Integer>> dictionaryT = randomList.rdmListDict(rdmL);
			stopTime(rdmL, "-d", null, dictionaryT);

			SortAlgorithms sortAlgrT = new SortAlgorithms();
			IndexedArray arrayT = new IndexedArray(rdmL);
			arrayT = sortAlgrT.lexMergeSort(arrayT);
			stopTime(rdmL, "-dc", arrayT, null);
		} else {

			for (int i = 0; i < args.length; i++) {

				switch (args[i]) {
				case "-i":
					path = args[i + 1];
					word = readInputWord(path);
					break;

				case "-o":
					writeOutput = true;
					outPath = args[i + 1];
					break;

				case "-l":
					String[] list = readInput(path);
					if (!measureTime) {
						seq = algr.linearSearch(list, word);
					} else {
						stopTime(list, "-l", null, null);
					}
					break;

				case "-dc":
					SortAlgorithms sortAlgr = new SortAlgorithms();
					SearchAlgorithms searchAlgr = new SearchAlgorithms();
					IndexedArray array = new IndexedArray(readInput(path));
					array = sortAlgr.lexMergeSort(array);
					if (!measureTime) {
						TreeSet<Integer> set = searchAlgr.binarySearch(array, word);
						seq = new ArrayList<Integer>(set);
					} else {
						stopTime(readInput(path), "-dc", array, null);
					}
					break;

				case "-d":
					HashMap<String, ArrayList<Integer>> dictionary = readInputInDict(path);
					if (!measureTime) {
						seq = algr.dictSearch(dictionary, word);
					} else {
						stopTime(readInput(path), "-d", null, dictionary);
					}
					break;
				}
			}

			if (!measureTime) {
				// Return -1 if word not found
				if (seq.size() == 0) {
					seq.add(-1);
				}

				// Write Output
				if (writeOutput) {
					File out_file = new File(outPath, "index.txt");
					writeOutput(seq, out_file);
				} else {
					for (int i : seq)
						System.out.print(i + "\t");
				}
			}
		}
	}

	public static String[] readInput(String path) {
		BufferedReader reader = null;
		try {
			String[] seq = new String[path.length()];
			File file = new File(path);
			reader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = reader.readLine()) != null) {
				if (line.indexOf('#') != 0) {
					String[] StringSeq = line.split(" ");
					seq = new String[StringSeq.length];
					for (int i = 0; i < StringSeq.length; i++) {
						seq[i] = StringSeq[i];
					}
				}
			}

			reader.close();
			return (seq);

		} catch (Exception e) {
			throw new RuntimeException("got error while reading txt.", e);
		}
	}

	public static String readInputWord(String path) {
		BufferedReader reader = null;
		String word = "";

		try {
			File file = new File(path);
			reader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = reader.readLine()) != null) {
				if (line.indexOf('#') == 0) {
					String[] StringSeq = line.split(":");
					for (int i = 0; i < StringSeq.length; i++) {
						word = StringSeq[1].trim();
					}
				}
			}
			reader.close();
			return (word);

		} catch (Exception e) {
			throw new RuntimeException("got error while reading txt.", e);
		}
	}

	public static HashMap<String, ArrayList<Integer>> readInputInDict(String path) {
		HashMap<String, ArrayList<Integer>> dictionary = new HashMap<String, ArrayList<Integer>>();

		BufferedReader reader = null;
		try {
			File file = new File(path);
			reader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = reader.readLine()) != null) {
				if (line.indexOf('#') != 0) {
					String[] StringSeq = line.split(" ");
					for (int i = 0; i < StringSeq.length; i++) {
						if (dictionary.containsKey(StringSeq[i])) {
							ArrayList<Integer> indexList = dictionary.get(StringSeq[i]);
							indexList.add(i);
							dictionary.put(StringSeq[i], indexList);
						} else {
							ArrayList<Integer> indexList = new ArrayList<Integer>();
							indexList.add(i);
							dictionary.put(StringSeq[i], indexList);
						}
					}
				}
			}

			reader.close();
			return (dictionary);

		} catch (Exception e) {
			throw new RuntimeException("got error while reading txt.", e);
		}
	}

	public static void writeOutput(ArrayList<Integer> result, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);

			for (Integer i : result) {
				writer.write(i + "\t");
			}
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException("Got error while writing output", e);
		}
	}

	public static void writeLongOutput(ArrayList<Long> result, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);

			for (Long i : result) {
				writer.write(i + "\t");
			}
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException("Got error while writing output", e);
		}
	}

	public static void stopTime(String[] words, String method, IndexedArray sorted,
			HashMap<String, ArrayList<Integer>> dictionary) {
		RandomWordSelector rdmWord = new RandomWordSelector();
		ArrayList<Long> times = new ArrayList<Long>();
		long start;
		long end;
		String name = "no_method.txt";

		for (int i = 0; i < 1000; i++) {
			String word = rdmWord.giveWord(words);

			switch (method) {
			case "-l":
				name = "linear_time.txt";
				start = System.currentTimeMillis();
				algr.linearSearch(words, word);
				end = System.currentTimeMillis() - start;
				times.add(end);
				break;
			case "-d":
				name = "dictionary_time.txt";
				start = System.currentTimeMillis();
				algr.dictSearch(dictionary, word);
				end = System.currentTimeMillis() - start;
				times.add(end);
				break;
			case "-dc":
				name = "divide_and_conquor_time.txt";
				start = System.currentTimeMillis();
				algr.binarySearch(sorted, word);
				end = System.currentTimeMillis() - start;
				times.add(end);
				break;

			}
		}
		File time_file = new File(outPath, name);
		writeLongOutput(times, time_file);
	}

}
