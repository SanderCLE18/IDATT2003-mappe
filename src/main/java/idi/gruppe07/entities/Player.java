package idi.gruppe07.entities;

import idi.gruppe07.transactions.TransactionArchive;
import java.math.BigDecimal;

public class Player {
  private String name;
  private BigDecimal startingMoney;
  private BigDecimal money;
  private Portfolio portfolio;
  private TransactionArchive transactionArchive;

  public Player(String name, BigDecimal startingMoney) {
    this.name = name;
    this.startingMoney = startingMoney;
    this.money = startingMoney;
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void addMoney(BigDecimal money) {
    this.money = this.money.add(money);
  }

  public void withdrawMoney(BigDecimal money) {
    this.money = this.money.subtract(money);
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public TransactionArchive getTransactionArchive () {
    return transactionArchive;
  }
}
