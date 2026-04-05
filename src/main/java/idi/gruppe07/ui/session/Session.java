package idi.gruppe07.ui.session;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.player.Player;

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

  private ArrayList<Stock> Stocks = new ArrayList<>();

  /**
   * Constructor. Created as an empty object.
   */
  public Session() {

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

  public ArrayList<Stock> getStocks() {
    return Stocks;
  }

  public void setStocks(ArrayList<Stock> stocks) {
    Stocks = stocks;
  }

  /***/


}
