import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;


public class OrderMaxNode implements Comparator<OrderMaxNode>
{
	public OrderMaxNode(int s, Point p, int priority) {
		score = s;
		point = p;
		priorityCount = priority;
	}
	
	public OrderMaxNode(int s, Point p)
	{
		point = p;
		score = s;
	}
	
	public OrderMaxNode()
	{
		score = 0;
		priorityCount = 0;
		orderMinNode_queue = null;
	}
	
	@Override
	public int compare(OrderMaxNode o1, OrderMaxNode o2) 
	{
		// Get 0, if two scores are equal
		// Get x < 0, if o1's score < o2's score
		// Get x > 0, if o1's score > o2's score
		int compareScore = o1.score.compareTo(o2.score);
		if (compareScore == 0)
		{
			return o2.priorityCount.compareTo(o1.priorityCount);
		}
		return compareScore;
	}
	
	public Integer score;
	public Point point;
	public Integer priorityCount;
	public PriorityQueue<OrderMinNode> orderMinNode_queue;
	
}
