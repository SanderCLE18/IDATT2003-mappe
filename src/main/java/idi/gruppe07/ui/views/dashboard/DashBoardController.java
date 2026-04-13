package idi.gruppe07.ui.views.dashboard;

import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewController;
import idi.gruppe07.ui.views.ViewElement;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

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
      button.setOnAction(_->{

      });
    });

    getViewElement().getSideBar().getTopBarBox().getPauseResumeButton().setOnAction(_->{
      getSession().getTimer().pauseTimer(getSession().getExchange(),  getSession().getPlayer());
      getViewElement().getSideBar().getTopBarBox().getGameTimerBar().toggleTimer();
    });

    List<Button> speedButtons = new ArrayList<>(getViewElement().getSideBar().getTopBarBox().getGameTimerBar().getSpeedButtons());
    for  (Button button : speedButtons) {
      String text = button.getText();
      int value = Integer.parseInt(text.replaceAll("[^0-9]", ""));
      button.setOnAction(_ -> getSession().getTimer().setSpeedMultiplier(value, getSession().getExchange(),  getSession().getPlayer()));
    }
  }
}
