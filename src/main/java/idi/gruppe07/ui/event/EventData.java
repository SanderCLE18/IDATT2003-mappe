package idi.gruppe07.ui.event;

/**
 * Data object sent through the event system by the {@link EventManager}.
 *
 * @param <T> the type of data this object represents.
 * @param eventType the event type this object represents.
 * @param data the data this object contains.
 *
 */
public record EventData<T>(EventType eventType, T data) {

}
