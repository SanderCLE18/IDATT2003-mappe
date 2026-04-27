package idi.gruppe07;

import idi.gruppe07.utils.StockDataFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import idi.gruppe07.entities.Stock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockDataFileReaderTest {
  InputStream path;

  @BeforeEach
  void setUp() {
    path = getClass().getClassLoader().getResourceAsStream("sp500.csv");
  }

  @Test
  void testThatFileIsReadCorrectly() throws IOException {

    ArrayList<Stock> stocks = new StockDataFileReader().readStockData(path);
    assertEquals(503, stocks.size());
  }

  @Test
  void testThatExceptionIsThrown() {
    InputStream emptyStream = new java.io.ByteArrayInputStream(new byte[0]);

    path = getClass().getClassLoader().getResourceAsStream("sp501.csv");

    assertThrows(FileNotFoundException.class, () -> new StockDataFileReader().readStockData(null));
    assertThrows(IllegalArgumentException.class, () -> new StockDataFileReader().readStockData(emptyStream));
    assertThrows(FileNotFoundException.class, () -> new StockDataFileReader().readStockData(path));

  }

}