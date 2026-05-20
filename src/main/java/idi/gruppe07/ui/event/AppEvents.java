package idi.gruppe07.ui.event;

import idi.gruppe07.entities.Stock;
import javafx.event.Event;
import javafx.event.EventType;

import java.math.BigDecimal;

public class AppEvents extends Event {

  public AppEvents(EventType<? extends Event> eventType) {
    super(eventType);
  }

  public static final EventType<Event> ROOT = new EventType<>(Event.ANY, "ROOT");

  public static final EventType<Event> SCENE_BACK = new EventType<>(ROOT, "SCENE_BACK");

}
