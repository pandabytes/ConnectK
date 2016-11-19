import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;


public class SearchThread extends Thread 
{
	public SearchThread(BoardModel s, PandaXPressAI ai) 
	{
		state = s;
		depth = 4;
		AI = ai;
		bestMove = null;
	}
	
	public void run() 
	{
		OrderMaxNode parent = new OrderMaxNode();
    	while (true)
    	{
    		bestMove = AI.alphaBetaPruning(state, depth, parent);
    		
    		// Debug purposes
//    		if (depth >= 2)
//    		{
//    			System.out.println("Depth: " + depth);
//    			System.out.println("# of children: " + parent.orderMinNode_queue.size());
//    			System.out.print("Queue = [");
//    			for (OrderMinNode order : parent.orderMinNode_queue)
//    			{
//    				System.out.print("(" + order.score + ", " + order.priorityCount + ") ");
//    			}
//    			System.out.println("]");
//    			
//    			System.out.println("Score: " + parent.orderMinNode_queue.peek().score + " Priority: " + parent.orderMinNode_queue.peek().priorityCount);
//    			System.out.println();
//    		}
    		depth++;
    	}
	}
	
	private BoardModel state;
	private int depth;
	public Point bestMove;
	private PandaXPressAI AI;
}
