package idi.gruppe07.ui.session;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.player.Player;
import idi.gruppe07.transactions.Exchange;
import java.util.ArrayList;

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

  /**Session timer*/
  private final SessionTimer sessionTimer;

  /**
   * Constructor. Created as an empty object.
   */
  public Session() {
    sessionTimer = new SessionTimer();
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

  public SessionTimer getTimer() {
    return sessionTimer;
  }
}
