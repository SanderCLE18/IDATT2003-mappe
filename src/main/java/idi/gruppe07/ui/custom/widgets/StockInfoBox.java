package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.entities.Stock;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A box containing different info regarding its associated stock. The box will contain a button
 * which sends the user to the purchase stock view.
 */
public class StockInfoBox extends GridPane {

  private final Stock stock;

  private Button purchaseButton;

  public StockInfoBox(Stock stock) {
    this.stock = stock;
    initialize();
  }

  /**
   * Creates a VBox that contains a specified text.
   *
   * @return A text box
   */
  private VBox createTextBox(String string) {
    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.setAlignment(Pos.CENTER_LEFT);
    Label label = new Label(string);
    label.getStyleClass().add("text-medium-bold");
    vBox.setPadding(new Insets(10, 10, 10, 10));
    vBox.getChildren().addAll(label);

    return vBox;
  }

  /**
   * Creates a symbols box, basically a label but with a black background.
   *
   * @return a box for the stock symbols.
   */
  private VBox createSymbolsBox(Stock stock) {
    Label label = new Label(stock.getSymbol());
    label.getStyleClass().addAll("text-medium-bold", "text-blue");

    VBox symbolsBox = new VBox(label);
    symbolsBox.setAlignment(Pos.CENTER);
    symbolsBox.setStyle("-fx-background-color: black; -fx-min-width: 50; -fx-min-height: 50;");

    return symbolsBox;
  }

  /** Sets up the grid pane's columns, first generates constraints and then sets widths. */
  private void setupColumns() {
    List<ColumnConstraints> cols = Stream.generate(ColumnConstraints::new).limit(6).toList();

    cols.getFirst().setPercentWidth(10);
    cols.get(1).setPercentWidth(30);
    cols.get(2).setPercentWidth(15);
    cols.get(3).setPercentWidth(20);
    cols.get(4).setPercentWidth(5);
    cols.getLast().setPercentWidth(10);

    for (var col : cols) {
      col.setHalignment(HPos.CENTER);
      col.setFillWidth(true);
      getColumnConstraints().add(col);
    }
  }

  private void setupRows(){
    RowConstraints row = new RowConstraints();
    row.setPercentHeight(100);
    row.setVgrow(Priority.ALWAYS);
    this.getRowConstraints().add(row);
  }

  /**
   * Creates a purchase button for use in object initialization
   *
   * @return a purchase button
   */
  private VBox makePurchaseButton() {
    Image image =
        new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/images/shoppingCartWhite.png")),
            64,
            64,
            true,
            true);
    ImageView imageView = new ImageView(image);


    imageView.setFitHeight(40);
    imageView.setPreserveRatio(true);

    purchaseButton = new Button();
    purchaseButton.setGraphic(imageView);
    purchaseButton.prefWidthProperty().bind(purchaseButton.prefHeightProperty());
    purchaseButton.getStyleClass().add("button-generic");

    VBox vBox = new VBox(purchaseButton);
    vBox.setAlignment(Pos.CENTER_RIGHT);
    return vBox;
  }

  /** initializes the StockInfoBox's layout and fils with content */
  private void initialize() {
    setupColumns();
    setupRows();

    getStyleClass().add("dashboard-secondary-pane");

    setPadding(new Insets(8, 16, 8, 16));
    setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(this, Priority.ALWAYS);
    VBox.setVgrow(this, Priority.NEVER);

    String price = stock.getPrice().setScale(2, RoundingMode.HALF_UP).toString();

    LineChart<Number, Number> lineChart = ChartUtils.buildChart(stock.getHistoricalPrices());
    lineChart.getStyleClass().add("portfolio-chart-pane-chart");
    lineChart.setMinSize(0, 0);
    lineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    lineChart.setPrefWidth(0);

    add(createSymbolsBox(stock), 0, 0);
    add(createTextBox(stock.getCompany()), 1, 0);
    add(createTextBox(" $" + price), 2, 0);
    add(ChartUtils.buildChangeLabel(stock.getHistoricalPrices()), 3, 0);
    add(lineChart, 4, 0);
    add(makePurchaseButton(), 5, 0);

    getChildren()
        .forEach(
            node -> {
              GridPane.setHalignment(node, HPos.LEFT);
              GridPane.setValignment(node, VPos.CENTER);
            });
    GridPane.setHalignment(getChildren().getLast(), HPos.RIGHT);
  }

  /**
   * Returns the purchase button for use with a controller
   *
   * @return the purchase stock button
   */
  public Button getPurchaseButton() {
    return purchaseButton;
  }

  /**Returns the button's stock
   *
   * @return the buttons stock.*/
  public Stock getStock() {
    return stock;
  }
}
