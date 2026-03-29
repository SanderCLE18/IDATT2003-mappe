package idi.gruppe07.player;

import idi.gruppe07.entities.Portfolio;
import idi.gruppe07.transactions.TransactionArchive;
import idi.gruppe07.utils.Validate;

import java.math.BigDecimal;

/** Represents a player. */
public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;

  /**
   * Constructs a player with the given name and starting money.
   *
   * @param name The name of the player.
   * @param startingMoney The starting money of the player.
   */
  public Player(String name, BigDecimal startingMoney) {

    Validate.that(name).isNotNullOrEmpty();
    Validate.that(startingMoney).isNotNegative();

    this.name = name;
    this.startingMoney = startingMoney;
    this.money = startingMoney;
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
  }

  /**
   * Gets the name of the player.
   *
   * @return name The name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the money of the player.
   *
   * @return money The starting money of the player.
   */
  public BigDecimal getMoney() {
    return money;
  }

  /**
   * Gets the starting money of the player.
   *
   * @return startingMoney The starting money of the player.
   */
  public BigDecimal getStartingMoney() {
    return startingMoney;
  }

  /**
   * Adds money to the player's account.
   *
   * @param money The amount of money to add.
   */
  public void addMoney(BigDecimal money) {
    this.money = this.money.add(money);
  }

  /**
   * Withdraws money from the player's account.
   *
   * @param money The amount of money to withdraw.
   */
  public void withdrawMoney(BigDecimal money) {
    this.money = this.money.subtract(money);
  }

  /**
   * Gets the player's portfolio.
   *
   * @return portfolio The player's portfolio.
   */
  public Portfolio getPortfolio() {
    return portfolio;
  }

  /**
   * Gets the player's transaction archive.
   *
   * @return transactionArchive The player's transaction archive.
   */
  public TransactionArchive getTransactionArchive() {
    return transactionArchive;
  }

  /**
   * Gets the player's net worth.
   *
   * @return the player's net worth
   */
  public BigDecimal getNetWorth() {
    return getPortfolio().getNetWorth().add(this.startingMoney);
  }

  /**
   * Method to get the player's investor status
   *
   * @return the players investor status.
   */
  public PlayerStatus getPlayerStatus() {

    // Get the amount of weeks the user has been active.
    int weeks =
        portfolio.getShares().stream()
            .mapToInt(n -> n.getStock().getHistoricalPrices().size())
            .max()
            .orElse(0);

    if (comparator(PlayerStatus.SPECULATOR.getConstant()) && weeks >= 20) {
      return PlayerStatus.SPECULATOR;
    } else if (comparator(PlayerStatus.INVESTOR.getConstant()) && weeks >= 10) {
      return PlayerStatus.INVESTOR;
    }
    return PlayerStatus.NOVICE;
  }

  /**
   * Helper method for getPlayerStatus.
   *
   * @param constant BigDecimal constant to check comparison
   * @return returns true if the net worth has grown more or equal to the given constant
   */
  private boolean comparator(BigDecimal constant) {
    return this.getNetWorth().compareTo(startingMoney.multiply(constant)) >= 0;
  }
}
