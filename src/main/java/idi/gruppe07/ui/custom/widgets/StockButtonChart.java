package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StockButtonChart extends VBox {

  private final Share share;
  private final Stock stock;

  public StockButtonChart(Share share) {
    this.share = share;
    this.stock = share.getStock();


    List<BigDecimal> lastTen = fetchLastTenPrices();


    VBox headerBox = createHeaderBox(lastTen);
    LineChart<Number, Number> chart = ChartUtils.buildLineChart(lastTen, 10);
    chart.getStyleClass().add("portfolio-chart-pane-chart");
    HBox footerBox = createFooterBox();


    this.getChildren().addAll(headerBox, chart, footerBox);
    this.setSpacing(5);
  }

  private List<BigDecimal> fetchLastTenPrices() {
    List<BigDecimal> history = stock.getHistoricalPrices();
    return history.stream()
        .skip(Math.max(0, history.size() - 10))
        .toList();
  }

  /**Creates the header box for the stock button chart
   *
   * @return the header box - a VBox*/
  private VBox createHeaderBox(List<BigDecimal> lastTen) {

    Label symbol = new Label(stock.getSymbol());
    Label price = new Label("$" + stock.getPrice().setScale(2, RoundingMode.HALF_UP));
    styleHeaderLabels(symbol, price);
    symbol.getStyleClass().add("text-medium-bold");
    price.getStyleClass().add("text-medium-bold");

    HBox topRow = new HBox(10, symbol, price);
    setupHGrow(symbol, price);

    Label company = new Label(stock.getCompany());
    Label changeLabel = calculateChangeLabel(lastTen);
    setupHGrow(company, changeLabel);
    company.getStyleClass().add("text-medium-bold");
    changeLabel.getStyleClass().add("text-medium-bold");
    HBox bottomRow = new HBox(10, company, changeLabel);

    return new VBox(5, topRow, bottomRow);
  }

  /**Creates the horizontally aligned footer box
   *
   * @return the horizontally aligned footer box*/
  private HBox createFooterBox() {
    Label holdings = new Label("Holdings: " + share.getQuantity());
    Label totalValue = new Label("$" + share.getQuantity().multiply(stock.getPrice())
        .setScale(2, RoundingMode.HALF_UP));

    setupHGrow(holdings, totalValue);
    holdings.setAlignment(Pos.BOTTOM_LEFT);
    totalValue.setAlignment(Pos.BOTTOM_RIGHT);
    holdings.getStyleClass().add("text-smaller-regular");
    totalValue.getStyleClass().add("text-smaller-regular");

    return new HBox(10, holdings, totalValue);
  }


  /**
   * Centers two/spaces out two Nodes inside another Node */
  private void setupHGrow(Node left, Node right) {
    HBox.setHgrow(left, Priority.ALWAYS);
    HBox.setHgrow(right, Priority.ALWAYS);
    ((Region) left).setMaxWidth(Double.MAX_VALUE);
    ((Region) right).setMaxWidth(Double.MAX_VALUE);
  }


  private void styleHeaderLabels(Label s, Label p) {
    String style = "-fx-font-size: 14; " +
        "-fx-font-fill: #798091";
    s.setStyle(style);
    p.setStyle(style);
    s.setAlignment(Pos.TOP_LEFT);
    p.setAlignment(Pos.TOP_RIGHT);
  }

  private Label calculateChangeLabel(List<BigDecimal> prices) {
    double start = prices.getFirst().doubleValue();
    double end = prices.getLast().doubleValue();
    double change = (end - start) / start * 100;

    Label label = new Label(String.format("%.2f%%", change));
    if (change > 0) label.setStyle("-fx-text-fill: #00ff88");
    else if (change < 0) label.setStyle("-fx-text-fill: #ff0000");
    label.setAlignment(Pos.TOP_RIGHT);
    return label;
  }
}