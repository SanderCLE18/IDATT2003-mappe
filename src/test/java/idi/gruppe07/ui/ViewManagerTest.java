package idi.gruppe07.ui;

import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewData;
import idi.gruppe07.ui.views.ViewElement;
import idi.gruppe07.ui.views.ViewManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ViewManagerTest {

  public static final String TEST_VIEW_NAME = "TestView";
  public static final String TEST_VIEW_NAME_2 = "TestView2";

  private class StubView extends ViewElement<Pane> {
    private final String name;

    StubView(String name) {
      super(null, name);
      this.name = name;
    }

    @Override public String getViewName() { return name; }
    @Override public Pane getRootPane() { return null; }

    @Override
    protected void initLayout() {}

    @Override
    protected void initStyling() {}

    @Override
    public void onActivate() {

    }

    @Override public void setData(ViewData data) {}
  }



  @Mock Stage stage;
  @Mock Scene scene;
  @Mock StubView testView, testView2;
  @Mock EventManager eventManager;
  @Mock Session session;

  ViewManager viewManager;



  @BeforeEach
  void setUp() {
    when(stage.getScene()).thenReturn(scene);

    testView = new StubView(TEST_VIEW_NAME);
    viewManager = new ViewManager(stage, eventManager, session);

    viewManager.addView(testView);
  }

  @Nested
  class SetScene {

    @Test
    public void testSetScene() {

      viewManager.setScene(testView);
      assertEquals(testView, viewManager.getCurrentView());
    }

    @Test
    public void testSetNewScene() {
      testView2 = new StubView(TEST_VIEW_NAME_2);
      viewManager.addView(testView2);
      viewManager.setScene(testView2);
      assertEquals(testView2, viewManager.getCurrentView());
    }

    @Test
    public void testSetSceneChanges() {
      testView2 = new StubView(TEST_VIEW_NAME_2);
      viewManager.addView(testView2);
      viewManager.setScene(testView2);
      viewManager.setScene(testView);
      assertEquals(testView, viewManager.getCurrentView());
    }

    @Test
    public void testSetSceneWorksOnRepeat() {
      viewManager.setScene(testView);
      viewManager.setScene(testView);
      assertEquals(testView, viewManager.getCurrentView());
    }

    @Test
    public void testViewNonNull() {
      viewManager.setScene(testView);
      assertNotNull(viewManager.getCurrentView());
    }

    @Test
    public void testThrowsOnNullPtrException() {

      StubView nullView = new StubView("nonAddedView");

      assertThrows(NullPointerException.class, () -> viewManager.setScene(nullView));
    }

  }


}
