package idi.gruppe07.transactions;

import idi.gruppe07.calculators.TransactionCalculator;
import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;

public abstract class Transaction {
  private Share share;
  private int week;
  private TransactionCalculator calculator;
  private boolean commited;

  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    this.share = share;
    this.week = week;
    this.calculator = calculator;
  }

  public Share getShare() {
    return share;
  }

  public int getWeek() {
    return week;
  }

  public TransactionCalculator getCalculator() {
    return calculator;
  }

  public boolean isCommited() {
    return commited;
  }

  public void commit(Player player) {
    this.commited = true;
  }

}
