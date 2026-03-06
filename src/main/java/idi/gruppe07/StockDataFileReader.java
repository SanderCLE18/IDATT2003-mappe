package idi.gruppe07;

import idi.gruppe07.entities.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


public class StockDataFileReader {

  public ArrayList<Stock> readStockData(String filePath) {
    ArrayList<Stock> stocks = new ArrayList<>();
    try (FileReader fileReader = new FileReader(filePath)) {
      try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          if (line.startsWith("#") || line.trim().isEmpty()) {
            continue; // Skip comments and empty lines
          }
          String[] stock = line.split(",");
          if (stock.length == 3) {
            stocks.add(new Stock(stock[0].trim(), stock[1].trim(), new BigDecimal(stock[2].trim())));
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stocks;
  }

}
