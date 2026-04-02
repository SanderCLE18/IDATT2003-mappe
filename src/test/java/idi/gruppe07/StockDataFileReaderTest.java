package idi.gruppe07;

import idi.gruppe07.utils.StockDataFileReader;
import org.junit.jupiter.api.Test;
import idi.gruppe07.entities.Stock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockDataFileReaderTest {

  @Test
  void testThatFileIsReadCorrectly() throws IOException {
    ArrayList<Stock> stocks = new StockDataFileReader().readStockData("src/test/resources/sp500.csv");
    assertEquals(503, stocks.size());
  }

  @Test
  void testThatExceptionIsThrown() {
    assertThrows(NullPointerException.class, () -> new StockDataFileReader().readStockData(null));
    assertThrows(IllegalArgumentException.class, () -> new StockDataFileReader().readStockData(""));
    assertThrows(FileNotFoundException.class, () -> new StockDataFileReader().readStockData("src/test/resources/sp0.csv"));

  }

}