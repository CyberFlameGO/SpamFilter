package spam;
import java.util.*;

class MainNoGUI
{
	public static void main(String[] args) {
		Training rs = new Training("dataset/hamMessages", "dataset/spamMessages");
		HashMap<String, Word> prints;
		prints=rs.addTrainingSetFrom("dataset/hamMessages");
		prints=rs.addTrainingSetFrom("dataset/spamMessages");
		rs.computeProbability();
		System.out.println(prints+" "+rs.getTotalHamCount()+" "+rs.getTotalSpamCount());
		Testing t = new Testing("dataset/spamTest.txt", rs.readset);
		t.test();
		boolean S = t.isSpam();
		Evaluator e = new Evaluator(rs.readset);
		e.evaluate();
	}
}