package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StockButtonChart extends VBox {
  Label name;
  LineChart<Number, Number> chart;


  public StockButtonChart(Share share) {
    Stock stock = share.getStock();
    List<BigDecimal> lastTen = stock.getHistoricalPrices()
        .stream()
        .skip(Math.max(0, stock.getHistoricalPrices().size()-10))
        .toList();

    name = new Label(stock.getSymbol());
    name.setAlignment(Pos.TOP_LEFT);
    Label currentValue = new Label("$" + stock.getPrice().setScale(2, RoundingMode.HALF_UP));
    currentValue.setAlignment(Pos.TOP_RIGHT);
    HBox.setHgrow(name, Priority.ALWAYS);
    HBox.setHgrow(currentValue, Priority.ALWAYS);
    HBox nameValueBox = new HBox(10, name, currentValue);

    Label fullName = new Label(stock.getCompany());
    double change = (lastTen.get(9).doubleValue() - lastTen.get(0).doubleValue()) / lastTen.get(0).doubleValue() * 100;
    Label changeLabel = new Label(change + "%");
    if (change > 0) {
      changeLabel.getStyleClass().add("green-text");
    }
    if (change < 0) {
      changeLabel.getStyleClass().add("red-text");
    }

    HBox.setHgrow(fullName, Priority.ALWAYS);
    HBox.setHgrow(changeLabel, Priority.ALWAYS);
    HBox changeBox = new HBox(10, fullName, changeLabel);
    HBox infoBox = new HBox(10, nameValueBox, changeBox);


    double min = lastTen.stream().mapToDouble(BigDecimal::doubleValue).min().orElse(0);
    double max = lastTen.stream().mapToDouble(BigDecimal::doubleValue).max().orElse(0);

    double padding = (max - min) * 0.05;

    NumberAxis xAxis = new NumberAxis(0, 9, 1);
    NumberAxis yAxis = new NumberAxis(min - padding, max + padding, padding);

    xAxis.setTickLabelsVisible(false);
    xAxis.setOpacity(0);
    yAxis.setTickLabelsVisible(false);
    yAxis.setOpacity(0);

    chart = new LineChart<>(xAxis, yAxis);
    chart.setCreateSymbols(false);
    chart.setLegendVisible(false);
    chart.setHorizontalGridLinesVisible(false);
    chart.setVerticalGridLinesVisible(false);
    chart.setAlternativeRowFillVisible(false);

    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    for (int i = 0; i < 10; i++) {
      series.getData().add(new XYChart.Data<>(i, lastTen.get(i).doubleValue()));
    }
    chart.getData().add(series);

    Label holdings = new Label("Holdings : " + share.getQuantity().toString());
    holdings.setAlignment(Pos.BOTTOM_LEFT);
    Label value = new Label("$" + share.getQuantity().multiply(stock.getPrice()).setScale(2, RoundingMode.HALF_UP));
    value.setAlignment(Pos.BOTTOM_RIGHT);
    HBox shareInfoBox = new HBox(10, holdings, value);
    shareInfoBox.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(holdings, Priority.ALWAYS);
    HBox.setHgrow(value, Priority.ALWAYS);
    getChildren().addAll(infoBox,chart,shareInfoBox);
  }

}
