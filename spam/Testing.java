package spam;

import java.io.*;
import java.util.*;

class Testing
{
	String filePath;
	HashMap<String, Word> trainingData;
	ArrayList<Double> spamWordProbabilities;
	ArrayList<Double> hamWordProbabilities;
	Testing(String filePath, HashMap<String, Word> trainingData)
	{
		this.filePath = filePath;
		this.trainingData = trainingData;
		spamWordProbabilities = new ArrayList<Double>();
		hamWordProbabilities = new ArrayList<Double>();
	}

	public void test(){

		File fl = new File(filePath);
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fl));
			
			String msg="";
			
			String line = br.readLine();
				
			while(line!=null) 
			{
				msg=msg + line + " ";
				line=br.readLine();
			}
				
			StringTokenizer tok = new StringTokenizer(msg);

			while(tok.hasMoreTokens())
			{
				String newString = (String)tok.nextToken();
				if(trainingData.containsKey(newString)){
					hamWordProbabilities.add(trainingData.get(newString).getHamProbability());
					spamWordProbabilities.add(trainingData.get(newString).getSpamProbability());
				}
			}
		}

		catch(Exception e)
		{
			System.err.println("ERROR in Training"+e);
		}

		System.out.println(spamWordProbabilities+" "+hamWordProbabilities);
	}

	public boolean isSpam()
	{
		double ham = compute(hamWordProbabilities);
		double spam = compute(spamWordProbabilities);
		
		if(spam>ham)
		{
			System.out.println("SPAM");
			return true;
		}
		else
		{
			System.out.println("HAM");
			return false;
		}

	}

	public double compute(ArrayList<Double> wordProbabilities)
	{
		double factor = 0, finalProbability=0;
		for(Double x: wordProbabilities)
		{
			factor = factor + Math.log(1-x) - Math.log(x); 
		}

		finalProbability = (double)1.0/(double)(1 + Math.exp(factor));
		System.out.println("Final Probability = "+finalProbability);
		return finalProbability;
	}
}
