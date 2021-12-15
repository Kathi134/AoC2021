package Raetsel2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Puzzle 
{
	protected static int records = 1; //wenn ein datensatz über mehrere zeilen geht, ist records die anzahl datensätze
	protected static int lines = 0;
	protected static int columns = 0; //anzahl chars in einer Zeile
	protected static BufferedReader br = null;
	protected static File f;
	
	protected static int[] inputIntList;
	protected static String[] inputStringList;
	protected static char[][] inputCharTable;	
	protected static String[][] inputStringTable;
	protected static int[][] inputIntTable;
	
	protected static int[] adjacentsParameters; 

	
	//prepare erstellt einen FileReader über die aktuelle InputDatei und legt die Anzahl Zeilen und Spalten fest
	public static void prepare() throws IOException 
	{
		FileReader fr = new FileReader(f);
		br = new BufferedReader(fr);
		lines = 0;
		
		while (br.readLine() != null)
		{
			lines++;
		}
		resetReader();
		
		columns = br.readLine().length();
		resetReader();
	}
	
	//resetReader bringt den Reader an den Anfang des Dokuments
	public static void resetReader() throws IOException
	{
		//der br1 ist schon mit fr ans ende des files durchgelaufen, reset() doesnt work, also neue Reader anlegen
		FileReader fr2 = new FileReader(f);
		br = new BufferedReader(fr2);	
	}

	
	//liest den input in ein Feld, je nach String eingabe, der Sorte<inputKind> ein
	public static void readInput(String inputKind) throws IOException
	{
		switch(inputKind)
		{
		case "charTable": readCharTable(); break;
		case "intList": readIntList(); break;
		case "intTable": readIntTable(); break;
		case "StringList": readStringList(); break;
		case "seperatedStringList": readSeperatedStringList(); break;
		}
		if(inputKind.contains("divBy "))
		{
			
			
			if(inputKind.contains("StringTable"))
			{
				String divisor = inputKind.replace("StringTable divBy ", "");
				readDividedStringTable(divisor);		
			}
			else if(inputKind.contains("intTable"))
			{
				String divisor = inputKind.replace("intTable divBy ", "");
				readDividedIntTable(divisor);
			}
			else if(inputKind.contains("intList"))
			{
				String divisor = inputKind.replace("intList divBy " , "");
				readDividedIntList(divisor);
			}
		}
	}
	

	public static void readDividedIntTable(String divisor) throws IOException
	{
		inputIntTable = new int[lines][];
		for(int i=0; i<inputIntTable.length; i++)
		{
			String line = br.readLine();
			line.trim();
			int[] columns = splitToIntegerArr(line, divisor);
			inputIntTable[i] = columns;
		}
	}
	
	public static void readIntTable() throws IOException
	{
		inputIntTable = new int[lines][columns]; 
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			for (int c=0; c<columns; c++)
			{
				inputIntTable[l][c] = Character.getNumericValue(line.charAt(c));
			}
		}
	}
	
	public static void readDividedIntList(String divisor) throws IOException
	{
		String all = "";
		String line = br.readLine();
		while(line!=null)
		{
			all+=line;
			line = br.readLine();
		}
		inputIntList = splitToIntegerArr(all, ",");
	}
	
	public static void readDividedStringTable(String divisor) throws IOException
	{
		inputStringTable = new String[lines][];
		for(int i=0; i<inputStringTable.length; i++)
		{
			String line = br.readLine();
			String[] columns = line.split(divisor);
			inputStringTable[i] = columns;
		}
	}
		
 	public static void readStringList() throws IOException
	{
		inputStringList = new String[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputStringList[l] = br.readLine();
		}
	}
 	
	public static void readSeperatedStringList() throws IOException
	{
		for(int i=0; i<lines; i++)
		{
			if (br.readLine().isBlank())
			{
				records++;
			}
		}
		resetReader();
		
		inputStringList = new String[records];
		//String currentRecord = ""; //aktueller Datensatz wird in einem String zsmgefasst
		for(int i=0; i<records; i++)
		{
			String zeile = br.readLine();
			String currentRecord = "";
			
			while (!zeile.isBlank())
			{
				currentRecord += zeile + " ";
				zeile = br.readLine();
				if(zeile == null)break;
			}
			inputStringList[i] = currentRecord;
		}
		
	}
	
	public static void readIntList() throws IOException
	{
		inputIntList = new int[lines]; 
		for(int l=0; l<lines; l++)
		{
			inputIntList[l] = Integer.parseInt(br.readLine());
		}
		
	}
	
	public static void readCharTable() throws IOException
	{
		inputCharTable = new char[lines][columns]; 
		for(int l=0; l<lines; l++)
		{
			String line = br.readLine();
			for (int c=0; c<columns; c++)
			{
				inputCharTable[l][c] = line.charAt(c);
			}
		}
	}

	//trennt ein zeilen-String beim divisor und macht daraus ein integer-array
	public static int[] splitToIntegerArr(String line, String divisor)
	{
		String[] tmp = line.split(divisor);
		int[] arr = new int[tmp.length];
		for(int i=0; i<tmp.length; i++)
		{
			arr[i] = Integer.parseInt(tmp[i]);
		}
		return arr;
	}
	
	
	//zum testen
	public static void printStringList()
	{
		for(int i=0; i<inputStringList.length; i++)
		{
			System.err.println(inputStringList[i]);
		}
	}
	
	public static void printCharTable()
	{
		for(int j=0; j<inputCharTable.length; j++)
		{
			for (int i=0; i<inputCharTable[0].length; i++)
			{
				System.err.print(inputCharTable[j][i]);
			}
			System.err.println();
		}
		
	}
	
	public static void printStringTable()
	{

		for(int j=0; j<inputStringTable.length; j++)
		{
			for (int i=0; i<inputStringTable[0].length; i++)
			{
				System.err.print(inputStringTable[j][i]+" ");
			}
			System.err.println();
		}
	}
	
	public static void printIntTable()
	{

		for(int j=0; j<inputIntTable.length; j++)
		{
			for (int i=0; i<inputIntTable[0].length; i++)
			{
				System.err.printf(inputIntTable[j][i] + " ");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	public static void printIntList()
	{
		for(int i=0; i<inputIntList.length; i++)
		{
			System.err.print(inputIntList[i]+",");
		}
		System.err.println();
	}
	
	
	//clone
	public static char[][] clone(char[][] original)
	{
		char[][] copy = new char[original.length][];
		for(int i=0; i<copy.length; i++)
		{
			copy[i] = new char[original[i].length];
			for(int j=0; j<original[i].length; j++)
			{
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}
	
	public static long[] clone(long[] original)
	{
		long[] copy = new long[original.length];
		for(int i=0; i<original.length; i++)
		{
			copy[i] = original[i];
		}
		return copy;
	}
	
	
	//erwartet eine pos fürs zentrum und die angabe des inputTable-datentyps
	//gibt ein Feld mit den Parametern zurück: [0: rowAbove][1: rowBelow][2: columnLeft][3: columnRight]
	public static int[][] getAdjacentParameters(int i, int j, String inputKind)
	{
		adjacentsParameters = new int[4];
		
		int rowAbove = i;
		int rowBelow = i;
		int columnRight = j;
		int columnLeft = j;
		
		if(inputKind.equals("intTable"))
		{
			if(i!=0) rowAbove = i-1; 
			if(i!=inputIntTable.length-1) rowBelow = i+1;
			if(j!=0) columnLeft = j-1;
			if(j!=inputIntTable[i].length-1) columnRight = j+1;
		}
		else if(inputKind.equals("charTable"))
		{
			if(i!=0) rowAbove = i-1; 
			if(i!=inputCharTable.length-1) rowBelow = i+1;
			if(j!=0) columnLeft = j-1;
			if(j!=inputCharTable[i].length-1) columnRight = j+1;
		}
		
		int[] parameters = {rowAbove, rowBelow, columnLeft, columnRight};
		adjacentsParameters = parameters;
		
		return adjacents(i, j);
	}	
	
	public static int[][] adjacents(int i, int j)
	{
		int[][] ad = new int[4][2];
		ad[0][0] = j;
		ad[0][1] = adjacentsParameters[0]; //row above, diese spalte
		ad[1][0] = adjacentsParameters[2];
		ad[1][1] = i; 						//diese reihe, links
		ad[2][0] = adjacentsParameters[3];
		ad[2][1] = i;						//diese reihe, rechts
		ad[3][0] = j;
		ad[3][1] = adjacentsParameters[1]; //row below, diese spalte
		return ad;
	}
	
	public static int[][] adjacentsHÄSSLICH(int i, int j)
	{
		int[][] ad = new int[2][2];
		ad[0][0] = adjacentsParameters[3];
		ad[0][1] = i;						//diese reihe, rechts
		ad[1][0] = j;
		ad[1][1] = adjacentsParameters[1]; //row below, diese spalte
		return ad;
	}
}
