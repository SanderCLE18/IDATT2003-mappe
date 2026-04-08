package idi.gruppe07.ui.views.dashboard;

import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewController;
import idi.gruppe07.ui.views.ViewElement;

public class DashBoardController extends ViewController<DashBoardView> {

  /**
   * ViewController constructor.
   *
   * @param viewElement  The {@link ViewElement} object this controller is attached to.
   * @param eventManager The {@link EventManager} object this controller is associated with.
   */
  protected DashBoardController(DashBoardView viewElement, EventManager eventManager) {
    super(viewElement, eventManager);
  }

  /**
   * ViewController constructor.
   *
   * @param viewElement  The {@link ViewElement} object this controller is attached to.
   * @param eventManager The {@link EventManager} object this controller is associated with.
   * @param session      The {@link Session} object this controller is associated with.
   *
   */
  public DashBoardController(DashBoardView viewElement, EventManager eventManager, Session session) {
    super(viewElement, eventManager, session);
  }

  @Override
  protected void initInteractions() {
    getViewElement().getSideBar().getLeftPanel().getViewButtons().forEach(button -> {
      button.setOnAction(e->{
        getViewElement().getSideBar().getLeftPanel().setSelected(button);
      });
    });
  }
  public void navigateTo(NavItem item) {
    getViewElement().getSideBar().setCenter(item.content().get());
  }
}
