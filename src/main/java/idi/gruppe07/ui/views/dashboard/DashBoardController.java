package idi.gruppe07.ui.views.dashboard;

import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.EventType;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class DashBoardController extends GameViewController<Pane, DashBoardView> {

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

  /**
   * {@inheritDoc}
   * */
  @Override
  protected void initInteractions() {
    initSideBarInteractions();
  }
}


