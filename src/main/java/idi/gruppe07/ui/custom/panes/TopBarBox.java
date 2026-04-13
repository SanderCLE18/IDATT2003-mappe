package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.ui.custom.widgets.GameTimerBar;
import idi.gruppe07.ui.session.Session;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TopBarBox extends HBox {
  private final GameTimerBar gameTimerBar;

  public TopBarBox(Session session) {
    super(5);
    getStyleClass().add("top-bar");
    gameTimerBar = new GameTimerBar(session);
    this.getChildren().add(gameTimerBar);
    HBox.setHgrow(this, Priority.ALWAYS);
  }

  public Button getPauseResumeButton(){
    return gameTimerBar.getPauseResumeButton();
  }

  /**@return gameTimerBar obhect*/
  public GameTimerBar getGameTimerBar(){
    return gameTimerBar;
  }

}
