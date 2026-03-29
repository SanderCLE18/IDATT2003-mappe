package idi.gruppe07.ui.event;

/**
 * Enum representing different event types.
 *
 * <p>Examples:</p>
 * <ul>
 *   <li>Scene changes</li>
 *   <li>Database operations</li>
 *   <li>Other</li>
 * </ul>
 *
 * */
public enum EventType {

  /**
   * Event type representing events that causes the current scene to change.
   *
   * <p>Primarily handled by the active
   * {@link idi.gruppe07.ui.views.ViewManager} object.</p>
   *
   * @see idi.gruppe07.ui.views.ViewManager
   * */
  SCENE_CHANGE,
  SCENE_BACK,
  BOOTSTRAP_COMPLETE

}