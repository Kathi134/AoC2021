package Raetsel2021;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*\ Extended Polymerization \*/
public class Dec14 extends Puzzle
{
	
	private Rule[] rules;
	private long erg1;
	private long erg2;
	private Rule[] counts;
	
	private ArrayList<Letter> countLetters = new ArrayList<Letter>(); 
	
	public void initializeCountLetters()
	{
		countLetters = new ArrayList<Letter>();
		for(int i=0; i<rules.length; i++)
		{
			Letter l = new Letter(rules[i].addedLetter, 0);
			
			if(!countLetters.contains(l))
			{
				countLetters.add(l);
			}
		}
	}
	
	public void read() throws IOException
	{
		prepare();
		br.readLine(); //überspringe die ersten zwei zeilen
		br.readLine(); 
		
		rules = new Rule[lines-2];
		counts = new Rule[lines-2];
		for(int i=0; i<rules.length; i++)
		{
			String line = br.readLine();
			Rule r = new Rule(line.substring(0, 2), line.charAt(6)+"");
			rules[i] = r;
			counts[i] = r;
		}
	}
	
	public void initializeRuleCounts()
	{
		for(int i=0; i<inputString.length()-1; i++)
		{
			String pairPattern = inputString.charAt(i) + "" + inputString.charAt(i+1);
			incrementQuantity(pairPattern);
		}
	}
	
	public void incrementQuantity(String pair)
	{
		for(int i=0; i<rules.length; i++)
		{
			if(rules[i].equals(pair))
			{
				rules[i].incrementTimesApplied();
				break;
			}
		}
	}
	
	public void setQuantity(String pair, int newQuantity)
	{
		for(int i=0; i<rules.length; i++)
		{
			if(rules[i].equals(pair))
			{
				rules[i].incrementTimesApplied();
			}
		}
	}
	
	public Dec14() throws IOException
	{
		f = new File("src\\Raetsel2021\\inputs\\Dec" + 14);
		
		prepare();
		readInput("String");
		
		erg1 = solve(10);
		erg2 = solve(40);
		//solveTask1();
		//solveTask2();
		System.out.println("Task 1 -- " + erg1);
		System.out.println("Task 2 -- " + erg2);
	}
	
	public long solve(int steps) throws IOException
	{
		read();
		initializeRuleCounts();
		for(int step=0; step<steps; step++)
		{
			//merke alle änderungen in einem neuen Rule array
			Rule[] changes = rules.clone();		
			for(int i=0; i<rules.length; i++)
			{
				if(rules[i].getTimesApplied()>0)
				{
					applyRule(rules[i], changes);
				}
			}
			rules = changes.clone();			
		}
		return computeFinalAnswer();
	}
	
	public Rule[] clone() throws CloneNotSupportedException
	{
		Rule[] clone = new Rule[rules.length];
		for(int i=0; i<clone.length; i++)
		{
			clone[i] = rules[i].clone();
		}
		return clone;
	}
	
	public void applyRule(Rule r, Rule[] changes)
	{
		long tmp = r.getTimesApplied();
		String pair1 = r.newPair1;
		String pair2 = r.newPair2;
		
		for(int i=0; i<rules.length; i++)
		{
			if(rules[i].equals(r.searchPattern))
			{
				changes[i].setTimesApplied(0);
			}
			if(rules[i].equals(pair1))
			{
				changes[i].addTimesApplied(tmp);
			}
			if(rules[i].equals(pair2))
			{
				changes[i].addTimesApplied(tmp);
			}
		}
	}
	
	public int getIndexLetterInCountList(Letter l)
	{
		for(int i=0; i<countLetters.size(); i++)
		{
			if(countLetters.get(i).equals(l))
			{
				return i;
			}
		}
		return -1;
	}
	
	public long computeFinalAnswer()
	{
		initializeCountLetters();

		for(int i=0; i<rules.length; i++)
		{
			long sCount = rules[i].getTimesApplied();
			
			Letter l1 = new Letter(rules[i].searchPattern.charAt(0)+"");
			int i1 = getIndexLetterInCountList(l1);
			countLetters.get(i1).addToQuantity(sCount);
			
			Letter l2 = new Letter(rules[i].searchPattern.charAt(1)+"");
			int i2 = getIndexLetterInCountList(l2);
			countLetters.get(i2).addToQuantity(sCount);
		}

		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;

		for(Letter l : countLetters)
		{
			if(min > l.getQuantity())
				min = l.getQuantity();
			if(max < l.getQuantity())
				max = l.getQuantity();
		}

		return (max / 2) - (min / 2) + 1;
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec14();
	}
	
	public void solveTask1(){}
	
	public void solveTask2(){}
}

class Rule implements Cloneable
{
	public final String searchPattern;
	public final String addedLetter;
	
	public final String newPair1;
	public final String newPair2;
	
	private long timesApplied;
	
	public Rule(String pattern, String letter)
	{
		searchPattern = pattern;
		addedLetter = letter;
		newPair1 = pattern.charAt(0) + letter;
		newPair2 = letter + pattern.charAt(1);
		timesApplied = 0;
	}
	
	public long getTimesApplied()
	{
		return timesApplied;
	}
	
	public void setTimesApplied(long timesApplied)
	{
		this.timesApplied = timesApplied;
	}
	
	public void addTimesApplied(long addition)
	{
		this.timesApplied += addition;
	}
	
	public void incrementTimesApplied()
	{
		this.timesApplied++;
	}
	
	@Override
	public boolean equals(Object givenPattern)
	{
		String s = (String) givenPattern;
		if(this.searchPattern.equals(s))
		{
			return true;
		}
		return false;
	}

	@Override
	protected Rule clone() throws CloneNotSupportedException
	{
		Rule r = new Rule(this.searchPattern, this.addedLetter);
		r.setTimesApplied(this.timesApplied);
		return r;
	}	
}

class Letter
{
	public final String letter;
	private long quantity;
	
	public Letter(String l)
	{
		letter = l;
	}
	
	public Letter(String l, int q)
	{
		this(l);
		quantity = q;
	}

	public long getQuantity()
	{
		return quantity;
	}

	public void addToQuantity(long q)
	{
		quantity += q;
	}

	@Override
	public boolean equals(Object obj)
	{
		Letter l = (Letter) obj;
		if(this.letter.equals(l.letter))
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "(" + letter + ", quantity=" + quantity + ")";
	}	
}