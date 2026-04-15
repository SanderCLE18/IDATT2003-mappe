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
import java.util.concurrent.atomic.AtomicLong;

public class SessionTimer {

  public static final int ADVANCE_INTERVAL_SECONDS = 45;
  private static final int TICK_MS = 50;

  private int speedMultiplier = 1;

  @FunctionalInterface
  public interface TickListener {
    void onTick(double progress);
  }

  @FunctionalInterface
  public interface AdvanceListener {
    void onAdvance();
  }

  private final List<TickListener> tickListeners = new ArrayList<>();
  private final List<AdvanceListener> advanceListeners = new ArrayList<>();


  private ScheduledExecutorService scheduler;
  private ScheduledFuture<?> advanceFuture;
  private ScheduledFuture<?> tickFuture;

  /** Elapsed milliseconds in the current 45-second window. */
  private final AtomicLong elapsedMs = new AtomicLong(0);

  /**Boolean for determining if the game is actively running */
  private boolean isRunning =  false;

  public void startTimer(Exchange exchange, Player player) {
    Validate.that(exchange).isNotNull();

    if (isRunning) {
      return;
    }

    isRunning = true;
    scheduler = Executors.newScheduledThreadPool(2, r -> {
      Thread t = new Thread(r);
      t.setDaemon(true);
      return t;
    });

    long intervalMs = (ADVANCE_INTERVAL_SECONDS * 1000L)/speedMultiplier;
    long initialDelay = Math.max(0, intervalMs - elapsedMs.get());

    advanceFuture = scheduler.scheduleAtFixedRate(() -> {
      exchange.advance();
      player.getPortfolio().createNetWorthSnapshot();
      elapsedMs.set(0);
      for (AdvanceListener listener : advanceListeners) {
        listener.onAdvance();
      }

    }, initialDelay, intervalMs, TimeUnit.MILLISECONDS);

    tickFuture = scheduler.scheduleAtFixedRate(() -> {
      elapsedMs.set(Math.min(elapsedMs.get() + TICK_MS, intervalMs));
      double progress = (double) elapsedMs.get() / intervalMs;
      for (SessionTimer.TickListener listener : tickListeners) {
        listener.onTick(progress);
      }
    }, 0, TICK_MS, TimeUnit.MILLISECONDS);
  }

  public void pauseTimer(Exchange exchange, Player player) {
    Validate.that(exchange).isNotNull();
    if (!isRunning){
      startTimer(exchange, player);
    } else {
      isRunning = false;

      if (advanceFuture != null) {
        advanceFuture.cancel(false);
      }
      if (tickFuture != null) {
        tickFuture.cancel(false);
      }
      if (scheduler != null) {
        scheduler.shutdownNow();
      }
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

  /** Adds advance listener*/
  public void addAdvanceListener(AdvanceListener listener) {
    advanceListeners.add(listener);
  }
  /** Removes an advance listener. */
  public void removeAdvanceListener(AdvanceListener listener) {
    advanceListeners.remove(listener);
  }

  /**Sets the speed multiplier.*/
  public void setSpeedMultiplier(int speedMultiplier, Exchange exchange, Player player) {
    long oldInterval = (ADVANCE_INTERVAL_SECONDS * 1000L) / this.speedMultiplier;
    double currentProgressPercent = (double) elapsedMs.get() / oldInterval;

    if (isRunning) {
      advanceFuture.cancel(false);
      tickFuture.cancel(false);
      scheduler.shutdownNow();
      isRunning = false;
      this.speedMultiplier = speedMultiplier;

      long newInterval = (ADVANCE_INTERVAL_SECONDS * 1000L) / speedMultiplier;
      this.elapsedMs.set((long) (currentProgressPercent * newInterval));

      startTimer(exchange, player);
    }
    else{
      this.speedMultiplier = speedMultiplier;
    }
  }


  /**Returns the boolean value of the gamestate.
   *
   * @return isRunning boolean value of whether the game is running.*/
  public boolean isRunning(){
    return isRunning;
  }
}
