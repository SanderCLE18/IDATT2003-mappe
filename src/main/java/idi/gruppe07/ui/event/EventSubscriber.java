package idi.gruppe07.ui.event;

/**
 * Interface representing a subscriber in a pub/sub model.
 *
 * @see EventManager
 * @see EventSubscriber
 * */
public interface EventSubscriber {

  /**
   * Method for handling the event subscribed to.
   *
   * @param <T> the type of event data to handle.
   * @param data the actual data to handle.
   *
   */
  <T> void handleEvent(EventData<T> data);
}