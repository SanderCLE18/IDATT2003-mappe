package idi.gruppe07.ui.event;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Event broker in the pub/sub model.
 *
 * @see EventSubscriber
 * @see EventPublisher
 *
 */
public final class EventManager {

  /**
   * Map where key is {@link EventType} and value is list of
   * {@link EventSubscriber} objects.
   *
   * <p>Used to identify which subscribers to fire the event towards.</p>
   */
  private final Map<EventType, List<EventSubscriber>> subscriberMap = new EnumMap<>(EventType.class);

  /**
   * Method for adding a subscriber to the subscriber map given an event type.
   *
   * @param subscriber the {@link EventSubscriber} object to add.
   * @param type       the {@link EventType} this subscriber should subscribe to.
   */
  public void addSubscriber(final EventSubscriber subscriber, final EventType type) {
    subscriberMap.computeIfAbsent(type, k -> List.of()).add(subscriber);
  }

  /**
   * Method for removing a subscriber from the subscriber map.
   *
   * @param subscriber the {@link EventSubscriber} object to remove.
   */
  public void removeSubscriber(final EventSubscriber subscriber, final EventType type) {
    subscriberMap.computeIfPresent(type, (key, list) -> {
      list.remove(subscriber);
      return list.isEmpty() ? null : list;
    });
  }

  /**
   * Method for invoking event to all subscriber of that type.
   *
   * @param <T>  the type of event data to send.
   * @param data the data to send.
   */
  public <T> void invokeEvent(final EventData<T> data) {
    for (EventSubscriber subscriber : subscriberMap.get(data.eventType())) {
      subscriber.handleEvent(data);
    }
  }
}
