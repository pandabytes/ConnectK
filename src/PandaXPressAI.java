import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PandaXPressAI extends CKPlayer 
{
	// Constructor
    public PandaXPressAI(byte player, BoardModel state) 
    {
        super(player, state);
        teamName = "PandaXPress";
        otherPlayerValue = getOtherPlayerValue(player);
        movesMade = new HashSet<>();
        opponentMoves = new HashSet<>();
    }  
    
    // Do alpha beta pruning to find the best worst move
    public Point alphaBetaPruning(BoardModel state, int plyDepth, OrderMaxNode parent) {
        if (plyDepth <= 1) { 
            return null;
        }
        Point opp = state.getLastMove();
        if (opp != null) {
        	opponentMoves.add(opp);
        }
       
        if (movesMade.size() == 0) {
        	Point p = new Point(state.getWidth() / 2, state.getHeight() / 2);
        	if (state.getSpace(p) != 0) {
        		p.x = p.x+1;
        		p.y = p.y+1;
        	}
        	
        	movesMade.add(p);
        	return p;
        }
        
        if (parent.orderMinNode_queue == null) {
	       	Map<Point, Integer> moves = getAvailableMoves(state, movesMade, opponentMoves);
	       	parent.orderMinNode_queue = Utils.convertToOrderMaxQueue(moves);
        }
        
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Point bestMove = parent.orderMinNode_queue.peek().point;
        
        PriorityQueue<OrderMinNode> copy = new PriorityQueue<>(parent.orderMinNode_queue);
        OrderMinNode order = null;
        
        while(!copy.isEmpty()) {
        	order = copy.remove();
        	HashSet<Point> myM = new HashSet<>(movesMade);
        	myM.add(order.point);
            int score = returnMin(state.clone().placePiece(order.point, player), 1, plyDepth, 
            		alpha, beta, opponentMoves, myM, order);
            
            if (score > alpha) {
                bestMove = order.point;
                alpha = score;
            }
        }        
        movesMade.add(bestMove);
//        System.out.println("at the end # of children: " + parent.orderMinNode_queue.size() + "\n");
        return bestMove;      
    }
    
    // Return the minimum value of the generated states
    private int returnMin(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta, 
    		HashSet<Point> oppM, HashSet<Point> myM, OrderMinNode o) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        if (currentDepth == plyDepth) {
        	return Utils.numberInARow(state, movesMade, player, otherPlayerValue) -
        		   Utils.numberInARow(state, movesMade, otherPlayerValue, player);
        }
        if (o.orderMaxNode_queue == null) {
	       	Map<Point, Integer> moves = getAvailableMoves(state, oppM, myM);
	       	o.orderMaxNode_queue = Utils.convertToOrderMinQueue(moves);
        }    
        // Total score belongs to parent node
        int totalScore = 0, count = 0;
        int localBeta = Integer.MAX_VALUE;
        PriorityQueue<OrderMaxNode> copy = new PriorityQueue<>(o.orderMaxNode_queue);
        OrderMaxNode order = null;
        while (!copy.isEmpty())
        {
        	order = copy.remove();
        	HashSet<Point> oppMoves = new HashSet<>(oppM);
        	oppMoves.add(order.point);
        	int score = returnMax(state.clone().placePiece(order.point, otherPlayerValue), currentDepth+1, plyDepth, 
        			alpha, localBeta, oppMoves, myM, order);
        	
        	totalScore += score;
        	count++;
        	if (score <= alpha) {
              o.score = totalScore / count;
          	  return alpha;
        	}
        	if (score < localBeta) {
              localBeta = score;
        	}
        	
        }
    	o.score = totalScore / count;
        return localBeta; 
    }
    
    // Return the maximum value of the generated states
    private int returnMax(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta, HashSet<Point> oppM, 
    		HashSet<Point> myM, OrderMaxNode o) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        
        if (currentDepth == plyDepth) {
        	return Utils.numberInARow(state, movesMade, player, otherPlayerValue) -
         		   Utils.numberInARow(state, movesMade, otherPlayerValue, player);
        }
        if (o.orderMinNode_queue == null) {
	       	Map<Point, Integer> moves = getAvailableMoves(state, myM, oppM);
	       	o.orderMinNode_queue = Utils.convertToOrderMaxQueue(moves);
        }
        
        // totalScore belongs to parent node
        int totalScore = 0, count = 0;
        int localAlpha = Integer.MIN_VALUE;
        
        PriorityQueue<OrderMinNode> copy = new PriorityQueue<>(o.orderMinNode_queue);
        OrderMinNode order = null;
        
    	while(!copy.isEmpty())
        {
    		order = copy.remove();
			HashSet<Point> myMoves = new HashSet<>(myM);
			myMoves.add(order.point);
			int score = returnMin(state.clone().placePiece(order.point, player), currentDepth+1, 
					              plyDepth, localAlpha, beta, oppM, myMoves, order);

			totalScore += score;
			count++;
			
			if (score >= beta) {
				o.orderMinNode_queue = new PriorityQueue<OrderMinNode>(o.orderMinNode_queue);
				o.score = totalScore / count;
				return beta;
			}
			if (localAlpha < score) {
			  localAlpha = score;
			}
        }
    	o.orderMinNode_queue = new PriorityQueue<OrderMinNode>(o.orderMinNode_queue);
  	  	o.score = totalScore / count;
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
    private Map<Point, Integer> getAvailableMoves(BoardModel state, HashSet<Point> p, HashSet<Point> q) 
    {
    	Map<Point, Integer> availableMoves = new HashMap<Point, Integer>();
    	for (Point move : p) {
    		Utils.getMovesPriority(state, availableMoves, move.x, move.y);
    	}
    	for (Point move : q) {
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
    
    @SuppressWarnings("deprecation")
    public Point executeMove(BoardModel state, int time) 
    {	
    	Point bestMove = null;
    	SearchThread idsSearch = new SearchThread(state, this);
    	idsSearch.start();
    	
    	// Wait for the child thread for 5 seconds
    	try 
    	{
			idsSearch.join((long)time - 3);
		}
    	catch (InterruptedException e) 
    	{
			e.printStackTrace();
		}
    	
    	// Stop thread if it's still executing after 5 seconds
    	if (idsSearch.isAlive())
    	{
    		idsSearch.stop();
    	}
    	
    	bestMove = idsSearch.bestMove;
    	return bestMove;
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return executeMove(state, deadline);
    }
    
	@Override
	public Point getMove(BoardModel state) {
		return executeMove(state, 5);
	}  
    
    // Private member variables
    private byte otherPlayerValue;
    private HashSet<Point> movesMade;  
    private HashSet<Point> opponentMoves;
}