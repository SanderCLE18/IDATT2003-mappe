package idi.gruppe07.ui.views.dashboard;

import idi.gruppe07.entities.Share;
import idi.gruppe07.entities.Stock;
import idi.gruppe07.news.NewsArticle;
import idi.gruppe07.transactions.Purchase;
import idi.gruppe07.transactions.Transaction;
import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.custom.panes.SideBarPane;
import idi.gruppe07.ui.custom.panes.SideBarView;
import idi.gruppe07.ui.custom.widgets.PortfolioChartPane;
import idi.gruppe07.ui.custom.widgets.StockButtonChart;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.session.SessionTimer;
import idi.gruppe07.ui.views.ViewElement;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The "main" view of the application. User will land here with a brief overview of the game.
 */
public class DashBoardView extends ViewElement<Pane> {

  /**
   * Dashboard name
   */
  public static final String DASHBOARD_VIEW = "DashboardView";

  /**
   * Dashboard controller
   */
  private DashBoardController controller;

  /**
   * Sidebar view
   */
  private SideBarView sideBar;

  private SideBarPane pane;
  private DashBoardPane content;
  private final List<NavItem> views;

  /**The session advanceListener*/
  private SessionTimer.AdvanceListener advanceListener;

  public DashBoardView(Session session, List<NavItem> views) {
    this(DASHBOARD_VIEW, session, views);
  }

  /**
   * Constructor without a session.
   *
   * @param viewName the name of the view.
   * @param views    the different views displayed in the sidebar.
   */
  protected DashBoardView(String viewName, List<NavItem> views) {
    super(new StackPane(), viewName, false);
    this.views = views;
    initLayout();
  }

  /**
   * Constructor with a session.
   *
   * @param viewName the name of the view.
   * @param session  the session of the application.
   * @param views    the different views displayed in the sidebar.
   */
  public DashBoardView(String viewName, Session session, List<NavItem> views) {
    super(new StackPane(), viewName, session, false);
    this.views = views;
    initLayout();

  }

  /**
   * Initializes the layout of the view.
   */
  @Override
  protected void initLayout() {
    this.content = new DashBoardPane();
    pane = new SideBarPane(this.views);
    VBox.setVgrow(this.pane, Priority.ALWAYS);
    sideBar = new SideBarView(getSession(), pane, content);
    getRootPane().getChildren().addAll(sideBar);
  }

  @Override
  public void onActivate() {
    this.content.update(getSession());

    if(advanceListener == null){
      advanceListener = () -> Platform.runLater(() -> this.content.update(getSession()));
    }

    getSession().getTimer().removeAdvanceListener(advanceListener);
    getSession().getTimer().addAdvanceListener(advanceListener);

  }

  /**
   * Initializes the styling of the view.
   */
  @Override
  protected void initStyling() {

  }

  /**
   * @return the SideBar of the view.
   */
  public SideBarView getSideBar() {
    return sideBar;
  }

  /**
   * @return the controller of the view.
   */
  public DashBoardController getController() {
    return controller;
  }

  /**Sets the controller of the view.*/
  public void setController(DashBoardController controller) {
    this.controller = controller;

  }

  /**
   * A custom UI component that displays the player's dashboard,
   * including portfolio charts and active stock holdings.
   */
  private static class DashBoardPane extends VBox {

    /**
     * Constructs a new DashBoardPane with default padding and background styling.
     */
    public DashBoardPane() {
      super(5);
      setPadding(new Insets(6, 4, 6, 4));
      this.setStyle("-fx-background-color: #060E20;");
    }

    /**
     * Refreshes the dashboard content based on the current session data.
     * @param session The current game session containing player and portfolio data.
     */
    public void update(Session session) {
      this.getChildren().clear();

      PortfolioChartPane portfolioPane = new PortfolioChartPane(session.getPlayer().getPortfolio());
      VBox secondaryPane = getSecondaryPane(session);
      secondaryPane.getStyleClass().add("dashboard-secondary-pane");

      HBox portfolioHbox = new HBox(5, portfolioPane, secondaryPane);
      HBox.setHgrow(portfolioPane, Priority.ALWAYS);

      portfolioPane.prefWidthProperty().bind(portfolioHbox.widthProperty().multiply(0.76));
      secondaryPane.prefWidthProperty().bind(portfolioHbox.widthProperty().multiply(0.24));

      VBox holdingsBox = createHoldingsBox(session);

      portfolioHbox.maxWidthProperty().bind(this.widthProperty());
      holdingsBox.maxWidthProperty().bind(this.widthProperty());

      VBox.setVgrow(portfolioPane, Priority.ALWAYS);
      VBox.setVgrow(holdingsBox, Priority.ALWAYS);
      holdingsBox.setMinHeight(150);
      portfolioHbox.setMinHeight(150);

      HBox newsAndHistory;
      try {
        newsAndHistory = getNewsAndHistoryBox(session);
      } catch (IOException e) {
        newsAndHistory = new HBox(5);
        IO.println(e.getMessage());
      }

      getChildren().addAll(portfolioHbox, holdingsBox, newsAndHistory);
    }

