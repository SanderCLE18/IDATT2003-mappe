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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public final class LaunchParams extends Application {
  @Override
  public void start(final Stage stage) throws Exception {
    final EventManager eventManager = new EventManager();
    Session session = new Session();
    final ViewManager viewManager = new ViewManager(stage, eventManager, session);

    // ADD VIEWS HERE
    StartScreenView startScreenView = new StartScreenView(viewManager.getSession());
    StartScreenController startScreenController = new StartScreenController(startScreenView, eventManager, session);
    startScreenView.setController(startScreenController);

    List<NavItem> navItems = List.of(
        new NavItem("Dashboard", DashBoardContentPane::new) // TODO:FIX THIS. UNDEFINED CLASS, SHOULD NOT BE DEFINED ETIHER. AS IT SHOULD POINT BACK TO THE DASHBOARDVIEW AND NOT A "NEW" CLASS
    );

    DashBoardView dashBoardView = new DashBoardView(session, navItems);
    DashBoardController dashBoardController = new DashBoardController(dashBoardView, eventManager, session);
    dashBoardView.setController(dashBoardController);

    viewManager.addView(startScreenView);
    viewManager.addView(dashBoardView);
    
    stage.setScene(
        new Scene(new StackPane(),720, 480)
    );

    stage.setMinWidth(720);
    stage.setMinHeight(480);
    viewManager.setScene(startScreenView);
    stage.setTitle("Millions");
    stage.show();
  }
}
