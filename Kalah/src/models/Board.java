package models;

import controllers.Kalah;
import java.util.ArrayList;

public class Board extends Subject {

    private Pot[] pots;

    public Board(int potsQty, int initialQty) {
        pots = new Pot[potsQty];
        for (int i = 0; i < potsQty; i++) {
            if (i == 0 || i == potsQty / 2) {
                pots[i] = new Pot(0);
            } else {
                pots[i] = new Pot(initialQty);
            }
        }
    }

    public Board(Board b) {
        pots = new Pot[b.getPots().length];
        int diamondsQty;
        for (int i = 0; i < pots.length; i++) {
            diamondsQty = b.getPots()[i].getDiamonds();
            pots[i] = new Pot(diamondsQty);

        }
    }

    public Pot[] getPots() {
        return pots;
    }

    public ArrayList getValidActions(Player p) {
        int[] choiceRange = p.getChoiceRange();
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = choiceRange[0]; i <= choiceRange[1]; i++) {
            if (!pots[i].isEmpty()) {  
                actions.add(new Action(i));
            }
        }
        return actions;
    }

    public int nextPot(int pot) {
        if (pot < pots.length - 1) {
            return pot + 1;
        } else {
            return 0;
        }
    }

    public int distributeFromPot(int pot, Player p) {
        if (!belongsToPlayerSide(pot, p) || pots[pot].isEmpty()) {
            return -1;
        } else {
            int originalDmnds = pots[pot].getDiamonds();
            pots[pot].removeDiamonds();
            int currPot = pot;
            for (int i = 0; i < originalDmnds; i++) {
                currPot = nextPot(currPot);
                pots[currPot].incrementDiamonds();
            }
            int oppositePot = pots.length - currPot;
            if (belongsToPlayerSide(currPot, p) && pots[currPot].getDiamonds() == 1) {
                int diamondsInOppositePot = pots[oppositePot].getDiamonds();
                pots[currPot].removeDiamonds();
                pots[oppositePot].removeDiamonds();
                pots[p.getKalah()].addDiamonds(diamondsInOppositePot + 1);
            }
            Player playerToBeEmptied = null;
            Player opponent = Kalah.getInstance().getPlayersOpponent(p);
            if (!sideHasDiamondsLeft(p.getChoiceRange()[0], p.getChoiceRange()[1])) {
                playerToBeEmptied = opponent;
            } else if (!sideHasDiamondsLeft(opponent.getChoiceRange()[0], opponent.getChoiceRange()[1])) {
                playerToBeEmptied = p;
            }
            if (playerToBeEmptied != null) {
                int playerFirstPot;
                int playerLastPot;
                playerFirstPot = playerToBeEmptied.getChoiceRange()[0];
                playerLastPot = playerToBeEmptied.getChoiceRange()[1];
                int diamondsInCurrPot;
                for (int i = playerFirstPot; i <= playerLastPot; i++) {
                    diamondsInCurrPot = pots[i].getDiamonds();
                    pots[i].removeDiamonds();
                    pots[playerToBeEmptied.getKalah()].addDiamonds(diamondsInCurrPot);
                }
            }

            notifyObservers();

            return currPot;
        }
    }

    public boolean belongsToPlayerSide(int pot, Player p) {
        return ((pot >= p.getChoiceRange()[0]) && (pot <= p.getChoiceRange()[1]));
    }

    public boolean sideHasDiamondsLeft(int firstInRange, int lastInRange) {
        for (int i = firstInRange; i <= lastInRange; i++) {
            if (pots[i].getDiamonds() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasGameEnded() {
        // Potes 0 e pots.length/2 são Kalah
        return !sideHasDiamondsLeft(1, (pots.length / 2) - 1) || !sideHasDiamondsLeft(pots.length / 2, pots.length - 1);
    }

    @Override
    public String toString() {
        String line = "";
        String ret = "";
        boolean inverse = false;
        for (int i = 0; i < pots.length; i++) {
            if (i % (pots.length / 2) == 0) {
                line += "|" + pots[i].getDiamonds() + "| ";
            } else {
                line += pots[i].getDiamonds() + " ";
                if ((i + 1) % (pots.length / 2) == 0) {
                    if (inverse) {
                        line = invertLine(line);
                    }
                    ret += line;
                    if (!inverse) {
                        ret += "\n\n";
                    }
                    line = "";
                    inverse = !inverse;
                }
            }
        }
        return ret;
    }

    private String invertLine(String text) {
        //recebe uma linha e inverte
        String[] numbers = text.split(" ");
        String[] invertedNumbers = new String[numbers.length];
        String ret = "    ";
        for (int i = 0; i < numbers.length; i++) {
            invertedNumbers[i] = numbers[numbers.length - i - 1];
        }
        for (int i = 0; i < invertedNumbers.length; i++) {
            ret += invertedNumbers[i] + " ";
        }
        return ret;
    }    

}
