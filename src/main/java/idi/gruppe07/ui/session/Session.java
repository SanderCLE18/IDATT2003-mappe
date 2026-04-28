package idi.gruppe07.ui.session;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import idi.gruppe07.news.NewsService;
import idi.gruppe07.player.Player;
import idi.gruppe07.transactions.Exchange;
import idi.gruppe07.transactions.Purchase;
import idi.gruppe07.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Session class to store game critical data.
 *
 */
public class Session {

  /**
   * The player of the game.
   */
  private Player player;

  /**
   * The path to the savefile.
   */
  private String savefile;

  /**
   * Exchange*/
  private Exchange exchange;

  private final NewsService newsService;
  /**Session timer*/
  private final SessionTimer sessionTimer;

  /**
   * Constructor.
   */
  public Session(NewsService newsService) {
    this.sessionTimer = new SessionTimer();
    this.newsService = newsService;
  }

  /**
   * Getter for player.
   *
   * @return The player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Setter for player.
   *
   * @param player The player to set.
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Getter for savefile.
   *
   * @return The savefile.
   */
  public String getSavefile() {
    return savefile;
  }

  /**
   * Setter for savefile.
   *
   * @param savefile The savefile to set.
   */
  public void setSavefile(String savefile) {
    this.savefile = savefile;
  }

  /**
   * Getter for exchange.
   *
   * @return The exchange.*/
  public Exchange getExchange() {
    return exchange;
  }

  /**
   * Makes an exchange with the given stocks and name.
   *
   * @param name The name of the exchange.
   * @param stocks The stocks to make the exchange with.*/
  public void makeExchange(String name, ArrayList<Stock> stocks) {
    exchange = new Exchange(name, stocks);
  }

  /**actual placeholder*/
  public void simulate(){
    Stock stock = new Stock("SSC", "SanderC", new BigDecimal("100"));
    Stock stock2 = new Stock("SCC", "SanderCC", new BigDecimal("151"));
    exchange.add(stock);
    exchange.add(stock2);
    for(int i = 0; i < 50; i++){
      this.exchange.advance(newsService);
      if (i == 35){
        Transaction buy1 = exchange.buy( "SSC", new BigDecimal("1"), getPlayer());
        Transaction buy2 = exchange.buy("SCC", new BigDecimal("1.12"), getPlayer());
        Transaction buy3 = exchange.buy( "TSLA", new BigDecimal("4.20"), getPlayer());
        Transaction buy4 = exchange.buy("AAPL", new BigDecimal("6.9"), getPlayer());
        Transaction buy5 = exchange.buy( "MSFT", new BigDecimal("2.1"), getPlayer());
        Transaction buy6 = exchange.buy("NVDA", new BigDecimal("3.3"), getPlayer());

        player.getTransactionArchive().add(buy1);
        player.getTransactionArchive().add(buy2);
        player.getTransactionArchive().add(buy3);
        player.getTransactionArchive().add(buy4);
        player.getTransactionArchive().add(buy5);
        player.getTransactionArchive().add(buy6);

      }
    }

  }

  /**Returns the news article factory
   *
   * @return the news article factory*/
  public NewsService getNewsService() {
    return newsService;
  }

  /**returns the session's timer
   *
   * @return the sessionTimer. */
  public SessionTimer getTimer() {
    return sessionTimer;
  }
}
