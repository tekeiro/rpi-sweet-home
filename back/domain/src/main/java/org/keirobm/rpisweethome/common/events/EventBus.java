package org.keirobm.rpisweethome.common.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Static event bus for decoupled communication between domain components.
 *
 * <p>Allows registering {@link EventListener}s bound to a concrete {@link BaseEvent} type
 * and publishing events to be processed by the matching listeners.
 * Dispatch is based on the concrete class of the event, not on {@code BaseEvent},
 * so each listener only receives events of the exact type it was registered for.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * EventBus.register(UserCreatedEvent.class, event -> handleUserCreated(event));
 * EventBus.publish(new UserCreatedEvent(userId));
 * }</pre>
 */
public class EventBus {
    private EventBus() {}

    private static final ConcurrentHashMap<Class<? extends BaseEvent>, List<EventListener<?>>> listeners =
            new ConcurrentHashMap<>();

    /**
     * Publishes an event and dispatches it to all listeners registered for its concrete class.
     *
     * @param event the event to publish; {@code event.getClass()} is used to look up listeners
     */
    @SuppressWarnings("unchecked")
    public static void publish(BaseEvent event) {
        Class<? extends BaseEvent> eventClass = event.getClass();
        List<EventListener<?>> eventListeners = listeners.get(eventClass);
        if (eventListeners == null) return;

        for (EventListener<?> listener : eventListeners) {
            ((EventListener<BaseEvent>) listener).onEvent(event);
        }
    }

    /**
     * Registers a listener for a concrete event type.
     *
     * @param <T>        the event type
     * @param eventClass the concrete class of the event to listen for
     * @param listener   the listener that will handle events of that type
     */
    public static <T extends BaseEvent> void register(Class<T> eventClass, EventListener<T> listener) {
        listeners.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(listener);
    }

    /**
     * Removes a previously registered listener for an event type.
     * Removal is by object identity, so the same instance that was registered must be provided.
     *
     * @param <T>        the event type
     * @param eventClass the concrete class of the event
     * @param listener   the listener instance to remove
     */
    public static <T extends BaseEvent> void unregister(Class<T> eventClass, EventListener<T> listener) {
        List<EventListener<?>> eventListeners = listeners.get(eventClass);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }
}
