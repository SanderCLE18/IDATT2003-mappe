package idi.gruppe07.ui.views.market;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.news.NewsArticle;
import idi.gruppe07.ui.custom.widgets.CandleStickChart;
import idi.gruppe07.ui.custom.widgets.ChartUtils;
import idi.gruppe07.ui.event.AppEvents;
import idi.gruppe07.ui.event.StockActionEvent;
import idi.gruppe07.ui.session.Session;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The pane where the player executes trades.
 * Implements the {@link MarketNode} interface to make it easily swappable with the nested class in {@link MarketView}
 * */
public class PurchasePane extends HBox implements MarketNode {

  private final Stock stock;

  private TextField amountField;

  private VBox purchase;

  /**Constructor, initializes the current object and sets a stock associated with it.
   *
   * @param stock the stock associated with the current object.
   * */
  public PurchasePane(Stock stock) {
    super(5);
    this.stock = stock;
  }

  /**Constructs the node based on the current variables
   *
   * @param session reference to the session object which houses most of the active player's information
   * */
  public void updateNode(Session session) {
    getChildren().clear();
    setPadding(new Insets(24, 16, 24, 16));

    Button exit = new Button("← Back");
    exit.getStyleClass().addAll("button-generic", "text-medium-regular");
    exit.setOnAction(_ -> fireEvent(new AppEvents(AppEvents.SCENE_BACK)));

    ScrollPane scroll = createScrollPane(buildLayoutGrid(
        exit,
        ChartUtils.buildCandleChart(stock.getHistoricalPrices(), 20),
        createArticleBox(stock.getNewsArticle()
        )));

    VBox content = new VBox(5, createHeader(), scroll);
    content.prefWidthProperty().bind(widthProperty().multiply(0.75));

    purchase = purchaseBox(session);
    purchase.prefWidthProperty().bind(widthProperty().multiply(0.25));
    getChildren().addAll(content, purchase);

    setStyle("-fx-background-color: #050d18");
  }

  /**
   * Retrieves the amount field so that it can be changed in the controller
   * 
   * @return the locally stored text field*/
  public TextField getAmountField() {
    return amountField;
  }

  /**
   * Creates the header for the purchase pane.
   *
   * @return the header (title) */
  private VBox createHeader() {

    Label title = new Label(stock.getSymbol());
    title.getStyleClass().add("text-large-bold");

    Label price = new Label("$" + stock.getPrice().setScale(2, RoundingMode.HALF_UP));
    price.getStyleClass().add("text-large-bold");
    Label changeLabel = ChartUtils.buildChangeLabel(stock.getHistoricalPrices());

    HBox titles = new HBox(15, title, price, changeLabel);

    Label company = new Label(stock.getCompany());
    company.getStyleClass().add("text-medium-regular");


    return new VBox(10, titles, company);
  }


  /**
   * Creates and configures the text content container containing the
   * news article headline and body text.
   *
   * @param article The news article data model containing the copy to display.
   * @return A VBox styled and configured to display the wrapped article text.
   */
  private VBox createArticleBox(NewsArticle article) {
    Label headline = new Label(article.getHeadline());
    headline.getStyleClass().add("text-medium-bold");
    headline.setStyle("-fx-text-fill: #70A1FF");

    Label content = new Label(article.getArticle());
    content.getStyleClass().add("text-medium-regular");
    content.setWrapText(true);


    VBox articleBox = new VBox(5, headline, content);
    articleBox.getStyleClass().add("dashboard-secondary-pane");

    return articleBox;
  }

  /**
   * Constructs a structured grid layout that assigns explicit sizing constraints
   * to the navigation button, financial chart, and text content area.
   *
   * @param exit The back navigation button.
   * @param chart The interactive candlestick chart component.
   * @param articleBox The container housing the formatted news text.
   * @return A configured GridPane ready for display rendering.
   */
  private GridPane buildLayoutGrid(Button exit, CandleStickChart chart, VBox articleBox) {
    GridPane layoutGrid = new GridPane();
    layoutGrid.setVgap(10);

    layoutGrid.add(exit, 0, 0);
    layoutGrid.add(chart, 0, 1);
    layoutGrid.add(articleBox, 0, 2);

    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    col.setPercentWidth(100);
    layoutGrid.getColumnConstraints().add(col);

    RowConstraints rowBack = new RowConstraints();
    rowBack.setVgrow(Priority.NEVER);

    RowConstraints rowChart = new RowConstraints();
    rowChart.setPercentHeight(75);
    rowChart.setVgrow(Priority.ALWAYS);

    RowConstraints rowArticle = new RowConstraints();
    rowArticle.setVgrow(Priority.NEVER);

    layoutGrid.getRowConstraints().addAll(rowBack, rowChart, rowArticle);

    return layoutGrid;
  }

