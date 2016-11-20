import connectK.BoardModel;
import java.awt.Point;


public class SearchThread extends Thread 
{
	public SearchThread(BoardModel s, PandaXPressAI ai) 
	{
		state = s;
		depth = 2;
		AI = ai;
		bestMove = null;
	}
	
	public void run() 
	{
		OrderMaxNode parent = new OrderMaxNode();
    	while (true)
    	{
    		bestMove = AI.alphaBetaPruning(state, depth, parent);
    		depth++;
    	}
	}
	
	private BoardModel state;
	private int depth;
	public Point bestMove;
	private PandaXPressAI AI;
}
