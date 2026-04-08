package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.utils.Validate;
import javafx.scene.control.Button;

/**
 * Custom button class for the main menu.
 * */
public class MenuButton extends Button {

  /**
   * Constructor for MenuButton.
   *
   * @param text The text to display on the button.
   * @param styleClasses Additional CSS classes to apply to the button.*/
  public MenuButton(String text, String... styleClasses) {
    super(text);
    this.getStyleClass().add("menu-button");

    Validate.that(styleClasses).isNotNull();
    this.getStyleClass().addAll(styleClasses);
  }

  /**
   * Constructor for MenuButton.
   *
   * @param text The text to display on the button.*/
  public MenuButton(String text) {
    super(text);
    this.getStyleClass().add("menu-button");
  }

}
