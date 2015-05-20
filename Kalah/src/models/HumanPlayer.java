

package models;

import java.util.Scanner;


public class HumanPlayer extends Player {

    public HumanPlayer(String n, int k, int firstInRange, int lastInRange){
        super(n, k, firstInRange, lastInRange);
    }

    @Override
    public int choosePot() {
        int choice;
        Scanner in = new Scanner(System.in);
        System.out.println("Escolha o pote:");
        choice = in.nextInt();

        return choice;

    }

}
