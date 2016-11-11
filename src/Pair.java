import java.awt.Point;
import java.util.Comparator;

public class Pair implements Comparator<Pair>
{
	public Pair(){}
	
	public Pair(Point x, Integer y)
	{
		point = x;
		priorityCount = y;
	}
	
	public Pair (Point x)
	{
		point = x;
		priorityCount = 1;
	}
	
	public Pair(Integer y)
	{
		priorityCount = y;
	}

	@Override
	public int compare(Pair p1, Pair p2) 
	{
		return p2.priorityCount.compareTo(p1.priorityCount);
	}
	
	@Override
	public String toString()
	{
		return point.toString() + " Priority Count: " + priorityCount.toString();
	}

	public Point point;
	public Integer priorityCount;
}
