import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class smms {

	static int[] seq = null;
	static int[] output = null;
	static long startTime;
	static long endTime;
	static String out_path;
	static boolean o_spec = false;
	static boolean empty = false;

	static ArrayList<int[]> amss = new ArrayList<int[]>();
	static boolean giveAmss = true;

	static TreeSet<int[]> treeAmss = new TreeSet<int[]>(new arrayComp());
	static int recMax = 0;

	public static void main(String[] args) throws Exception {
		if (args[0].equals("-i")) {
			readInput(args[1]);
		} else {
			System.err.println("Input path should be specified after \"-i\" tag");
			System.err.print("Given coomand: ");
			for (String s : args) {
				System.err.print(s + " ");
			}
			System.err.println();
		}

		// Check if -o flag is given.
		if (args.length > 3) {
			out_path = args[4];
			o_spec = true;
		}

		for (int i = 0; i < args.length; i++) {

			switch (args[i]) {
			case "-n":
				startTime = System.nanoTime();
				output = naive(seq);
				endTime = System.nanoTime();
				if (output[0] == 1 & output[1] == 0 & output[2] == 0) {
					empty = true;
				}
				// System.out.print("Naive algorithm runtime in mikro seconds: ");
				// System.out.println(Math.floor((endTime - startTime) / 1000));
				if (!empty) {
					if (o_spec) {
						File out_file = new File(out_path, "smms.txt");
						writeOutput(output, out_file, giveAmss);
					} else {
						if (giveAmss) {
							for (int[] mss : amss) {
								for (int ms : mss) {
									System.out.print(ms + "\t");
								}
								System.out.println();
							}
						} else {
							for (int r : output) {
								System.out.print(r + "\t");
							}
						}
					}
				}
				break;

			case "-l":
				startTime = System.nanoTime();
				output = linear(seq);
				endTime = System.nanoTime();
				if (output[0] == 1 & output[1] == 0 & output[2] == 0) {
					empty = true;
				}
				// System.out.print("Linear algorithm runtime in mikro seconds: ");
				// System.out.println(Math.floor((endTime - startTime) / 1000));
				if (!empty) {
					if (o_spec) {
						File out_file = new File(out_path, "smms.txt");
						writeOutput(output, out_file, giveAmss);
					} else {
						if (giveAmss) {
							for (int[] mss : amss) {
								for (int ms : mss) {
									System.out.print(ms + "\t");
								}
								System.out.println();
							}
						} else {
							for (int r : output) {
								System.out.print(r + "\t");
							}
						}
					}
				}
				break;

			case "-d":
				startTime = System.nanoTime();
				output = smms_dp(seq);
				endTime = System.nanoTime();

				if (output[0] == 1 & output[1] == 0 & output[2] == 0) {
					empty = true;
				}
				// System.out.print("Dynamic algorithm runtime in mikro seconds: ");
				// System.out.println(Math.floor((endTime - startTime) / 1000));
				if (!empty) {
					if (o_spec) {
						File out_file = new File(out_path, "smms.txt");
						writeOutput(output, out_file, giveAmss);
					} else {
						if (giveAmss) {
							for (int[] mss : amss) {
								for (int ms : mss) {
									System.out.print(ms + "\t");
								}
								System.out.println();
							}
						} else {
							for (int r : output) {
								System.out.print(r + "\t");
							}
						}
					}
				}
				break;

			case "-dc":
				startTime = System.nanoTime();
				output = mms_dc(seq);
				endTime = System.nanoTime();

				if (output[0] == 0 & output[1] == -1 & output[2] == 0) {
					empty = true;
				}
				// System.out.print("Divide&Conquer algorithm runtime in mikro seconds: ");
				// System.out.println(Math.floor((endTime - startTime) / 1000));
				if (!empty) {
					if (o_spec) {
						File out_file = new File(out_path, "smms.txt");
						writeOutput(output, out_file, giveAmss);
					} else {
						for (int r : output) {
							System.out.print(r + "\t");
						}
					}
				}
				break;
			}
		}

	}

	/**
	 * Reads int sequence from txt file. Lines starting with '#' are ignored.
	 * 
	 * @param path
	 */
	public static void readInput(String path) {
		BufferedReader reader = null;
		try {
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

		} catch (Exception e) {
			throw new RuntimeException("got error while reading txt.", e); // TODO Handle Exception!
		}
	}

	/**
	 * Writes tab separated file for int results.
	 * 
	 * @param result
	 * @param path
	 */
	public static void writeOutput(int[] result, File file, boolean giveAmss) {
		if (giveAmss) {
			try {
				FileWriter fw = new FileWriter(file);
				BufferedWriter writer = new BufferedWriter(fw);

				for (int[] mss : amss) {
					for (int ms : mss) {
						writer.write(ms + "\t");
					}
					writer.newLine();
				}
				writer.close();
			} catch (Exception e) {
				throw new RuntimeException("Got error while writing output", e); // TODO handle exception properly
			}
		} else {
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

	/**
	 * Naive algorithm
	 * 
	 * @param a
	 * @return
	 */
	public static int[] naive(int[] a) {
		int n = a.length;
		int maxscore = 0;
		int l = 1;
		int r = 0;
		boolean first = false;

		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				int s = 0;
				for (int k = i; k <= j; k++) {
					s = s + a[k - 1];
				}
				first = false;
				if (s > maxscore) {
					maxscore = s;
					l = i - 1;
					r = j - 1;
					if (giveAmss) {
						// Remove everything in amms list and add new max if new max is bigger then previous max
						amss.removeAll(amss);
						int[] mss = { l, r, maxscore };
						amss.add(mss);
						first = true;
					}
				}

				if (s == maxscore) {
					if (giveAmss & !first) {
						l = i - 1;
						r = j - 1;
						int[] mss = { l, r, maxscore };
						amss.add(mss);
					}
					if (j - i < r - l & !giveAmss) {
						maxscore = s;
						l = i - 1;
						r = j - 1;
					}
				}
			}
		}

		int mms[] = new int[3];
		mms[0] = l;
		mms[1] = r;
		mms[2] = maxscore;

		return mms;
	}

	/**
	 * Dynamic programming algorithm
	 * 
	 * @param a
	 * @return
	 */
	public static int[] smms_dp(int[] a) {
		int n = a.length;
		int maxscore = 0;
		int l = 1;
		int r = 0;
		int[][] S = new int[n][n];

		// Iterate through all subsequences
		for (int i = 0; i <= n - 1; i++) {
			for (int j = i; j <= n - 1; j++) {

				// Add value to previous sum
				if (j != 0) {
					S[i][j] = S[i][j - 1] + a[j];
				} else {
					S[i][j] = S[i][j] + a[j];
				}

				if (S[i][j] >= maxscore & giveAmss) {
					if (S[i][j] > maxscore) {
						amss.removeAll(amss);
						int[] mss = { i, j, S[i][j] };
						amss.add(mss);
					} else {
						int[] mss = { i, j, S[i][j] };
						amss.add(mss);
					}
				}

				// Save if new max
				if (S[i][j] > maxscore) {
					maxscore = S[i][j];
					l = i;
					r = j;
				}

				// Save if new max is shorter than previous
				if (j - i < r - l & S[i][j] == maxscore) {
					maxscore = S[i][j];
					l = i;
					r = j;
				}
			}
		}

		int mms[] = new int[3];
		mms[0] = l;
		mms[1] = r;
		mms[2] = maxscore;

		return mms;
	}

	/**
	 * Linear runtime algorithm
	 * 
	 * @param a
	 * @return
	 */
	public static int[] linear(int[] a) {
		int n = a.length;
		int max = 0;
		int l = 1;
		int r = 0;
		int rmax = 0;
		int rstart = 1;
		int frstart = 1;
		int frmax = 0;

		for (int i = 0; i <= n - 1; i++) {

			if (rmax > 0) {
				rmax = rmax + a[i];
				frmax = frmax + a[i];
			} else if (rmax == 0) {
				frmax = frmax + a[i];
				rmax = a[i];
				frstart = i;
				rstart = i;

			} else {
				frmax = a[i];
				frstart = i;
				rmax = a[i];
				rstart = i;
			}

			if (giveAmss & rmax > max) {
				amss.removeAll(amss);
				int[] mss = { rstart, i, rmax };
				if (frstart == rstart) {
					amss.add(mss);
				} else {
					int[] mss1 = { frstart, i, frmax };
					amss.add(mss1);
					amss.add(mss);
				}
			}

			if (giveAmss & rmax == max) {
				int[] mss = { rstart, i, rmax };
				if (frstart == rstart) {
					amss.add(mss);
				} else {
					int[] mss1 = { frstart, i, frmax };
					amss.add(mss1);
					amss.add(mss);
				}
			}

			if (rmax > max) {
				max = rmax;
				l = rstart;
				r = i;
			}

			// Save if new max is shorter than previous
			if (i - rstart < r - l & rmax == max) {
				max = rmax;
				l = rstart;
				r = i;
			}
		}

		int mms[] = new int[3];
		mms[0] = l;
		mms[1] = r;
		mms[2] = max;

		return mms;
	}

	/**
	 * Divide&Conquer algorithm
	 * 
	 * @param a
	 * @return
	 */
	public static int[] mms_dc(int[] a) {
		int n = a.length;
		int[] result = mms_dc_rec(0, n - 1, a);

		for (int[] mss : treeAmss) {
			for (int ms : mss) {
				System.out.print(ms + " ");
			}
			System.out.println();
		}
		
		System.out.println(treeAmss.contains(result));
		
		return result;
	}

	/**
	 * Recursive part of dc algorithm
	 * 
	 * @param a
	 * @param i
	 * @param j
	 * @return
	 */
	public static int[] mms_dc_rec(int i, int j, int[] a) {

		if (i == j) {
			// Stop dividing
			if (a[i] > 0) {
				int[] result = { i, i, a[i] };
				return result;
			} else {
				int[] result = { i, i - 1, 0 };
				return result;
			}
		} else {
			// Divide sequence
			int m = (int) Math.floor((i + j - 1) / 2);
			int[] preSeq = mms_dc_rec(i, m, a);
			int[] postSeq = mms_dc_rec(m + 1, j, a);

			// Get max from left side of the sequence
			int i3 = m;
			int s = a[i3];
			int simax = s;

			for (int k = i3 - 1; k >= i; k--) {
				s = s + a[k];
				if (s > simax) {
					simax = s;
					i3 = k;
				}
			}

			// Get max from right side of the sequence
			int j3 = m + 1;
			s = a[j3];
			int sjmax = s;

			for (int k = j3 + 1; k <= j; k++) {
				s = s + a[k];
				if (s > sjmax) {
					sjmax = s;
					j3 = k;
				}
			}

			// Get total max
			int s3 = simax + sjmax;

//				if(	treeAmss.ceiling(preSeq) == null) {
//					treeAmss.clear();
//					treeAmss.add(preSeq);
//				} else if(treeAmss.contains(preSeq)) {
//					treeAmss.add(preSeq);	
//				}
//			
//				int[] midSeq = {i3, j3, s3 };
//				if(	treeAmss.ceiling(midSeq) == null) {
//					treeAmss.clear();
//					treeAmss.add(midSeq);
//				} else if(treeAmss.contains(midSeq)) {
//					treeAmss.add(midSeq);
//				}
//			
//				if(	treeAmss.ceiling(postSeq) == null) {
//					treeAmss.clear();
//					treeAmss.add(postSeq);
//				} else if(treeAmss.contains(postSeq)) {
//					treeAmss.add(postSeq);
//				}
			

			// PreSeq contains sequence with highest score?
			if (preSeq[2] >= postSeq[2] & preSeq[2] >= s3) {
				// postSeq has same score and is shorter?
				if (preSeq[2] == postSeq[2] & preSeq[1] - preSeq[0] > postSeq[1] - postSeq[0]) {
					return postSeq;	
					// midSeq has same score and is shorter?
				} else if (preSeq[2] == s3 & preSeq[1] - preSeq[0] > j3 - i3) {
					 int[] midSeq = { i3, j3, s3 };
					return midSeq;
				} else {
					return preSeq;
				}
				// midSeq contains sequence with highest score?
			} else if (s3 > preSeq[2] & s3 >= postSeq[2]) {
				// postSeq has same score and is shorter?
				if (s3 == postSeq[2] & postSeq[1] - postSeq[0] < j3 - i3) {
					return postSeq;
				} else {
					 int[] midSeq = { i3, j3, s3 };
					return midSeq;
				}
			} else {
				return postSeq;
			}
		}
	}
}

class arrayComp implements Comparator<int[]>{
	@Override
	public int compare(int[] a, int[] b) {
		if(a[2] < b[2]) {
			return -1;
		} else if(a[2] > b[2]) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
}