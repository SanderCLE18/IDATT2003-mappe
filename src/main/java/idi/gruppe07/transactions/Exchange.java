package idi.gruppe07.transactions;

import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import idi.gruppe07.utils.NormalDistribution;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Exchange {
  private String name;
  private int week;
  private Map<String, Stock> stockMap;
  private Random random;

  public Exchange(String name, List<Stock> stocks) {
    this.name = name;
    stockMap = stocks.stream()
        .collect(Collectors.toMap(
            Stock::getSymbol, stock -> stock));
  }

  public String getName() {
    return name;
  }

  public int getWeek() {
    return week;
  }

  public boolean hasStock(String symbol) {
    return stockMap.containsKey(symbol);
  }

  public Stock getStock(String symbol) {
    return stockMap.get(symbol);
  }

  public List<Stock> findStocks(String search){
    return stockMap.values().stream()
        .filter(n -> n.getCompany().toUpperCase().contains(search.toUpperCase()))
        .collect(Collectors.toList());
  }

  //INGEN AV DE UNDER LEGGER TIL I TRANSACTION ARCHIVE!
  public Transaction buy(String symbol, BigDecimal quantity, Player player){
    if(hasStock(symbol)){
      Share newShare = new Share(getStock(symbol), quantity, getStock(symbol).getPrice());
      player.getPortfolio().addShare(newShare);
      return new Purchase(newShare, getWeek());
    }
    else return null;
  }
  public Transaction sell(Share share, Player player){
    player.getPortfolio().removeShare(share);
    player.addMoney(share.getQuantity().multiply(share.getStock().getPrice()));
    return new Sale(share, getWeek());
  }
  public void advance(){
    this.week++;
    NormalDistribution distribution = new NormalDistribution(0, 0.05);
    for(var stock : stockMap.values()){
      double returnRandom = distribution.nextGaussian();

      BigDecimal multiplier = BigDecimal.valueOf(Math.exp(returnRandom));
      BigDecimal deltaPrice = stock.getPrice().multiply(multiplier);
      stock.addNewSalesPrice(deltaPrice);
    }
  }

}
