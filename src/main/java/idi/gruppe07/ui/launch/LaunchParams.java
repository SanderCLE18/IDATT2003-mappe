package idi.gruppe07.ui.launch;

import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewManager;
import idi.gruppe07.ui.views.dashboard.DashBoardController;
import idi.gruppe07.ui.views.dashboard.DashBoardView;
import idi.gruppe07.ui.views.startscreen.StartScreenController;
import idi.gruppe07.ui.views.startscreen.StartScreenView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    Session session = new Session();
    final ViewManager viewManager = new ViewManager(stage, eventManager, session);

    StartScreenView startScreenView = new StartScreenView(viewManager.getSession());
    StartScreenController startScreenController = new StartScreenController(startScreenView, eventManager, session);
    startScreenView.setController(startScreenController);

    // ADD VIEWS HERE

    List<NavItem> navItems = List.of(
        new NavItem("DASHBOARD", DashBoardView.DASHBOARD_VIEW),
        new NavItem("MARKETS", ""),
        new NavItem("PORTFOLIO",""),
        new NavItem("NEWS FEED","")

    );

    DashBoardView dashBoardView = new DashBoardView(session, navItems);
    DashBoardController dashBoardController = new DashBoardController(dashBoardView, eventManager, session);
    dashBoardView.setController(dashBoardController);

    viewManager.addView(startScreenView);
    viewManager.addView(dashBoardView);
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
    viewManager.setScene(startScreenView);
    stage.setTitle("Millions");
    stage.show();
  }
}
