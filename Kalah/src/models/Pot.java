package models;

public class Pot {
    
    private int diamonds;
    
    public Pot() {
        new Pot(0);
    }
    
    public Pot(int c) {
        diamonds = c;
    }
    
    public int getDiamonds() {
        return diamonds;
    }
    
    public void removeDiamonds() {
        diamonds = 0;
    }
    
    public void incrementDiamonds() {
        diamonds++;
    }
    
    public void addDiamonds(int d) {
        diamonds += d;
    }
    
    public boolean isEmpty() {
        if (diamonds == 0)
            return true;
        else
            return false;
    }
    
}
