package spam;

import java.util.*;
import java.io.*;

class Training
{
	String trainSpamPath, trainHamPath;
	int totalHamCount, totalSpamCount;
	static HashMap<String, Word> readset;

	Training()
	{
		this.totalHamCount = 0;
		this.totalSpamCount = 0;
		this.trainHamPath = "dataset/hamMessages";
		this.trainSpamPath = "dataset/spamMessages";
		this.readset = new HashMap<String, Word>();
	}

	Training(String trainHamPath, String trainSpamPath)
	{
		this.totalHamCount = 0;
		this.totalSpamCount = 0;
		this.trainHamPath = trainHamPath;
		this.trainSpamPath = trainSpamPath;
		this.readset = new HashMap<String, Word>();			
	}

	public int getTotalHamCount()
	{
		return totalHamCount;
	}

	public int getTotalSpamCount()
	{
		return totalSpamCount;
	}

	public HashMap<String, Word> addTrainingSetFrom(String filePath)
	{

		File folder = new File(filePath);
		File[] files = folder.listFiles();

		try
		{
			for(File fl : files)
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
					
					Word newWord = new Word(newString);
					
					if(filePath.equals(trainHamPath)){
						totalHamCount++;
						this.readset.put(newString , readset.containsKey(newString)?(Word)(readset.get(newString)).foundInHam():newWord.foundInHam());

					}
					if(filePath.equals(trainSpamPath)){
						totalSpamCount++;
						this.readset.put(newString , readset.containsKey(newString)?(Word)(readset.get(newString)).foundInSpam():newWord.foundInSpam());

					}	
				}
			}
		}

		catch(Exception e)
		{
			System.err.println("ERROR in Training"+e);
		}

		return readset;
	}

	public void computeProbability()
	{
		for(Map.Entry<String, Word> element: readset.entrySet())
		{
			Word newWord = element.getValue();
			newWord.computeInHamRatio(totalHamCount);
			newWord.computeInSpamRatio(totalSpamCount);
			newWord.computeHamProbability();
			newWord.computeSpamProbability();
			readset.put(newWord.getWord(), newWord);
		}
	}

	public void reset(HashMap<String, Word> set){
		set.clear();
		System.out.println("Training data reset\n");
	}

}