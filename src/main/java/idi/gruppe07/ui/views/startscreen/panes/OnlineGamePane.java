package idi.gruppe07.ui.views.startscreen.panes;

import idi.gruppe07.ui.custom.widgets.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Pane for connecting to an existing game, purely visual. Currently defunct*/
public class OnlineGamePane extends VBox {

  /**The suffix of the save files.*/
  private static final String SAVE_SUFFIX = ".sav";
  /**The directory where the save files are stored.*/
  private static final String SAVE_DIRECTORY = "saves";

  /**List container for the save files.*/
  private final VBox listContainer;

  /**Buttons for cancel and connect.*/
  private final Button cancelButton, connectButton;

  /**Selected lobby, to be changed.*/
  private SaveFileButton selectedButton = null;

  /** Constructor for OnlineGamePane. Initializes the layout and populates the list.*/
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

  /** Functions for finding lobbies. Currently unimplemented.*/
  private List<Object> findLobbies() {
    List<Object> result = new ArrayList<>();

    return result;
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

  /**Shows the pane.*/
  public void show() {
    refresh();
    this.setVisible(true);
    this.setManaged(true);
  }

  /**Hides the pane. Also deselects the currently selected lobby*/
  public void hide() {
    this.setVisible(false);
    this.setManaged(false);
    this.selectedButton = null;
  }

  /**@return the cancel button.*/
  public Button getCancelButton() {
    return cancelButton;
  }
  /**@return the selected save file.*/
  public String getSelectedSave(){
    return selectedButton.getSave();
  }

  /**@return the connect button.*/
  public Button getConnectButton() {
    return connectButton;
  }

  /**@return the currently selected save file button.*/
  public SaveFileButton getSelectedButton() {
    if(selectedButton == null){
      return null;
    }
    return selectedButton;
  }
}
