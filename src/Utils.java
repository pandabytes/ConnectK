import java.awt.Point;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

import connectK.BoardModel;

public final class Utils {
	private Utils() {}
    
    // Given the current state, determine the number of possible wins
    public static int numberOfPossibleWins(BoardModel state, byte player, byte otherPlayer)
    {
    	int numPaths = 0;;
    	for (int i = 0; i < state.getWidth(); i++)
    	{
    		for (int j = 0; j < state.getHeight(); j++)
    		{
    			if (state.getSpace(i,j) == player)
    			{
    				int currentCol;
    				int currentRow;
    				int maxIndexCol = i + (state.getkLength() - 2);
    				int maxIndexRow = j + (state.getkLength() - 2);
    				int minIndexCol = i - (state.getkLength() - 2);
    				int minIndexRow = j - (state.getkLength() - 2);
    				boolean isBadPath = false;
    				boolean isOutOfRange = false;
    				
    				// Go right
    				currentCol = i + 1;
    				if (checkOutOfRange(maxIndexCol, state.getWidth()))
    					isOutOfRange = true;
    				
    				while (currentCol <= maxIndexCol && !isOutOfRange)
    				{
    					if (state.getSpace(currentCol, j) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentCol++;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go left
    				currentCol = i - 1;
    				if (checkOutOfRange(minIndexCol, state.getWidth()))
    					isOutOfRange = true;
    				
    				while (currentCol >= minIndexCol && !isOutOfRange)
    				{
    					if (state.getSpace(currentCol, j) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentCol--;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go up
    				currentRow = j + 1;
    				if (checkOutOfRange(maxIndexRow, state.getHeight()))
    					isOutOfRange = true;
    				
    				while (currentRow <= maxIndexRow && !isOutOfRange)
    				{
    					if (state.getSpace(i, currentRow) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentRow++;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go down
    				currentRow = j - 1;
    				if (checkOutOfRange(minIndexRow, state.getHeight()))
    					isOutOfRange = true;
    				
    				while (currentRow >= minIndexRow && !isOutOfRange)
    				{
    					if (state.getSpace(i, currentRow) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentRow--;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go up right
    				currentCol = i + 1;
    				currentRow = j + 1;
    				if (checkOutOfRange(maxIndexCol, state.getWidth()) || checkOutOfRange(maxIndexRow, state.getHeight()))
    					isOutOfRange = true;
    				
    				while (currentCol <= maxIndexCol && currentRow <= maxIndexRow && !isOutOfRange)
    				{
    					if (state.getSpace(currentCol, currentRow) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentCol++;
    					currentRow++;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go up left
    				currentCol = i - 1;
    				currentRow = j + 1;
    				if (checkOutOfRange(minIndexCol, state.getWidth()) || checkOutOfRange(maxIndexRow, state.getHeight()))
    					isOutOfRange = true;
    				
    				while (currentCol >= minIndexCol && currentRow <= maxIndexRow && !isOutOfRange)
    				{
    					if (state.getSpace(currentCol, currentRow) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentCol--;
    					currentRow++;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go down right
    				currentCol = i + 1;
    				currentRow = j - 1;
    				if (checkOutOfRange(maxIndexCol, state.getWidth()) || checkOutOfRange(minIndexRow, state.getHeight()))
    					isOutOfRange = true;
    				
    				while (currentCol <= maxIndexCol && currentRow >= minIndexRow && !isOutOfRange)
    				{
    					if (state.getSpace(currentCol, currentRow) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentCol++;
    					currentRow--;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    				
    				// Go down left
    				currentCol = i - 1;
    				currentRow = j - 1;
    				if (checkOutOfRange(minIndexCol, state.getWidth()) || checkOutOfRange(minIndexRow, state.getHeight()))
    					isOutOfRange = true;
    				
    				while (currentCol >= minIndexCol && currentRow >= minIndexRow && !isOutOfRange)
    				{
    					if (state.getSpace(currentCol, currentRow) == otherPlayer)
    					{
    						isBadPath = true;
    						break;
    					}
    					currentCol--;
    					currentRow--;
    				}
    				
    				if (!isBadPath && !isOutOfRange)
    				{
    					numPaths++;
    				}
    				
    				isBadPath = false;
    				isOutOfRange = false;
    			}
    		}
    	}
    	
    	return numPaths;
    }
    
    // Sort the moves based on their priority
    public static PriorityQueue<Pair> convertToPriorityQueue( Map<Point, Integer> availableMoves)
    {
    	PriorityQueue<Pair> priorityQueue = new PriorityQueue<Pair>(10, new Pair());
    	
    	for (Map.Entry<Point, Integer> entry : availableMoves.entrySet())
    	{
    		Pair p = new Pair(entry.getKey(), entry.getValue());
    		priorityQueue.add(p);
    	}
    	
    	return priorityQueue;
    }
    
    // Get the priority of each move
    public static void getMovesPriority(BoardModel state, Map<Point, Integer> availableMoves, int i, int j)
    {
    	//int steps = Math.floorDiv(state.getkLength(), 2);
    	int steps = 1;
    	
    	// Go right
		for (int k = 1; k < steps + 1; k++)
		{
			if (!checkOutOfRange(i + k, state.getWidth()) && state.getSpace(i + k, j) == 0)
			{
				Point p = new Point(i + k, j);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go left
		for (int k = 1; k < steps + 1; k++)
		{
			if (!checkOutOfRange(i - k, state.getWidth()) && state.getSpace(i - k, j) == 0)
			{
				Point p = new Point(i - k, j);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go up
		for (int k = 1; k < steps + 1; k++)
		{
			if (!checkOutOfRange(j + k, state.getHeight()) && state.getSpace(i, j + k) == 0)
			{
				Point p = new Point(i, j + k);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go down
		for (int k = 1; k < steps + 1; k++)
		{
			if (!checkOutOfRange(j - k, state.getHeight()) && state.getSpace(i, j - k) == 0)
			{
				Point p = new Point(i, j - k);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go up right
		for (int k = 1; k < steps + 1; k++)
		{
			if ( !(checkOutOfRange(i + k, state.getWidth()) || checkOutOfRange(j + k, state.getHeight())) &&
				   state.getSpace(i + k, j + k) == 0)
			{
				Point p = new Point(i + k, j + k);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go up left
		for (int k = 1; k < steps + 1; k++)
		{
			if ( !(checkOutOfRange(i - k, state.getWidth()) || checkOutOfRange(j + k, state.getHeight())) &&
				   state.getSpace(i - k, j + k) == 0)
			{
				Point p = new Point(i - k, j + k);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go down left
		for (int k = 1; k < steps + 1; k++)
		{
			if ( !(checkOutOfRange(i - k, state.getWidth()) || checkOutOfRange(j - k, state.getHeight())) &&
				   state.getSpace(i - k, j - k) == 0)
			{
				Point p = new Point(i - k, j - k);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
		
		// Go down right
		for (int k = 1; k < steps + 1; k++)
		{
			if ( !(checkOutOfRange(i + k, state.getWidth()) || checkOutOfRange(j - k, state.getHeight())) &&
				   state.getSpace(i + k, j - k) == 0)
			{
				Point p = new Point(i + k, j - k);
				if (availableMoves.containsKey(p))
					availableMoves.put(p, availableMoves.get(p) + 1);
				else
					availableMoves.put(p, 1);
			}
		}
    }
    
    // Return true if index goes out of bound
    private static boolean checkOutOfRange(int index, int boardDimension)
    {
    	return index > (boardDimension - 1) || index < 0;
    }
}
