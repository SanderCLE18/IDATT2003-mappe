package idi.gruppe07.ui.views.startscreen.panes;

import javafx.scene.control.Button;

public class SaveFileButton extends Button {

  private final String name;
  public SaveFileButton(String text) {
    super(text);
    this.name = text;
    this.getStyleClass().add("menu-button");
  }
  public void select(){
    this.getStyleClass().add("menu-button-primary");
  }
  public void deselect(){
    this.getStyleClass().remove("menu-button-primary");
  }

  public String getSave() {
    return name;
  }
}
