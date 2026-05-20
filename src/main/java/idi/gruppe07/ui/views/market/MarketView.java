package idi.gruppe07.ui.views.market;

import idi.gruppe07.entities.Stock;
import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.custom.panes.SideBarPane;
import idi.gruppe07.ui.custom.panes.SideBarView;
import idi.gruppe07.ui.custom.widgets.StockInfoBox;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.session.SessionTimer;
import idi.gruppe07.ui.views.SideBar;
import idi.gruppe07.ui.views.ViewElement;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

public class MarketView extends ViewElement<Pane> implements SideBar {

  /**
   * The view's name.*/
  public static String MARKET_VIEW = "MarketView";

  /**
   * Sidebar view.
   */
  private SideBarView sideBar;

  /**The session advanceListener*/
  private SessionTimer.AdvanceListener advanceListener;

  private MarketPane pane;

  private final Deque<MarketNode> navStack = new ArrayDeque<>();

  private VBox contentContainer;

  private MarketNode content;

  private final List<NavItem> views;
  public MarketView(Session session , List<NavItem> views) {
    super(new StackPane(), MARKET_VIEW, session,  false);
    pane = new MarketPane();

    this.contentContainer = new VBox();
    VBox.setVgrow(contentContainer, Priority.ALWAYS);


    this.views = views;
    initLayout();

  }

  @Override
  protected void initLayout() {
    this.contentContainer.getChildren().setAll(pane);
    VBox.setVgrow(pane, Priority.ALWAYS);

    SideBarPane sideBarPane = new SideBarPane(this.views);
    VBox.setVgrow(sideBarPane, Priority.ALWAYS);
    sideBar = new SideBarView(getSession(), sideBarPane, contentContainer);
    getRootPane().getChildren().addAll(sideBar);
  }

  @Override
  protected void initStyling() {

  }

  /**
   * @inheritDoc
   * */
  @Override
  public void onActivate() {
    navStack.clear();
    swapView(pane);

    if(advanceListener == null) {
      advanceListener = () -> Platform.runLater(() -> this.content.updateNode(getSession()));
    }

    getSession().getTimer().removeAdvanceListener(advanceListener);
    getSession().getTimer().addAdvanceListener(advanceListener);

  }

  public void swapView(MarketNode node){
    content = node;

    if (node instanceof Node newNode) {
      contentContainer.getChildren().setAll(newNode);
      VBox.setVgrow(newNode, Priority.ALWAYS);
      HBox.setHgrow(newNode, Priority.ALWAYS);
    }
    content.updateNode(getSession());
  }

  public SideBarView getSideBar() {
    return sideBar;
  }

  public MarketNode getContent() {
    return content;
  }

  public void pushView(MarketNode node) {
    navStack.push(content);   // save current before swapping
    swapView(node);
  }

  public void popView() {
    if (!navStack.isEmpty()) {
      swapView(navStack.pop());
    }
  }

  private static class MarketPane extends VBox implements MarketNode {

    private Pagination pagination;
    private HBox customNavBar;

    static final int ITEMS_PER_PAGE = 10;

    private MarketPane() {
      super(5);
      setPadding(new Insets(24, 16, 24, 16));
      this.setStyle("-fx-background-color: #060E20;");
    }

    public void updateNode(Session session) {
      getChildren().clear();
      HBox header = createHeader(session);
      List<Stock> allStocks = session.getExchange().getStockMap().values().stream().toList();

      int pageCount = (int) Math.ceil((double) allStocks.size() / ITEMS_PER_PAGE);

      pagination = new Pagination(pageCount, 0);

      VBox.setVgrow(pagination, Priority.ALWAYS);

      pagination.setPageFactory(pageIndex -> createPaginationBox(pageIndex, allStocks));

      customNavBar = new HBox(8);
      customNavBar.setAlignment(Pos.CENTER);
      updateNavBar(pageCount, 0);
      pagination.currentPageIndexProperty().addListener((_, _, newVal)
          -> updateNavBar(pageCount, newVal.intValue()));
      ScrollPane scrollPane = new ScrollPane(pagination);

      scrollPane.setFitToWidth(true);
      scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

      getChildren().addAll(header, scrollPane, customNavBar);
    }

