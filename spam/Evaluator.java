package spam;

import java.io.*;
import java.util.*;

class Evaluator
{
	private double truePositive, trueNegative, falsePositive, falseNegative;
	private int hamFileCount, spamFileCount;
	HashMap<String, Word> trainingData;

	Evaluator( HashMap<String, Word> trainingData)	
	{
		truePositive = 0.0;
		trueNegative = 0.0;
		falsePositive = 0.0;
		falseNegative = 0.0;
		hamFileCount = 0;
		spamFileCount = 0;
		this.trainingData = trainingData;
		
	}

	public void evaluate()
	{
		try
		{
			File hamFolder = new File("dataset/evaluateHamMessages");
			File[] hamFiles = hamFolder.listFiles();
			for(File fl : hamFiles)
			{
				hamFileCount++;
				String fileName = fl.getAbsolutePath();
				Testing temp = new Testing(fileName, trainingData);
				temp.test();
				System.out.println(temp.spamWordProbabilities+" "+temp.hamWordProbabilities);
				if(temp.isSpam())
				{
					falsePositive++;
					System.out.println(falsePositive);
				}
				else
				{
					trueNegative++;
					System.out.println(trueNegative);
				}			
			}
			
			File spamFolder = new File("dataset/evaluateSpamMessages");
			
			File[] spamFiles = spamFolder.listFiles();
			for(File fl : spamFiles)
			{
				spamFileCount++;
				String fileName = fl.getAbsolutePath();
				Testing temp = new Testing(fileName, trainingData);
				temp.test();
				if(temp.isSpam())
				{
					truePositive++;
					System.out.println(truePositive);
				}
				else
				{
					falseNegative++;
					System.out.println(falseNegative);
				}
			}

			falsePositive = falsePositive/hamFileCount;
			trueNegative = trueNegative/hamFileCount;
			truePositive = truePositive/spamFileCount;
			falseNegative = falseNegative/spamFileCount;
		}
		catch(Exception e)
		{
			System.err.println("ERROR in Evaluation"+e);
		}
		String result="";
		result = "False Positive ="+falsePositive+"\nTrue Negative = "+trueNegative+"\nTrue Positive = "+truePositive+"\nFalse Negative = "+falseNegative;
		System.out.println(result);


	}
	public String printResult()
	{
		String result="";
		result = "False Positive ="+falsePositive+"\nTrue Negative = "+trueNegative+"\nTrue Positive = "+truePositive+"\nFalse Negative = "+falseNegative;
		return result;
	}
}
