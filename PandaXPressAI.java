import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;
import java.util.*;

public class PandaXPressAI extends CKPlayer 
{
	public PandaXPressAI(byte player, BoardModel state) 
	{
		super(player, state);
		teamName = "PandaXPress";
		count = 0;
		emptySlots = new LinkedList<Point>();
	}
	
	// Method use to compute the score of a direction
	int computeDirectionScore(int leftoverPieces, boolean reachOtherPlayer, boolean reachBorder)
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
    boolean checkOutOfRange(int index, int boardDimension)
    {
    	return index > (boardDimension - 1) || index < 0;
    }
    
    // Check in the right direction
    private int scoreR(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreL(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreU(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreD(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreUR(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreUL(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreDL(int currentCol, int currentRow, BoardModel currentState) {
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
    private int scoreDR(int currentCol, int currentRow, BoardModel currentState) {
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
    
	@Override
	public Point getMove(BoardModel state) 
	{
		for (int i=0; i < state.getWidth(); ++i)
		{
			for (int j=0; j < state.getHeight(); ++j)
			{
				if (state.getSpace(i, j) == 0)
				{
					emptySlots.add(new Point(i, j));
					count++;
				}
			}
		}
		
		// If count is 0 return null
		if (count != 0)
		{
			int randomIndex = rand.nextInt(count);
			Point move = emptySlots.get(randomIndex);
	
			emptySlots.clear();
			count = 0;
			System.out.println("Down Right Score is: " + scoreDR(move.x, move.y, state));
			return move;
		}
		
		return null;
	}

	@Override
	public Point getMove(BoardModel state, int deadline) 
	{
		return getMove(state);
	}
	
	private List<Point> emptySlots;
	private int count;
	private static Random rand = new Random();
	
}
