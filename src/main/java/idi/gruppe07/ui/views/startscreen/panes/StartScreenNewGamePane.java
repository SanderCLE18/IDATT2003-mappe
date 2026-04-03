package idi.gruppe07.ui.views.startscreen.panes;

import idi.gruppe07.ui.views.startscreen.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartScreenNewGamePane extends VBox {
  private final TextField nameInput;
  private final TextField startingCash;
  private final TextField gameName;
  private final Button startButton;
  private final Button cancelButton;


  public StartScreenNewGamePane() {
    super(20);

    this.setAlignment(Pos.CENTER);

    this.getStyleClass().add("side-widget");
    this.setMaxSize(400, 200);
    this.setMinSize(400, 200);

    Label nameLabel = new Label("NEW_GAME");

    nameInput = new TextField();
    nameInput.setPromptText("Your name: ");
    nameInput.getStyleClass().add("menu-button");

    startingCash = new TextField();
    startingCash.setPromptText("Starting amount: ");
    startingCash.getStyleClass().add("menu-button");

    HBox startValues = new HBox(10, nameInput, startingCash);
    gameName = new TextField();
    gameName.setPromptText("Save name: ");
    gameName.getStyleClass().add("menu-button");
    VBox valueBox = new VBox(10, startValues, gameName);

    //text for player name
    //save name
    startButton = new MenuButton("START");
    cancelButton = new MenuButton("ABOR_T");

    HBox controls = new HBox(10, cancelButton, startButton);
    controls.setAlignment(Pos.CENTER);

    this.getChildren().addAll(nameLabel, valueBox, controls);

    this.setVisible(false);
    this.setManaged(false);
  }

  public String getName(){
    return nameInput.getText();
  }

  public TextField getNameInput(){
    return nameInput;
  }

  public String getStartingCash(){
    return startingCash.getText();
  }

  public TextField getStartingCashInput(){
    return startingCash;
  }


  public String getGameName(){
    return gameName.getText();
  }

  public TextField getGameNameInput(){
    return gameName;
  }

  public Button getStartButton(){
    return startButton;
  }

  public Button getCancelButton(){
    return cancelButton;
  }

  public void show(){
    this.setVisible(true);
    this.setManaged(true);
  }
  public void hide(){
    this.setVisible(false);
    this.setManaged(false);
  }
}
