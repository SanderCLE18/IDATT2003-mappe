package idi.gruppe07.ui.views.dashboard;

import idi.gruppe07.entities.Share;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.util.List;

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
      setPadding(new Insets(6,4,6,4));
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

      getChildren().addAll(portfolioHbox, holdingsBox);
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

  }

}
