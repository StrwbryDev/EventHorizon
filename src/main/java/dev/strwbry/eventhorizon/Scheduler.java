package dev.strwbry.eventhorizon;

import dev.strwbry.eventhorizon.utility.Config;
import dev.strwbry.eventhorizon.utility.MsgUtility;

/**
 * The Scheduler class manages the timing and execution of events in the EventHorizon plugin.
 * It handles starting, pausing, resuming, and ending game sessions, as well as managing the event frequency.
 */
public class Scheduler {
    /** The frequency at which events occur during the game session */
    private int eventFrequency;
    /** Reference to the main plugin instance */
    private final EventHorizon plugin;
    /** The active game timer that manages the countdown and event triggers */
    private GameTimer gameTimer;
    /** Flag indicating whether a game session has started */
    public boolean hasStarted = false;
    /** Flag indicating whether the current game session is paused */
    public boolean isPaused = false;
    /** Stores the remaining time when a session is paused, -1 when not paused */

    public int pausedTime = -1;

    /**
     * Constructor for the Scheduler class.
     */
    public Scheduler() {
        this.plugin = EventHorizon.getPlugin();
        this.eventFrequency = Config.getEventFrequency();
    }

    /**
     * Starts a new game timer with the specified duration.
     *
     * @param duration The duration of the game timer in seconds
     * @return true if the session was successfully started, false if a session is already running
     */
    public boolean start(int duration) {
        if (hasStarted && !isPaused) {
            return false;
        }
        gameTimer = new GameTimer(duration, eventFrequency);
        gameTimer.runTaskTimerAsynchronously(plugin, 0, 20);
        hasStarted = true;
        return true;
    }

    /**
     * Pauses the current game session.
     *
     * @return true if the session was successfully paused, false if no session is running or already paused
     */
    public boolean pause() {
        if (!hasStarted || isPaused) {
            return false;
        }
        pausedTime = gameTimer.endTimer();
        isPaused = true;
        return true;
    }

    /**
     * Resumes a paused game session.
     *
     * @return true if the session was successfully resumed, false if no session exists or couldn't be started
     */
    public boolean resume() {
        if (pausedTime == -1 || !hasStarted) {
            return false;
        }
        boolean hasStarted = this.start(pausedTime);
        if (hasStarted) {
            isPaused = false;
        }
        return hasStarted;
    }

    /**
     * Ends the current game session.
     *
     * @return true if the session was successfully ended, false if no session was running
     */
    public boolean end() {
        if (!hasStarted) {
            return false;
        }
        gameTimer.endTimer();
        hasStarted = false;
        isPaused = false;
        return true;
    }

    /**
     * Gets the remaining time in the current game session.
     *
     * @return the remaining time in seconds, or -1 if no session is active
     */
    public int getRemainingTime() {
        return gameTimer.getRemainingTime();
    }
    /**
     * Reloads the event frequency from the configuration.
     * This method updates the internal event frequency value by fetching
     * the latest value from the plugin's configuration.
     */
    public void reloadEventFrequency() {
        this.eventFrequency = Config.getEventFrequency();
    }
}