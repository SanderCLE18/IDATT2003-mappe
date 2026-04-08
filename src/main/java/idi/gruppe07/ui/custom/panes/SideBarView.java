package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.utils.Validate;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class SideBarView extends BorderPane {
  SideBarPane leftPanel;
  Node content;

  public SideBarView(SideBarPane leftPanel, Node content) {
    Validate.that(leftPanel).isNotNull();
    this.leftPanel = leftPanel;
    this.content = content;

    initLayout();
  }
  public void initLayout(){
    setLeft(leftPanel);
    setCenter(content);
  }

  public SideBarPane getLeftPanel() {
    return leftPanel;
  }

  public Node getContent() {
    return content;
  }

}
