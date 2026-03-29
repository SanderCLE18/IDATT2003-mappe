package idi.gruppe07.ui.views;

/**
 * Object containing data about {@link ViewElement} objects.
 *
 * <p>Sent in {@link idi.gruppe07.ui.event.EventData}
 * objects of type SCENE_CHANGE</p>
 *
 * <p>Handled by the {@link ViewManager} to update and change views</p>
 * */
public class ViewData {

  /**
   * Name of the scene
   */
  private final String sceneName;

  /**
   * Constructs a new {@code viewData} object.
   *
   * @param sceneName The name of the scene.*/
  public ViewData(final String sceneName){
    this.sceneName = sceneName;
  }

  /**
   * Getter for scene name
   *
   * @return The name of the scene.
   * */
  public String getSceneName(){
    return sceneName;
  }
}
