package org.keirobm.rpisweethome.common.events;

/**
 * Interface to declare an event listener for a specific event type.
 * @param <T> Specific event type.
 */
public interface EventListener<T extends BaseEvent> {
    void onEvent(T event);
}
