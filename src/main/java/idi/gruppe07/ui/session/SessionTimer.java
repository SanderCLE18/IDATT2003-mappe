package idi.gruppe07.ui.session;

import idi.gruppe07.player.Player;
import idi.gruppe07.transactions.Exchange;
import idi.gruppe07.utils.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SessionTimer {

  public static final int ADVANCE_INTERVAL_SECONDS = 45;
  private static final int TICK_MS = 50;

  @FunctionalInterface
  public interface TickListener {
    void onTick(double progress);
  }

  private final List<TickListener> tickListeners = new ArrayList<>();

  private ScheduledExecutorService scheduler;
  private ScheduledFuture<?> advanceFuture;
  private ScheduledFuture<?> tickFuture;

  /** Elapsed milliseconds in the current 45-second window. */
  private volatile long elapsedMs = 0;

  /**Boolean for determining if the game is actively running */
  private boolean isRunning =  false;

  public void startTimer(Exchange exchange, Player player) {
    Validate.that(exchange).isNotNull();

    isRunning = true;

    elapsedMs = 0;
    scheduler = Executors.newScheduledThreadPool(2);

    long intervalMs = ADVANCE_INTERVAL_SECONDS * 1000L;

    advanceFuture = scheduler.scheduleAtFixedRate(() -> {
      exchange.advance();
      player.getPortfolio().createNetWorthSnapshot();
      elapsedMs = 0;
    }, intervalMs, intervalMs, TimeUnit.MILLISECONDS);

    tickFuture = scheduler.scheduleAtFixedRate(() -> {
      elapsedMs = Math.min(elapsedMs + TICK_MS, intervalMs);
      double progress = (double) elapsedMs / intervalMs;
      for (SessionTimer.TickListener listener : tickListeners) {
        listener.onTick(progress);
      }
    }, 0, TICK_MS, TimeUnit.MILLISECONDS);
  }

  public void pauseTimer(Exchange exchange) {
    Validate.that(exchange).isNotNull();
    if (!isRunning){
      return;
    }
    isRunning = false;

    if  (advanceFuture != null){
      advanceFuture.cancel(false);
    }
    if (tickFuture != null){
      tickFuture.cancel(false);
    }
    if (scheduler != null){
      scheduler.shutdownNow();
    }

  }


  public void stopTimer(){
    isRunning = false;

  }

  /** Adds tick listener*/
  public void addTickListener(TickListener listener) {
    tickListeners.add(listener);
  }

  /** Removes a tick listener. */
  public void removeTickListener(TickListener listener) {
    tickListeners.remove(listener);
  }


  public boolean isRunning(){
    return isRunning;
  }
}
