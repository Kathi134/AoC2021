package Raetsel2021;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dec15 extends Puzzle
{
	private long erg = 0;
	ArrayList<Node> information = new ArrayList<>();
	private int endPosX;
	private int endPosY;
	
	private int[][][] mapComponents;
	private int[][] filledMap;
	
 	public Dec15() throws IOException
	{
		f = new File("src\\Raetsel2021\\inputs\\Dec" + 15);
		prepare();
		readInput("intTable");
		mapComponents = new int[9][lines][columns];
		
		solveTask1();
		long erg1 = erg;
		solveTask2();
		System.out.println("Task 1 -- " + erg1);
		System.out.println("Task 2 -- " + erg);
	}
	
	public void solveTask1()
	{
		erg = 0;
		endPosX = inputIntTable[0].length-1;
		endPosY = inputIntTable.length-1;
		initializeList();
		
		while(information.size()!=0)  //es ist ncoh was drin
		{
			dijkstra();
		}
	}
	
	public void initializeList() 
	{
		for(int i=0; i<inputIntTable.length; i++)
		{
			for(int j=0; j<inputIntTable[i].length; j++)
			{
				information.add(new Node(i, j));
			}
		}
		information.get(0).setGesamtDistanz(0);
	}
	
	public void dijkstra()
	{
		Node currNode = information.remove(0); //entferne knoten mit niedrigster gesdis aus der liste
		System.err.println(currNode.yPos + "|" + currNode.xPos);
		
		if(currNode.xPos == endPosX && currNode.yPos == endPosY)
		{
			erg = currNode.getGesamtDistanz();
		}
		else
		{
			ArrayList<int[]> neighbours = getDirectAdjacents(currNode.yPos, currNode.xPos, "intTable");
			
			for(int[] p: neighbours) // p hat [y][x]
			{
				int pos = posInList(p[0], p[1]);
				if(pos != -1) //falls zum nachbar-knoten noch nicht der kleinste weg gefunden wurde
				{
					Node currNeighbour = information.get(pos);
					long newGesDis = currNode.getGesamtDistanz() + inputIntTable[p[0]][p[1]];
					if(currNeighbour.getGesamtDistanz() > newGesDis)
					{
						currNeighbour.setGesamtDistanz(newGesDis);
					}
				}
			}
		}
		//sortInformation();
	}
	
	//sortiert die Liste nach gesamt distanz absteigend
/*	public void sortInformation()
	{
		while(isUnsorted())
		{
			for(int i=0; i<information.size()-1; i++)
			{
				int currValue = information.get(i).getGesamtDistanz();
				int nextValue = information.get(i+1).getGesamtDistanz();
				if(currValue > nextValue)
				{
					Node firstNode = information.get(i+1);
					Node secondNode = information.get(i);
					information.set(i, firstNode);
					information.set(i+1, secondNode);
				}
			}
			
		}
	}
	
	public boolean isUnsorted()
	{
		//returnt true, falls ein nachfolger einen kleineren Wert hat
		for(int i=0; i<information.size()-1; i++)
		{
			int currValue = information.get(i).getGesamtDistanz();
			int nextValue = information.get(i+1).getGesamtDistanz();
			if(currValue > nextValue)
			{
				return true;
			}
		}
		return false;
	}*/
	
	public int posInList(int posY, int posX)
	{
		for(int i=0; i<information.size(); i++)
		{
			if(information.get(i).xPos == posX && information.get(i).yPos == posY)
			{
				return i;
			}
		}
		return -1;
	}
	
	
	public void solveTask2()
	{
		extendInput();
		//pseudocode: inputIntTable = filledMap
		inputIntTable = clone(filledMap);
		solveTask1();
		print(inputIntTable);
	}
	
	/* gibt die komponenten für die neue erweiterte map
	   00 01 02 03 04 
	   01 02 03 04 05 
	   02 03 04 05 06 
	   03 04 05 06 07
	   04 05 06 07 08 */
	public void extendInput()
	{
		mapComponents[0] = inputIntTable;
		for (int i=1; i<=8; i++)
		{
			mapComponents[i] = computeIncrement(mapComponents[i-1]);
		}
		fillMap();
	}
	
	/* baut die neue erweiterte map
	   00 01 02 03 04 
	   05 06 07 08 09 
	   10 11 12 13 14 
	   15 16 17 18 19
	   20 21 22 23 24 */
	public void fillMap()
	{
		filledMap = new int[lines*5][columns*5];
		
		for(int cntC=0; cntC<5; cntC++)
		{
			for(int cntL=0; cntL<5; cntL++)
			{
				for(int i=lines*cntL; i<lines*(cntL+1); i++)
				{
					for(int j=columns*cntC; j<columns*(cntC+1); j++)
					{
						filledMap[i][j] = mapComponents[cntL+cntC][i%lines][j%columns];
					}
				}
			}
		}

		//print(filledMap);
	}
	
	public int[][] computeIncrement(int[][] predecessor)
	{
		int[][] incremented = new int[predecessor.length][predecessor[0].length];
		
		for(int i=0; i<incremented.length; i++)
		{
			for(int j=0; j<incremented.length; j++)
			{
				int value = predecessor[i][j] + 1;
				if (value==10)
				{
					value=1;
				}
				incremented[i][j] = value;
			}
		}
		return incremented;
	}
	
	public void print(int[][] arr)
	{

		for(int j=0; j<arr.length; j++)
		{
			for (int i=0; i<arr[0].length; i++)
			{
				System.err.printf(arr[j][i] + "");
			}
			System.err.println();
		}
		System.err.println();
	}
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec15 d = new Dec15();
	}
}

class Node
{
	public final int xPos;
	public final int yPos;
	private long gesamtDistanz; 
	
	public Node(int yPos, int xPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		gesamtDistanz = Long.MAX_VALUE;
	}

	public long getGesamtDistanz()
	{
		return gesamtDistanz;
	}

	public void setGesamtDistanz(long gesamtDistanz)
	{
		this.gesamtDistanz = gesamtDistanz;
	}
}
