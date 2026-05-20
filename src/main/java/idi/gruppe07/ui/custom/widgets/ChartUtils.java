package idi.gruppe07.ui.custom.widgets;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ChartUtils {

  public static LineChart<Number, Number> buildLineChart(List<BigDecimal> values, int amount) {
    LineChart<Number, Number> chart;

    long skip = Math.max(0, values.size() - amount);
    double min = values.stream()
        .skip(skip)
        .mapToDouble(BigDecimal::doubleValue).min().orElse(0);
    double max = values.stream()
        .skip(skip)
        .mapToDouble(BigDecimal::doubleValue).max().orElse(0);
    double padding = (max - min) * 0.05;

    NumberAxis xAxis = new NumberAxis(0, amount, 1);
    NumberAxis yAxis = new NumberAxis(min - padding, max + padding, padding);
    hideAxis(xAxis);
    hideAxis(yAxis);


    chart = new LineChart<>(xAxis, yAxis);
    chart.setCreateSymbols(false);
    setProperties(chart);

    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    int start = Math.max(0, values.size() - amount);
    int size_t = Math.min(values.size(), amount);

    for (int i = 0 ; i < size_t; i++) {
      series.getData().add(new XYChart.Data<>(i, values.get(start + i).doubleValue()));
    }
    chart.getData().add(series);
    return chart;
  }

  private static void hideAxis(NumberAxis axis) {
    axis.setTickLabelsVisible(false);
    axis.setOpacity(0);
  }

  /**
   * Builds a change label based on the history of a stock's price history.
   * Label is green on positive change, and red on negative change
   *
   * @return a {@link Label} with the percentage change of the stock. */
  public static Label buildChangeLabel(List<BigDecimal> history) {
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

  /**
   * Builds and configures a {@link CandleStickChart} using the latest data points from the provided list.
   *
   * @param values a {@code List} of {@link BigDecimal} numbers representing the data points to plot;
   *               ideally contains at least 10 elements for a full chart.
   * @param amount the amount of xValues for the chart. Larger amount gives a larger chart.
   * @return a configured {@link CandleStickChart} containing a series of up to the last n data points. */
  public static CandleStickChart buildCandleChart(List<BigDecimal> values, int amount) {
    CandleStickChart chart;

    long skip = Math.max(0, values.size() - amount);
    double min = values.stream()
        .skip(skip)
        .mapToDouble(BigDecimal::doubleValue).min().orElse(0);
    double max = values.stream()
        .skip(skip)
        .mapToDouble(BigDecimal::doubleValue).max().orElse(0);

    double padding = (max - min) * 0.15;
    int start = Math.max(0, values.size() - amount);
    int size_t = Math.min(values.size(), amount);

    NumberAxis xAxis = new NumberAxis(-0.5, size_t - 0.5, 1);
    NumberAxis yAxis = new NumberAxis(min - padding, max + padding, padding);
    hideAxis(xAxis);
    hideAxis(yAxis);

    chart = new CandleStickChart(xAxis, yAxis);
    chart.getStyleClass().add("portfolio-chart-pane-chart");
    chart.setPadding(new Insets(4,4,4,4));
    setProperties(chart);

    XYChart.Series<Number, Number> series = new XYChart.Series<>();


    for (int i = 0 ; i < size_t; i++) {
      series.getData().add(new XYChart.Data<>(i, values.get(start + i).doubleValue()));
    }

    chart.getData().add(series);
    return chart;
  }

  /**Helper function to set the chart properties.
   *
   * @param chart the chart that needs to be changed.
   * */
  private static void setProperties(XYChart<?,?> chart){
    chart.setLegendVisible(false);
    chart.setHorizontalGridLinesVisible(false);
    chart.setVerticalGridLinesVisible(false);
    chart.setAlternativeRowFillVisible(false);

  }

}
