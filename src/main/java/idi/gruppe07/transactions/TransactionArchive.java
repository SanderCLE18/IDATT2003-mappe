package idi.gruppe07.transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a transaction archive.*/

public class TransactionArchive {
  private final List<Transaction> transactions;

  /**
   * Constructs a new transaction archive. Initializing the list of transactions.*/
  public TransactionArchive() {
    this.transactions = new ArrayList<>();
  }

  /**
   * Adds a transaction to the transaction archive.
   *
   * @param transaction The transaction to add.
   * @return true if the transaction was added, false otherwise.
   * @throws IllegalArgumentException if transaction is null.*/
  public boolean add(Transaction transaction) throws IllegalArgumentException {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null");
    }
    return this.transactions.add(transaction);
  }

  /**
   * Checks if the transaction archive is empty.
   *
   * @return true if the transaction archive is empty, false otherwise.*/
  public boolean isEmpty() {
    return this.transactions.isEmpty();
  }

  /**
   * Gets the list of transactions for a specific week.
   *
   * @param week The week for which to get the transactions.
   * @return transactions The list of transactions for the specified week.*/
  public List<Transaction> getTransactions(int week) {
    return this.transactions.stream()
        .filter(n -> n.getWeek() == week)
        .collect(Collectors.toList());
  }

  /**
   * Gets the list of purchases for a specific week.
   *
   * @param week The week for which to get the purchases.
   * @return purchases The list of purchases for the specified week.  */
  public List<Purchase> getPurchases(int week) {
    return this.transactions.stream()
        .filter(n -> n instanceof Purchase)
        .map(n -> (Purchase) n)
        .collect(Collectors.toList());
  }

  /**
   * Gets the list of sales for a specific week.
   *
   * @param week The week for which to get the sales.
   * @return sales The list of sales for the specified week.  */
  public List<Sale> getSales(int week) {
    return this.transactions.stream()
        .filter(n -> n instanceof Sale)
        .map(n -> (Sale) n)
        .collect(Collectors.toList());
  }

  /**
   * Gets the number of distinct weeks in the transaction archive.
   *
   * @return count The number of distinct weeks in the transaction archive. */
  public int countDistinctWeeks() {
    return this.transactions.stream()
        .map(Transaction::getWeek)
        .distinct()
        .toList().size();
  }
}
