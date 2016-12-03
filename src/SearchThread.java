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
		OrderMaxNode parent = new OrderMaxNode();
    	while (!Thread.interrupted())
    	{	
    		System.out.println("Depth: " + depth);
    		bestMove = AI.alphaBetaPruning(state, depth, parent);
    		if (!this.isInterrupted())
    		{
    			finalBestMove = bestMove;
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
