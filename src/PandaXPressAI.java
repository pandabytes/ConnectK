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
        movesMade = new HashSet<>();
        opponentMoves = new HashSet<>();
    }    
    
    // Do minimax search to find the best worst move
    private Point alphaBetaPruning(BoardModel state, int plyDepth) {
        if (plyDepth <= 1) { 
            return null;
        }
        
        Point opp = state.getLastMove();
        if (opp != null) {
        	opponentMoves.add(opp);
        }
        Map<Point, Integer> moves = getAvailableMoves(state, movesMade);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        if (movesMade.size() == 0) {
        	Point p = new Point(state.getWidth() / 2, state.getHeight() / 2);
        	if (state.getSpace(p) != 0) {
        		p.x = p.x+1;
        		p.y = p.y+1;
        	}
        	movesMade.add(p);
        	return p;
        }
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Point bestMove = priorityMoves.peek().point; 
        
        for (Pair pair : priorityMoves) {
        	HashSet<Point> myM = new HashSet<>(movesMade);
        	myM.add(pair.point);
            int score = returnMin(state.clone().placePiece(pair.point, player), 1, plyDepth, 
            		alpha, beta, opponentMoves, myM);
//            System.out.println(score);
            if (score > alpha) {
                bestMove = pair.point;
                alpha = score;
            }
        }
        movesMade.add(bestMove);
        return bestMove;      
    }
    
    // Return the minimum value of the generated states
    private int returnMin(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta, HashSet<Point> oppM, HashSet<Point> myM) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        
        if (currentDepth == plyDepth) {
        	return Utils.numberOfPossibleWins(state, player, otherPlayerValue) - 
        		   Utils.numberOfPossibleWins(state, otherPlayerValue, player);
        }
        Map<Point, Integer> moves = getAvailableMoves(state, oppM);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int localBeta = Integer.MAX_VALUE;
        
        for (Pair pair : priorityMoves) {
        	HashSet<Point> oppMoves = new HashSet<>(oppM);
        	oppMoves.add(pair.point);
            int score = returnMax(state.clone().placePiece(pair.point, otherPlayerValue), currentDepth+1, plyDepth, alpha, localBeta, oppMoves, myM);
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
    private int returnMax(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta, HashSet<Point> oppM, HashSet<Point> myM) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        if (currentDepth == plyDepth) {
        	return Utils.numberOfPossibleWins(state, player, otherPlayerValue) - 
         		   Utils.numberOfPossibleWins(state, otherPlayerValue, player);
        }
        
        Map<Point, Integer> moves = getAvailableMoves(state, myM);
        PriorityQueue<Pair> priorityMoves = Utils.convertToPriorityQueue(moves);
        int localAlpha = Integer.MIN_VALUE;
        for (Pair pair : priorityMoves) {
        	HashSet<Point> myMoves = new HashSet<>(myM);
        	myMoves.add(pair.point);
        	int score = returnMin(state.clone().placePiece(pair.point, player), currentDepth+1, 
            		              plyDepth, localAlpha, beta, oppM, myMoves);
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
    private Map<Point, Integer> getAvailableMoves(BoardModel state, HashSet<Point> p) 
    {
    	Map<Point, Integer> availableMoves = new HashMap<Point, Integer>();
    	for (Point move: p) {
    		Utils.getMovesPriority(state, availableMoves, move.x, move.y);
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
    	return alphaBetaPruning(state, 6);
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return getMove(state);
    }
    
    private byte otherPlayerValue;
    private HashSet<Point> movesMade;
    private HashSet<Point> opponentMoves;
}
