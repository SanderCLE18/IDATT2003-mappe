package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.entities.Portfolio;
import idi.gruppe07.entities.Share;
import idi.gruppe07.utils.Validate;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class PortfolioChartPane extends VBox {
  Portfolio portfolio;
  LineChart<Number, Number> chart;


  public PortfolioChartPane(Portfolio portfolio) {
    this.portfolio = portfolio;
    initLayout();

  }
  public void initLayout(){
    Label totalValue = new Label("Total portfolio value");
    getChildren().add(totalValue);
    Label averageValue = new Label("$"+portfolio.getNetWorth());



    buildChart(portfolio.getHistoricNetWorth());

  }

  public void buildChart(List<BigDecimal> values) {
    Validate.that(values).isNotNull();

    double min = values.stream().mapToDouble(BigDecimal::doubleValue).min().orElse(0);
    double max = values.stream().mapToDouble(BigDecimal::doubleValue).max().orElse(0);

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
    int start = Math.max(0, values.size() - 10);
    int size_t = Math.min(values.size(), 10);

    for (int i = 0 ; i < size_t; i++) {
      series.getData().add(new XYChart.Data<>(i, values.get(start + i).doubleValue()));
    }
    chart.getData().add(series);

  }
  public Label buildChangeLabel(List<BigDecimal> history) {
    Label label = new Label();

    if (history.size() < 2) {
      label.setText("N/A");
      label.setStyle("-fx-background-color: grey; -fx-text-fill: white; "
          + "-fx-padding: 6 12 6 12; -fx-background-radius: 6;");
      return label;
    }

    BigDecimal previous = history.get(history.size() - 2);
    BigDecimal current  = history.get(history.size() - 1);

    BigDecimal change = current.subtract(previous)
        .divide(previous, 4, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));

    boolean positive = change.compareTo(BigDecimal.ZERO) >= 0;
    String arrow  = positive ? "▲" : "▼";
    String color  = positive ? "#1a7a3f" : "#a02020";  // adjust to match your theme

    label.setText(String.format("%s %+.1f%%", arrow, change.doubleValue()));
    if (positive) {
      label.getStyleClass().add("portfolio-chart-pane-label-green");
    }
    else{
      label.getStyleClass().add("portfolio-chart-pane-label-red");
    }
    return label;
  }

}