    private void sortPagination(List<Stock> allStocks) {
      int pageCount = (int) Math.ceil((double) allStocks.size() / ITEMS_PER_PAGE);
      pagination.setPageCount(pageCount);

      pagination.setPageFactory(null);
      pagination.setPageFactory(index -> createPaginationBox(index, allStocks));
    }

    private VBox createPaginationBox(int pageIndex, List<Stock> allStocks) {
      VBox pageBox = new VBox(10);
      int from = pageIndex * MarketPane.ITEMS_PER_PAGE;
      int to = Math.min(from + MarketPane.ITEMS_PER_PAGE, allStocks.size());
      for (int i = from; i < to; i++) {
        StockInfoBox box = new StockInfoBox(allStocks.get(i));

        box.prefHeightProperty().bind(
            pagination.heightProperty().divide(MarketPane.ITEMS_PER_PAGE).subtract(10)
        );

        box.prefWidthProperty().bind(pageBox.widthProperty());
        pageBox.getChildren().add(box);
      }
      return pageBox;
    }

    private void updateNavBar(int totalPages, int current) {
      customNavBar.getChildren().clear();
      if (totalPages <= 1) return;

      addPageButton(0, current);

      int start = Math.max(1, current - 1);
      int end = Math.min(totalPages - 2, current + 1);

      if (start > 1) {
        Label dots = new Label("...");
        dots.getStyleClass().add("text-medium-bold");
        customNavBar.getChildren().add(dots);
      }

      for (int i = start; i <= end; i++) {
        addPageButton(i, current);
      }

      if (end < totalPages - 2) {
        Label dots = new Label("...");
        dots.getStyleClass().add("text-medium-bold");
        customNavBar.getChildren().add(dots);
      }

      addPageButton(totalPages - 1, current);
    }

    private void addPageButton(int index, int current) {
      Button btn = new Button(String.valueOf(index + 1));
      btn.getStyleClass().addAll("button-generic", "text-medium-regular");
      btn.getStyleClass().add(index == current ? "button-generic-selected" : "button-generic");
      btn.setOnAction(_ -> pagination.setCurrentPageIndex(index));
      customNavBar.getChildren().add(btn);
    }

    private HBox createHeader(Session session) {
      Label title = new  Label("MARKET_OVERVIEW");
      title.getStyleClass().add("text-large-bold");
      boolean bullish = session.getExchange().isBullish();
      Label status = new Label(bullish ? "BULLISH" : "BEARISH");
      status.getStyleClass().add("text-sub-medium");
      status.setStyle(bullish ? "-fx-text-fill: #00ff88" : "-fx-text-fill: #ff0000");

      Label subtitle = new  Label("REAL TIME GLOBAL EQUITIES • STATUS");
      subtitle.getStyleClass().add("text-sub-medium");

      HBox sub = new HBox(5, subtitle, status);
      Label sort = new Label("SORT: ");
      sort.getStyleClass().add("text-medium-bold");
      Button noSort = new Button ("X");
      Button sortDesc = new Button("↑");
      Button sortAsc = new Button("↓");

      noSort.getStyleClass().addAll("button-generic", "text-medium-bold");
      sortDesc.getStyleClass().addAll("button-generic", "text-medium-bold");
      sortAsc.getStyleClass().addAll("button-generic", "text-medium-bold");
      HBox sortButtons = new HBox(5, sort, noSort, sortDesc, sortAsc);

      sortDesc.setOnAction(_ -> {
        List<Stock> sortedStocks = session.getExchange().getStockMap().values().stream()
            .sorted(Comparator.comparingDouble((Stock stock) -> stock.getPercentageChange().doubleValue()).reversed())
            .toList();
        sortPagination(sortedStocks);
      });
      sortAsc.setOnAction(_ -> {
        List<Stock> sortedStocks = session.getExchange().getStockMap().values().stream()
            .sorted(Comparator.comparingDouble(stock -> stock.getPercentageChange().doubleValue()))
            .toList();
        sortPagination(sortedStocks);
      });
      noSort.setOnAction(_ -> sortPagination(session.getExchange().getStockMap().values().stream().toList()));

      VBox headerText = new VBox(5,  title, sub, sortButtons);
      return new HBox(5, headerText);
    }


  }
}
