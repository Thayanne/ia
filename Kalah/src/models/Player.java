package models;

public abstract class Player {
    
    protected String name;
    protected int kalah;
    protected int[] choiceRange = new int[2];
    
    public Player(String n, int k, int firstInRange, int lastInRange) {
        name = n;
        kalah = k;
        choiceRange[0] = firstInRange;
        choiceRange[1] = lastInRange;
    }
    
    public int getKalah() {
        return kalah;
    }
    
    public int[] getChoiceRange() {
        return choiceRange;
    }
    
    public abstract int choosePot();
    
    @Override
    public String toString() {
        return name;
    }
    
}
