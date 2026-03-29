package idi.gruppe07.ui.launch;

import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.views.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public final class LaunchParams extends Application {
  @Override
  public void start(final Stage stage) throws Exception{
    final EventManager eventManager = new EventManager();
    final ViewManager viewManager = new ViewManager(stage, eventManager);

    // ADD VIEWS HERE

    stage.setTitle("Millions");
    stage.show();
  }
}
