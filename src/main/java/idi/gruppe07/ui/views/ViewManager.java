package idi.gruppe07.ui.views;

import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.EventSubscriber;
import idi.gruppe07.ui.event.EventType;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.utils.Validate;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for handling views and view changes
 * <p> Implements {@link EventSubscriber}</p>
 * <p> Listens to events of EventType enum</p>
 */
public class ViewManager implements EventSubscriber {

  /**
   * Map with String keys and {@link ViewElement} values.
   */
  private final Map<String, ViewElement<?>> viewMap;

  /**
   * Deque with String values.
   * */
  private final Deque<String> sceneHistory;

  /**
   * The main stage of the application.*/

  private final Stage stage;

  /**
   * The session of the application.
   * @see Session*/
  private final Session session = new Session();

  /**
   * Current view.*/
  private ViewElement<?> currentView;

  /**
   * Constructor.
   *
   * @param stage        the {@link Stage} object this application is running on.
   * @param eventManager the {@link EventManager} used by this application.
   */
  public ViewManager(final Stage stage, final EventManager eventManager) {
    this.viewMap = new HashMap<>();
    sceneHistory = new ArrayDeque<>();

    eventManager.addSubscriber(this, EventType.SCENE_CHANGE);
    eventManager.addSubscriber(this, EventType.SCENE_BACK);

    this.stage = stage;
  }

  /**
   * Method for adding {@link ViewElement} to the view map.
   *
   * @param view the {@link ViewElement} to add.*/
  public void addView(final ViewElement<?> view) {
    viewMap.put(view.getViewName(), view);
  }

  /**
   * Method for removing a {@link ViewElement} from the view map.*/
  public void removeView(final ViewElement<?> view) {
    viewMap.remove(view.getViewName());
  }
  /**
   * Method for setting the active scene to a view.
   *
   * <p>Sets the root of the current scene to the {@link ViewElement} provided.
   * </p>
   *
   * @param viewElement the {@link ViewElement} to change the scene to.
   *
   */
  public void setScene(final ViewElement<?> viewElement) {
    stage.getScene().setRoot(viewMap.get(viewElement.getViewName()).getRootPane());
    currentView = viewElement;
  }

  /**
   * Method for setting the active scene to a view.
   *
   * <p>Sets the root of the current scene to the {@link ViewElement} associated
   * with the string provided.
   * </p>
   *
   * @param data the id of the {@link ViewElement} to change the scene to.
   * @see EventSubscriber
   * @see EventData
   */
  public void setScene(final ViewData data) {
    ViewElement<?> viewElement = viewMap.get(data.getSceneName());
    viewElement.setData(data);
    stage.getScene().setRoot(viewElement.getRootPane());
    currentView = viewElement;
  }

  /**
   * Method for getting the viewmap.
   *
   * @return the viewmap.*/
  public Map<String, ViewElement<?>> getViewMap() {
    return viewMap;
  }

  /**
   * Getter for the current scene
   *
   * @return the view currently active.*/
  public ViewElement<?> getCurrentView() {
    return currentView;
  }

  /**
   * Handles event of EventType enum.
   *
   * <p>Sets the scene and scene data to the {@link ViewData}
   * object provided.</p>*/
  @Override
  public <T> void handleEvent(final EventData<T> data)  {
    switch (data.eventType()){
      case SCENE_CHANGE -> {

        Validate.that(currentView).isNotNull();
        sceneHistory.push(currentView.getViewName());

      }
      case SCENE_BACK -> {
        if (!sceneHistory.isEmpty()) {
          setScene(new ViewData(sceneHistory.pop()));
        }

      }
    }
  }

  /**
   * Getter for the session.
   *
   * @return the session.*/
  public Session getSession() {
    return session;
  }
}
