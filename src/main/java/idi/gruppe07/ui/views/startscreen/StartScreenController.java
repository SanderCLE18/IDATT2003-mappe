package idi.gruppe07.ui.views.startscreen;

import idi.gruppe07.player.Player;
import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.EventType;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewController;
import idi.gruppe07.ui.views.ViewManager;
import idi.gruppe07.ui.views.ViewElement;
import idi.gruppe07.ui.views.ViewData;
import idi.gruppe07.ui.views.startscreen.panes.LoadGamePane;
import idi.gruppe07.utils.StockDataFileReader;
import idi.gruppe07.utils.StockFileChooser;
import idi.gruppe07.utils.Validate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Controller for {@link StartScreenView}.
 *
 * <p>Wires up the three menu buttons and fires {@code SCENE_CHANGE}
 * events via the inherited {@link #invoke} method so that
 * {@link ViewManager} can navigate to the correct scene.</p>
 *
 * @see ViewController
 * @see StartScreenView
 */
public class StartScreenController extends ViewController<StartScreenView> {


  /**
   * Constructs a {@code StartScreenController} without a session.
   *
   * @param view         the {@link StartScreenView} this controller manages.
   * @param eventManager the application-wide {@link EventManager}.
   */
  public StartScreenController(final StartScreenView view,
                               final EventManager eventManager) {
    super(view, eventManager);
  }

  /**
   * Constructs a {@code StartScreenController} with a session.
   *
   * @param view         the {@link StartScreenView} this controller manages.
   * @param eventManager the application-wide {@link EventManager}.
   * @param session      the active {@link Session}.
   */
  public StartScreenController(final StartScreenView view,
                               final EventManager eventManager,
                               final Session session) {
    super(view, eventManager, session);
  }


  /**
   * Registers action handlers on all three menu buttons.
   *
   * <p>Each button fires a {@code SCENE_CHANGE} event carrying
   * a {@link ViewData} object that identifies the target scene.</p>
   */
  @Override
  protected void initInteractions() {
    getViewElement().getNewGameButton().setOnAction(e ->
        getViewElement().getNewGamePane().show()
    );
    getViewElement().getNewGamePane().getCustomGameButton().setOnAction(e -> {
      Stage stage = (Stage) getViewElement().getNewGamePane().getCustomGameButton().getScene().getWindow();
      String path = StockFileChooser.getStringFromFile(stage);

      StockDataFileReader reader = new StockDataFileReader();
      if(path != null){
        getSession().setSavefile(path);
        try {
          getSession().setStocks(reader.readStockData(path));
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }

      }
      navigateTo("dashboardGameScreen");

    });
    getViewElement().getNewGamePane().getCancelButton().setOnAction(e ->
        getViewElement().getNewGamePane().hide()
    );
    getViewElement().getNewGamePane().getStartButton().setOnAction(e -> {
      String startingMoney = getViewElement().getNewGamePane().getStartingCash();
      BigDecimal cash;

      if(startingMoney.isEmpty() || startingMoney.equals("0")){
        startingMoney = "10000";
      }

      try{
        Validate.that(startingMoney).isValidNumber();
        cash = new BigDecimal(startingMoney);
      }catch (IllegalArgumentException ex){
        getViewElement().getNewGamePane().getStartingCashInput().setPromptText("Invalid starting amount! ");
        getViewElement().getNewGamePane().getStartingCashInput().setStyle("-fx-border-color: red;");
        getViewElement().getNewGamePane().getStartingCashInput().requestFocus();
        return;
      }

      try{
        Validate.that(getViewElement().getNewGamePane().getName()).isNotNullOrEmpty();
      }
      catch (IllegalArgumentException ex){
        getViewElement().getNewGamePane().getNameInput().setPromptText("Invalid name! ");
        getViewElement().getNewGamePane().getNameInput().setStyle("-fx-border-color: red;");
        getViewElement().getNewGamePane().getNameInput().requestFocus();
        return;
      }
      Player player = new Player(getViewElement().getNewGamePane().getName(), cash);
      getSession().setPlayer(player);
      getViewElement().getNewGamePane().hide();
      navigateTo("dashboardGameScreen");  // Må erstattes med  faktisk scene
    });
    getViewElement().getLoadGameButton().setOnAction(e -> {
      getViewElement().getLoadGamePane().show();
    });
    getViewElement().getLoadGamePane().getCancelButton().setOnAction(e -> {
      getViewElement().getLoadGamePane().hide();
    });
    getViewElement().getLoadGamePane().getLoadButton().setOnAction(e -> {

      LoadGamePane pane = getViewElement().getLoadGamePane();
      if(pane.getSelectedButton() == null){
        return;
      }

      getSession().setSavefile(getViewElement().getLoadGamePane().getSelectedSave());
      navigateTo("dashboardGameScreen");
      getViewElement().getLoadGamePane().hide();

    });
    getViewElement().getMultiplayerButton().setOnAction(e -> {
      getViewElement().getOnlineGamePane().show();
    });

    getViewElement().getOnlineGamePane().getCancelButton().setOnAction(e -> {
      getViewElement().getOnlineGamePane().hide();
    });
    getViewElement().getOnlineGamePane().getConnectButton().setOnAction(e -> {
      if (getViewElement().getOnlineGamePane().getSelectedButton() == null) {
        return;
      }

      getViewElement().getOnlineGamePane().hide();
      navigateTo("dashboardGameScreen");
    });

    getViewElement().getSettingsButton().setOnAction(e -> {

    });

    getViewElement().getExitButton().setOnAction(e -> System.exit(0));
  }

  /**
   * Fires a {@code SCENE_CHANGE} event for the given scene name.
   *
   * @param sceneName the identifier of the target {@link ViewElement}.
   */
  private void navigateTo(final String sceneName) {
    ViewData data = new ViewData(sceneName);
    invoke(
        new EventData<>(EventType.SCENE_CHANGE, data),
        getEventManager()
    );
  }
}