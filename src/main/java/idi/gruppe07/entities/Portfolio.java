package idi.gruppe07.entities;

import idi.gruppe07.calculators.SaleCalculator;
import idi.gruppe07.utils.Validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a player's portfolio.*/
public class Portfolio {
  private final List<Share> shares;
  private final List<BigDecimal> historicNetWorth;

  /**
   * Constructs a new portfolio.
   * */
  public Portfolio() {
    this.shares = new ArrayList<>();
    this.historicNetWorth = new ArrayList<>();
  }

  /**
   * Adds a share to the portfolio.
   *
   * @param share The share to add.
   * @return true if the share was added successfully, false otherwise.
   * @throws NullPointerException if share is null.*/
  public boolean addShare(Share share) throws NullPointerException {

    Validate.that(share).isNotNull();

    return this.shares.add(share);
  }

  /**
   * Removes a share from the portfolio.
   *
   * @param share The share to remove.
   * @return true if the share was removed successfully, false otherwise.
   * @throws NullPointerException if share is null.*/
    public boolean removeShare(Share share) {
    Validate.that(share).isNotNull();

    return this.shares.remove(share);
  }

  /**
   * Returns the list of shares in the portfolio.
   *
   * @return A list of shares.
   * @throws NullPointerException if List is null.*/
  public List<Share> getShares() {

    Validate.that(this.shares).isNotNull();
    return this.shares;
  }

  /**
   * Returns a list of shares with the specified symbol.
   *
   * @param symbol The symbol of the shares to return.
   * @return A list of shares with the specified symbol.
   * @throws NullPointerException if List is null.
   * @throws IllegalArgumentException if symbol is empty.*/
  public List<Share> getShares(String symbol)
      throws NullPointerException, IllegalArgumentException {

    Validate.that(this.shares).isNotNull();
    Validate.that(symbol).isNotNullOrEmpty();

    return shares.stream()
        .filter(n -> n.getStock().getCompany().equals(symbol))
        .collect(Collectors.toList());
  }

  /**
   * Checks if the portfolio contains a specific share.
   *
   * @param share The share to check.
   * @return true if the portfolio contains the share, false otherwise.
   * @throws NullPointerException if share is null.*/
  public boolean contains(Share share) throws NullPointerException {

    Validate.that(share).isNotNull();

    return this.shares.contains(share);
  }

  /**
   * Calculates the net worth of the portfolio.
   * It converts the map into a stream, calculates the value of each share, and sums them up.
   *
   * @return The net worth of the portfolio.*/
  public BigDecimal getNetWorth() {
    return this.shares.stream()
        .map(e->new SaleCalculator(e).calculateTotal())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**@return A list of portfolio value snapshots*/
  public List<BigDecimal> getHistoricNetWorth() {
    return this.historicNetWorth;
  }

}
