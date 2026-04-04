package idi.gruppe07.ui.views.startscreen.panes;

import javafx.scene.control.Button;

/**Save file button for the save game pane.</br>
 *
 * Currently unimplemented features:
 * <ul>
 *   <li>Header will peak inside the savefile header and display various information.</li>
 *   <li>The "save" will be a record that's passed in to the saveButton. This way the button will remain stupid and not need to know anything about the save file.</li>
 * </ul>
 */
public class SaveFileButton extends Button {

  /** The name of the save file. */
  private final String name;

  /** Constructor, sets the text and adds the menu-button style class. */
  public SaveFileButton(String text) {
    super(text);
    this.name = text;
    this.getStyleClass().add("menu-button");
  }

  /** Selects the save file button. Purely visual.*/
  public void select(){
    this.getStyleClass().add("menu-button-primary");
  }
  /** Deselects the save file button. Removes the visual selection.*/
  public void deselect(){
    this.getStyleClass().remove("menu-button-primary");
  }

  /**@return the name of the save file.*/
  public String getSave() {
    return name;
  }
}
