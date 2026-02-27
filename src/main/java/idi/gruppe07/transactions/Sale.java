package idi.gruppe07.transactions;

import idi.gruppe07.calculators.SaleCalculator;
import idi.gruppe07.entities.Player;
import idi.gruppe07.entities.Share;

/**
 * Represents a sale.
 * */
public class Sale extends Transaction {
  /**
   * Creates a new sale.
   *
   * @param share The share to be sold.
   * @param week The week of the sale.
   * @throws NullPointerException if share is null.
   * */
  public Sale(Share share, int week) {
    SaleCalculator saleCalculator = new SaleCalculator(share);
    super(share, week, saleCalculator);
  }


  /**
   * Commits the sale.
   *
   * @param player The player who made the sale.
   * @throws NullPointerException if player is null.*/
  @Override
  public void commit(Player player) throws NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    this.commited = true;
    player.getTransactionArchive().add(this);
    player.getPortfolio().removeShare(this.getShare());
    player.addMoney(this.getCalculator().calculateTotal());
  }
}
