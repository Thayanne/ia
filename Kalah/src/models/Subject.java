package models;

import java.util.ArrayList;

public class Subject {
    
    protected ArrayList<Observer> observers;
    
    public Subject() {
        observers = new ArrayList<>();
    }
    
    public void addObserver(Observer o) {
        observers.add(o);
    }
    
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for(Observer ob : observers) {
            ob.updateData();
        }
    }
}
