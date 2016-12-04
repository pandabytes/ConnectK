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
	
	@Override
	public void run() 
	{
		// Create the root
		OrderMaxNode parent = new OrderMaxNode();
		
		// Go down each depth incrementally
    	while (!Thread.interrupted())
    	{	
    		bestMove = AI.alphaBetaPruning(state, depth, parent);
    		if (!this.isInterrupted())
    		{
    			finalBestMove = bestMove;
    		}
    		depth++;
    	}
	}
	
	private Point bestMove;
	public Point finalBestMove;
	private int depth;
	private BoardModel state;
	private PandaXPressAI AI;
}
