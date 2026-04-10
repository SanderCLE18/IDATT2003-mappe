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
import java.nio.file.Paths;

import static idi.gruppe07.ui.views.dashboard.DashBoardView.DASHBOARD_NAME;

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

  private String stockPath = "default";


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
    // New game button
    getViewElement().getNewGameButton().setOnAction(e ->
        getViewElement().getNewGamePane().show()
    );
    // Custom game button
    getViewElement().getNewGamePane().getCustomGameButton().setOnAction(e -> {
      Stage stage = (Stage) getViewElement().getNewGamePane().getCustomGameButton().getScene().getWindow();
      String path = StockFileChooser.getStringFromFile(stage);

      String name;
      StockDataFileReader reader = new StockDataFileReader();
      if(path != null){
        this.stockPath = path;
        name = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf("."));
        getViewElement().getNewGamePane().getCustomGameButton().setText("Loaded: " + name);
      }
    });
    // Cancel button in new game pane
    getViewElement().getNewGamePane().getCancelButton().setOnAction(e ->
        getViewElement().getNewGamePane().hide()
    );
    // Start button in new game pane
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
      getSession().setSavefile(getViewElement().getNewGamePane().getName());
      try{
        StockDataFileReader reader = new StockDataFileReader();
        if (stockPath.equals("default")) {
          String path = Paths.get("src/sp500.csv").toAbsolutePath().toString();
          getSession().makeExchange("S&P 500", reader.readStockData(path));
        }else{
          String name = stockPath.substring(stockPath.lastIndexOf("\\") + 1, stockPath.lastIndexOf("."));
          getSession().makeExchange(name, reader.readStockData(stockPath));
        }
      }catch (Exception ex) {
        getViewElement().getNewGamePane().getCustomGameButton().setText("Error loading stock data!");
        getViewElement().getNewGamePane().getCustomGameButton().setStyle("-fx-border-color: red;");
        return;
      }
      getViewElement().getNewGamePane().hide();
      navigateTo(DASHBOARD_NAME);
      IO.println("called navigate");
    });
    // Load game button
    getViewElement().getLoadGameButton().setOnAction(e -> {
      getViewElement().getLoadGamePane().show();
    });
    // Cancel button in load game pane
    getViewElement().getLoadGamePane().getCancelButton().setOnAction(e -> {
      getViewElement().getLoadGamePane().hide();
    });
    // Load button in load game pane
    getViewElement().getLoadGamePane().getLoadButton().setOnAction(e -> {

      LoadGamePane pane = getViewElement().getLoadGamePane();
      if(pane.getSelectedButton() == null){
        return;
      }

      getSession().setSavefile(getViewElement().getLoadGamePane().getSelectedSave());
      navigateTo(DASHBOARD_NAME);
      getViewElement().getLoadGamePane().hide();

    });
    // Multiplayer button
    getViewElement().getMultiplayerButton().setOnAction(e -> {
      getViewElement().getOnlineGamePane().show();
    });

    // Cancel button in online game pane
    getViewElement().getOnlineGamePane().getCancelButton().setOnAction(e -> {
      getViewElement().getOnlineGamePane().hide();
    });

    //Connect button in online game pane
    getViewElement().getOnlineGamePane().getConnectButton().setOnAction(e -> {
      if (getViewElement().getOnlineGamePane().getSelectedButton() == null) {
        return;
      }

      getViewElement().getOnlineGamePane().hide();
      navigateTo("OnlineSessionScreen");
    });

    getViewElement().getSettingsButton().setOnAction(e -> {

    });

    //Exit button
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