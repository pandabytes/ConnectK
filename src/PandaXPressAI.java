import connectK.CKPlayer;
import javafx.concurrent.Task;
import connectK.BoardModel;
import java.awt.Point;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;


public class PandaXPressAI extends CKPlayer 
{
	// Constructor
    public PandaXPressAI(byte player, BoardModel state) 
    {
        super(player, state);
        teamName = "PandaXPress";
        otherPlayerValue = getOtherPlayerValue(player);
        movesMade = new HashSet<>();
    }
    
    private Point IDS(BoardModel state, int timeLimit)
    {
    	int depth = 0;
    	OrderMaxNode parent = new OrderMaxNode();
    	Point bestMove = null;
    	long startTime = System.currentTimeMillis();
    	long currentTime = 0;
    	
    	return bestMove;
    }
    
    // Do alpha beta pruning to find the best worst move
    public Point alphaBetaPruning(BoardModel state, int plyDepth, OrderMaxNode parent) {
        if (plyDepth <= 1) { 
            return null;
        }
        
        // Check if we're the first player
        if (movesMade.size() == 0) {
        	Point p = new Point(state.getWidth() / 2, state.getHeight() / 2);
        	if (state.getSpace(p) != 0) {
        		p.x = p.x+1;
        		p.y = p.y+1;
        	}
        	movesMade.add(p);
        	return p;
        }
        
       	Map<Point, Integer> moves = getAvailableMoves(state, movesMade);
       	parent.orderMinNode_queue = Utils.convertToOrderMaxQueue(moves);
        
        int score = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Point bestMove = parent.orderMinNode_queue.peek().point;

        for (OrderMinNode order : parent.orderMinNode_queue) {
        	HashSet<Point> n = new HashSet<>(movesMade);
        	n.add(order.point);
            score = returnMin(state.clone().placePiece(order.point, player), 1, plyDepth, 
            		              alpha, beta, n, order);
            
            if (score > alpha) {
                bestMove = order.point;
                alpha = score;
            }
        }
        movesMade.add(bestMove);
        return bestMove;      
    }
    
    // Return the minimum value of the generated states
    private int returnMin(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta, HashSet<Point> m, 
    		              OrderMinNode o) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        
        if (currentDepth == plyDepth) {
        	return Utils.numberOfPossibleWins(state, player, otherPlayerValue) - 
        		   Utils.numberOfPossibleWins(state, otherPlayerValue, player);
        }
        
       	Map<Point, Integer> moves = getAvailableMoves(state, m);
       	o.orderMaxNode_queue = Utils.convertToOrderMinQueue(moves);
        
        // Total score belongs to parent node
        int totalScore = 0, count = 0;
        
        int localBeta = Integer.MAX_VALUE;
//        for (OrderMaxNode order : priorityMaxNodes) {
//        	HashSet<Point> n = new HashSet<>(m);
//        	n.add(order.point);
//            int score = returnMax(state.clone().placePiece(order.point, otherPlayerValue), currentDepth+1, 
//            					  plyDepth, alpha, localBeta, n, order);
//            
//            // Assign score to child node
//            order.score = score;
//            
//            totalScore += score;
//            count++;
//            
//            if (score <= alpha) {
//            	return alpha;
//            }
//            if (score < localBeta) {
//                localBeta = score;
//            }
//        }
        
        OrderMaxNode savedNode = null;
        for (Iterator<OrderMaxNode> iterator = o.orderMaxNode_queue.iterator(); iterator.hasNext();)
        {
        	OrderMaxNode order = iterator.next();
        	HashSet<Point> n = new HashSet<>(m);
        	n.add(order.point);
        	int score = returnMax(state.clone().placePiece(order.point, otherPlayerValue), currentDepth+1, 
					  plyDepth, alpha, localBeta, n, order);
        	
          totalScore += score;
          count++;
          
          if (score <= alpha) {
        	savedNode = o.orderMaxNode_queue.peek();
        	o.orderMaxNode_queue.remove(savedNode);
        	o.orderMaxNode_queue.add(savedNode);
        	o.score = totalScore / count;
          	return alpha;
          }
          if (score < localBeta) {
              localBeta = score;
          }
        	
        }
        savedNode = o.orderMaxNode_queue.peek();
        o.orderMaxNode_queue.remove(savedNode);
    	o.orderMaxNode_queue.add(savedNode);
    	o.score = totalScore / count;
        return localBeta; 
    }
    
    // Return the maximum value of the generated states
    private int returnMax(BoardModel state, int currentDepth, int plyDepth, int alpha, int beta, HashSet<Point> m,
    		              OrderMaxNode o) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        
        if (currentDepth == plyDepth) {
        	return Utils.numberOfPossibleWins(state, player, otherPlayerValue) - 
         		   Utils.numberOfPossibleWins(state, otherPlayerValue, player);
        }
        
       	Map<Point, Integer> moves = getAvailableMoves(state, m);
       	o.orderMinNode_queue = Utils.convertToOrderMaxQueue(moves);
        
        
        // totalScore belongs to parent node
        int totalScore = 0, count = 0;
        
        int localAlpha = Integer.MIN_VALUE;
//        for (OrderMinNode order : priorityMinNodes) {
//        	HashSet<Point> n = new HashSet<>(m);
//        	n.add(order.point);
//        	int score = returnMin(state.clone().placePiece(order.point, player), currentDepth+1, 
//            		              plyDepth, localAlpha, beta, n, order);
//            if (score >= beta) {
//                return beta;
//            }
//            if (localAlpha < score) {
//            	localAlpha = score;
//            }
//        }
        
        OrderMinNode savedNode = null;
        for (Iterator<OrderMinNode> iterator = o.orderMinNode_queue.iterator(); iterator.hasNext();)
        {
        	OrderMinNode order = iterator.next();
        	HashSet<Point> n = new HashSet<>(m);
        	n.add(order.point);
        	int score = returnMin(state.clone().placePiece(order.point, otherPlayerValue), currentDepth+1, 
					  plyDepth, alpha, localAlpha, n, order);
        	
          totalScore += score;
          count++;
          
          if (score >= beta) {
        	  savedNode = o.orderMinNode_queue.peek();
        	  o.orderMinNode_queue.remove(savedNode);
        	  o.orderMinNode_queue.add(savedNode);
        	  o.score = totalScore / count;
        	  return beta;
          }
          if (localAlpha < score) {
        	  localAlpha = score;
          }
        	
        }
        
        savedNode = o.orderMinNode_queue.peek();
        o.orderMinNode_queue.remove(savedNode);
  	  	o.orderMinNode_queue.add(savedNode);
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
    private Map<Point, Integer> getAvailableMoves(BoardModel state, HashSet<Point> p) 
    {
    	Map<Point, Integer> availableMoves = new HashMap<Point, Integer>();
    	for (Point move : p) {
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
	@Override
    public Point getMove(BoardModel state) 
    {	
    	Point bestMove = null;
    	SearchThread idsSearch = new SearchThread(state, this);
    	idsSearch.start();
    	
    	// Wait for the child thread for 5 seconds
    	try 
    	{
			idsSearch.join(4997);
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
    	System.out.println("Thread Alive? " + idsSearch.isAlive());
    	bestMove = idsSearch.bestMove;
    	return bestMove;
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return getMove(state);
    }
    
    // Private member variables
    private byte otherPlayerValue;
    private HashSet<Point> movesMade;    
}
