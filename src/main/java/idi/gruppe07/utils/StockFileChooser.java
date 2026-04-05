package idi.gruppe07.utils;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Simple utility class for opening a file chooser.
 */
public class StockFileChooser {

  /**
   * The file chooser.
   */
  private static final FileChooser chooser;

  static {
    final FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv");
    chooser = new FileChooser();
    chooser.setTitle("Open Stock File...");
    chooser.getExtensionFilters().add(filter);
  }

  /**
   * Private constructor to prevent instantiation.
   */
  private StockFileChooser() {
  }

  /**
   * Method for opening a file chooser.
   * <ul>
   *   <li>Checks if the file exists.</li>
   *   <li>Checks if the file is a CSV file.</li>
   *   <li>Returns the path to the selected file.</li>
   *   <li>Returns null if the user cancels the file chooser.</li>
   *   <li>Returns null if the file does not exist or is not a CSV file.</li>
   * </ul>
   *
   * @param stage The stage to open the file chooser on.
   * @return The path to the selected file.
   */
  public static String getStringFromFile(Stage stage) {
    File selectedFile;
    try {
      selectedFile = chooser.showOpenDialog(stage);
      Validate.that(selectedFile).isNotNull();
    } catch (NullPointerException e) {
      return null;
    }

    if (!selectedFile.exists()) {
      return null;
    }
    if (!selectedFile.getName().toLowerCase().endsWith(".csv")) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Please select a CSV file");
      alert.showAndWait();
      return null;
    }

    return selectedFile.getAbsolutePath();

  }

}
