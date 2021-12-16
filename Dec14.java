package Raetsel2021;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*\ Extended Polymerization \*/
public class Dec14 extends Puzzle
{
	private String polymer;
	private String[] polymerParts;
	private ArrayList<Letter> countLetters = new ArrayList<Letter>(); 
	private long erg1;
	private long erg2;
	
	public void initializeCountLetters()
	{
		countLetters = new ArrayList<Letter>();
		for(int i=0; i<inputStringTable.length; i++)
		{
			Letter l = new Letter(inputStringTable[i][1], 0);
			
			if(!countLetters.contains(l))
			{
				countLetters.add(l);
			}
		}
		
		for(int i=0; i<polymer.length(); i++)
		{
			incrementLetter(polymer.charAt(i)+"");
		}
	}

	public void initializePolymerParts()
	{
		polymer = "PFVKOBSHPSPOOOCOOHBP";
		polymerParts = new String[polymer.length()-1];
		
		for(int i=0; i<polymerParts.length; i++)
		{
			polymerParts[i] = polymer.charAt(i) + "" + polymer.charAt(i+1);
		}
	}
	
	public Dec14() throws IOException
	{
		f = new File("src\\Raetsel2021\\inputs\\Dec" + 14);
		prepare();
		readInput("StringTable divBy  -> ");
		
		solveTask1();
		//solveTask2();
		System.out.println("Task 1 -- " + erg1);
		System.out.println("Task 2 -- " + erg2);
	}
	
	
	public void solveTask1()
	{
		initializePolymerParts();
		execute(10);
		erg1 = computeFinalAnswer();
	}
	
	public void execute(int steps)
	{
		initializeCountLetters();
		
		for(String k: polymerParts)
		{
			workOffPair(k, 0, steps);
			System.err.println("current pos: " + k);
		}
		
	}
	
	public void workOffPair(String pair, int currStep, int steps)
	{
		if(currStep < steps)
		{
			for(int i=0; i<inputStringTable.length; i++) //look at all rules
			{
				String currPattern = inputStringTable[i][0];
				if(pair.equals(currPattern)) //if replaceable
				{
					String addedLetter = inputStringTable[i][1];
					//create 2 new pairs
					String newPair1 = currPattern.charAt(0)+""+addedLetter;
					String newPair2 = addedLetter+""+currPattern.charAt(1);
					
					//increment the counter for the added letter
					incrementLetter(addedLetter);
					
					//work off the new pairs
					workOffPair(newPair1, currStep+1, steps);
					workOffPair(newPair2, currStep+1, steps);
				}
			}
		}
	}
	
	public void incrementLetter(String addedLetter)
	{
		for(int i=0; i<countLetters.size(); i++)
		{
			if(countLetters.get(i).letter.equals(addedLetter))
			{
				countLetters.get(i).addToQuantity(1);
			}
		}
	}
	
	public long computeFinalAnswer()
	{
		long max = Long.MIN_VALUE;
		long min = Long.MAX_VALUE;
		
		for(int i=0; i<countLetters.size(); i++)
		{
			if(countLetters.get(i).getQuantity() > max)
			{
				max = countLetters.get(i).getQuantity();
			}
			if(countLetters.get(i).getQuantity() < min)
			{
				min = countLetters.get(i).getQuantity();
			}
		}
		
		return max-min;
	}
	
	
	public void solveTask2()
	{
		initializePolymerParts();
		execute(40);
		erg2 = computeFinalAnswer();
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec14();
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

	public void addToQuantity(int q)
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
