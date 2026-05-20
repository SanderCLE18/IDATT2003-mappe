package idi.gruppe07.ui.views.market;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.transactions.Transaction;
import idi.gruppe07.ui.event.AppEvents;
import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.StockActionEvent;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.GameViewController;
import idi.gruppe07.utils.Validate;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;

/**
 * Controller associated with the {@link MarketView}, extends the {@link GameViewController}
 * which houses the functionality associated with the sidebar and top bar.
 * */
public class MarketController extends GameViewController<Pane, MarketView> {

  /**
   * Constructor, sets its current view, the current session and its associated event manager.
   *
   * @param viewElement the view associated with this controller.
   * @param eventManager the {@link EventManager} this controller communicates with.
   * @param session the {@link Session} the controller retrieves it's session data from.
   * */
  public MarketController(MarketView viewElement, EventManager eventManager, Session session) {
    super(viewElement, eventManager, session);
  }

  /**
   * @inheritDoc
   * */
  @Override
  protected void initInteractions() {
    initSideBarInteractions();
    getViewElement().getRootPane().addEventHandler(StockActionEvent.PURCHASE_REQUESTED, event -> {
      Stock stock = event.getStock();
      purchaseStockInteraction(stock);
    });
    getViewElement().getRootPane().addEventHandler(AppEvents.SCENE_BACK, _ -> getViewElement().popView());

    getViewElement().getRootPane().addEventHandler(StockActionEvent.STOCK_PURCHASE, this::onStockPurchase);

  }

  /**
   * Creates a new {@link PurchasePane} based on the stock the user wants to purchase.
   * @param stock a reference to the selected {@link Stock}.
   * */
  public void purchaseStockInteraction(Stock stock){
    getViewElement().pushView(new PurchasePane(stock));
  }

  /**
   * Functionality associated with trying to purchase a stock.
   *
   * @param stockActionEvent the event sent from the purchase button in {@link PurchasePane},
   *                         contains a {@link Stock} and an amount of that stock. */
  public void onStockPurchase(StockActionEvent stockActionEvent) {
    MarketNode content = getViewElement().getContent();

    if (!(content instanceof PurchasePane pane)) {
      return;
    }

    if(!verifyAction(stockActionEvent.getStock().getPrice(), pane.getAmountField())){
      pane.notSuccessPurchase();
      return;
    }

    Transaction purchase = getSession().getExchange().buy(
        stockActionEvent.getStock().getSymbol(),
        stockActionEvent.getAmount(),
        getSession().getPlayer()
    );

    getSession().getPlayer().getTransactionArchive().add(purchase);
    getSession().getPlayer().withdrawMoney(stockActionEvent.getAmount().multiply(stockActionEvent.getStock().getPrice()));
    pane.updateNode(getSession());
    pane.successPurchase(stockActionEvent.getAmount());

  }

  /**
   * Verifies if what the user has inputted is valid.
   *
   * @param price the current {@link Stock} price.
   * @param field a reference to the {@link TextField}.
   * @return a boolean which signifies if the trade can happen or not. */
  private boolean verifyAction(BigDecimal price, TextField field) {
    try{
      Validate.that(field.getText()).isNotNegative().isValidNumber().isNotNull();
      BigDecimal amount = new BigDecimal(field.getText()).multiply(price);
      if (amount.compareTo(getSession().getPlayer().getMoney()) > 0) {
        return false;
      }
    }catch (Exception ex){
      return false;
    }
    return true;
  }

}
