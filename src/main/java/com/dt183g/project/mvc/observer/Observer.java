package com.dt183g.project.mvc.observer;

/**
 * Interface implementing the "observer" part of the Observer Pattern.
 *
 * @author Albin Eliasson & Martin K. Herkules
 */
public interface Observer {
    /**
     * Method which is triggered by observed objects when an event occurs.
     *
     * @param eventName The name of the event.
     * @param data Optional parameter data.
     */
    void handleEvent(String eventName, Object... data);
}