  /**
   * Wraps the primary application layout grid within a scroll pane container
   * to ensure scalable window clipping bounds.
   *
   * @param layoutGrid The primary structured layout container.
   * @return A ScrollPane configured to prevent horizontal shifting and optimize heights.
   */
  private ScrollPane createScrollPane(GridPane layoutGrid) {

    ScrollPane scrollPane = new ScrollPane(layoutGrid);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    return scrollPane;
  }

  /**Creates a box where the player can purchase the stock.
   *
   * @param session a reference to the active session.
   * @return a VBox where the player can purchase stocks. */
  private VBox purchaseBox(Session session) {


    Label title = new Label("EXECUTE_TRADE");
    title.getStyleClass().add("text-large-bold");


    Label symbolDescription = new Label("Stock: ");
    symbolDescription.getStyleClass().add("text-medium-regular");
    Label stockSymbol = new Label(stock.getSymbol());
    stockSymbol.getStyleClass().add("text-medium-bold");
    VBox symbolDesc = new VBox(5, symbolDescription, stockSymbol);

    Label money = new Label("Current balance: $" + session.getPlayer().getMoney().setScale(2, RoundingMode.HALF_UP));
    money.getStyleClass().add("text-medium-bold");

    Label amount = new Label("Amount (Shares): ");
    amount.getStyleClass().add("text-medium-regular");

    amountField = new TextField("");
    amountField.setPromptText(stock.getSymbol());
    amountField.getStyleClass().add("text-sub-medium");

    VBox amountBox = new VBox(5, money, amount, amountField);

    Button purchaseButton = new Button("PURCHASE");
    purchaseButton.getStyleClass().addAll("sidebar-new-trade", "text-medium-bold");
    purchaseButton.setOnAction(_ -> executeTrade(amountField));
    purchaseButton.prefWidthProperty().bind(amountField.widthProperty());

    VBox purchaseBox = new VBox(10, title, symbolDesc, amountBox, purchaseButton);
    purchaseBox.getStyleClass().add("dashboard-secondary-pane");
    return purchaseBox;
  }

  /**
   * Sends an event up to the controller where it's executed or discarded.
   *
   * @param amount the amount parsed in from the {@link TextField}.
   */
  private void executeTrade(TextField amount) {
    BigDecimal shareAmount = new BigDecimal(amount.getText());
    fireEvent(new StockActionEvent(StockActionEvent.STOCK_PURCHASE, this.stock, shareAmount));
  }

  /**
   * Adds a box to tell the user that they have completed a successful purchase.
   *
   * @param amount the amount injected into the new label.
   * */
  public void successPurchase(BigDecimal amount) {
    clearField();
    Label text = new Label("Successfully purchased " + amount.setScale(2, RoundingMode.HALF_UP) + " shares of " +  stock.getSymbol());
    text.getStyleClass().add("text-medium-regular");
    text.setWrapText(true);
    VBox textBox = new VBox(5, text);
    textBox.getStyleClass().add("info-label");
    textBox.prefWidthProperty().bind(amountField.widthProperty());
    purchase.getChildren().add(textBox);
  }

  /**
   * Clears the text fields such that it can be reused without misleading the user.
   * */
  private void clearField() {
    amountField.setText("");
    amountField.setPromptText(stock.getSymbol());
    amountField.setStyle("-fx-border-color: transparent;");
  }

  /**Changes the text field to reflect that the purchase could not go through due to an invalid input.*/
  public void notSuccessPurchase() {
    amountField.clear();
    amountField.setPromptText("Enter valid amount!");
    amountField.setStyle("-fx-border-color: red;");

  }

}
