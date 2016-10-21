import java.awt.Point;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

import connectK.BoardModel;

public final class Utils {
	private Utils() {}
	
    // Method use to compute the score of a board state
    public static int boardStateScore(BoardModel currentState, byte player) {
        int score = 0;
        for (int i=0; i < currentState.getWidth(); ++i) {
            for (int j=0; j < currentState.getHeight(); ++j) {
                if (currentState.getSpace(i,j) == player) {
                    score += Utils.scoreR(i, j, currentState, player);
                    score += Utils.scoreL(i, j, currentState, player);
                    score += Utils.scoreU(i, j, currentState, player);
                    score += Utils.scoreD(i, j, currentState, player);
                    score += Utils.scoreUR(i, j, currentState, player);
                    score += Utils.scoreUL(i, j, currentState, player);
                    score += Utils.scoreDR(i, j, currentState, player);
                    score += Utils.scoreDL(i, j, currentState, player);
                }
            }
        }
        return score;
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
    	int steps = Math.floorDiv(state.getkLength(), 2);
    	
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
    
	// Method use to compute the score of a direction
	private static int computeDirectionScore(int leftoverPieces, boolean reachOtherPlayer, boolean reachBorder)
	{
		// Check if it encounters an opposing piece
        if (reachOtherPlayer) 
            return -128 / (int)Math.pow(2, 6 - leftoverPieces); 
       
        // Check if it reaches to the border the board
        if (reachBorder)
        	return -128 / (int)Math.pow(4,  3 - leftoverPieces);
        
        return 128 / (int)Math.pow(2, leftoverPieces);
	}
    
    // Return true if index goes out of bound
    private static boolean checkOutOfRange(int index, int boardDimension)
    {
    	return index > (boardDimension - 1) || index < 0;
    }
    
    // Check in the right direction
    private static int scoreR(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndex = currentCol + (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentCol++;
        
        // Go right
        while (currentCol <= kIndex) {
        	if (checkOutOfRange(currentCol, currentState.getWidth()))
        	{
        		reachBorder = true;
        		break;
        	}
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
            currentCol++;
        }
        
        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
    
    // Check in the left direction
    private static int scoreL(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndex = currentCol - (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentCol--;
        
        // Go left
        while (currentCol >= kIndex){
        	if (checkOutOfRange(currentCol, currentState.getWidth()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
            currentCol--;
        }

        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
    
    // Check in the up direction
    private static int scoreU(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndex = currentRow + (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentRow++;
        
        // Go up
        while (currentRow <= kIndex){
        	if (checkOutOfRange(currentRow, currentState.getHeight()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
            currentRow++;
        }

        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
    
    // Check in the down direction
    private static int scoreD(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndex = currentRow - (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentRow--;
        
        // Go up
        while (currentRow >= kIndex){
        	if (checkOutOfRange(currentRow, currentState.getHeight()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
            currentRow--;
        }

        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
    
    // Check in the up right direction
    private static int scoreUR(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndexCol = currentCol + (currentState.getkLength() - 1);
        int kIndexRow = currentRow + (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentCol++;
        currentRow++;
        
        // Go up right
        while (currentCol <= kIndexCol && currentRow <= kIndexRow){
        	if (checkOutOfRange(currentRow, currentState.getHeight()) || 
        		checkOutOfRange(currentCol, currentState.getWidth()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
        	currentCol++;
            currentRow++;
        }

        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
	
    // Check in the up left direction
    private static int scoreUL(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndexCol = currentCol - (currentState.getkLength() - 1);
        int kIndexRow = currentRow + (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentCol--;
        currentRow++;
        
        // Go up left
        while (currentCol >= kIndexCol && currentRow <= kIndexRow){
        	if (checkOutOfRange(currentRow, currentState.getHeight()) || 
        		checkOutOfRange(currentCol, currentState.getWidth()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
        	currentCol--;
            currentRow++;
        }

        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }

    // Check in the down left direction
    private static int scoreDL(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndexCol = currentCol - (currentState.getkLength() - 1);
        int kIndexRow = currentRow - (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentCol--;
        currentRow--;
        
        // Go down left
        while (currentCol >= kIndexCol && currentRow >= kIndexRow){
        	if (checkOutOfRange(currentRow, currentState.getHeight()) || 
        		checkOutOfRange(currentCol, currentState.getWidth()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
        	currentCol--;
            currentRow--;
        }

        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
    
    // Check in the down right direction
    private static int scoreDR(int currentCol, int currentRow, BoardModel currentState, byte player) {
        int leftoverPieces = 0;
        int kIndexCol = currentCol + (currentState.getkLength() - 1);
        int kIndexRow = currentRow - (currentState.getkLength() - 1);
        boolean reachOtherPlayer = false;
        boolean reachBorder = false;
        currentCol++;
        currentRow--;
        
        // Go down right
        while (currentCol <= kIndexCol && currentRow >= kIndexRow){
        	if (checkOutOfRange(currentRow, currentState.getHeight()) || 
        		checkOutOfRange(currentCol, currentState.getWidth()))
        	{
        		reachBorder = true;
        		break;
        	}        	
        	else if (currentState.getSpace(currentCol, currentRow) == 0) {
                leftoverPieces++;
            }
            else if (currentState.getSpace(currentCol, currentRow) != player) {
            // If it's the other player, then go to this statement    
                reachOtherPlayer = true;
                break;
            }
        	currentCol++;
            currentRow--;
        }
       
        return computeDirectionScore(leftoverPieces, reachOtherPlayer, reachBorder);
    }
}
