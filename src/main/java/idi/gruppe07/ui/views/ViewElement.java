package idi.gruppe07.ui.views;

import idi.gruppe07.ui.session.Session;
import javafx.scene.layout.Pane;

/**
 * Base class for all view elements.,
 *
 * @param <T> The type of {@link Pane} this element is attached to.
 * @see Pane
 */
public abstract class ViewElement<T extends Pane> {

  /**
   * The {@link Pane} that this element is attached to.
   *
   * @see Pane
   */
  private T rootPane;

  /**
   * The name of the view.
   *
   * <p>Used as identification by the {@link ViewManager}.</p>
   */
  private String viewName;

  /**
   *Session object.*/
  private Session session;

  /**
   * Constructor with a name.
   *
   * @param rootPane an instance of type T (defined in the class).
   * @param viewName The name of the view.
   *
   */
  protected ViewElement(final T rootPane, final String viewName) {
    this(rootPane, true);
    this.viewName = viewName;
  }

  /**
   * Constructor with a name and initLoading.
   *
   * @param rootPane    an instance of type T (defined in the class).
   * @param viewName    The name of the view.
   * @param initLoading boolean value representing if the view should be initialized. Default is true.
   *
   */
  protected ViewElement(final T rootPane, final String viewName, boolean initLoading) {
    this(rootPane, initLoading);
    this.viewName = viewName;
  }

  /**
   * Constructor with a name and initLoading.
   *
   * @param rootPane    an instance of type T (defined in the class).
   * @param viewName    The name of the view.
   * @param session     {@link Session} object.
   * @param initLoading boolean value representing if the view should be initialized. Default is true.
   *
   */
  protected ViewElement(final T rootPane, final String viewName, Session session, boolean initLoading) {
    this(rootPane, initLoading);
    this.viewName = viewName;
    this.session = session;
  }

  /**
   * Constructor with a name and initLoading.
   *
   * @param rootPane    an instance of type T (defined in the class).
   * @param viewName    The name of the view.
   * @param session     {@link Session} object.
   *
   */
  protected ViewElement(final T rootPane, final String viewName, Session session) {
    this(rootPane, true);
    this.viewName = viewName;
    this.session = session;
  }

  /**
   * Constructor without a specific name.
   *
   * @param rootPane an instance of type T (defined in the class).
   *
   */
  protected ViewElement(final T rootPane, boolean isInit) {
    setRootPane(rootPane);

    if (isInit) {
      initLayout();
    }

    initStyling();
  }

  /**
   * Getter method for view name.
   *
   * @return the name of this view.
   *
   */
  public String getViewName() {
    return viewName;
  }

  /**
   * Getter method for the root pane.
   *
   * @return the root pane of this view element.
   *
   */
  public T getRootPane() {
    return rootPane;
  }

  /**
   * Setter method for the root pane.
   *
   * @param pane the pane to set the root pane to.
   *
   */
  protected void setRootPane(final T pane) {
    rootPane = pane;
  }

  /**
   * Setter method for the view name.
   *
   * @param name the new name to set this view element to.
   *
   */
  protected void setViewName(final String name) {
    viewName = name;
  }

  /**
   * Abstract method that initializes the layout for the view element.
   *
   */
  protected abstract void initLayout();

  /**
   * Abstract method that initializes the styling for the view element.
   *
   */
  protected abstract void initStyling();

  /**
   * Abstract method that defines how view elements set data.
   *
   * @param <T2> The type of data to set.
   * @param data the data to set.
   *
   */
  public <T2 extends ViewData> void setData(final T2 data) {
    setViewName(data.getSceneName());
  }

}
