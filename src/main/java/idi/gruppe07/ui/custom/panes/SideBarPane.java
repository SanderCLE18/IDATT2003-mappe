package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.ui.custom.widgets.MenuButton;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class SideBarPane extends VBox {
  private Button selected;

  private final List<Button> viewButtons = new ArrayList<>();
  private Button tradeButton;

  public SideBarPane(List<NavItem> theViews) {
    super(4);
    loadStylesheet();
    getStyleClass().add("side-bar-pane");

    //TODO: ProfileView

    for(NavItem view : theViews){
      Button button = new MenuButton(view.label());
      viewButtons.add(button);
      getChildren().add(button);
    }

    if(!theViews.isEmpty()){
      setSelected(viewButtons.getFirst());
    }

    tradeButton = new MenuButton("New Trade", "menu-button-primary");
    getChildren().add(tradeButton);

  }

  private void loadStylesheet(){
    try{
      this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());
    }catch (Exception ex){
      IO.println("Failed to load stylesheet" + ex);
    }
  }

  public void setSelected(Button button){
    if(selected != null){
      selected.getStyleClass().remove("menu-button-primary");
    }
    selected = button;
    selected.getStyleClass().add("menu-button-primary");
  }

  public Button getSelected(){
    return selected;
  }

  public List<Button> getViewButtons(){
    return viewButtons;
  }


}
