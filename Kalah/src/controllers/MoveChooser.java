package controllers;

import java.util.ArrayList;
import models.Player;
import models.Board;

public class MoveChooser {
    
   // private static final int LOOK_AHEAD = 7;
    
    private Board board;
    private Player owner;
    
    public MoveChooser(Board b, Player p) {
        board = b;
        owner = p;
    }
    
    public int choose() {
        int v = maxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE);
        //TODO: retornar a ação referente ao valor v
        return 9;
    }
    
    public int maxOrMin(Board b, int alpha, int beta, String maxOrMin) {
        if (b.hasGameEnded()) {
            Player opponent = Kalah.getInstance().getPlayersOpponent(owner);
            int ownerDiamonds = b.getPots()[owner.getKalah()].getDiamonds();
            int opponentDiamonds = b.getPots()[opponent.getKalah()].getDiamonds();
            return ownerDiamonds - opponentDiamonds;
        }
        if (maxOrMin.equals("max")) {
            return maxValue(b, alpha, beta);
        }
        else {
            return minValue(b, alpha, beta);
        }
    }
    
    public int maxValue(Board b, int alpha, int beta) {
        String maxOrMin;
        int v = Integer.MIN_VALUE;
        Player player = owner;
        ArrayList<Integer> actions = b.getValidActions(player);
        for (Integer action : actions) {
            b = new Board(b);
            int lastPot = Kalah.getInstance().distributeFromPot(b, action, player);
            if (lastPot == player.getKalah()) {
                maxOrMin = "max";
            }
            else {
                maxOrMin = "min";
            }
            v = Math.max(v, maxOrMin(b, alpha, beta, maxOrMin));
            if(v >= beta){
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        return v;
    }
    
    public int minValue(Board b, int alpha, int beta){
        String maxOrMin;
        int v = Integer.MAX_VALUE;
        Player player = Kalah.getInstance().getPlayersOpponent(owner);
        b.getValidActions(player);
        ArrayList<Integer> actions = b.getValidActions(player);
        for (Integer action : actions) {
            b = new Board(b);
            int lastPot = Kalah.getInstance().distributeFromPot(b, action, player);
            if (lastPot == player.getKalah()) {
                maxOrMin = "min";
            }
            else {
                maxOrMin = "max";
            }
            v = Math.min(v, maxOrMin(b,alpha,beta,maxOrMin));
            if(v <= alpha){
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
        
     }

}
