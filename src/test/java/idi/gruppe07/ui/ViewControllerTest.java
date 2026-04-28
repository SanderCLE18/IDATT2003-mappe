package idi.gruppe07.ui;

import idi.gruppe07.news.NewsService;
import idi.gruppe07.ui.event.EventData;
import idi.gruppe07.ui.event.EventManager;
import idi.gruppe07.ui.event.EventType;
import idi.gruppe07.ui.session.Session;
import idi.gruppe07.ui.views.ViewController;
import idi.gruppe07.ui.views.ViewData;
import idi.gruppe07.ui.views.ViewElement;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ViewControllerTest {

  @Mock EventManager eventManager;

  private Session session;
  private TestView testView;

  class TestView extends ViewElement<Pane> {
    private final String name;

    TestView(String name) {
      super(null, name);
      this.name = name;
    }

    @Override
    public String getViewName() {
      return name;
    }

    @Override
    public Pane getRootPane() {
      return null;
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

    @Override
    public void setData(ViewData data) {
    }

  }

  class TestViewController extends ViewController<TestView> {
    boolean initCalled;

    TestViewController(TestView view, EventManager eventManager) {
      super(view, eventManager);
    }

    TestViewController(TestView view, EventManager eventManager, Session session) {
      super(view, eventManager, session);
    }

    @Override
    protected void initInteractions() {
      initCalled = true;
    }
  }

  @BeforeEach
  void setUp() {

    NewsService newsService = mock(NewsService.class);
    session = new Session(newsService);
    eventManager = new EventManager();
    testView = new TestView("test");

  }

  @Nested
  class Constructor {

    @Test
    void testNullViewElemt() {

      assertThrows(Exception.class, () -> new TestViewController(null, eventManager));
    }

    @Test
    void testInitInteractionsCalled() {

      TestViewController controller = new TestViewController(testView, eventManager);
      assertTrue(controller.initCalled);
    }

    @Test
    void testInitInteractionsCalledWithSession() {

      TestViewController controller = new TestViewController(testView, eventManager, session);
      assertTrue(controller.initCalled);
      assertEquals(session, controller.getSession());
    }
  }

  @Nested
  class Getters {

    @Test
    public void testGetViewElement() {

      TestViewController controller = new TestViewController(testView, eventManager);
      assertEquals(testView, controller.getViewElement());
    }

    @Test
    public void testGetSession() {

      TestViewController controller = new TestViewController(testView, eventManager, session);
      assertEquals(session, controller.getSession());
    }

    @Test
    public void testGetNullSession() {

      TestViewController controller = new TestViewController(testView, eventManager);
      assertNull(controller.getSession());
    }

    @Test
    public void testGetRealSession() {

      TestViewController controller = new TestViewController(testView, eventManager, session);
      assertNotNull(controller.getSession());
      assertEquals(session, controller.getSession());
    }
  }

  @Nested
  class Invoke {

    @Test
    void testInvoke() {

      EventManager mockEventManager = mock(EventManager.class);

      TestViewController controller = new TestViewController(testView, mockEventManager);
      EventData<String> data = new EventData<>(EventType.SCENE_CHANGE, null);

      controller.invoke(data, mockEventManager);

      verify(mockEventManager).invokeEvent(data);

    }
  }
}
