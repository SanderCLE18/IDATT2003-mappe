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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

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

  private MarketPane content;

  private final List<NavItem> views;
  public MarketView(Session session , List<NavItem> views) {
    super(new StackPane(), MARKET_VIEW, session,  false);
    this.views = views;
    initLayout();

  }

  @Override
  protected void initLayout() {
    this.content = new MarketPane();
    SideBarPane pane = new SideBarPane(this.views);
    VBox.setVgrow(pane, Priority.ALWAYS);
    sideBar = new SideBarView(getSession(), pane, content);
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
    this.content.update(getSession());

    if(advanceListener == null){
      advanceListener = () -> Platform.runLater(() -> this.content.update(getSession()));
    }

    getSession().getTimer().removeAdvanceListener(advanceListener);
    getSession().getTimer().addAdvanceListener(advanceListener);

  }

  public SideBarView getSideBar() {
    return sideBar;
  }

  private static class MarketPane extends VBox {
    //Scary
    Pagination pagination;
    private HBox customNavBar;

    private MarketPane() {
      super(5);
      setPadding(new Insets(24, 16, 24, 16));
      this.setStyle("-fx-background-color: #060E20;");
    }

    private void update(Session session) {
      getChildren().clear();
      HBox header = createHeader(session);
      List<Stock> allStocks = session.getExchange().getStockMap().values().stream().toList();
      int itemsPerPage = 10;
      int pageCount = (int) Math.ceil((double) allStocks.size() / itemsPerPage);


      pagination = new Pagination(pageCount, 0);
      pagination.getStyleClass().add("custom-pagination");
      VBox.setVgrow(pagination, Priority.ALWAYS);

      pagination.setPageFactory(pageIndex -> createPaginationBox(itemsPerPage, pageIndex, allStocks));

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

    private VBox createPaginationBox(int itemsPerPage, int pageIndex, List<Stock> allStocks) {
      VBox pageBox = new VBox(10);
      int from = pageIndex * itemsPerPage;
      int to = Math.min(from + itemsPerPage, allStocks.size());
      for (int i = from; i < to; i++) {
        StockInfoBox box = new StockInfoBox(allStocks.get(i));

        box.prefHeightProperty().bind(
            pagination.heightProperty().divide(itemsPerPage).subtract(10)
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
      VBox headerText = new VBox(5,  title, sub);
      return new HBox(5, headerText);
    }


  }
}
