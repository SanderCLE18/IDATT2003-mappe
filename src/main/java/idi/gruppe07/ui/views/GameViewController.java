package idi.gruppe07.ui.views;

import idi.gruppe07.ui.custom.panes.SideBarView;
import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.EventType;
import idi.gruppe07.ui.session.Session;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public abstract class GameViewController<P extends Pane, V extends ViewElement<P> & SideBar>
    extends ViewController<V> {
  public GameViewController(V viewElement, EventManager eventManager, Session session) {
    super(viewElement, eventManager, session);
  }

  protected void initSideBarInteractions() {
    switchViews();
    toggleTimer();
    setGameSpeed();
  }

  /**
   * Wires view switching to the different buttons in the sidebar
   * */
  private void switchViews(){
    getViewElement().getSideBar().getLeftPanel().wireButtons(navItem -> {
      ViewData data = new ViewData(navItem.viewName());
      invoke(
          new EventData<>(EventType.SCENE_CHANGE, data),
          getEventManager()
      );
    });
  }

  /**Toggles whether the game is running or not. */
  private void toggleTimer(){
    getViewElement().getSideBar().getTopBarBox().getPauseResumeButton().setOnAction(_->{
      getSession().getTimer().pauseTimer(getSession());
      getViewElement().getSideBar().getTopBarBox().getGameTimerBar().toggleTimer();
    });
  }

  /**
   * Sets the game speed based on which button is pressed. */
  private void setGameSpeed(){
    List<Button> speedButtons = new ArrayList<>(getViewElement().getSideBar().getTopBarBox().getGameTimerBar().getSpeedButtons());
    for  (Button button : speedButtons) {
      String text = button.getText();
      int value = Integer.parseInt(text.replaceAll("[^0-9]", ""));
      button.setOnAction(_ -> getSession().getTimer().setSpeedMultiplier(value, getSession()));
    }
  }
}


