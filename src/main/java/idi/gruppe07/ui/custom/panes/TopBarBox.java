package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.ui.custom.widgets.GameTimerBar;
import idi.gruppe07.ui.session.Session;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class TopBarBox extends BorderPane {
  private final GameTimerBar gameTimerBar;

  public TopBarBox(Session session) {

    this.setPadding(new javafx.geometry.Insets(0, 16, 0, 16));
    Label titleLabel = new Label("MILLIONS");
    titleLabel.styleProperty().bind(this.heightProperty()
        .multiply(0.66)
        .asString("-fx-font-size: %.2fpx;"));
    titleLabel.getStyleClass().add("top-bar-label");
    getStyleClass().add("top-bar");
    gameTimerBar = new GameTimerBar(session);
    setLeft(titleLabel);
    setRight(gameTimerBar);
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
