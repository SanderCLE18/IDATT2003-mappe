package idi.gruppe07;

import idi.gruppe07.utils.StockDataFileReader;
import org.junit.jupiter.api.Test;

class StockDataFileReaderTest {

  //TODO: UPDATE USING RELATIVE PATH!!

  @Test
  void testThatFileIsReadCorrectly() {
    new StockDataFileReader().readStockData("C:\\Users\\herma\\Downloads\\sp500.csv");
  }
}