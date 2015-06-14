

package models;

import controllers.Kalah;
import controllers.MoveChooser;

public class AIPlayer extends Player {

    public AIPlayer(String n, int k, int firstInRange, int lastInRange){
        super(n, k, firstInRange, lastInRange);
    }

    @Override
    public int choosePot() {
        int choice;

        System.out.println("O computador está pensando...");
        choice = new MoveChooser(Kalah.getInstance().getBoard(), this).choose();
        System.out.println("O computador escolheu o pote!");
        System.out.println(choice);
        //choice = new MoveChooser(this, this, Kalah.getInstance().getBoard()).dumbChoose();

        return choice;
    }

}
