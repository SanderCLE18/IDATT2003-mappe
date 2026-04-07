package idi.gruppe07.transactions;

import idi.gruppe07.player.Player;
import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import idi.gruppe07.utils.NormalDistribution;
import idi.gruppe07.utils.Validate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a stock exchange where players can trade stocks.
 */
public class Exchange {
  private String name;
  private int week;
  private final Map<String, Stock> stockMap;
  private Random random;

  /**
   * Constructs an exchange with the given name and stocks.
   *
   * @param name The name of the exchange.
   * @param stocks The list of stocks in the exchange.
   * @throws NullPointerException if stocks is null.
   * @throws IllegalArgumentException if stocks is empty.
   */
  public Exchange(String name, List<Stock> stocks)
      throws NullPointerException, IllegalArgumentException {
    this.name = name;
    stockMap = stocks.stream()
        .collect(Collectors.toMap(
            Stock::getSymbol, stock -> stock));
  }

  /**
   * Gets the name of the exchange.
   *
   * @return name The name of the exchange.
   * */
  public String getName() {
    return name;
  }

  /**
   * Gets the current week of the exchange.
   *
   * @return week The current week of the exchange.
   * */
  public int getWeek() {
    return week;
  }

  /**
   * Checks if the exchange has a stock with the given symbol.
   *
   * @param symbol The symbol of the stock to check.
   * @return true if the exchange has the stock, false otherwise.*/
  public boolean hasStock(String symbol) {
    return stockMap.containsKey(symbol);
  }

  /**
   * Gets the stock with the given symbol.
   *
   * @param symbol The symbol of the stock to get.
   * @return stock The stock with the given symbol.*/
  public Stock getStock(String symbol) {
    return stockMap.get(symbol);
  }

  /**
   * Gets a list of stocks that contain the given search string in their company name.
   *
   * @param search The string to search for in the company name.
   * @return stocks A list of stocks that contain the search string.
   * @throws NullPointerException if search is null.
   * @throws IllegalArgumentException if search is empty.
   * */
  public List<Stock> findStocks(String search) {
    return stockMap.values().stream()
        .filter(n -> n.getCompany().toUpperCase().contains(search.toUpperCase()))
        .collect(Collectors.toList());
  }

  /**
   * Buys a share of a stock with the given symbol and quantity from the exchange.
   *
   * @param symbol The symbol of the stock to buy.
   * @param quantity The quantity of shares to buy.
   * @param player The player who is buying the stock.
   * @return transaction The transaction object representing the buy transaction.
   * @throws IllegalArgumentException if symbol is not a valid stock symbol.
   * @throws NullPointerException if symbol is not a valid stock symbol. Or if player is null*/
  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    Validate.that(quantity).isNotNegative();
    Validate.that(player).isNotNull();

    if (!hasStock(symbol)) {
      throw new IllegalArgumentException("Invalid stock");
    }

    if (hasStock(symbol)) {
      Share newShare = new Share(getStock(symbol), quantity, getStock(symbol).getPrice());
      player.getPortfolio().addShare(newShare);
      return new Purchase(newShare, getWeek());
    } else {
      return null;
    }
  }

  /**
   * Sells a share of a stock from the player's portfolio.
   *
   * @param share The share to sell.
   * @param player The player who is selling the stock.
   * @return transaction The transaction object representing the sell transaction.
   * @throws IllegalArgumentException if share is not a valid share or player is not a valid player.
   * */
  public Transaction sell(Share share, Player player) throws IllegalArgumentException {
    Validate.that(share).isNotNull();
    Validate.that(player).isNotNull();
    Validate.that(share.getQuantity()).isNotNegative();
    if (!player.getPortfolio().contains(share)) {
      throw new IllegalArgumentException("Player does not own this share");
    }
    player.getPortfolio().removeShare(share);
    player.addMoney(share.getQuantity().multiply(share.getStock().getPrice()));
    return new Sale(share, getWeek());
  }

  /**
   * Advances the exchange by one week.
   * Sets new prices for each stock based on a normal distribution.*/
  public void advance() {
    this.week++;
    NormalDistribution distribution = new NormalDistribution(0, 0.05);
    for (var stock : stockMap.values()) {
      double returnRandom = distribution.nextGaussian();

      BigDecimal multiplier = BigDecimal.valueOf(Math.exp(returnRandom));
      BigDecimal deltaPrice = stock.getPrice().multiply(multiplier);
      stock.addNewSalesPrice(deltaPrice);
    }
  }

  public List<Stock> getGainers(int limit) {
    Validate.that(limit).isNotNegative();

    ArrayList<Stock> gainers = new ArrayList<>(stockMap.values());
    gainers.removeIf(stock -> stock.getLatestPriceChange().compareTo(BigDecimal.ZERO) <= 0);
    gainers.sort((s1, s2) -> s2.getLatestPriceChange().compareTo(s1.getLatestPriceChange()));
    return gainers.subList(0, Math.min(limit, gainers.size()));
  }

  public List<Stock> getLoosers(int limit) {
    Validate.that(limit).isNotNegative();
    if(stockMap.isEmpty()) return new ArrayList<>();

    ArrayList<Stock> loosers = new ArrayList<>(stockMap.values());
    loosers.sort(Comparator.comparing(Stock::getLatestPriceChange));
    return loosers.subList(0, Math.min(limit, loosers.size()));
  }

}
