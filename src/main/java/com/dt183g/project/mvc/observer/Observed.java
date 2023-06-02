package com.dt183g.project.mvc.observer;

import java.util.ArrayList;

public abstract class Observed {
    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Method for attaching an observer object to listen of object events.
     *
     * @param observer The observer to attach.
     */
    public void attachObserver(Observer observer) {
        if(!this.observers.contains(observer))
            this.observers.add(observer);
    }

    /**
     * Method for detaching an observer.
     *
     * @param observer The observer to detach.
     */
    public void detachObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Method for pushing an event to all attached observers.
     *
     * @param eventName The name of the event.
     * @param data Optional parameter data.
     */
    public void pushEvent(String eventName, Object... data) {
        observers.forEach(o -> o.handleEvent(eventName, data));
    }
}
