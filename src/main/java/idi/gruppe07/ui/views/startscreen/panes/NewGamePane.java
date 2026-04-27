package idi.gruppe07.ui.views.startscreen.panes;

import idi.gruppe07.ui.custom.widgets.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Pane for creating a new game, purely visual.*/
public class NewGamePane extends VBox {

  /**The textfields used for input*/
  private final TextField nameInput, startingCash, gameName;

  /**Buttons for starting or hiding the new game pane*/
  private final Button startButton, customGameButton, cancelButton;

  /**Constructs a new {@code NewGamePane}*/
  public NewGamePane() {
    super(20);

    this.setAlignment(Pos.CENTER);

    this.getStyleClass().add("start-screen-panel");
    this.setMaxSize(600, 300);
    this.setPrefSize(500, 250);
    this.setMinSize(400, 200);

    Label nameLabel = new Label("NEW_GAME");
    nameLabel.getStyleClass().add("bolt-label");

    nameInput = new TextField();
    nameInput.setPromptText("Your name: ");
    nameInput.setMaxWidth(Double.MAX_VALUE);
    nameInput.getStyleClass().add("menu-button");

    startingCash = new TextField();
    startingCash.setPromptText("Starting amount: (Default: 10,000)");
    startingCash.setStyle("-fx-font-size: 12px;");
    startingCash.setMaxWidth(Double.MAX_VALUE);
    startingCash.getStyleClass().add("menu-button");

    HBox startValues = new HBox(10, nameInput, startingCash);
    HBox.setHgrow(nameInput, Priority.ALWAYS);
    HBox.setHgrow(startingCash, Priority.ALWAYS);
    startValues.setMaxWidth(Double.MAX_VALUE);
    gameName = new TextField();
    gameName.setPromptText("Save name: ");
    gameName.getStyleClass().add("menu-button");
    VBox valueBox = new VBox(10, startValues, gameName);

    //text for player name
    //save name
    startButton = new MenuButton("START", "menu-button-primary");
    startButton.setMaxWidth(Double.MAX_VALUE);
    customGameButton = new MenuButton("CUSTOM__GAME", "menu-button");
    customGameButton.setMaxWidth(Double.MAX_VALUE);
    cancelButton = new MenuButton("ABOR__T", "menu-button");
    cancelButton.setMaxWidth(Double.MAX_VALUE);

    HBox controls = new HBox(5, cancelButton, customGameButton);
    HBox.setHgrow(cancelButton, Priority.ALWAYS);     // Allow expansion
    HBox.setHgrow(customGameButton, Priority.ALWAYS);
    controls.setMaxWidth(Double.MAX_VALUE);

    controls.setAlignment(Pos.CENTER);

    this.getChildren().addAll(nameLabel, valueBox, startButton, controls);

    this.setVisible(false);
    this.setManaged(false);
  }

  /**@return the name of the game*/
  public String getName(){
    return nameInput.getText();
  }

  /**@return the textfield for the name of the game*/
  public TextField getNameInput(){
    return nameInput;
  }

  /**@return the starting cash of the game*/
  public String getStartingCash(){
    return startingCash.getText();
  }

  /**@return the textfield for the starting cash of the game*/
  public TextField getStartingCashInput(){
    return startingCash;
  }

  /**@return the name of the save file*/
  public String getGameName(){
    return gameName.getText();
  }

  /**@return the textfield for the save file name*/
  public TextField getGameNameInput(){
    return gameName;
  }

  /**@return the start button*/
  public Button getStartButton(){
    return startButton;
  }

  /**@return the custom game button*/
  public Button getCustomGameButton(){
    return customGameButton;
  }

  /**@return the cancel button*/
  public Button getCancelButton(){
    return cancelButton;
  }

  /**Shows the new game pane*/
  public void show(){
    this.setVisible(true);
    this.setManaged(true);
  }

  /**Hides the new game pane*/
  public void hide(){
    this.setVisible(false);
    this.setManaged(false);
  }
}