    /**
     * Creates the holdings section containing the scrollable list of stock buttons.
     *
     * @param session The current game session.
     * @return A VBox containing the "Active Holdings" label and the scrollable stock list.
     */
    private VBox createHoldingsBox(Session session) {
      Label activeHoldingsLabel = new Label("Active Holdings");
      activeHoldingsLabel.getStyleClass().add("text-medium-bold");
      activeHoldingsLabel.setAlignment(Pos.CENTER_LEFT);

      HBox stockButtonsBox = new HBox(12);
      stockButtonsBox.setStyle("-fx-background-color: #141F38;");

      List<Share> portfolioList = session.getPlayer().getPortfolio().getShares();
      if (portfolioList.isEmpty()) {
        stockButtonsBox.getChildren().add(new Label("No Holdings"));
      } else {
        for (Share share : portfolioList) {
          StockButtonChart stockButtonChart = new StockButtonChart(share);

          stockButtonChart.prefWidthProperty().bind(this.widthProperty().multiply(0.125));
          stockButtonChart.prefHeightProperty().bind(stockButtonChart.prefWidthProperty());
          stockButtonChart.getStyleClass().add("stock-button-chart");
          stockButtonsBox.getChildren().add(stockButtonChart);
        }
      }

      ScrollPane activeHoldingsScroll = new ScrollPane(stockButtonsBox);

      activeHoldingsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      activeHoldingsScroll.setFitToHeight(true);

      VBox holdingsBox = new VBox(5, activeHoldingsLabel, activeHoldingsScroll);
      holdingsBox.getStyleClass().add("dashboard-secondary-pane");

      return holdingsBox;
    }

    /**Creates the holdings section containing the scrollable list of stock buttons.
     *
     * @param session a reference to the session object containing the player's portfolio
     * @return a pane holding the monetary change of the portfolio */
    private static VBox getSecondaryPane(Session session) {
      Label performance = new Label(" Weekly Performance");
      Label changePl = new Label("Weekly P/L");
      Label realized = new Label("0$");

      changePl.setMaxWidth(Double.MAX_VALUE);
      realized.setMaxWidth(Double.MAX_VALUE);
      HBox.setHgrow(changePl, Priority.ALWAYS);
      HBox.setHgrow(realized, Priority.ALWAYS);

      changePl.setAlignment(Pos.CENTER_LEFT);
      realized.setAlignment(Pos.CENTER_RIGHT);

      var history = session.getPlayer().getPortfolio().getHistoricNetWorth();
      if (history.size() >= 2) {
        BigDecimal latest = history.getLast();
        BigDecimal previous = history.get(history.size() - 2);
        BigDecimal change = latest.subtract(previous);

        boolean positive = change.compareTo(BigDecimal.ZERO) >= 0;
        String color = positive ? "#00ff88" : "#ff0000";
        String symbol = (positive && change.compareTo(BigDecimal.ZERO) > 0) ? "+" : "";

        realized.setText(String.format("%s%.2f$", symbol, change.doubleValue()));
        realized.setStyle("-fx-text-fill: " + color + ";");
      }

      HBox plChange = new HBox(changePl, realized);
      plChange.setMaxWidth(Double.MAX_VALUE);

      VBox secondaryPane = new VBox(10, performance, plChange);
      secondaryPane.setPadding(new Insets(10));

      return secondaryPane;
    }

    /**
     * Centers two/spaces out two Nodes inside another Node */
    private void setupHGrow(Node left, Node right) {
      HBox.setHgrow(left, Priority.ALWAYS);
      HBox.setHgrow(right, Priority.ALWAYS);
      ((Region) left).setMaxWidth(Double.MAX_VALUE);
      ((Region) right).setMaxWidth(Double.MAX_VALUE);
    }

    private HBox getNewsAndHistoryBox(Session session) throws IOException {
      VBox newsBox = createNewsSection(session);
      ScrollPane newsSection = new ScrollPane(newsBox);
      newsBox.prefWidthProperty().bind(newsSection.prefWidthProperty());
      newsSection.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      VBox historySection = createHistorySection(session);
      ScrollPane historyScroll = new ScrollPane(historySection);
      historySection.prefWidthProperty().bind(historyScroll.prefWidthProperty());
      historyScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      HBox container = new HBox(5, newsSection, historyScroll);

      newsSection.prefWidthProperty().bind(container.widthProperty().multiply(0.6));
      historySection.prefWidthProperty().bind(container.widthProperty().multiply(0.4));

      return container;
    }

