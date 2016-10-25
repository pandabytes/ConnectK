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
    private Point alphaBetaPruning(BoardModel state, int plyDepth) {
        if (plyDepth <= 1) { 
            return null;
        }
        Map<Point, Integer> moves = getAvailableMoves(state);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        
        if (priorityMoves.size() == 0)
        	return new Point(0, 0);
        
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Point bestMove = priorityMoves.peek().point; 
        
        for (Pair pair : priorityMoves) {
            int score = returnMin(state.clone().placePiece(pair.point, otherPlayerValue), 1, plyDepth, alpha, beta);
            if (score > alpha) {
                bestMove = pair.point;
                alpha = score;
            }
        }
        return bestMove;      
    }
    
    // Return the minimum value of the generated states
    private int returnMin(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        
        if (currentDepth == plyDepth) {
        	return Utils.numberOfPossibleWins(state, player, otherPlayerValue) - 
        		   Utils.numberOfPossibleWins(state, otherPlayerValue, player);
        }
        
        Map<Point, Integer> moves = getAvailableMoves(state);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int localBeta = Integer.MAX_VALUE;
        
        for (Pair pair : priorityMoves) {
            int score = returnMax(state.clone().placePiece(pair.point, player), currentDepth+1, plyDepth, alpha, localBeta);
            if (score <= alpha) {
            	return alpha;
            }
            if (score < localBeta) {
                localBeta = score;
            }
        }
        return localBeta; 
    }
    
    // Return the maximum value of the generated states
    private int returnMax(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        if (currentDepth == plyDepth) {
        	return Utils.numberOfPossibleWins(state, player, otherPlayerValue) - 
         		   Utils.numberOfPossibleWins(state, otherPlayerValue, player);
        }
        
        Map<Point, Integer> moves = getAvailableMoves(state);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int localAlpha = Integer.MIN_VALUE;
        for (Pair pair : priorityMoves) {
            int score = returnMin(state.clone().placePiece(pair.point, otherPlayerValue), currentDepth+1, 
            		              plyDepth, localAlpha, beta);
            if (score >= beta) {
                return beta;
            }
            if (localAlpha < score) {
            	localAlpha = score;
            }
        }
        return localAlpha; 
    }
    
    // Evaluate the winner
    private int evaluateWinner(byte winner) {
        if (winner == player) {
            return Integer.MAX_VALUE;
        }
        return Integer.MIN_VALUE;
    }
    
    // Get all the available moves
    private Map<Point, Integer> getAvailableMoves(BoardModel state) 
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
    
    @Override
    public Point getMove(BoardModel state) 
    {	
    	return alphaBetaPruning(state, 9);
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return getMove(state);
    }
    
    private byte otherPlayerValue;
}
