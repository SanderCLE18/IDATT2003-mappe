package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.ui.custom.widgets.MenuButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SideBarPane extends VBox {
  private Button selected;

  private final List<Button> viewButtons = new ArrayList<>();
  private final List<NavItem>  navItems;
  private final Button tradeButton;

  public SideBarPane(List<NavItem> theViews) {
    super(4);
    this.navItems = theViews;
    //TODO: ProfileView
    getStyleClass().add("side-bar-pane");
    for(NavItem view : navItems){
      Button button = new MenuButton(view.label(), "side-bar-button");
      button.prefWidthProperty().bind(this.widthProperty().multiply(0.9));

      viewButtons.add(button);
      getChildren().add(button);
    }


    tradeButton = new MenuButton("New Trade", "sidebar-new-trade");
    tradeButton.prefWidthProperty().bind(this.widthProperty().multiply(0.9));
    getChildren().add(tradeButton);

  }

  public void setSelected(Button button){
    if(selected != null){
      selected.getStyleClass().remove("menu-button-primary");
    }
    selected = button;
    selected.getStyleClass().add("menu-button-primary");
  }

  public void wireButtons(Consumer<NavItem> handler){
    for (int i = 0; i < viewButtons.size() - 1; i++) {
      Button button = viewButtons.get(i);
      NavItem item = navItems.get(i);
      button.setOnAction(_ -> {
        handler.accept(item);
      });
    }
  }

  public void setOnTradeAction(EventHandler<ActionEvent> handler){
    tradeButton.setOnAction(handler);
  }

  public Button getSelected(){
    return selected;
  }

  public List<Button> getViewButtons(){
    return viewButtons;
  }

  public Button getTradeButton(){
    return tradeButton;
  }


}
