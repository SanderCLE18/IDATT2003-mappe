package idi.gruppe07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockDataFileReaderTest {

  @Test
  void testThatFileIsReadCorrectly() {
    new StockDataFileReader("borsen").readStockData("C:\\Users\\herma\\Downloads\\sp500.csv");
  }
}