    /** Creates the News Feed column */
    private VBox createNewsSection(Session session) throws IOException {
      VBox newsBox = new VBox(10);
      newsBox.setFillWidth(true);

      Label title = new Label("Gazillionaire Market News");
      title.getStyleClass().add("text-medium-bold");
      newsBox.getChildren().add(title);

      List<Stock> stocks =
          session.getPlayer().getPortfolio().getShares().stream()
              .map(Share::getStock)
              .distinct()
              .collect(Collectors.toList());

      Collections.shuffle(stocks);
      stocks.stream()
          .limit(5)
          .forEach(
              stock -> {
                newsBox.getChildren().add(createNewsArticleCard(stock, newsBox));
              });

      return newsBox;
    }

    /** Creates an individual News Article card */
    private VBox createNewsArticleCard(Stock stock, VBox parent) {
      NewsArticle article = stock.getNewsArticle();

      Label headline = new Label(article.getHeadline());
      headline.getStyleClass().add("text-medium-bold");
      headline.setStyle("-fx-text-fill: #70A1FF");

      Label content = new Label(article.getArticle());
      content.getStyleClass().add("text-medium-regular");
      content.setWrapText(true);
      content.prefWidthProperty().bind(parent.widthProperty());

      VBox articleBox = new VBox(5, headline, content);
      articleBox.getStyleClass().add("dashboard-secondary-pane");
      return articleBox;
    }

    /** Creates the Recent Executions column */
    private VBox createHistorySection(Session session) {
      VBox historyBox = new VBox(10);
      historyBox.setFillWidth(true);
      historyBox.getStyleClass().add("dashboard-secondary-pane");

      Label title = new Label("Recent Executions");
      title.getStyleClass().add("text-medium-bold");
      historyBox.getChildren().add(title);

      List<Transaction> transactions =
          session.getPlayer().getTransactionArchive().getLatestTransactions(6);

      for (Transaction transaction : transactions) {
        historyBox.getChildren().add(createTransactionRow(transaction));
      }

      return historyBox;
    }

    /** Creates an individual Transaction row */
    private HBox createTransactionRow(Transaction transaction) {
      HBox row = new HBox(10);


      boolean isPurchase = transaction instanceof Purchase;
      String iconPath = isPurchase ? "/images/shoppingCart.png" : "/images/dollarSign.png";
      String iconStyle = isPurchase ? "icon-box-green" : "icon-box-red";

      ImageView imageView =
          new ImageView(
              new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath))));
      imageView.getStyleClass().add(iconStyle);
      imageView.setFitHeight(30);
      imageView.setFitWidth(30);


      String action =
          (isPurchase ? "BUY " : "SELL ") + transaction.getShare().getStock().getSymbol();
      Label actionLabel = new Label(action);
      actionLabel.getStyleClass().add("text-medium-bold");

      String priceText =
          transaction
              .getShare()
              .getPurchasePrice()
              .multiply(transaction.getShare().getQuantity())
              .setScale(2, RoundingMode.HALF_UP)
              .toString();
      Label priceLabel = new Label(priceText);
      priceLabel.getStyleClass().add("text-medium-bold");
      actionLabel.setAlignment(Pos.CENTER_LEFT);
      priceLabel.setAlignment(Pos.CENTER_RIGHT);

      setupHGrow(actionLabel, priceLabel);
      HBox executionInfo = new HBox(10, actionLabel, priceLabel);
      HBox.setHgrow(executionInfo, Priority.ALWAYS);


      Label sharesLabel = new Label(
              transaction.getShare().getQuantity().setScale(2, RoundingMode.HALF_UP)
                  + " SHARES");
      Label executedLabel = new Label("EXECUTED");

      sharesLabel.getStyleClass().add("text-medium-regular");
      executedLabel.getStyleClass().add("text-medium-regular");
      sharesLabel.setAlignment(Pos.CENTER_LEFT);
      executedLabel.setAlignment(Pos.CENTER_RIGHT);

      sharesLabel.setStyle("-fx-text-fill: #798091");
      executedLabel.setStyle("-fx-text-fill: #798091");

      setupHGrow(sharesLabel, executedLabel);
      HBox infoBox = new HBox(10, sharesLabel, executedLabel);
      HBox.setHgrow(infoBox, Priority.ALWAYS);

      VBox wrapper = new VBox(10, executionInfo, infoBox);
      wrapper.setFillWidth(true);

      HBox.setHgrow(wrapper, Priority.ALWAYS);
      row.getChildren().addAll(imageView, wrapper);
      return row;
    }
  }
}
