import java.awt.Point;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
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
		
    	for (Point point : movesMade)
    	{
    		int numberPiecesFound = 0;
    		int numberSpaces = 0;
    		int currentCol, currentRow;
    		int maxIndexCol = point.x + (state.getkLength() - 1);
    		int maxIndexRow = point.y + (state.getkLength() - 1);
    		int minIndexCol = point.x - (state.getkLength() - 1);
    		int minIndexRow = point.y - (state.getkLength() - 1);
    		boolean isOutOfRange = false;
    		
    		// Go right
    		currentCol = point.x;
    		if (checkOutOfRange(maxIndexCol, state.getWidth()))
				isOutOfRange = true;
			for (; currentCol <= maxIndexCol && !isOutOfRange; currentCol++)
			{
				if (checkOutOfRange(currentCol, state.getWidth()) || state.getSpace(currentCol, point.y) == otherPlayer)
					break;
				else if (state.getSpace(currentCol, point.y) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go left
			currentCol = point.x;
			if (checkOutOfRange(minIndexCol, state.getWidth()))
				isOutOfRange = true;
			for (; currentCol >= minIndexCol && !isOutOfRange; currentCol--)
			{
				if (checkOutOfRange(currentCol, state.getWidth()) || state.getSpace(currentCol, point.y) == otherPlayer)
					break;
				else if (state.getSpace(currentCol, point.y) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go Up
			currentRow = point.y;
			if (checkOutOfRange(maxIndexRow, state.getHeight()))
				isOutOfRange = true;
			for (; currentRow <= maxIndexRow && !isOutOfRange; currentRow++)
			{
				if (checkOutOfRange(currentRow, state.getHeight()) || state.getSpace(point.x, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go down
			currentRow = point.y;
			if (checkOutOfRange(minIndexRow, state.getHeight()))
				isOutOfRange = true;
			for (; currentRow >= minIndexRow && !isOutOfRange; currentRow--)
			{
				if (checkOutOfRange(currentRow, state.getHeight()) || state.getSpace(point.x, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go up right
			currentCol = point.x;
			currentRow = point.y;
			if (checkOutOfRange(maxIndexCol, state.getWidth()) || checkOutOfRange(maxIndexRow, state.getHeight()))
				isOutOfRange = true;
			for (; currentCol <= maxIndexCol && currentRow <= maxIndexRow && !isOutOfRange; currentCol++, currentRow++)
			{
				if (checkOutOfRange(currentRow, state.getHeight()) || checkOutOfRange(currentCol, state.getWidth()) || 
					state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go up left
			currentCol = point.x;
			currentRow = point.y;
			if (checkOutOfRange(minIndexCol, state.getWidth()) || checkOutOfRange(maxIndexRow, state.getHeight()))
				isOutOfRange = true;
			for (; currentCol >= minIndexCol && currentRow <= maxIndexRow && !isOutOfRange; currentCol--, currentRow++)
			{
				if (checkOutOfRange(currentRow, state.getHeight()) || checkOutOfRange(currentCol, state.getWidth()) || 
					state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
			numberPiecesFound = 0;
			numberSpaces = 0;
			isOutOfRange = false;
			
			// Go down right
			currentCol = point.x;
			currentRow = point.y;
			if (checkOutOfRange(maxIndexCol, state.getWidth()) || checkOutOfRange(minIndexRow, state.getHeight()))
				isOutOfRange = true;
			for (; currentCol <= maxIndexCol && currentRow >= minIndexRow && !isOutOfRange; currentCol++, currentRow--)
			{
				if (checkOutOfRange(currentRow, state.getHeight()) || checkOutOfRange(currentCol, state.getWidth()) || 
					state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
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
				if (checkOutOfRange(currentRow, state.getHeight()) || checkOutOfRange(currentCol, state.getWidth()) || 
					state.getSpace(currentCol, currentRow) == otherPlayer)
					break;
				else if (state.getSpace(point.x, currentRow) == 0)
					numberSpaces++;
				else
					numberPiecesFound++;
			}
			totalScore += Math.pow(2, 2*numberPiecesFound) + numberSpaces;
		}
    	return totalScore;
    }
 
    // Get the queue containing OrderMinNode's
    public static PriorityQueue<OrderMinNode> convertToOrderMaxQueue( Map<Point, Integer> availableMoves)
    {
    	PriorityQueue<OrderMinNode> priorityQueue = new PriorityQueue<>(10, new OrderMinNode());
    	
    	for (Map.Entry<Point, Integer> entry : availableMoves.entrySet())
    	{
    		priorityQueue.add(new OrderMinNode(0, entry.getKey(), entry.getValue()));
    	}
    	
    	return priorityQueue;
    }
    
    // Get the queue containing OrderMaxNode's
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