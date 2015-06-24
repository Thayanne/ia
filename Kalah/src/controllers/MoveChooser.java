package controllers;

import java.util.ArrayList;
import models.Action;
import models.Player;
import models.Board;

public class MoveChooser {

    private Board board;
    private Player owner; //owner é o Player referente a IA, pq ele é o "dono" da IA 
    final int CUTOFF = 3;
    public MoveChooser(Board b, Player p) {
        board = b;
        owner = p;
    }

    public int choose() {
        Action a = maxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        int p = a.getPot();
        return p;
    }

    public Action maxOrMin(Board b, int alpha, int beta, String maxOrMin, int cutOff) {
        /*Funcao decide se chama a funcao min (minValue) ou max (mavValue)
         dependendo de quem é a vez de jogar (para cobrir o caso em que a IA joga 2x)
         */

        if (b.hasGameEnded() || (cutOff >= CUTOFF)) { //se eu cheguei num nó que indica o final do jogo ou se acabou meu cutoff, eu retorno uma action com aquele valorResult
            Action action = new Action();
            action.setValueResult(this.getStatus(b));
            return action;
        }
        if (maxOrMin.equals("max")) {
            return maxValue(b, alpha, beta, cutOff);
        } else {
            return minValue(b, alpha, beta, cutOff);
        }
    }

    public Action maxValue(Board bEntrada, int alpha, int beta, int cutOff) {
        //funcao max
        
        String maxOrMin;
        Action chosenAction = new Action();
        chosenAction.setValueResult(Integer.MIN_VALUE);
        Player player = owner;
        ArrayList<Action> actions = bEntrada.getValidActions(player);
        for (Action action : actions) {
            Board b = new Board(bEntrada);
            int lastPot = Kalah.getInstance().distributeFromPot(b, action.getPot(), player);
            if (lastPot == player.getKalah()) {//se o ultimo pote caiu no meu kalah, eu faço um max, se nao eu faço um min
                maxOrMin = "max";
            } else {
                maxOrMin = "min";
            }
            //aplicando a heurística no action candidato(aplicando minMax pra saber qual valor tem que chegar aqui)
            action.setValueResult(maxOrMin(b, alpha, beta, maxOrMin, cutOff + 1).getValueResult());
            
            //se o valor que eu adquiri nesse action for maior que o antigo chosen action, seto chosenAction para esse action atual
            if(action.getValueResult() > chosenAction.getValueResult())
               chosenAction = action;
            
            if (action.getValueResult() >= beta) {
                return action;                
            }
            alpha = Math.max(alpha,action.getValueResult());
        }
        return chosenAction;
    }

    public Action minValue(Board bEntrada, int alpha, int beta, int cutOff) {
        //funcao min
        
        String maxOrMin;
        Action chosenAction = new Action();       
        chosenAction.setValueResult(Integer.MAX_VALUE);        
        Player player = Kalah.getInstance().getPlayersOpponent(owner);
        ArrayList<Action> actions = bEntrada.getValidActions(player);
        for (Action action : actions) {
            Board b = new Board(bEntrada);
            int lastPot = Kalah.getInstance().distributeFromPot(b, action.getPot(), player);
            if (lastPot == player.getKalah()) {
                maxOrMin = "min";
            } else {
                maxOrMin = "max";
            }
            //aplicando a heurística no action candidato(aplicando minMax pra saber qual valor tem que chegar aqui)
            action.setValueResult(maxOrMin(b, alpha, beta, maxOrMin, cutOff + 1).getValueResult());
            
            //se o valor que eu adquiri nesse action for maior que o antigo chosen action, seto chosenAction para esse action atual
            if(action.getValueResult() < chosenAction.getValueResult())
               chosenAction = action;
            
            if (action.getValueResult() <= alpha) {
                return action;
            }
            beta = Math.min(beta, action.getValueResult());
        }
        return chosenAction;
    }
    
//heurística para descobrir a função de avaliação
    public int getStatus(Board b) {
        Player opponent = Kalah.getInstance().getPlayersOpponent(owner);
        int ownerDiamonds = b.getPots()[owner.getKalah()].getDiamonds();
        int opponentDiamonds = b.getPots()[opponent.getKalah()].getDiamonds();
        return ownerDiamonds - opponentDiamonds; //diferença entre a quantidade de diamantes
    }
}
