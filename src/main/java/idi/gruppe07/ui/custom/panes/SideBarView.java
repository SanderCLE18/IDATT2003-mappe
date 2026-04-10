package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.ui.session.Session;
import idi.gruppe07.utils.Validate;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class SideBarView extends BorderPane {
  SideBarPane leftPanel;
  TopBarBox topBarBox;
  Node content;

  public SideBarView(Session session, SideBarPane leftPanel, Node content) {
    Validate.that(leftPanel).isNotNull();
    this.topBarBox = new TopBarBox(session);
    this.leftPanel = leftPanel;
    this.leftPanel.prefWidthProperty().bind(this.widthProperty().multiply(0.2)); // 20%

    this.content = content;

    initLayout();
  }
  public void initLayout(){
    setTop(topBarBox);
    setLeft(leftPanel);
    setCenter(content);
  }

  public SideBarPane getLeftPanel() {
    return leftPanel;
  }

  public Node getContent() {
    return content;
  }
  public TopBarBox getTopBarBox(){
    return this.topBarBox;
  }

}
