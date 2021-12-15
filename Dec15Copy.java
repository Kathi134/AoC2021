package Raetsel2021;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dec15Copy extends Puzzle
{
	private int erg1 = 0;
	ArrayList<Node> information = new ArrayList<>();
	
	public Dec15Copy() throws IOException
	{
		f = new File("src\\Raetsel2021\\inputs\\Dec" + 15);
		prepare();
		readInput("charTable");
		
		solveTask1();
		solveTask2();
		System.out.println("Task 1 -- " + erg1);
		System.out.println("Task 2 -- ");
	}
	
	public void solveTask1()
	{
		initializeList();
			
		int cnt = 0;
		while(information.size()!=0)  //es ist ncoh was drin
		{
			dijkstra();
			//System.err.println(cnt);
			cnt++;
		}
	}
	
	public void initializeList() 
	{
		for(int i=0; i<inputCharTable.length; i++)
		{
			for(int j=0; j<inputCharTable[i].length; j++)
			{
				information.add(new Node(i, j));
			}
		}
		information.get(0).setGesamtDistanz(0);
	}
	
	public void dijkstra()
	{
		Node currNode = information.remove(0); //entferne knoten mit niedrigster gesdis aus der liste
		System.err.println(currNode.name);
		if(currNode.name.equals("[" + 99 + "][" + 99 + "]"))
		{
			erg1 = currNode.getGesamtDistanz();
		}
		else
		{
			int[][] neighbours = getAdjacentParameters(currNode.yPos, currNode.xPos, "charTable");
			
			for(int[] p: neighbours) // p hat [x][y]
			{
				String newName = "[" + p[1] + "][" + p[0] + "]";
				int pos = posInList(newName);
				if(pos != -1) //falls zum nachbar-knoten noch nicht der kleinste weg gefunden wurde
				{
					Node currNeighbour = information.get(pos);
					int newGesDis = currNode.getGesamtDistanz() + Character.getNumericValue(inputCharTable[p[1]][p[0]]);
					if(currNeighbour.getGesamtDistanz() > newGesDis)
					{
						currNeighbour.setGesamtDistanz(newGesDis);
						currNeighbour.setVorgaenger(currNode);
					}
					//System.err.print(currNeighbour.getGesamtDistanz() + " ");
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
	
	public int posInList(String name)
	{
		for(int i=0; i<information.size(); i++)
		{
			if(information.get(i).name.equals(name))
			{
				return i;
			}
		}
		return -1;
	}
	
	public void solveTask2()
	{
		
	}
	
	public static void main(String[] args) throws IOException
	{
		@SuppressWarnings("unused")
		Dec15Copy d = new Dec15Copy();
	}
}

class Node
{
	public final String name; //setzt sich zsm aus [y][x]
	public final int xPos;
	public final int yPos;
	private Node vorgaenger;
	private int gesamtDistanz; 
	
	public Node(int yPos, int xPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		name = "[" + yPos + "][" + xPos + "]";
		vorgaenger = null;
		gesamtDistanz = Integer.MAX_VALUE;
	}

	public Node getVorgaenger()
	{
		return vorgaenger;
	}

	public void setVorgaenger(Node vorgaenger)
	{
		this.vorgaenger = vorgaenger;
	}

	public int getGesamtDistanz()
	{
		return gesamtDistanz;
	}

	public void setGesamtDistanz(int gesamtDistanz)
	{
		this.gesamtDistanz = gesamtDistanz;
	}
}
