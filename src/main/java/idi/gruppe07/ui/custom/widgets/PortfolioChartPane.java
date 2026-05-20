package idi.gruppe07.ui.custom.widgets;

import idi.gruppe07.entities.Portfolio;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.RoundingMode;

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
    HBox labels = new HBox(10, totalValue, ChartUtils.buildChangeLabel(portfolio.getHistoricNetWorth()));

    Label value = new Label("$"+portfolio.getNetWorth().setScale(2, RoundingMode.HALF_UP));
    value.getStyleClass().add("portfolio-chart-pane-label");
    value.styleProperty().bind(this.heightProperty()
        .multiply(0.05)
        .asString("-fx-font-size: %.2fpx;"));


    getChildren().addAll(labels, value);

    chart = ChartUtils.buildLineChart(portfolio.getHistoricNetWorth(),20);
    chart.getStyleClass().add("portfolio-chart-pane-chart");
    this.getChildren().add(chart);

  }

}