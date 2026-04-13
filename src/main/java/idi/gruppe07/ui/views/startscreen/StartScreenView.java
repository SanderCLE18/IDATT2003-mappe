package idi.gruppe07.ui.views.startscreen;

import idi.gruppe07.ui.custom.widgets.MenuButton;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewController;
import idi.gruppe07.ui.views.ViewElement;
import idi.gruppe07.ui.views.ViewManager;
import idi.gruppe07.ui.views.startscreen.panes.LoadGamePane;
import idi.gruppe07.ui.views.startscreen.panes.NewGamePane;
import idi.gruppe07.ui.views.startscreen.panes.OnlineGamePane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;


import java.util.Objects;

  /**
   * View class for the start screen.
   *
   * <p>Displays the main menu with navigation options for starting
   * a new game, loading an existing game, and accessing settings.</p>
   *
   * @see ViewElement
   * @see StartScreenController
   */
public class StartScreenView extends ViewElement<StackPane> {

  /** The scene name identifier used by {@link ViewManager}. */
  public static final String VIEW_NAME = "StartScreen";

  /**
   * The controller for this view.
   * */
  private ViewController controller;


  /**
   * Buttons on the main menu.*/
  private Button newGameButton, loadGameButton, multiplayerButton, settingsButton, exitButton;



  /**
   * Constructs a new {@code StartScreenView}.
   */
  public StartScreenView() {
    super(new StackPane(), VIEW_NAME);
  }

  /**
   * Constructs a new {@code StartScreenView} with a session.
   *
   * @param session the active session.*/
  public StartScreenView(Session session) {
    super(new StackPane(), VIEW_NAME, session);
  }


  /**The new game pane for the main menu.*/
  private NewGamePane newGamePane;
  /**The load game pane for the main menu.*/
  private LoadGamePane loadGamePane;
  /**The online game pane for the main menu.*/
  private OnlineGamePane onlineGamePane;


  /**
   * Initializes the layout of the view.*/
  @Override
  protected void initLayout() {
    getRootPane().getStyleClass().add("root-pane");



    Pane serverBar1 = new Pane();
    serverBar1.getStyleClass().add("server-bar");

    Pane serverBar2 = new Pane();
    serverBar2.getStyleClass().add("server-bar");

    Label boltLabel = new Label("⚡");
    boltLabel.getStyleClass().add("bolt-label");

    StackPane logoIcon = new StackPane(serverBar1, serverBar2, boltLabel);
    logoIcon.getStyleClass().add("logo-icon");



    Label titleLabel = new Label("MILLIONS");
    titleLabel.getStyleClass().add("title-label");



    newGameButton  =    new MenuButton("▶   NEW_GAME",  "menu-button-primary");
    loadGameButton =    new MenuButton("⊡   LOAD_GAME", "menu-button");
    multiplayerButton = new MenuButton("\uD83D\uDC65   MULTIPLAYER", "menu-button");
    settingsButton =    new MenuButton("≡   SETTINGS",  "menu-button");
    exitButton =        new MenuButton("∅   EXIT", "menu-button");

    newGameButton.prefWidthProperty().bind(getRootPane().widthProperty().multiply(0.4));
    loadGameButton.prefWidthProperty().bind(getRootPane().widthProperty().multiply(0.4));
    multiplayerButton.prefWidthProperty().bind(getRootPane().widthProperty().multiply(0.4));
    settingsButton.prefWidthProperty().bind(getRootPane().widthProperty().multiply(0.4));
    exitButton.prefWidthProperty().bind(getRootPane().widthProperty().multiply(0.4));

    VBox buttonBox = new VBox(12, newGameButton, loadGameButton, multiplayerButton, settingsButton, exitButton);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getStyleClass().add("button-box");



    VBox centerBox = new VBox(32, logoIcon, titleLabel, buttonBox);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.getStyleClass().add("center-box");



    Label secureLabel = new Label("◀◀  SECURE CHANNEL");
    secureLabel.getStyleClass().add("footer-label");

    Label versionLabel = new Label("≡  SIMULATION V.4.0.2");
    versionLabel.getStyleClass().add("footer-label");

    Region footerSpacer = new Region();
    HBox.setHgrow(footerSpacer, Priority.ALWAYS);

    HBox footer = new HBox(secureLabel, footerSpacer, versionLabel);
    footer.getStyleClass().add("footer-bar");
    StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);



    VBox rightWidget = buildSideWidget();
    StackPane.setAlignment(rightWidget, Pos.CENTER_RIGHT);

    VBox leftWidget = buildCornerDecoration();
    StackPane.setAlignment(leftWidget, Pos.TOP_LEFT);

    footer.setPickOnBounds(false);
    rightWidget.setPickOnBounds(false);
    leftWidget.setPickOnBounds(false);

    getRootPane().getChildren().addAll(leftWidget, rightWidget, footer, centerBox);

    newGamePane = new NewGamePane();
    loadGamePane = new LoadGamePane();
    onlineGamePane = new OnlineGamePane();

    getRootPane().getChildren().addAll(newGamePane, loadGamePane, onlineGamePane);

  }


  /**
   * Applies styling to the view.*/
  @Override
  protected void initStyling() {
    getRootPane().getStylesheets().add(
        Objects.requireNonNull(
            getClass().getResource("/stylesheet.css"),
            "stylesheet.css not found on classpath"
        ).toExternalForm()
    );
  }

  @Override
  public void onActivate() {
    
  }

  /**
   * @return the "New Game" button.
   */
  public Button getNewGameButton() {
    return newGameButton;
  }

  /**
   * @return the "Load Game" button.
   */
  public Button getLoadGameButton() {
    return loadGameButton;
  }
    /**
     * @return the "Multiplayer" button.
     */
  public Button getMultiplayerButton() {
      return multiplayerButton;
  }

  /**
   * @return the "Settings" button.
   */
  public Button getSettingsButton() {
    return settingsButton;
  }

  /**
   * @return the "Exit" button.
   */
  public Button getExitButton() {
    return exitButton;
  }

  /**
   * @param controller the controller to set.
   */
  public void setController(StartScreenController controller) {
    this.controller = controller;
  }

  /**
   * @return the controller.
   */
  public StartScreenController getController() {
    return (StartScreenController) controller;
  }

  /**@return the new game pane.*/
  public NewGamePane getNewGamePane() {
    return newGamePane;
  }

  /**@return the load game pane.*/
  public LoadGamePane getLoadGamePane() {
    return loadGamePane;
  }

  /**@return the online game pane.*/
  public OnlineGamePane getOnlineGamePane() {
    return onlineGamePane;
  }


  /** Small decorative widget. */
  private VBox buildSideWidget() {
    Label header = new Label("● LIVE FEED ACTIVE");
    header.getStyleClass().add("widget-header");

    Pane chartPane = new Pane();
    chartPane.getStyleClass().add("chart-pane");

    VBox widget = new VBox(8, header, chartPane);
    widget.getStyleClass().add("side-widget");
    return widget;
  }

  /** Top-left corner decoration lines. */
  private VBox buildCornerDecoration() {
    Pane line1 = new Pane();
    line1.getStyleClass().add("corner-line");

    Pane line2 = new Pane();
    line2.getStyleClass().addAll("corner-line", "corner-line-short");

    VBox corner = new VBox(6, line1, line2);
    corner.getStyleClass().add("corner-decoration");
    return corner;
  }


}

