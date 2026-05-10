package idi.gruppe07.ui.views.purchaseview;

import idi.gruppe07.ui.custom.panes.NavItem;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewElement;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;



public class PurchaseView extends ViewElement<Pane> {
  private final List<NavItem> views;
  

  public static String PURCHASE_VIEW = "PurchaseView";

  public PurchaseView(Session session, List<NavItem> views) {
    this(PURCHASE_VIEW, session, views);
  }

  /**
   * Constructor without a session.
   *
   * @param viewName the name of the view.
   * @param views    the different views displayed in the sidebar.
   */
  protected PurchaseView(String viewName, List<NavItem> views) {
    super(new StackPane(), viewName, false);
    this.views = views;
    initLayout();
  }

  /**
   * Constructor with a session.
   *
   * @param viewName the name of the view.
   * @param session  the session of the application.
   * @param views    the different views displayed in the sidebar.
   */
  public PurchaseView(String viewName, Session session, List<NavItem> views) {
    super(new StackPane(), viewName, session, false);
    this.views = views;
    initLayout();

  }

  @Override
  protected void initLayout() {

  }

  @Override
  protected void initStyling() {

  }

  @Override
  public void onActivate() {

  }
}
