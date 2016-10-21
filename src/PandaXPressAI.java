import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;
import java.util.Map;
import java.util.HashMap;
import java.util.*;
import java.util.PriorityQueue;

public class PandaXPressAI extends CKPlayer 
{
    public PandaXPressAI(byte player, BoardModel state) 
    {
        super(player, state);
        teamName = "PandaXPress";
        otherPlayerValue = getOtherPlayerValue(player);
    }
    
    // Do minimax search to find the best worst move
    private Point implementMinMax(BoardModel state, int plyDepth) {
        if (plyDepth <= 1) { 
            return null;
        }
        Map<Point, Integer> moves = getAvailableMoves(state, otherPlayerValue);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int bestScore = Integer.MIN_VALUE;
        Point bestMove = priorityMoves.peek().point; 
        		
        for (Pair pair : priorityMoves) {
            int score = returnMin(state.clone().placePiece(pair.point, otherPlayerValue), 1, plyDepth);
            if (score > bestScore) {
                bestMove = pair.point;
                bestScore = score;
            }
        }
        return bestMove;      
    }
    
    // Return the minimum value of the generated states
    private int returnMin(BoardModel state, int currentDepth, int plyDepth) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        if (currentDepth == plyDepth) {
            return Utils.boardStateScore(state, player);
        }
        
        Map<Point, Integer> moves = getAvailableMoves(state, player);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int worstScore = Integer.MAX_VALUE;
        
        for (Pair pair : priorityMoves) {
            int score = returnMax(state.clone().placePiece(pair.point, player), currentDepth+1, plyDepth);
            if (score < worstScore) {
                worstScore = score;
            }
        }
        return worstScore; 
    }
    
    // Return the maximum value of the generated states
    private int returnMax(BoardModel state, int currentDepth, int plyDepth) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        if (currentDepth == plyDepth) {
            return Utils.boardStateScore(state, player);
        }
        
        Map<Point, Integer> moves = getAvailableMoves(state, otherPlayerValue);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int bestScore = Integer.MIN_VALUE;
        
        for (Pair pair : priorityMoves) {
            int score = returnMin(state.clone().placePiece(pair.point, otherPlayerValue), currentDepth+1, plyDepth);
            if (score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore; 
    }
    
    // Evaluate the winner
    private int evaluateWinner(byte winner) {
        if (winner == player) {
            return Integer.MAX_VALUE;
        }
        return Integer.MIN_VALUE;
    }
    
    // Get all the avaialbe moves
    private Map<Point, Integer> getAvailableMoves(BoardModel state, byte player) 
    {
    	Map<Point, Integer> availableMoves = new HashMap<Point, Integer>();
    	for (int i = 0; i < state.getWidth(); i++)
    	{
    		for (int j = 0; j < state.getHeight(); j++)
    		{
    			if (state.getSpace(i, j) == player)
    			{
    				Utils.getMovesPriority(state, availableMoves, i, j);
    			}
    		}
    	}
    	return availableMoves;
    }
    
    // Get the value of the other player
    private byte getOtherPlayerValue(byte player) {
        if (player == 1) {
            return 2;
        }
        else return 1;
    }
    
    int count = 0;
    
    
    @Override
    public Point getMove(BoardModel state) 
    {	
//    	return new Point(count++, 0);
    	if (player == 1 && startState == state)
    		return new Point(0,0);
    	else
    		return implementMinMax(state, 3);
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return getMove(state);
    }
    
    private byte otherPlayerValue;
    
}
