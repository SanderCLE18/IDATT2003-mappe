package idi.gruppe07.utils;

import idi.gruppe07.entities.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Utility class for reading stock data from a CSV file.
 * */
public class StockDataFileReader {

  /**
   * Reads stock data from a CSV file and returns an ArrayList of Stock objects.
   *
   * @param inputStream Replacement for string, takes in a resource instead of an expected path
   * @return An ArrayList of Stock objects.
   * @throws IOException If an I/O error occurs while reading the file.
   * */
  public ArrayList<Stock> readStockData(InputStream inputStream) throws IOException, IllegalArgumentException {
    ArrayList<Stock> stocks = new ArrayList<>();
    Validate.that(inputStream).isNotNullOrEmpty();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (line.startsWith("#") || line.trim().isEmpty()) {
          continue; // Skip comments and empty lines
        }
        String[] stock = line.split(",");
        if (stock.length == 3) {
          stocks.add(new Stock(stock[0].trim(), stock[1].trim(), new BigDecimal(stock[2].trim())));
        }
      }
    }

    return stocks;
  }

}

