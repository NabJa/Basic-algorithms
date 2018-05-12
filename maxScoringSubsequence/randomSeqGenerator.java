package maxScoringSubsequence;
import java.util.concurrent.ThreadLocalRandom;

public class randomSeqGenerator {
	
	public int[] generateSequence(int length, int min, int max){
		int[] list = new int[length];
		for(int i =0; i < length; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
			list[i] = randomNum;
		}
		return list;
	}
	
	
}
