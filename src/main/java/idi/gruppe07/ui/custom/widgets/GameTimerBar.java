package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.session.SessionTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GameTimerBar extends VBox {
  private static final String BAR_STYLE_DEFAULT  = "-fx-accent: #4caf50;";
  private static final String BAR_STYLE_NEAR_END = "-fx-accent: #f44336;";

  private final Session session;
  private final ProgressBar progressBar;
  private final Label weekLabel;

  private final List<Button> speedButtons;
  private final Button pauseResumeButton;

  private final SessionTimer.TickListener tickListener;

  public GameTimerBar(Session session){
    this.session = session;
    progressBar = new ProgressBar(0.0);
    progressBar.setPrefHeight(25);
    HBox.setHgrow(progressBar, Priority.ALWAYS);

    weekLabel = new Label("Week: ");


    pauseResumeButton = new Button("▶ Start");

    speedButtons = List.of(
        new Button("1x"),
        new Button("3x"),
        new Button("5x")
    );


    HBox topBox = new HBox(8, pauseResumeButton, speedButtons.get(0), speedButtons.get(1), speedButtons.get(2));
    topBox.setAlignment(Pos.CENTER_LEFT);

    HBox bottomBox = new HBox(8, progressBar, weekLabel);
    bottomBox.setAlignment(Pos.CENTER);
    HBox.setHgrow(progressBar, Priority.ALWAYS);


    this.getChildren().addAll(topBox, bottomBox);
    getStyleClass().add("timer-bar");

    tickListener = progress -> Platform.runLater(() -> updateUi(progress));
    session.getTimer().addTickListener(tickListener);
  }

  private void updateUi(double progress) {
    progressBar.setProgress(progress);
    progressBar.setStyle(progress >= 0.9 ? BAR_STYLE_NEAR_END : BAR_STYLE_DEFAULT);


    if (session.getExchange() != null) {
      weekLabel.setText("Week " + session.getExchange().getWeek());
    }
  }

  public void toggleTimer() {
    if (session.getTimer().isRunning()) {
      pauseResumeButton.setText("⏸ Pause");
    } else {
      pauseResumeButton.setText("▶ Resume");
    }
  }

  public Button getPauseResumeButton() {
    return pauseResumeButton;
  }
  public List<Button> getSpeedButtons() {
    return speedButtons;
  }
}
