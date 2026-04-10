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

public class GameTimerBar extends VBox {
  private static final String BAR_STYLE_DEFAULT  = "-fx-accent: #4caf50;";
  private static final String BAR_STYLE_NEAR_END = "-fx-accent: #f44336;";

  private final Session session;
  private final ProgressBar progressBar;
  private final Label weekLabel;

  private final Button pauseResumeButton;

  private final SessionTimer.TickListener tickListener;

  public GameTimerBar(Session session){
    this.session = session;
    progressBar = new ProgressBar(0.0);
    progressBar.setPrefHeight(25);
    HBox.setHgrow(progressBar, Priority.ALWAYS);

    weekLabel = new Label("Week: ");

    pauseResumeButton = new Button("▶ Start");
    pauseResumeButton.setOnAction(e -> {
      toggleTimer();
    });

    HBox topBox = new HBox(8, weekLabel, pauseResumeButton);
    topBox.setAlignment(Pos.CENTER_LEFT);

    HBox bottomBox = new HBox(8, progressBar, pauseResumeButton);
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

  private void toggleTimer() {
    if (session.getTimer().isRunning()) {
      pauseResumeButton.setText("▶ Resume");
    } else {
      pauseResumeButton.setText("⏸ Pause");
    }
  }

  public Button getPauseResumeButton() {
    return pauseResumeButton;
  }
}
