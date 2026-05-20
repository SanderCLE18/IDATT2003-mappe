package idi.gruppe07.ui.event;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import javafx.event.Event;
import javafx.event.EventType;

import java.math.BigDecimal;


/**
 * A custom ActionEvent had to be written such that stocks could be passed "upstream" and used in other parts of the project.
 * */
public class StockActionEvent extends Event {

  public static final EventType<StockActionEvent> PURCHASE_REQUESTED =
      new EventType<>(Event.ANY, "PURCHASE REQUESTED");
  public static final EventType<StockActionEvent> STOCK_PURCHASE =
      new EventType<>(Event.ANY, "STOCK PURCHASE");

  private final Stock stock;
  private final BigDecimal amount;

  /**Constructor for the Action event, sets the event type and stock
   *
   * @param event the event type
   * @param stock the stock attached to the event
   * */
  public StockActionEvent(EventType<StockActionEvent> event, Stock stock, BigDecimal amount) {
    super(event);
    this.stock = stock;
    this.amount = amount;
  }

  /**
   * Returns the event's stored stock
   *
   * @return a reference to the stored stock.
   * */
  public Stock getStock() {
    return stock;
  }

  /**
   * Returns the event's stored amount (Critical that it's stored as {@link BigDecimal} as this gets passed into a {@link Share} )
   *
   * @return a reference to the chosen amount. */
  public BigDecimal getAmount() {
    return amount;
  }
}
