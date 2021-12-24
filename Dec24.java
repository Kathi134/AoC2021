package Raetsel2021;

import java.io.File;
import java.io.IOException;

public class Dec24 extends Puzzle
{
	int w;
	int x; 
	int y;
	int z;
	int[] inputData = new int[14];
	int cntInpLoads = 0; //der nächste zu adressierende index in inputData
	long erg;
	
	public Dec24() throws IOException
	{
		f = new File("src\\Raetsel2021\\inputs\\Dec" + 24);
		prepare();
		readInput("StringTable divBy  ");
		printStringTable();
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- " + erg);
		System.out.println("Task 2 -- ");
	}
	
	public void solveTask1()
	{
		long modelNumber = 99999999999999L;
		for(int i=0; i<inputData.length; i++) //store in inputData
		{
			inputData[i] = 9;
		}
		
		while (runProgramm() != 0)
		{
			System.err.println(modelNumber);
			String tmp;
			do //keine nullen im inputData
			{
				modelNumber--;
				tmp = modelNumber + "";
			}
			while(tmp.contains("0"));
			
			for(int i=0; i<tmp.length(); i++) //store in inputData
			{
				inputData[i] = tmp.charAt(i);
			}
			
			cntInpLoads = 0;
			w = 0; x = 0; y = 0; z = 0;
		}
		
		erg = modelNumber;
	}
	
	//runs programm on current inputData status
	public int runProgramm()
	{
		for(int i=0; i<inputStringTable.length; i++)
		{
			String command = inputStringTable[i][0];
			String storagePlace = inputStringTable[i][1];
			String parameter = inputStringTable[i].length==3 ? inputStringTable[i][2] : "";
			
			switch (command)
			{
			case "inp": inp(storagePlace); break;
			case "add": add(storagePlace, parameter); break;
			case "mul": mul(storagePlace, parameter); break;
			case "div": div(storagePlace, parameter); break;
			case "mod": mod(storagePlace, parameter); break;
			case "eql": eql(storagePlace, parameter); break;
			}	
		}
		int result = z;
		return result;
	}
	
	//inp a -> loadi value, store a
	public void inp(String place)
	{
		 int value = inputData[cntInpLoads];
		 cntInpLoads++;
		 store(place, value);
	}
	
	//add a b -> load a, add b, store a
	public void add(String place, String parameter)
	{
		int a = load(place);
		int b;
		if (Character.isAlphabetic(parameter.charAt(0)))
		{
			b = load(parameter);
		}
		else
		{
			b = Integer.parseInt(parameter);
		}
		int result = a+b;
		store(place, result);
	}
	
	//mul a b -> loas a, mul b, store a
	public void mul(String place, String parameter)
	{
		int a = load(place);
		int b;
		if (Character.isAlphabetic(parameter.charAt(0)))
		{
			b = load(parameter);
		}
		else
		{
			b = Integer.parseInt(parameter);
		}
		int result = a*b;
		store(place, result);
	}
	
	//div a b -> load a, div b, store a //ganzzahlig
	public void div(String place, String parameter)
	{
		int a = load(place);
		int b;
		if (Character.isAlphabetic(parameter.charAt(0)))
		{
			b = load(parameter);
		}
		else
		{
			b = Integer.parseInt(parameter);
		}
		int result = a/b;
		store(place, result);
	}
	
	//mod a b -> load a, mod b, store a
	public void mod(String place, String parameter)
	{
		int a = load(place);
		int b;
		if (Character.isAlphabetic(parameter.charAt(0)))
		{
			b = load(parameter);
		}
		else
		{
			b = Integer.parseInt(parameter);
		}
		int result = a%b;
		store(place, result);
	}
	
	//eql a b -> load a, sub b, jmpz loadi 1 | jmpnz >0, store a
	public void eql(String place, String parameter)
	{
		int a = load(place);
		int b;
		if (Character.isAlphabetic(parameter.charAt(0)))
		{
			b = load(parameter);
		}
		else
		{
			b = Integer.parseInt(parameter);
		}
		int result = a==b ? 1 : 0;
		store(place, result);
	}
	
	public void store(String place, int value)
	{
		switch (place)
		{
		case "w": w = value; break;
		case "x": x = value; break;
		case "y": y = value; break;
		case "z": z = value; break;
		}
	}
	
	public int load(String place)
	{
		switch (place)
		{
		case "w": return w;
		case "x": return x;
		case "y": return y;
		case "z": return z;
		}
		return -1;
	}
	
	
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args) throws IOException
	{
		new Dec24();
	}
	
}
