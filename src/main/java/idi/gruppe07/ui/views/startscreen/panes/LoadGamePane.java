package idi.gruppe07.ui.views.startscreen.panes;

import idi.gruppe07.ui.views.startscreen.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadGamePane extends VBox {

  private static final String SAVE_SUFFIX = ".sav"; // Change to your suffix
  private static final String SAVE_DIRECTORY = "saves"; // Change to your directory

  private final VBox listContainer;
  private final Button cancelButton;

  private Runnable onCancelAction;
  private java.util.function.Consumer<String> onLoadAction;

  public LoadGamePane() {
    super(20);

    this.setAlignment(Pos.CENTER);
    this.getStyleClass().add("side-widget");
    this.setMaxSize(800, 300);
    this.setMinSize(400, 300);

    // --- Title ---
    Label titleLabel = new Label("LOAD_GAME");

    // --- Scrollable list ---
    listContainer = new VBox(5);
    listContainer.setAlignment(Pos.TOP_CENTER);

    ScrollPane scrollPane = new ScrollPane(listContainer);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(680);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.getStyleClass().add("menu-button");

    // --- Cancel button ---
    cancelButton = new MenuButton("CANCEL");
    cancelButton.setOnAction(e -> {
      if (onCancelAction != null) onCancelAction.run();
    });

    this.getChildren().addAll(titleLabel, scrollPane, cancelButton);

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

    List<File> saves = findSaveFiles();

    if (saves.isEmpty()) {
      Label empty = new Label("No games found...");
      empty.getStyleClass().add("menu-button");
      empty.setMaxWidth(Double.MAX_VALUE);
      listContainer.getChildren().add(empty);
    } else {
      for (File save : saves) {
        String displayName = save.getName()
            .replace(SAVE_SUFFIX, "");

        Button entry = new MenuButton(displayName);
        entry.setMaxWidth(Double.MAX_VALUE);
        entry.setOnAction(e -> {
          if (onLoadAction != null) onLoadAction.accept(save.getAbsolutePath());
        });

        listContainer.getChildren().add(entry);
      }
    }
  }

  /** Returns all files in SAVE_DIRECTORY that end with SAVE_SUFFIX. */
  private List<File> findSaveFiles() {
    List<File> result = new ArrayList<>();

    File dir = new File(SAVE_DIRECTORY);

    if(!dir.exists()){
      boolean succ = dir.mkdirs();
      if(!succ) {
        System.err.println("Failed to create directory");
      }
    }

    if (dir.isDirectory()) {
      File[] files = dir.listFiles(
          (d, name) -> name.endsWith(SAVE_SUFFIX)
      );
      if (files != null) {
        for (File f : files) result.add(f);
      }
    }
    return result;
  }

  // --- Callback setters ---

  /** Called when the user clicks the Cancel button. */
  public void setOnCancelAction(Runnable action) {
    this.onCancelAction = action;
  }

  /**
   * Called when the user clicks a save entry.
   * The consumer receives the absolute path of the chosen save file.
   */
  public void setOnLoadAction(java.util.function.Consumer<String> action) {
    this.onLoadAction = action;
  }

  public Button getCancelButton() {
    return cancelButton;
  }

  public void show() {
    refresh(); // Always re-scan when opening
    this.setVisible(true);
    this.setManaged(true);
  }

  public void hide() {
    this.setVisible(false);
    this.setManaged(false);
  }
}
