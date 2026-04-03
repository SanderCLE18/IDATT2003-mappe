package idi.gruppe07.ui.views.startscreen;

import idi.gruppe07.utils.Validate;
import javafx.scene.control.Button;

public class MenuButton extends Button {

  public MenuButton(String text, String... styleClasses) {
    super(text);
    this.getStyleClass().add("menu-button");

    Validate.that(styleClasses).isNotNull();
    this.getStyleClass().addAll(styleClasses);
  }

  public MenuButton(String text) {
    super(text);
    this.getStyleClass().add("menu-button");
  }

}
