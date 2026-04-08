package idi.gruppe07.ui.views.dashboard;

import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.custom.panes.SideBarPane;
import idi.gruppe07.ui.custom.panes.SideBarView;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewElement;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


import java.lang.classfile.Label;
import java.util.List;

/**
 * The "main" view of the application. User will land here with a brief overview of the game.
 */
public class DashBoardView extends ViewElement<Pane> {

  /**
   * Dashboard name
   */
  public static final String DASHBOARD_NAME = "DashboardScreen";

  /**
   * Dashboard controller
   */
  private DashBoardController controller;

  /**
   * Side bar view
   */
  private SideBarView sideBar;

  private SideBarPane pane;
  private DashBoardPane content;
  private final List<NavItem> views;


  public DashBoardView(Session session, List<NavItem> views) {
    this(DASHBOARD_NAME, session, views);
  }

  /**
   * Constructor without a session.
   *
   * @param viewName the name of the view.
   * @param views    the different views displayed in the side bar.
   */
  protected DashBoardView(String viewName, List<NavItem> views) {
    super(new StackPane(), viewName, false);
    this.views = views;
    initLayout();
  }

  /**
   * Constructor with a session.
   *
   * @param viewName the name of the view.
   * @param session  the session of the application.
   * @param views    the different views displayed in the side bar.
   */
  public DashBoardView(String viewName, Session session, List<NavItem> views) {
    super(new StackPane(), viewName, session, false);
    this.views = views;
    initLayout();
    content.update(session);
  }

  /**
   * Initializes the layout of the view.
   */
  @Override
  protected void initLayout() {
    this.content = new DashBoardPane();
    pane = new SideBarPane(this.views);
    sideBar = new SideBarView(pane, content);
  }

  /**
   * Initializes the styling of the view.
   */
  @Override
  protected void initStyling() {

  }

  /**
   * @return the SideBar of the view.
   */
  public SideBarView getSideBar() {
    return sideBar;
  }

  /**
   * @return the controller of the view.
   */
  public DashBoardController getController() {
    return controller;
  }

  /**Sets the controller of the view.*/
  public void setController(DashBoardController controller) {
    this.controller = controller;

  }

  public DashBoardPane getContent() {
    return content;
  }

  private class DashBoardPane extends StackPane {

    public DashBoardPane() {

    }
    public void update(Session session){

    }

  }

}
