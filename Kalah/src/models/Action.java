/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Julius Caffaro
 */
public class Action {
    private int pote;
    private int valorResult;

    public Action(int i) {
        this.pote = i;
    }

    public Action(){
        
    }
    public int getPote() {
        return pote;
    }

    public void setPote(int pote) {
        this.pote = pote;
    }

    public int getValorResult() {
        return valorResult;
    }

    public void setValorResult(int valorResult) {
        this.valorResult = valorResult;
    }
       
}
