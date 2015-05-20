package controllers;

import models.Player;
import models.Board;

public class MoveChooser {
    
    private static final int LOOK_AHEAD = 7;
    
    private Board board;
    private Player original; //O jogador que está escolhendo seu movimento c/ IA
    private Player current;
    
    
    public MoveChooser(Player o, Player c, Board b) {
        original = o;
        current = c;
        board = b;
    }
    
    public int dumbChoose() {
        for (int i = current.getChoiceRange()[0]; i <= current.getChoiceRange()[1]; i++) {
            if (!board.getPots()[i].isEmpty())
                return i;
        }
        return 0;
    }
    
    public int smartChoose() {
        //retorna o indice da melhor escolha
        int bestChoiceValue = Integer.MIN_VALUE;
        int bestChoice = original.getChoiceRange()[0];
        int choiceValue;
        for (int i = original.getChoiceRange()[0]; i <= original.getChoiceRange()[1]; i++) {
            choiceValue = (new MoveChooser(original, original, new Board(board))).recursiveSmartChoose(i, 0);
            if (choiceValue > bestChoiceValue) {
                bestChoiceValue = choiceValue; 
                bestChoice = i; //escolha do melhor pote
            }
        }
        return bestChoice;
    }
    
    public int recursiveSmartChoose(int pot, int itrIndex) {
        //retorna o valor da melhor escolha
        itrIndex++;
        int lastPot = Kalah.getInstance().distributeFromPot(board, pot, current);
        if (hasGameEnded() || itrIndex > LOOK_AHEAD)// Diferença entre os diamantes do kalah da IA e do oponente dela
            return board.getPots()[original.getKalah()].getDiamonds() - board.getPots()[Kalah.getInstance().getPlayersOpponent(original).getKalah()].getDiamonds();
        else {
            int bestChoiceValue = Integer.MIN_VALUE;
            if (lastPot >= 0) {             //teste se a jogada é válida
                boolean playingAgain = (lastPot == current.getKalah());
                int choiceValue;
                Player p;
                if (playingAgain)
                    p = current;
                else
                    p = Kalah.getInstance().getPlayersOpponent(current);
                for (int i = p.getChoiceRange()[0]; i <= p.getChoiceRange()[1]; i++) {
                        choiceValue = (new MoveChooser(original, p, new Board(board)).recursiveSmartChoose(i, itrIndex));
                        if (choiceValue > bestChoiceValue)
                            bestChoiceValue = choiceValue;
                }
            }
            return bestChoiceValue;
        }
    }
    
    private boolean hasGameEnded() {
        return !board.sideHasDiamondsLeft(original.getChoiceRange()[0], original.getChoiceRange()[1]) &&
            !board.sideHasDiamondsLeft(Kalah.getInstance().getPlayersOpponent(original).getChoiceRange()[0], Kalah.getInstance().getPlayersOpponent(original).getChoiceRange()[1]);
    }

}
