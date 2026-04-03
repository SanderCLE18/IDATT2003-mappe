package idi.gruppe07.ui.views;

import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.EventPublisher;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.utils.Validate;

/**
 * Handles logic and event publishing for {@link ViewElement} objects.
 *
 * <p>Implements {@link EventPublisher}</p>
 *
 * @param <T> The type of {@link ViewElement} this controller is attached to.
 *           We use generics because we want to call methods that may be
 *           specific for certain view element objects,
 *           and we want to reduce casting.
 *
 * @see ViewElement
 * @see EventPublisher
 * @see EventManager
 * */
public abstract class ViewController <T extends ViewElement<?>> implements EventPublisher {

  /**
   * The {@link EventManager} object that this controller is associated with.
   *
   * @see EventManager
   */
  private final EventManager eventManager;

  /**
   * The object this controller attaches to
   */
  private T viewElement;

  /**
   * Stored session object.
   */
  private Session session;

  /**
   * ViewController constructor.
   *
   * @param viewElement The {@link ViewElement} object this controller is attached to.
   * @param eventManager The {@link EventManager} object this controller is associated with.
   */
  protected ViewController(final T viewElement, final EventManager eventManager) {
    Validate.that(viewElement).isNotNull();

    this.viewElement = viewElement;
    this.eventManager = eventManager;
    initInteractions();
  }

  /**
   * ViewController constructor.
   *
   * @param viewElement The {@link ViewElement} object this controller is attached to.
   * @param eventManager The {@link EventManager} object this controller is associated with.
   * @param session The {@link Session} object this controller is associated with.
   * */
  protected ViewController(final T viewElement, final EventManager eventManager, final Session session) {
    this(viewElement, eventManager);
    this.session = session;
  }

  /**
   * Getter method for the event manager.
   *
   * @return the {@link EventManager} object.
   * */
  protected EventManager getEventManager() {
    return eventManager;
  }

  /**
   * Getter method for the current view element.
   *
   * @return the {@link ViewElement} object.
   * */
  public T getViewElement() {
    return viewElement;
  }

  /**
   * Abstract method to initialize logic for the view element.
   * */
  protected abstract void initInteractions();

  @Override
  public final <T> void invoke(final EventData<T> data, final EventManager eventManager) {
    eventManager.invokeEvent(data);
  }

  /**
   * Getter method for the session.
   *
   * @return the {@link Session} object.*/
  public Session getSession() {
    return session;
  }



}
