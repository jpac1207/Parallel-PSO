package ae.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
	    
	private List<Observer> observadores = new ArrayList<Observer>();
	
	    public void addObserver(Observer obs) {
		    observadores.add(obs);
	    }
	 
	    
	    public void delObserver(Observer obs) {
		    observadores.remove(obs);
	    }
	 
	    public void notifyObservers() {
		    for (Observer o : observadores) {
			    o.update(this);
		    }
	    }
}
