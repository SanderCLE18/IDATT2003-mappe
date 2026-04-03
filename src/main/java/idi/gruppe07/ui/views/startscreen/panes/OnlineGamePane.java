package idi.gruppe07.ui.views.startscreen.panes;

import idi.gruppe07.ui.views.startscreen.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OnlineGamePane extends VBox {

  private static final String SAVE_SUFFIX = ".sav"; // Change to your suffix
  private static final String SAVE_DIRECTORY = "saves"; // Change to your directory

  private final VBox listContainer;
  private final Button cancelButton;
  private final Button connectButton;

  private SaveFileButton selectedButton = null;

  public OnlineGamePane() {
    super(20);

    this.setAlignment(Pos.CENTER);
    this.getStyleClass().add("start-screen-panel");
    this.setPrefSize(200, 300);
    this.setMaxSize(400, 600);

    // --- Title ---
    Label titleLabel = new Label("CONNECT_TO_LOBBY");
    titleLabel.getStyleClass().add("bolt-label");

    // --- Scrollable list ---
    listContainer = new VBox();
    listContainer.setAlignment(Pos.TOP_CENTER);
    listContainer.getStyleClass().add("start-screen-list");
    listContainer.setMaxWidth(Double.MAX_VALUE);

    ScrollPane scrollPane = new ScrollPane(listContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setPrefHeight(400);
    scrollPane.setPrefWidth(200);

    scrollPane.getStyleClass().add("start-screen-panel");


    cancelButton = new MenuButton("CANCEL");
    connectButton = new MenuButton("C∅NNECT");
    cancelButton.setMaxWidth(Double.MAX_VALUE);
    connectButton.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(cancelButton, Priority.ALWAYS);
    HBox.setHgrow(connectButton, Priority.ALWAYS);

    HBox controlWrapper = new HBox(5, cancelButton, connectButton);
    this.getChildren().addAll(titleLabel, scrollPane, controlWrapper);

    this.setVisible(false);
    this.setManaged(false);
  }

  /**
   * Scans the save directory for files with the configured suffix and
   * populates the list.
   * Shows "No games found..." when the directory is
   * empty or missing.
   */
  public void refresh() {
    listContainer.getChildren().clear();

    List<Object> lobbies = findLobbies();

    if (lobbies.isEmpty()) {
      SaveFileButton entry = new SaveFileButton("No lobbies found...");
      entry.setMaxWidth(Double.MAX_VALUE);
      listContainer.getChildren().add(entry);
    } else {
      for (Object obj : lobbies) {
        //TODO: Implement multiplayer lobby selection
      }
    }
  }

  /** Returns all files in SAVE_DIRECTORY that end with SAVE_SUFFIX. */
  private List<Object> findLobbies() {
    List<Object> result = new ArrayList<>();

    return result;
  }

  public Button getCancelButton() {
    return cancelButton;
  }

  public String getSelectedSave(){
    return selectedButton.getSave();
  }

  public Button getConnectButton() {
    return connectButton;
  }
  public SaveFileButton getSelectedButton() {
    if(selectedButton == null){
      return null;
    }
    return selectedButton;
  }

  public void show() {
    refresh(); // Always re-scan when opening
    this.setVisible(true);
    this.setManaged(true);
  }

  public void hide() {
    this.setVisible(false);
    this.setManaged(false);
    this.selectedButton = null;
  }
}
