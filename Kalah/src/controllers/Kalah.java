package controllers;

import views.KalahInterface;
import models.Player;
import models.PlayerFactory;
import models.Pot;
import models.Board;
import java.util.ArrayList;

//Uso do Padrão Singleton

public class Kalah {
    
    private static final int DEFAULT_INIT_DIAMONDS_QTY = 6;
    
    private Board board;
    private Player[] players;
    private PlayerFactory factory;
    private int currPlayer;
    private static Kalah instance;
    
    private Kalah() { }
    
    public static Kalah getInstance() {
        if (instance == null)
            instance = new Kalah();
        return instance;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public Player getPlayersOpponent(Player p) {
        if (players[0] == p)
            return players[1];
        else
            return players[0];
    }
    
    public void init(int potsPerPlayer) {
        int potsQty = potsPerPlayer * 2;
        board = new Board(potsQty, DEFAULT_INIT_DIAMONDS_QTY);
        players = new Player[2];
        factory = new PlayerFactory();
        players[0] = factory.createPlayer("opponent", potsQty);
        players[1] = factory.createPlayer("human", potsQty);
        currPlayer = 1;
        KalahInterface kalahInterface = new KalahInterface(board);
        kalahInterface.setVisible(true);
        board.addObserver(kalahInterface);
        startGame();
    }
    
    private void startGame() {
        while (true) {
            System.out.println(board);
            
            //Uso do polimorfismo, uma vez que o choosePot se comporta diferentemente conforme o tipo de jogador
            int chosenHole = players[currPlayer].choosePot();
            
            int lastPot = distributeFromPot(board, chosenHole, players[currPlayer]);
            if (lastPot != players[currPlayer].getKalah() && lastPot >= 0)
                changePlayer();
            else {
                if (lastPot == players[currPlayer].getKalah())
                    System.out.println("Jogue novamente!");
                else if (lastPot < 0)
                    System.out.println("Impossível distribuir deste pote!");
            }
            
            ArrayList<Player> winners = getWinners(); //lista de vencedores
            if (!winners.isEmpty()) {
                System.out.println(board);
                if (winners.size() > 1)
                    System.out.println("Empate!");
                else
                    System.out.println(winners.get(0)+" venceu!");
                break;
            }
        }
    }
    
    //Os parâmetros são necessários porque esse método é usado tanto no fluxo
    //do jogo quanto pela IA
    public int distributeFromPot(Board b, int pot, Player p) {
        //controle de fluxo do jogo e retorna o pote onde caiu o último diamante
        Pot[] pots = b.getPots();
        if (!b.belongsToPlayerSide(pot, p) || pots[pot].isEmpty())
            return -1;
        else {
            int originalDmnds = pots[pot].getDiamonds();
            pots[pot].removeDiamonds();
            int currPot = pot;
            for (int i = 0; i < originalDmnds; i++) {
                currPot = b.nextPot(currPot);
                pots[currPot].incrementDiamonds();
            }
            int oppositePot = pots.length - currPot;
            if (b.belongsToPlayerSide(currPot, p) && pots[currPot].getDiamonds() == 1) { //se caiu numa casa anteriormente vazia
                int diamondsInOppositePot = pots[oppositePot].getDiamonds();
                pots[currPot].removeDiamonds();
                pots[oppositePot].removeDiamonds();
                pots[p.getKalah()].addDiamonds(diamondsInOppositePot + 1);
            }
            Player playerToBeEmptied = null; //jogador a ter os potes esvaziados por causa do fim do jogo
            Player opponent = Kalah.getInstance().getPlayersOpponent(p);
            if (!b.sideHasDiamondsLeft(p.getChoiceRange()[0], p.getChoiceRange()[1]))
                playerToBeEmptied = opponent;
            else if (!b.sideHasDiamondsLeft(opponent.getChoiceRange()[0], opponent.getChoiceRange()[1]))
                playerToBeEmptied = p;
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

            b.notifyObservers();
            return currPot;
        }
    }
    
    private void changePlayer() {
        currPlayer++;
        if (currPlayer == players.length)
            currPlayer = 0;
    }
    
    private ArrayList<Player> getWinners() {
        //pega o vencedor se houver
        ArrayList<Player> winners = new ArrayList<>();
        if (!board.sideHasDiamondsLeft(players[0].getChoiceRange()[0], players[0].getChoiceRange()[1]) ||
            !board.sideHasDiamondsLeft(players[1].getChoiceRange()[0], players[1].getChoiceRange()[1])) {
            int player1Diamonds = board.getPots()[players[0].getKalah()].getDiamonds();
            int player2Diamonds = board.getPots()[players[1].getKalah()].getDiamonds();
            if (player1Diamonds >= player2Diamonds)
                winners.add(players[0]);
            if (player2Diamonds >= player1Diamonds)
                winners.add(players[1]);
        }
        return winners;
    }
    
}
