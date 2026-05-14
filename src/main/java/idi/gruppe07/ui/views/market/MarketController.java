package idi.gruppe07.ui.views.market;

import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.GameViewController;
import javafx.scene.layout.Pane;

public class MarketController extends GameViewController<Pane, MarketView> {

  public MarketController(MarketView viewElement, EventManager eventManager, Session session) {
    super(viewElement, eventManager, session);
  }

  @Override
  protected void initInteractions() {
    initSideBarInteractions();
  }

}
