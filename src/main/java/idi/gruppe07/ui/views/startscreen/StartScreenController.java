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
import idi.gruppe07.ui.views.startscreen.panes.NewGamePane;
import idi.gruppe07.utils.StockDataFileReader;
import idi.gruppe07.utils.StockFileChooser;
import idi.gruppe07.utils.Validate;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static idi.gruppe07.ui.views.dashboard.DashBoardView.DASHBOARD_VIEW;

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

  InputStream stockPath;


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
    var view = getViewElement();


    view.getNewGameButton().setOnAction(_ -> {
      view.getNewGamePane().show();
      setMenuButtonsDisabled(true);
    });

    view.getLoadGameButton().setOnAction(_ -> {
      view.getLoadGamePane().show();
      setMenuButtonsDisabled(true);
    });

    view.getMultiplayerButton().setOnAction(_ -> {
      view.getOnlineGamePane().show();
      setMenuButtonsDisabled(true);
    });

    view.getSettingsButton().setOnAction(_ -> { /*Something epic*/ });

    view.getExitButton().setOnAction(_ -> System.exit(0));

    // New Game Pane Handlers
    initNewGameHandlers();

    // Load Game Pane Handlers
    initLoadGameHandlers();

    // Online Game Pane Handlers
    initOnlineGameHandlers();
  }

  private void initNewGameHandlers() {
    var pane = getViewElement().getNewGamePane();

    pane.getCancelButton().setOnAction(_ -> {
      pane.hide();
      setMenuButtonsDisabled(false);
    });
    pane.getCustomGameButton().setOnAction(_ -> handleCustomFileSelection(pane));
    pane.getStartButton().setOnAction(_ -> handleStartGame(pane));
  }

  /**Handles custom file selection in the NewGamePane
   *
   * @param pane The NewGamePane object*/
  private void handleCustomFileSelection(NewGamePane pane) {
    try {
      Stage stage = (Stage) pane.getCustomGameButton().getScene().getWindow();
      InputStream path = StockFileChooser.getPathFromFile(stage);

      if (path != null) {
        this.stockPath = path;
        String name = "Custom Data";
        pane.getCustomGameButton().setText("Loaded: " + name);
      }
    } catch (IOException ex) {
      throw new RuntimeException("Failed to load custom stock file", ex);
    }
  }

  private void handleStartGame(NewGamePane pane) {
    try {
      BigDecimal cash = validateAndGetStartingCash(pane);
      String name = validateAndGetName(pane);

      getSession().setPlayer(new Player(name, cash));
      getSession().setSavefile(name);

      loadStockData();

      pane.hide();
      navigateTo(DASHBOARD_VIEW);
      getSession().simulate();
    } catch (IllegalArgumentException ex) {
      //Handled inside the method
    } catch (Exception ex) {
      pane.getCustomGameButton().setText("Error loading stock data!");
      pane.getCustomGameButton().setStyle("-fx-border-color: red;");
    }
  }

  private void loadStockData() throws Exception {
    StockDataFileReader reader = new StockDataFileReader();
    String exchangeName = (stockPath == null) ? "S&P 500" : "Custom Exchange";
    InputStream finalStream = (stockPath == null)
        ? getClass().getResourceAsStream("/sp500.csv")
        : this.stockPath;

    getSession().makeExchange(exchangeName, reader.readStockData(finalStream));
  }

  private BigDecimal validateAndGetStartingCash(NewGamePane pane) {
    String raw = pane.getStartingCash();
    String val = (raw.isEmpty() || raw.equals("0")) ? "10000" : raw;

    try {
      Validate.that(val).isValidNumber();
      return new BigDecimal(val);
    } catch (IllegalArgumentException ex) {
      markInvalid(pane.getStartingCashInput(), "Invalid starting amount!");
      throw ex;
    }
  }

  private String validateAndGetName(NewGamePane pane) {
    String name = pane.getName();
    try {
      Validate.that(name).isNotNullOrEmpty();
      return name;
    } catch (IllegalArgumentException ex) {
      markInvalid(pane.getNameInput(), "Invalid name!");
      throw ex;
    }
  }

  private void markInvalid(TextInputControl input, String message) {
    input.setPromptText(message);
    input.setStyle("-fx-border-color: red;");
    input.requestFocus();
  }

  private void initLoadGameHandlers() {
    var pane = getViewElement().getLoadGamePane();
    pane.getCancelButton().setOnAction(_ -> {
      pane.hide();
      setMenuButtonsDisabled(false);
    });
    pane.getLoadButton().setOnAction(_ -> {
      if (pane.getSelectedButton() != null) {
        getSession().setSavefile(pane.getSelectedSave());
        navigateTo(DASHBOARD_VIEW);
        pane.hide();
      }
    });
  }

  private void initOnlineGameHandlers() {
    var pane = getViewElement().getOnlineGamePane();
    pane.getCancelButton().setOnAction(_ -> {
      pane.hide();
      setMenuButtonsDisabled(false);
    });
    pane.getConnectButton().setOnAction(_ -> {
      if (pane.getSelectedButton() != null) {
        pane.hide();
        navigateTo("OnlineSessionScreen");
      }
    });
  }

  private void setMenuButtonsDisabled(boolean disabled) {
    var view = getViewElement();
    view.getNewGameButton().setDisable(disabled);
    view.getLoadGameButton().setDisable(disabled);
    view.getMultiplayerButton().setDisable(disabled);
    view.getSettingsButton().setDisable(disabled);
    view.getExitButton().setDisable(disabled);
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