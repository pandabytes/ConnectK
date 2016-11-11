import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;


public class OrderMinNode implements Comparator<OrderMinNode>
{

	public OrderMinNode(int s, Point p, int priority) {
		score = s;
		point = p;
		priorityCount = priority;
	}
	
	public OrderMinNode(int s, Point p)
	{
		point = p;
		score = s;
	}
	
	public OrderMinNode()
	{
		score = 0;
		priorityCount = 0;
		orderMaxNode_queue = null;
	}
	
	@Override
	public int compare(OrderMinNode o1, OrderMinNode o2) 
	{
		// Get 0, if two scores are equal
		// Get x < 0, if o1's score < o2's score
		// Get x > 0, if o1's score > o2's score
		int compareScore = o2.score.compareTo(o1.score);
		if (compareScore == 0)
		{
			return o2.priorityCount.compareTo(o1.priorityCount);
		}
		return compareScore;
	}
	
	public Integer score;
	public Point point;
	public Integer priorityCount;
	public PriorityQueue<OrderMaxNode> orderMaxNode_queue;
	
}
