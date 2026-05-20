package idi.gruppe07.ui.launch;

import idi.gruppe07.news.NewsService;
import idi.gruppe07.player.Player;
import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewManager;
import idi.gruppe07.ui.views.dashboard.DashBoardController;
import idi.gruppe07.ui.views.dashboard.DashBoardView;
import idi.gruppe07.ui.views.market.MarketController;
import idi.gruppe07.ui.views.market.MarketView;
import idi.gruppe07.ui.views.startscreen.StartScreenController;
import idi.gruppe07.ui.views.startscreen.StartScreenView;
import idi.gruppe07.utils.JsonParser;
import idi.gruppe07.utils.StockDataFileReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public final class LaunchParams extends Application {
  @Override
  public void start(final Stage stage) throws Exception {

    stage.getIcons().addAll(
        new Image("/icons/millionsIcon16.png"),
        new Image("/icons/millionsIcon32.png"),
        new Image("/icons/millionsIcon48.png"),
        new Image("/icons/millionsIcon128.png"),
        new Image("/icons/millionsIcon256.png")
    );

    final EventManager eventManager = new EventManager();
    NewsService newsService =
        new NewsService(
            new JsonParser(Objects.requireNonNull(
                LaunchParams.class.getResourceAsStream("/misc/news.json"))));
    Session session = new Session(newsService);
    final ViewManager viewManager = new ViewManager(stage, eventManager, session);

    StartScreenView startScreenView = new StartScreenView(session);
    StartScreenController startScreenController = new StartScreenController(startScreenView, eventManager, session);
    startScreenView.setController(startScreenController);

    // ADD VIEWS HERE

    List<NavItem> navItems = List.of(
        new NavItem("DASHBOARD", DashBoardView.DASHBOARD_VIEW),
        new NavItem("MARKETS", MarketView.MARKET_VIEW),
        new NavItem("PORTFOLIO",""),
        new NavItem("NEWS FEED","")

    );

    DashBoardView dashBoardView = new DashBoardView(session, navItems);
    DashBoardController dashBoardController = new DashBoardController(dashBoardView, eventManager, session);

    //DEBUG
    StockDataFileReader  stockDataFileReader = new StockDataFileReader();
    session.makeExchange("SP500", stockDataFileReader.readStockData(getClass().getResourceAsStream("/sp500.csv")));
    session.setPlayer(new Player("A", new BigDecimal("12000")));
    session.simulate();
    //END DEBUG

    MarketView marketView = new MarketView(session, navItems);
    MarketController marketController = new MarketController(marketView, eventManager, session);

    viewManager.addView(startScreenView);
    viewManager.addView(dashBoardView);
    viewManager.addView(marketView);
    Scene scene;
    stage.setScene(
        scene = new Scene(new StackPane(),1200, 750)
    );
    try{
      scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());

    }catch (Exception ex){
      IO.println("Failed to load stylesheet" + ex);
      exit(0);
    }
    String[] fontFiles = {
        "SpaceGrotesk-Regular.ttf",
        "SpaceGrotesk-Bold.ttf",
        "SpaceGrotesk-Medium.ttf",
    };

    for (String fileName : fontFiles) {
      Font.loadFont(getClass().getResourceAsStream("/fonts/" + fileName), 16);
    }

    stage.setMinWidth(1200);
    stage.setMinHeight(750);
    //Debug: Actual - setScene(startScreenView)
    viewManager.setScene(marketView);
    //Debug
    marketView.onActivate();

    stage.setTitle("Millions");
    stage.show();
  }
}
