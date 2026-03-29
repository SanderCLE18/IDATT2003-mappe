package idi.gruppe07.ui.event;
/**
 * Interface representing a publisher in a pub/sub model.
 *
 * @see EventManager
 * @see EventSubscriber
 * */
public interface EventPublisher {

  /**
   * Method for triggering (invoking) the event.
   *
   * @param <T> the type of event data to invoke.
   * @param data the data to invoke.
   * @param eventManager the {@link EventManager} object to use as broker when
   *                     invoking event.
   * */

  <T> void invoke(EventData<T> data, EventManager eventManager);
}
