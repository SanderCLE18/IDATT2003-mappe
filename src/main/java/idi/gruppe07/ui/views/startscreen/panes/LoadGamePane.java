package idi.gruppe07.ui.views.startscreen.panes;

import idi.gruppe07.ui.custom.widgets.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pane for loading a game, purely visual. Logic is handled in StartScreenController*/
public class LoadGamePane extends VBox {

  /**The suffix of the save files.*/
  private static final String SAVE_SUFFIX = ".sav";
  /**The directory where the save files are stored.*/

  private static final String SAVE_DIRECTORY = "saves";

  /**List container for the save files.*/
  private final VBox listContainer;

  /**Buttons for cancel and load.*/
  private final Button cancelButton, loadButton;

  /**The currently selected save file button.*/
  private SaveFileButton selectedButton = null;

  /**
   * Constructor for LoadGamePane.
   * Initializes the layout and populates the list.*/
  public LoadGamePane() {
    super(20);

    this.setAlignment(Pos.CENTER);
    this.getStyleClass().add("start-screen-panel");
    this.setPrefSize(200, 300);
    this.setMaxSize(400, 600);



    Label titleLabel = new Label("LOAD_GAME");
    titleLabel.getStyleClass().add("bolt-label");



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
    loadButton = new MenuButton("LOAD");
    cancelButton.setMaxWidth(Double.MAX_VALUE);
    loadButton.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(cancelButton, Priority.ALWAYS);
    HBox.setHgrow(loadButton, Priority.ALWAYS);

    HBox controlWrapper = new HBox(5, cancelButton, loadButton);
    this.getChildren().addAll(titleLabel, scrollPane, controlWrapper);

    this.setVisible(false);
    this.setManaged(false);
  }

  /**
   * Scans the save directory for files with the configured suffix and populates the list.
   * Shows "No games found..." when the directory isempty or missing.
   */
  public void refresh() {
    listContainer.getChildren().clear();

    List<File> saves = findSaveFiles();

    if (saves.isEmpty()) {
      SaveFileButton entry = new SaveFileButton("No games found...");
      entry.setMaxWidth(Double.MAX_VALUE);
      listContainer.getChildren().add(entry);
    } else {
      for (File save : saves) {
        SaveFileButton entry = new SaveFileButton(save.getName());
        entry.setMaxWidth(Double.MAX_VALUE);
        entry.setOnAction(e -> {
          if(selectedButton != null){
            selectedButton.deselect();
          }
          selectedButton = (SaveFileButton) e.getSource();
          entry.select();
        });
        listContainer.getChildren().add(entry);
      }
    }
  }

  /** @return all files in SAVE_DIRECTORY that end with SAVE_SUFFIX. */
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
        result.addAll(Arrays.asList(files));
      }
    }
    return result;
  }

  /**@return the cancel button.*/
  public Button getCancelButton() {
    return cancelButton;
  }

  /**@return the selected save file.*/
  public String getSelectedSave(){
    return selectedButton.getSave();
  }

  /**@return the load button.*/
  public Button getLoadButton() {
    return loadButton;
  }

  /**@return the currently selected save file button.*/
  public SaveFileButton getSelectedButton() {
    if(selectedButton == null){
      return null;
    }
    return selectedButton;
  }

  /**Shows the pane. Also calls refresh in-case the list needs to be updated.*/
  public void show() {
    refresh();
    this.setVisible(true);
    this.setManaged(true);
  }

  /**Hides the pane. Also deselects the currently selected save file button.*/
  public void hide() {
    this.setVisible(false);
    this.setManaged(false);
    this.selectedButton = null;
  }
}
