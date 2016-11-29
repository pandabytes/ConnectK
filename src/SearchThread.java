import connectK.BoardModel;
import java.awt.Point;


public class SearchThread extends Thread 
{
	public SearchThread(BoardModel s, PandaXPressAI ai) 
	{
		state = s;
		AI = ai;
		bestMove = null;
		finalBestMove = null;
		depth = 2;
	}
	
	public void run() 
	{
		OrderMaxNode parent = new OrderMaxNode();
    	while (!Thread.interrupted() && !AI.is_timeOut)
    	{
    		bestMove = AI.alphaBetaPruning(state, depth, parent);
    		if (!Thread.interrupted() || finalBestMove == null)
    		{
    			finalBestMove = new Point(bestMove.x, bestMove.y);
    		}
    		depth++;
    	}
	}
	
	public Point bestMove;
	public Point finalBestMove;
	private int depth;
	private BoardModel state;
	private PandaXPressAI AI;
}
