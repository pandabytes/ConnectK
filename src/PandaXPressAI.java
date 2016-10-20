import connectK.CKPlayer;
import connectK.BoardModel;
import java.awt.Point;
import java.util.*;

public class PandaXPressAI extends CKPlayer 
{
    public PandaXPressAI(byte player, BoardModel state) 
    {
        super(player, state);
        teamName = "PandaXPress";
        count = 0;
        emptySlots = new LinkedList<Point>();
        otherPlayerValue = getOtherPlayerValue(player);
    }
    
    private Point implementMinMax(BoardModel state, int plyDepth) {
        if (plyDepth <= 1) { 
            return null;
        }
        Vector<Point> moves = getAvailableMoves(state);
        Point bestMove = moves.get(0);
        int bestScore = Integer.MIN_VALUE;
        for (Point move:moves) {
            int score = returnMin(state.clone().placePiece(move, otherPlayerValue), 1, plyDepth);
            if (score > bestScore) {
                bestMove = move;
                bestScore = score;
            }
        }
        return bestMove;      
    }
    
    private int returnMin(BoardModel state, int currentDepth, int plyDepth) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        Vector<Point> moves = getAvailableMoves(state);
        int worstScore = Integer.MAX_VALUE;
        for (Point move:moves) {
            int score = returnMax(state.clone().placePiece(move, player), currentDepth+1, plyDepth);
            if (score < worstScore) {
                worstScore = score;
            }
        }
        return worstScore; 
    }
    
    private int returnMax(BoardModel state, int currentDepth, int plyDepth) {
        if (state.winner() != -1) {
            return evaluateWinner(state.winner());
        }
        if (currentDepth == plyDepth) {
            return Utils.boardStateScore(state, player);
        }
        Vector<Point> moves = getAvailableMoves(state);
        int bestScore = Integer.MIN_VALUE;
        for (Point move:moves) {
            int score = returnMin(state.clone().placePiece(move, otherPlayerValue), currentDepth, plyDepth);
            if (score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore; 
    }
    
    private int evaluateWinner(byte winner) {
        if (winner == player) {
            return Integer.MAX_VALUE;
        }
        return Integer.MIN_VALUE;
    }
    
    private Vector<Point> getAvailableMoves(BoardModel state) {
          Vector<Point> availableMoves = new Vector<Point>();
          
          for (int i = 0; i < state.getWidth(); i++)
          {
              for (int j = 0; j < state.getHeight(); j++)
              {
                  if (state.getSpace(i, j) == 0)
                  {
                      availableMoves.add(new Point(i, j));
                  }
              }
          }
          return availableMoves;
    }
    
    private byte getOtherPlayerValue(byte player) {
        if (player == 1) {
            return 2;
        }
        else return 1;
    }
    
    @Override
    public Point getMove(BoardModel state) 
    {
        return implementMinMax(state, 3);
    }

    @Override
    public Point getMove(BoardModel state, int deadline) 
    {
        return getMove(state);
    }
    
    private List<Point> emptySlots;
    private int count;
    private static Random rand = new Random();
    private byte otherPlayerValue;
    
}
