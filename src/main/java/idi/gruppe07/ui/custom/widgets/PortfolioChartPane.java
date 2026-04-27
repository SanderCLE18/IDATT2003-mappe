package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.entities.Portfolio;
import idi.gruppe07.utils.Validate;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PortfolioChartPane extends VBox {
  Portfolio portfolio;
  LineChart<Number, Number> chart;


  public PortfolioChartPane(Portfolio portfolio) {
    this.portfolio = portfolio;
    this.getStyleClass().add("portfolio-chart-pane");
    if (portfolio != null){
      initLayout();
    }
  }

  public void initLayout(){

    Label totalValue = new Label("Total portfolio value");
    totalValue.getStyleClass().add("portfolio-chart-pane-label");
    totalValue.styleProperty().bind(this.heightProperty()
        .multiply(0.05)
        .asString("-fx-font-size: %.2fpx;"));
    HBox labels = new HBox(10, totalValue, buildChangeLabel(portfolio.getHistoricNetWorth()));

    Label value = new Label("$"+portfolio.getNetWorth().setScale(2, RoundingMode.HALF_UP));
    value.getStyleClass().add("portfolio-chart-pane-label");
    value.styleProperty().bind(this.heightProperty()
        .multiply(0.05)
        .asString("-fx-font-size: %.2fpx;"));


    getChildren().addAll(labels, value);

    buildChart(portfolio.getHistoricNetWorth());
    chart.getStyleClass().add("portfolio-chart-pane-chart");
    this.getChildren().add(chart);

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
    BigDecimal current  = history.getLast();

    BigDecimal change = current.subtract(previous)
        .divide(previous, 4, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100));

    boolean positive = change.compareTo(BigDecimal.ZERO) >= 0;
    String arrow  = positive ? "▲" : "▼";

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
