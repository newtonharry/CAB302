package patt.ReactorMonitoring;

import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Subject abstract class for Observer pattern.
 * This provides the core implementation to manage a subject's observers.
 *
 * @author Richard Thomas
 *
 */
public abstract class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    /**
     * Being clear that the Subject constructor does nothing.
     */
    public Subject() {
    }

    /**
     * Add an observer to the list that are monitoring this subject.
     * @param o The observer to be added to this subject's list.
     */
    public void attachObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Remove this observer from the list that are monitoring this subject.
     * It is no longer interested in updates to this subject.
     * @param o The observer to remove from this subject's list
     */
    public void detachObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Notify all of the observers monitoring this subject that the subject
     * has been updated.
     */
    public void notifyObservers() {
        ListIterator<Observer> iter = observers.listIterator();
        while (iter.hasNext()) {
            iter.next().update(this);
        }
    }
}