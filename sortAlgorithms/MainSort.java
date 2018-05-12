package sortAlgorithms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainSort {

	static int[] seq;
	static boolean giveOutput = false;
	static String outPath;

	public static void main(String args[]) {
		SortAlgorithms algr = new SortAlgorithms();

		for (int i = 0; i < args.length; i++) {

			switch (args[i]) {
			case "-i":
				seq = readInput(args[i + 1]);
				break;

			case "-o":
				giveOutput = true;
				outPath = args[i + 1];
				break;

			case "-s":
				seq = algr.insertionSort(seq);
				break;

			case "-m":
				seq = algr.mergeSort(seq);
				break;
			}

		}

		if (giveOutput) {
			File out_file = new File(outPath, "sortedOut.txt");
			writeOutput(seq, out_file);
		} else {
			for (int i : seq)
				System.out.print(i + "\t");
		}

	}

	public static int[] readInput(String path) {
		BufferedReader reader = null;
		try {
			int[] seq = new int[path.length()];
			File file = new File(path);
			reader = new BufferedReader(new FileReader(file));
			String line = "";

			while ((line = reader.readLine()) != null) {
				if (line.indexOf('#') != 0) {
					String[] StringSeq = line.split("\t");
					seq = new int[StringSeq.length];
					for (int i = 0; i < StringSeq.length; i++) {
						seq[i] = Integer.parseInt(StringSeq[i]);
					}
				}
			}

			reader.close();
			return (seq);

		} catch (Exception e) {
			throw new RuntimeException("got error while reading txt.", e); // TODO Handle Exception!
		}
	}

	public static void writeOutput(int[] result, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);

			for (int i : result) {
				writer.write(i + "\t");
			}
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException("Got error while writing output", e); // TODO handle exception properly
		}
	}
}
