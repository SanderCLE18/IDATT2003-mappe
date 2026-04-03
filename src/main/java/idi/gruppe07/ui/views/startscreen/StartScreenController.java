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
import idi.gruppe07.utils.Validate;

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

  // ── Scene-name constants ─────────────────────────────────────────────────

  /** Target scene navigated to when "New Game" is pressed. */
  public static final String NEW_GAME_SCENE   = "newGameScreen";

  /** Target scene navigated to when "Load Game" is pressed. */
  public static final String LOAD_GAME_SCENE  = "loadGameScreen";

  /** Target scene navigated to when "Multiplayer" is pressed. */
  public static final String MULTIPLAYER_SCENE = "multiplayerScreen";

  /** Target scene navigated to when "Settings" is pressed. */
  public static final String SETTINGS_SCENE   = "settingsScreen";

  // ── Constructors ─────────────────────────────────────────────────────────

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

  // ── ViewController contract ──────────────────────────────────────────────

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
    getViewElement().getNewGamePane().getCancelButton().setOnAction(e ->
        getViewElement().getNewGamePane().hide()
    );
    getViewElement().getNewGamePane().getStartButton().setOnAction(e -> {
      String startingMoney = getViewElement().getNewGamePane().getStartingCash();
      BigDecimal cash;
      try{
        Validate.that(startingMoney).isValidNumber();
        cash = new BigDecimal(startingMoney);
      }catch (IllegalArgumentException ex){
        getViewElement().getNewGamePane().getStartingCashInput().setText("Invalid starting amount! ");
        getViewElement().getNewGamePane().getStartingCashInput().setStyle("-fx-border-color: red;");
        getViewElement().getNewGamePane().getStartingCashInput().requestFocus();
        return;
      }
      try{
        Validate.that(getViewElement().getNewGamePane().getName()).isNotNullOrEmpty();
      }
      catch (IllegalArgumentException ex){
        getViewElement().getNewGamePane().getNameInput().setText("Invalid name! ");
        getViewElement().getNewGamePane().getNameInput().setStyle("-fx-border-color: red;");
        getViewElement().getNewGamePane().getNameInput().requestFocus();
        return;
      }
      Player player = new Player(getViewElement().getNewGamePane().getName(), cash);
      getSession().setPlayer(player);
      getViewElement().getNewGamePane().hide();
      navigateTo(NEW_GAME_SCENE);  // Må erstattes med  faktisk scene
    });
    getViewElement().getLoadGameButton().setOnAction(e ->
        navigateTo(LOAD_GAME_SCENE)
    );

    getViewElement().getMultiplayerButton().setOnAction(e ->
        navigateTo(MULTIPLAYER_SCENE)
    );


    getViewElement().getSettingsButton().setOnAction(e ->
        navigateTo(SETTINGS_SCENE)
    );
  }

  // ── Private helpers ──────────────────────────────────────────────────────

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