package models;

//Uso do Simple Factory

import models.HumanPlayer;
import models.AIPlayer;


public class PlayerFactory {
    
    public PlayerFactory() {}
    
    public Player createPlayer(String type, int totalPots){
        int kalah;
        int firstInRange;
        int lastInRange;
        if (type.equals("human")) {
            firstInRange = 1;
            lastInRange = (totalPots / 2) - 1;
            kalah = lastInRange + 1;
            return new HumanPlayer("Jogador", kalah, firstInRange, lastInRange);
        }
        else {
            firstInRange = totalPots / 2 + 1;
            lastInRange = totalPots - 1;
            kalah = 0;
            return new AIPlayer("Oponente", kalah, firstInRange, lastInRange);
        }
    }

}
