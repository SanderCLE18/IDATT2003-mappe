package idi.gruppe07.transactions;

import idi.gruppe07.calculators.TransactionCalculator;
import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;

public abstract class Transaction {
  private Share share;
  private int week;
  private TransactionCalculator calculator;
  protected boolean commited;

  protected Transaction(Share share, int week, TransactionCalculator calculator) {
    this.share = share;
    this.week = week;
    this.calculator = calculator;
    this.commited = false;
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

  abstract public void commit(Player player);

}
