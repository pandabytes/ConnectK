import java.awt.Point;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import connectK.BoardModel;

public final class Utils {
	private Utils() {}
    
    // Return true if index goes out of bound
    private static boolean checkOutOfRange(int index, int boardDimension)
    {
        return index > (boardDimension - 1) || index < 0;
    }
    
    // Return the score based on a given board state
    public static int numberInARow(BoardModel state, HashSet<Point> movesMade, byte player, byte otherPlayer)
    {
    	int totalScore = 0;
//    	Comparator<Point> comparator = new Comparator<Point>() {
//    		@Override
//    		public int compare(Point p1, Point p2)
//    		{
//    			Integer x = p1.x;
//    			Integer y = p2.x;
//    			return x.compareTo(y);
//    		}
//    	};
//    	PriorityBlockingQueue<Point> moves = new PriorityBlockingQueue<Point>(10, comparator);
		
    	for (Iterator<Point> iterator = movesMade.iterator(); iterator.hasNext();)
    	{
    		Point point = iterator.next();
    		int numberPiecesFound = 0;
    		int numberSpaces = 0;
    		int currentCol, currentRow;
    		int maxIndexCol = point.x + (state.getkLength() - 1);
    		int maxIndexRow = point.y + (state.getkLength() - 1);
    		int minIndexCol = point.x - (state.getkLength() - 1);
    		int minIndexRow = point.y - (state.getkLength() - 1);
    		boolean isOutOfRange = false;
    		
    		// Go right
    		currentCol = point.x + 1;
			if (checkOutOfRange(maxIndexCol, state.getWidth()))
				isOutOfRange = true;
			
			for (; currentCol <= maxIndexCol && !isOutOfRange; currentCol++)
			{
				if (state.getSpace(currentCol, point.y) == otherPlayer)
					break;
				else if (state.getSpace(currentCol, point.y) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(currentCol, point.y);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go left
			currentCol = point.x - 1;
			if (checkOutOfRange(minIndexCol, state.getWidth()))
				isOutOfRange = true;
			
			for (; currentCol >= minIndexCol && !isOutOfRange; currentCol--)
			{
				if (state.getSpace(currentCol, point.y) == otherPlayer)
					break;
				else if (state.getSpace(currentCol, point.y) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(currentCol, point.y);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go Up
			currentRow = point.x + 1;
			if (checkOutOfRange(maxIndexRow, state.getHeight()))
				isOutOfRange = true;
			
			for (; currentRow <= maxIndexRow && !isOutOfRange; currentRow++)
			{
				if (state.getSpace(point.x, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(point.x, currentRow);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go down
			currentRow = point.y - 1;
			if (checkOutOfRange(minIndexRow, state.getHeight()))
				isOutOfRange = true;
			
			for (; currentRow >= minIndexRow && !isOutOfRange; currentRow--)
			{
				if (state.getSpace(point.x, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(point.x, currentRow);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go up right
			currentCol = point.x + 1;
			currentRow = point.y + 1;
			if (checkOutOfRange(maxIndexCol, state.getWidth()) || checkOutOfRange(maxIndexRow, state.getHeight()))
				isOutOfRange = true;
			
			for (; currentCol <= maxIndexCol && currentRow <= maxIndexRow && !isOutOfRange; currentCol++, currentRow++)
			{
				if (state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(currentCol, currentRow);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go up left
			currentCol = point.x - 1;
			currentRow = point.y + 1;
			if (checkOutOfRange(minIndexCol, state.getWidth()) || checkOutOfRange(maxIndexRow, state.getHeight()))
				isOutOfRange = true;
			
			for (; currentCol >= minIndexCol && currentRow <= maxIndexRow && !isOutOfRange; currentCol--, currentRow++)
			{
				if (state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(currentCol, currentRow);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go down right
			currentCol = point.x + 1;
			currentRow = point.y - 1;
			if (checkOutOfRange(maxIndexCol, state.getWidth()) || checkOutOfRange(minIndexRow, state.getHeight()))
				isOutOfRange = true;
			
			for (; currentCol <= maxIndexCol && currentRow >= minIndexRow && !isOutOfRange; currentCol++, currentRow--)
			{
				if (state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(currentCol, currentRow);
//				movesMade.remove(removedPoint);
			}
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go down left
			currentCol = point.x - 1;
			currentRow = point.y - 1;
			if (checkOutOfRange(minIndexCol, state.getWidth()) || checkOutOfRange(minIndexRow, state.getHeight()))
				isOutOfRange = true;
			
			for (; currentCol >= minIndexCol && currentRow >= minIndexRow && !isOutOfRange; currentCol--, currentRow--)
			{
				if (state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
				
//				Point removedPoint = new Point(currentCol, currentRow);
//				movesMade.remove(removedPoint);
			}
			
			totalScore += Math.pow(2, Math.pow(state.getkLength(), numberPiecesFound)) + numberSpaces;
		}
    	
    	return totalScore;
    }
    
    // Given the current state, determine the number of possible wins for a player
    public static int numberOfPossibleWins(BoardModel state, byte player, byte otherPlayer)
    {
    	int numPaths = 0;
    	int currentCol, currentRow;
		int maxIndexCol, maxIndexRow;
		int minIndexCol, minIndexRow;
		boolean isBadPath = false;
		boolean isOutOfRange = false;
		
    	for (int i = 0; i < state.getWidth(); i++)
    	{
    		for (int j = 0; j < state.getHeight(); j++)
    		{
    			if (state.getSpace(i,j) == player)
    			{
    				maxIndexCol = i + (state.getkLength() - 1);
    				maxIndexRow = j + (state.getkLength() - 1);
    				minIndexCol = i - (state.getkLength() - 1);
    				minIndexRow = j - (state.getkLength() - 1);
    				isBadPath = false;
    				isOutOfRange = false;
    				
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
   
    // 
    public static PriorityQueue<OrderMinNode> convertToOrderMaxQueue( Map<Point, Integer> availableMoves)
    {
    	PriorityQueue<OrderMinNode> priorityQueue = new PriorityQueue<>(10, new OrderMinNode());
    	
    	for (Map.Entry<Point, Integer> entry : availableMoves.entrySet())
    	{
    		priorityQueue.add(new OrderMinNode(0, entry.getKey(), entry.getValue()));
    	}
    	
    	return priorityQueue;
    }
    
    // 
    public static PriorityQueue<OrderMaxNode> convertToOrderMinQueue( Map<Point, Integer> availableMoves)
    {
    	PriorityQueue<OrderMaxNode> priorityQueue = new PriorityQueue<>(10, new OrderMaxNode());
    	
    	for (Map.Entry<Point, Integer> entry : availableMoves.entrySet())
    	{
    		priorityQueue.add(new OrderMaxNode(0, entry.getKey(), entry.getValue()));
    	}
    	
    	return priorityQueue;
    }
    
    // Get the priority of each move
    public static void getMovesPriority(BoardModel state, Map<Point, Integer> availableMoves, int i, int j)
    {
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
}