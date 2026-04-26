package idi.gruppe07.ui.custom.panes;

import idi.gruppe07.ui.session.Session;
import idi.gruppe07.utils.Validate;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SideBarView extends BorderPane {
  SideBarPane leftPanel;
  TopBarBox topBarBox;
  VBox content;

  public SideBarView(Session session, SideBarPane leftPanel, VBox content) {
    Validate.that(leftPanel).isNotNull();
    this.topBarBox = new TopBarBox(session);
    this.leftPanel = leftPanel;
    this.leftPanel.prefWidthProperty().bind(this.widthProperty().multiply(0.2));

    this.content = content;
    content.prefWidthProperty().bind(this.widthProperty().multiply(0.8));
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
