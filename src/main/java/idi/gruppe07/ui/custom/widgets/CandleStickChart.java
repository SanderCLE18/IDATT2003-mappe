package idi.gruppe07.ui.custom.widgets;

import javafx.collections.FXCollections;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;

import java.util.List;


public class CandleStickChart extends XYChart<Number, Number> {
  /**
   * Constructs a XYChart given the two axes. The initial content for the chart
   * plot background and plot area that includes vertical and horizontal grid
   * lines and fills, are added.
   *
   * @param axis  X Axis for this XY chart
   * @param axis2 Y Axis for this XY chart
   */
  public CandleStickChart(Axis<Number> axis, Axis<Number> axis2) {
    super(axis, axis2);

    setData(FXCollections.observableArrayList());
  }

  @Override
  protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
    if (item.getNode() != null) {
      getPlotChildren().add(item.getNode());
    }
  }

  @Override
  protected void dataItemRemoved(Data<Number, Number> item, Series<Number, Number> series) {
    if(item.getNode() != null){
      getPlotChildren().remove(item.getNode());
    }
  }

  @Override
  protected void dataItemChanged(Data<Number, Number> item) {

  }

  @Override
  protected void seriesAdded(Series<Number, Number> series, int seriesIndex) {
    for (Data<Number, Number> d : series.getData()) {
      dataItemAdded(series, 0, d);
    }
  }

  @Override
  protected void seriesRemoved(Series<Number, Number> series) {
    for (Data<Number, Number> d : series.getData()){
      dataItemRemoved(d, series);
    }
  }

  @Override
  protected void layoutPlotChildren() {
    for (Series<Number, Number> series : getData()) {
      List<Data<Number, Number>> dataList = series.getData();

      for (int i = 0; i < dataList.size(); i++) {
        Data<Number, Number> currentData = dataList.get(i);


        double closePrice = currentData.getYValue().doubleValue();
        double openPrice = (i == 0) ? closePrice : dataList.get(i - 1).getYValue().doubleValue();


        double yOpen  = getYAxis().getDisplayPosition(openPrice);
        double yClose = getYAxis().getDisplayPosition(closePrice);


        double x = getXAxis().getDisplayPosition(currentData.getXValue());


        Rectangle candle = (Rectangle) currentData.getNode();
        if (candle == null) {
          candle = new Rectangle();
          currentData.setNode(candle);
          if(!getPlotChildren().contains(candle)){
            getPlotChildren().add(candle);
          }
        }


        double slotWidth = Math.abs(
            getXAxis().getDisplayPosition(1) - getXAxis().getDisplayPosition(0)
        );
        double candleWidth = slotWidth * 0.8;

        double candleHeight = Math.abs(yOpen - yClose);

        candle.setWidth(candleWidth);
        candle.setHeight(Math.max(candleHeight, 2));
        candle.setX(x - (candleWidth / 2));
        candle.setY(Math.min(yOpen, yClose));


        if (closePrice >= openPrice) {
          candle.setStyle("-fx-fill: #00ff88; -fx-stroke: #00ff88;");
        } else {
          candle.setStyle("-fx-fill: #ff0000; -fx-stroke: #ff0000;");
        }
      }
    }
  }
}
