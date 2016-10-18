import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;
import java.util.*;

public class PandaXPressAI extends CKPlayer {
	
	private byte otherPlayerValue;
	
    public PandaXPressAI(byte player, BoardModel state) {
        super(player, state);
        teamName = "PandaXPress";
        otherPlayerValue = getOtherPlayerValue(player);
    }
    
    // Method use to compute the score of a direction
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
    
    
    private int boardStateScore(BoardModel currentState) {
        int score = 0;
        for (int i=0; i < currentState.getWidth(); ++i) {
            for (int j=0; j < currentState.getHeight(); ++i) {
                if (currentState.getSpace(i,j) == player) {
                    score += scoreR(i, j, currentState);
                    score += scoreL(i, j, currentState);
                    score += scoreU(i, j, currentState);
                    score += scoreD(i, j, currentState);
                    score += scoreUR(i, j, currentState);
                    score += scoreUL(i, j, currentState);
                    score += scoreDR(i, j, currentState);
                    score += scoreDL(i, j, currentState);
                }
            }
        }
        return score;
    }
    
    private Point implementMinMax(BoardModel state) {
        Vector<Point> moves = getAvailableMoves(state);
        Point bestMove = moves.get(0);
        int bestScore = Integer.MIN_VALUE;
        for (Point move:moves) {
        	int score = returnMin(state.clone().placePiece(move, otherPlayerValue));
        	if (score > bestScore) {
        		bestMove = move;
        		bestScore = score;
        	}
        }
        return bestMove;      
    }
    
    private int returnMin(BoardModel state) {
    	if (state.winner() != -1) {
    		return evaluateWinner(state.winner());
    	}
        Vector<Point> moves = getAvailableMoves(state);
        int worstScore = Integer.MAX_VALUE;
        for (Point move:moves) {
        	int score = returnMax(state.clone().placePiece(move, player));
        	if (score < worstScore) {
        		worstScore = score;
        	}
        }
        return worstScore; 
    }
    
    private int returnMax(BoardModel state) {
    	if (state.winner() != -1) {
    		return evaluateWinner(state.winner());
    	}
        Vector<Point> moves = getAvailableMoves(state);
        int bestScore = Integer.MIN_VALUE;
        for (Point move:moves) {
        	int score = returnMin(state.clone().placePiece(move, otherPlayerValue));
        	if (score > bestScore) {
        		bestScore = score;
        	}
        }
        return bestScore; 
    }
    
    private int evaluateWinner(byte winner) {
    	if (winner == player) {
    		return Integer.MAX_VALUE;
    	}
    	return Integer.MIN_VALUE;
    }
    
    private Vector<Point> getAvailableMoves(BoardModel state) {
          return null;
    }
    
    private byte getOtherPlayerValue(byte player) {
    	if (player == 1) {
    		return 2;
    	}
    	else return 1;
    }
    
    
    
    @Override
    public Point getMove(BoardModel state) 
    {    
        return implementMinMax(state);
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return getMove(state);
    }
    
}
