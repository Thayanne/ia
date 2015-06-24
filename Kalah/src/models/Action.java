
package models;

public class Action {
    private int pot;
    private int valueResult;

    public Action(int i) {
        this.pot = i;
    }

    public Action(){
        
    }
    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getValueResult() {
        return valueResult;
    }

    public void setValueResult(int valorResult) {
        this.valueResult = valorResult;
    }
       
}
