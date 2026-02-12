package idi.gruppe07.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionArchive {
  private final List<Transaction> transactions;

  public TransactionArchive(){
    this.transactions = new ArrayList<>();
  };

  public boolean add(Transaction transaction){
    return this.transactions.add(transaction);
  }

  public boolean isEmpty(){
    return this.transactions.isEmpty();
  }

  public List<Transaction> getTransactions(int week){
    return this.transactions.stream()
        .filter(n -> n.getWeek() == week)
        .collect(Collectors.toList());
  }

  public List<Purchase> getPurchases(int week){
    return this.transactions.stream()
        .filter(n -> n instanceof Purchase)
        .map(n -> (Purchase)n)
        .collect(Collectors.toList());
  }

  public List<Sale> getSales(int week){
    return this.transactions.stream()
        .filter(n -> n instanceof Sale)
        .map(n -> (Sale)n)
        .collect(Collectors.toList());
  }
  public int countDistinctWeeks(){
    return this.transactions.stream()
        .map(Transaction::getWeek)
        .distinct()
        .toList().size();
  }
}
