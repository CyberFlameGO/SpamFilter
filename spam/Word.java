package spam;

public class Word{
	
	private String word;
	
	private int hamCount;
	
	private int spamCount;
	
	private double inHamRatio;
	
	private double inSpamRatio;
	
	private double hamProbability;
	
	private double spamProbability;
	
	private int strength;
	
	public Word(String word)
	{
		this.word=word;
		this.hamCount = 0;
		this.spamCount = 0;
		this.inHamRatio = 0.0;
		this.inSpamRatio = 0.0;
		this.hamProbability=0.0;
		this.spamProbability=0.0;
		this.strength = 3;
	}
	
	
	public String getWord()
	{
		return this.word;
	}
	

	public Word foundInHam()
	{
		this.hamCount++;
		return this;
	}
	
	public Word foundInSpam()
	{
		this.spamCount++;
		return this;
	}
	

	public void computeInHamRatio(int totalHamCount)
	{
		this.inHamRatio = (double)this.hamCount/(double)totalHamCount;
	}

	public void computeInSpamRatio(int totalSpamCount)
	{
		this.inSpamRatio = (double)this.spamCount/(double)totalSpamCount;
	}



	public double getHamProbability()
	{
		return hamProbability;
	}

	public double getSpamProbability()
	{
		return spamProbability;
	}



	public void computeHamProbability()
	{
		this.hamProbability = (this.inHamRatio)/(this.inHamRatio + this.inSpamRatio);
		this.hamProbability = (strength*0.5+this.hamCount*hamProbability)/(strength + hamCount);
	}	
	
	public void computeSpamProbability()
	{
		this.spamProbability= (this.inSpamRatio)/(this.inHamRatio + this.inSpamRatio);
		this.spamProbability = (strength*0.5+this.spamCount*spamProbability)/(strength + spamCount);
	}
	


	public String toString()
	{
		return ("\nWord : "+this.word+
				"\nWord count in ham messages = " +this.hamCount+
				"\nWord count in spam messages = "+this.spamCount+
				"\nP("+this.word+"|ham) = "+this.inHamRatio+
				"\nP("+this.word+"|spam) = "+this.inSpamRatio+
				"\nP(ham|"+this.word+") = "+this.hamProbability+
				"\nP(spam|"+this.word+") = "+this.spamProbability+"\n");
	}	
}
