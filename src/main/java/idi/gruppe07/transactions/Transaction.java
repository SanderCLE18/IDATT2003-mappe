package idi.gruppe07.transactions;

import idi.gruppe07.calculators.TransactionCalculator;
import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;

/**
 * Represents a transaction.*/
public abstract class Transaction {
  private Share share;
  private int week;
  private TransactionCalculator calculator;
  protected boolean commited;

  /**
   * Constructs a new transaction with the given share, week, and calculator.
   *
   * @param share The share involved in the transaction.
   * @param week The week of the transaction.
   * @param calculator The calculator used to calculate the transaction.*/
  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    this.share = share;
    this.week = week;
    this.calculator = calculator;
    this.commited = false;
  }

  /**
   * Gets the share involved in the transaction.
   *
   * @return share The share involved in the transaction.*/
  public Share getShare() {
    return share;
  }

  /**
   * Gets the week of the transaction.
   *
   * @return week The week of the transaction.*/
  public int getWeek() {
    return week;
  }

  /**
   * Gets the calculator used to calculate the transaction.
   *
   * @return calculator The calculator used to calculate the transaction.*/
  public TransactionCalculator getCalculator() {
    return calculator;
  }

  /**
   * Checks if the transaction has been committed.
   *
   * @return commited True if the transaction has been committed, false otherwise.*/
  public boolean isCommited() {
    return commited;
  }

  /**
   * Commits the transaction.
   *
   * @param player The player who made the transaction.*/
  abstract void commit(Player player);

}
